package com.revature.repository;

import java.util.ArrayList;
import java.util.List;

import com.revature.model.User;
import com.revature.model.UserList;

public interface ListRepository {
	
	// used
	int insertUserList(UserList userList); // create
	void deleteUserList(UserList userList, User u); // delete
	List<UserList> getAllSharableLists(); // get all UserLists that are "sharable" 
	void inserSharedList(UserList sharedList, User currentUser); // insert the "shared" list that a different user wants
	void updateUserList(UserList selectedList);
	ArrayList<UserList> getAllUserListTitles(User u); // selects all list titles to show to user
	UserList getCompleteUserList(UserList userList, User u); // get UserList by title so user can edit it OR even a sharable list
	
}
