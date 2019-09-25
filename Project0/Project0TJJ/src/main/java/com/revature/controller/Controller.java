package com.revature.controller;

import java.util.Scanner;

import com.revature.model.User;
import com.revature.model.UserList;
import com.revature.service.ListService;
import com.revature.service.UserService;

public class Controller {

	public void createUser() {
		boolean success = false; // used to loop until a successful user is created
		boolean valid = false;
		UserService userService = new UserService();
		Scanner scanLine = new Scanner(System.in);
		String username = "";
		String password = "";
		String tempName = "";
		String tempPassword = "";

		while (!success) {
			System.out.print("Create new username: ");
			tempName = scanLine.next();
			while (!valid) {
				if (tempName.length() > 3) {
					// correct
					username = tempName;
					valid = true;
				} else {
					System.out.print("Username must be 4 characters. Renter: ");
					tempName = scanLine.next();
				}
			}
			valid = false;
			System.out.print("Enter password: ");
			tempPassword = scanLine.next();
			while (!valid) {
				if (tempPassword.length() > 5) {
					// correct
					password = tempPassword;
					valid = true;
				} else {
					System.out.print("Username must be 6 characters. Renter: ");
					tempPassword = scanLine.next();// garbage
				}
			}

			// save the username and password as a new user object in database!
			success = userService.insertUser(username, password);
			tempName = ""; // clean up if failure
			username = "";
			tempPassword = "";
			password = "";
			
		}
//		scanLine.close();
	}
	
	public User logUserIn() {
		boolean success = false;
		Scanner scanLine = new Scanner(System.in);
		User returnedUser = null;
		UserService us = new UserService();
		String tempUsername = "";
		String tempPassword = "";
		
		while (!success) {
			System.out.print("Login - enter a username: ");
			tempUsername = scanLine.next();
			
			System.out.print("Enter password: ");
			tempPassword = scanLine.next();	
			returnedUser = us.getUserLogin(tempUsername, tempPassword);
			if(returnedUser != null) {
				success = true;
			} else {
				// fails
				tempUsername = "";
				tempPassword = "";
			}
		}
//		scanLine.close();
		return returnedUser; 
	}
	
	public void createNewList(User currentUser) {
		int createdListID = -1;
		boolean success = false;
		Scanner scanLine = new Scanner(System.in);
		ListService listService = new ListService();
		UserList createdList = null;
		String title = "";
		
		while (!success) {
			System.out.print("Enter new list title: ");
			title = scanLine.nextLine();
			
			createdList = new UserList(title, currentUser.getId());
			createdListID = listService.insertUserList(createdList);
			if(createdListID < 0) {
				System.out.println("Error creating new list..");
			} else {
				success = true;
			}
			
		}
	}
	

}
