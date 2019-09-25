package com.revature.service;

import com.revature.model.User;
import com.revature.repository.UserRepositoryImpl;

public class UserService {
	
	public boolean insertUser(String username, String password) {
		return new UserRepositoryImpl().insertUser(username, password);
	}
	
	public User getUser(String username, String password) {
		return new UserRepositoryImpl().getUserByUsername(username, password);
	}
	
	public User getUserLogin(String username, String password) {
		return new UserRepositoryImpl().logUserIn(username, password);
	}

}
