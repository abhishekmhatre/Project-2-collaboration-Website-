package com.niit.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
import com.niit.model.Notification;
@Repository
@Transactional
public class BlogPostDaoImpltn implements BlogPostDao {
	@Autowired
private SessionFactory sessionFactory;
	public void addBlogPost(BlogPost blogPost) {
		Session session=sessionFactory.getCurrentSession();
		session.save(blogPost);
	}
	public List<BlogPost> getBlogs(boolean approved) {//true or false
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPost where approved=?");
		query.setBoolean(0, approved);
		return query.list();
		//select * from blogpost_s180233 where approved=1; or approved=0;
		
	}
	public BlogPost getBlogById(int id) {
		Session session = sessionFactory.getCurrentSession();
		BlogPost blogPost = (BlogPost) session.get(BlogPost.class, id);
		return blogPost;
	}
	
	public void addBlogComment(BlogComment blogComment) {
		Session session=sessionFactory.getCurrentSession();
		session.save(blogComment);
		
	}
	public List<BlogComment> getAllBlogComments(int blogPostId) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogComment where blogPost.id=?"); //HQL query
		query.setInteger(0, blogPostId);
		List<BlogComment> blogComments=query.list();
		return blogComments;
	}
	
	public void blogApproved(int id) {
		Session session = sessionFactory.getCurrentSession();
		BlogPost blogPost = (BlogPost) session.get(BlogPost.class, id);//BlogPost is approved
		//Two queries ---1] update approval column from blogpost to true
		//				 2] insert new record in ntfctn
		blogPost.setApproved(true);
		session.update(blogPost);
		Notification notification=new Notification();
		notification.setBlogTitle(blogPost.getBlogTitle());
		notification.setEmail(blogPost.getPostedBy().getEmail());
		notification.setApprovalStatus("Approved");
		session.save(notification);
	}
	
	public void blogRejected(int id, String rejectionReason) {
		Session session = sessionFactory.getCurrentSession();
		BlogPost blogPost = (BlogPost) session.get(BlogPost.class, id);//BlogPost is approved
		//Two queries ---1] update approval column from blogpost to true
		//				 2] insert new record in ntfctn
		blogPost.setApproved(true);
		session.update(blogPost);
		Notification notification=new Notification();
		notification.setBlogTitle(blogPost.getBlogTitle());
		notification.setEmail(blogPost.getPostedBy().getEmail());
		notification.setApprovalStatus("Rejected");
		notification.setRejectionReason(rejectionReason);
		session.save(notification);
		session.delete(blogPost);
		
	}

}