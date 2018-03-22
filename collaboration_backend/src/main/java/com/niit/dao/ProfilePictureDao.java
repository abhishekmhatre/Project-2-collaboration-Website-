package com.niit.dao;

import com.niit.model.ProfilePicture;

public interface ProfilePictureDao {
	void uploadProfilePicture(ProfilePicture profilePicture); // insert/update
	ProfilePicture getProfilePicture(String email); //select ..from where email=?;
	
}
