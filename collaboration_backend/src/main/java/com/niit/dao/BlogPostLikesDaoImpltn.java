package com.niit.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.User;

@Repository
@Transactional
public class BlogPostLikesDaoImpltn implements BlogPostLikesDao {
	@Autowired
	public SessionFactory sessionFactory;

	public BlogPostLikes hasUserLikedPost(int postId, String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPostLikes where blogPost.id=? and user.email=?");
		query.setInteger(0,postId);
		query.setString(1,email);
		BlogPostLikes blogPostLikes=(BlogPostLikes) query.uniqueResult();
		return blogPostLikes; // 1 object or null---glyphicon blue color orrr null=glyphicon color black
	}

	//when user click glyphicon from frontend
	public BlogPost updateLikes(int postId, String email) {
		Session session=sessionFactory.getCurrentSession();
		//check the previous conditions
		BlogPostLikes blogPostLikes=hasUserLikedPost(postId,email);
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class,postId);
		//user has not yet liked the post
		if(blogPostLikes==null) {//previous condition-currently glyphicon color=black
			BlogPostLikes likes=new BlogPostLikes();
			User user =(User)session.get(User.class,email);
			likes.setBlogPost(blogPost);
			likes.setUser(user);
			session.save(likes); //insert into blogPostLikes
			blogPost.setLikes(blogPost.getLikes() + 1); //increment the likes
			session.update(blogPost); //update blogPost
		}
		else {//unlike
			session.delete(blogPostLikes);// delete from blogPostLikes where id=?
			blogPost.setLikes(blogPost.getLikes() - 1); //decrement likes
			session.update(blogPost);//update blogPost
		}
		return blogPost;
	}

}


