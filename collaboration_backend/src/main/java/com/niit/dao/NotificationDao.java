package com.niit.dao;

import java.util.List;

import com.niit.model.Notification;

public interface NotificationDao {
	
	List <Notification> getAllNotification(String email); //login id of user
	void updateViewedStatus(int notificationId);
	Notification getNotification(int id);

}
