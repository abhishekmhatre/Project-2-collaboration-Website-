package com.niit.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.BlogPostDao;
import com.niit.dao.BlogPostLikesDao;
import com.niit.dao.UserDao;
import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.ErrorClazz;
import com.niit.model.User;

@Controller
public class BlogPostController {
	@Autowired
	private BlogPostDao blogPostDao;
	@Autowired
	private UserDao  userDao;
	@Autowired
	private BlogPostLikesDao blogPostLikesDao;
	
	@RequestMapping(value="/addblogpost",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogPost(@RequestBody BlogPost blogPost,HttpSession session){
		System.out.println("BlogPost" +blogPost);
		
		String email=(String)session.getAttribute("loginId");
		if(email==null){
			ErrorClazz error=new ErrorClazz(4,"Unauthrozied access.. Please login");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //2nd callback function
		}
		
		blogPost.setPostedOn(new Date());
		User postedBy=userDao.getUser(email);
		blogPost.setPostedBy(postedBy);
		try{
		blogPostDao.addBlogPost(blogPost);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);//1st callback function
		}catch(Exception e){
			ErrorClazz error=new ErrorClazz(7,"Unable to insert blogpost details");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);//2nd callback function
		}
	}
	//$http.get(BASE_URL + "/getblogs/true") -> list of blogs approved
	//$http.get(BASE_URL + "/getblogs/false") -> list of blogs waiting for approval
	@RequestMapping(value="/getblogs/{approved}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogs(@PathVariable boolean approved,HttpSession session){
		
		
		String email=(String)session.getAttribute("loginId");
		if(email==null){
			ErrorClazz error=new ErrorClazz(4,"Unauthrozied access.. Please login");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //2nd callback function
		}
		
		//List of blogs waiting for approval - only ADMIN can view - Authorized 
		if(!approved){
			User user=userDao.getUser(email);
			if(!user.getRole().equals("ADMIN")){ //NOT ADMIN
				ErrorClazz error=new ErrorClazz(4,"Access Denied..");
				return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
			}
		}
		List<BlogPost> blogs=blogPostDao.getBlogs(approved);//false[ADMIN] or true
		return new ResponseEntity<List<BlogPost>>(blogs,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getblog/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogPost(@PathVariable int id,HttpSession session){
		String email=(String)session.getAttribute("loginId");
		if(email==null){
			ErrorClazz error=new ErrorClazz(4,"Unauthrozied access.. Please login");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //2nd callback function
		}
		User user=userDao.getUser(email);
		BlogPost blogPost=blogPostDao.getBlogById(id);
		if(!blogPost.isApproved())
			if(!user.getRole().equals("ADMIN")){
				ErrorClazz error=new ErrorClazz(4,"Access Denied..");
				return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
			}
			
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/haspostliked/{id}",method=RequestMethod.GET)
	public ResponseEntity<?> hasUserLikedBlogPost(@PathVariable int id,HttpSession session){
		String email=(String)session.getAttribute("loginId");
		if(email==null){
			ErrorClazz error=new ErrorClazz(4,"Unauthrozied access.. Please login");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //2nd callback function
		}
		BlogPostLikes blogPostLikes=blogPostLikesDao.hasUserLikedPost(id,email);
		return new ResponseEntity<BlogPostLikes>(blogPostLikes,HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatelikes/{id}",method=RequestMethod.PUT)
	public ResponseEntity<?> updateLikes(@PathVariable int id,HttpSession session){
		String email=(String)session.getAttribute("loginId");
		if(email==null){
			ErrorClazz error=new ErrorClazz(4,"Unauthrozied access.. Please login");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //2nd callback function
		}
		BlogPost blogPost=blogPostLikesDao.updateLikes(id,email);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	
	@RequestMapping(value="/blogapproved/{id}",method=RequestMethod.PUT)
	public ResponseEntity<?> blogApproved(@PathVariable int id,HttpSession session){
		String email=(String)session.getAttribute("loginId");
		if(email==null){
			ErrorClazz error=new ErrorClazz(4,"Unauthrozied access.. Please login");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //2nd callback function
		}
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")){
			ErrorClazz error=new ErrorClazz(4,"Access Denied..");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		blogPostDao.blogApproved(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/blogrejected/{id}/{rejectionReason}",method=RequestMethod.PUT)
	public ResponseEntity<?> blogRejected(@PathVariable int id,@PathVariable String rejectionReason,HttpSession session){
		String email=(String)session.getAttribute("loginId");
		if(email==null){
			ErrorClazz error=new ErrorClazz(4,"Unauthrozied access.. Please login");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //2nd callback function
		}
		/*User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")){
			ErrorClazz error=new ErrorClazz(4,"Access Denied..");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); }*/
		
		blogPostDao.blogRejected(id,rejectionReason);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/addblogcomment",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
		String email=(String)session.getAttribute("loginId");
		if(email==null){
			ErrorClazz error=new ErrorClazz(4,"Unauthrozied access.. Please login");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //2nd callback function
		}
		try {
			blogComment.setCommentedOn(new Date());
			User CommentedBy=userDao.getUser(email);
			blogComment.setCommentedBy(CommentedBy);
			blogPostDao.addBlogComment(blogComment);
			return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
		}catch(Exception e) {
			ErrorClazz error=new ErrorClazz(5,"Unable to post blog comment.");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);//2nd callback function
		}
	}
	
	@RequestMapping(value="/getblogcomments/{blogPostId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogComments(@PathVariable int blogPostId,HttpSession session){
		String email=(String)session.getAttribute("loginId");
		if(email==null){
			ErrorClazz error=new ErrorClazz(4,"Unauthrozied access.. Please login");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED); //2nd callback function
		}
		List<BlogComment> blogComments=blogPostDao.getAllBlogComments(blogPostId);
		return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.OK);
	}
}
