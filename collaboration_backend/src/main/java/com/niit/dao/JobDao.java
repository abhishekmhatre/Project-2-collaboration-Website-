package com.niit.dao;

import java.util.List;

import com.niit.model.Job;

public interface JobDao {

	void addJob(Job job);
	List <Job> getAllJobs();// select * from job_s180233
	Job getJob(int id);
}
