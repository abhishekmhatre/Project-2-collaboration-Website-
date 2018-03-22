package com.niit.dao;

import java.util.List;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;
public interface BlogPostDao {

	void addBlogPost(BlogPost blogPost);
	List<BlogPost> getBlogs(boolean approved); //0 or 1
	BlogPost getBlogById(int id);
	
	void blogApproved(int id);
	void blogRejected(int id,String rejectionReason);
	
	void addBlogComment(BlogComment blogComment);
	List<BlogComment> getAllBlogComments(int blogPostId);
}
