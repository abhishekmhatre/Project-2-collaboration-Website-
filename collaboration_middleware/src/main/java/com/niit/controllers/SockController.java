
package com.niit.controllers;



import java.util.ArrayList;

import java.util.List;


import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;

import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.messaging.simp.annotation.SendToUser;

//import org.springframework.messaging.simp.annotation.SubscribeEvent;

import org.springframework.messaging.simp.annotation.SubscribeMapping;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Controller;



import com.niit.dao.UserDao;
import com.niit.model.Chat;


@Controller
public class SockController {


	public SockController() {
		
	}

	private static final Log logger = LogFactory.getLog(SockController.class);

	//private final SimpMessagingTemplate messagingTemplate;

	private List<String> users = new ArrayList<String>();


	@Autowired

	SimpMessagingTemplate messagingTemplate;
	public SockController(SimpMessagingTemplate messagingTemplate) {

		this.messagingTemplate = messagingTemplate;

	}

	@SubscribeMapping("/join/{email}")

	public List<String> join(@DestinationVariable("email") String email) {
        

		 System.out.println("email in sockcontroller" + email);
		 
		 if(!users.contains(email)) {
				users.add(email);
			}


		System.out.println("====JOIN==== " + email);

		// notify all subscribers of new user

		messagingTemplate.convertAndSend("/topic/join", email);

		return users;

	}

	@MessageMapping(value = "/chat")

	public void chatReveived(Chat chat) {


		if ("all".equals(chat.getTo())) {

			System.out.println("IN CHAT REVEIVED " + chat.getMessage() + " " + chat.getFrom() + " to " + chat.getTo());

			messagingTemplate.convertAndSend("/queue/chats", chat);

		}

		else {

			System.out.println("CHAT TO " + chat.getTo() + " From " + chat.getFrom() + " Message " + chat.getMessage());

			messagingTemplate.convertAndSend("/queue/chats/" + chat.getTo(), chat);

			messagingTemplate.convertAndSend("/queue/chats/" + chat.getFrom(), chat);
			
		

		}

	}

}