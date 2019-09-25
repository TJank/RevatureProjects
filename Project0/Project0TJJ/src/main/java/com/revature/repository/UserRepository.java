package com.revature.repository;

import com.revature.model.User;

public interface UserRepository {
	
	// this app only supports creating a user and getting a user
	// NO editing of the username or password.
	boolean insertUser(String username, String password);
	User getUserByUsername(String username, String password);
	User logUserIn(String username, String password);
	
}
