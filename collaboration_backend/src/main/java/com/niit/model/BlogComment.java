package com.niit.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="blogcomment_s180233")
public class BlogComment {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@ManyToOne
	private BlogPost blogPost;
	@ManyToOne
	private User CommentedBy;
	private Date commentedOn;
	private String commentTxt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BlogPost getBlogPost() {
		return blogPost;
	}
	public void setBlogPost(BlogPost blogPost) {
		this.blogPost = blogPost;
	}
	public User getCommentedBy() {
		return CommentedBy;
	}
	public void setCommentedBy(User commentedBy) {
		CommentedBy = commentedBy;
	}
	public Date getCommentedOn() {
		return commentedOn;
	}
	public void setCommentedOn(Date commentedOn) {
		this.commentedOn = commentedOn;
	}
	public String getCommentTxt() {
		return commentTxt;
	}
	public void setCommentTxt(String commentTxt) {
		this.commentTxt = commentTxt;
	}
}
