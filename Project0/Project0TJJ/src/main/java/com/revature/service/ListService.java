package com.revature.service;

import java.util.ArrayList;

import com.revature.model.User;
import com.revature.model.UserList;
import com.revature.repository.ListRepositoryImpl;

public class ListService {
	
	// CREATE a new list item with all list fields and the user id 
	public int insertUserList(UserList userList) {
		return new ListRepositoryImpl().insertUserList(userList);
	}
	
	// READ all lists (TITLES) associated with a user ID 
	public ArrayList<UserList> getAllUserListTitles(User u) {
		return new ListRepositoryImpl().getAllUserListTitles(u);
	}
	
	// DELETE a list where the user is the owner, muse use list ID and user ID
	public void deleteUserList(UserList userList, User u) {
		new ListRepositoryImpl().deleteUserList(userList, u);
	}
	
	// gets a user list based off of a title string and the user ID so that the user can edit this list.
	public UserList getCompleteUserList(UserList userList, User u) {
		return new ListRepositoryImpl().getCompleteUserList(userList, u);
	}
	
	// gets all "sharable" lists to display to user to allow them to copy a list
	// is only based off of the "sharable" column within the database.
	public ArrayList<UserList> getAllSharableLists() {
		return new ListRepositoryImpl().getAllSharableLists();
	}

	// updates the user list -- mainly changes to to sharable or private
	public void updateUserList(UserList selectedList) {
		new ListRepositoryImpl().updateUserList(selectedList);
	}

	public void insertSharedList(UserList sharedList, User currentUser) {
		new ListRepositoryImpl().inserSharedList(sharedList, currentUser);
	}
	

}
