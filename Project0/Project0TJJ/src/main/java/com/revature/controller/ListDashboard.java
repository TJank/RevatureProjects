package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import com.revature.model.User;
import com.revature.model.UserList;
import com.revature.service.ListService;

public class ListDashboard {
	
	private ArrayList<UserList> currentUserLists;
	private User currentUser;
	private ListService listService;
	
	public ListDashboard(User currentUser) {
		this.currentUser = currentUser;
		this.listService = new ListService();
		setCurrentUserLists();
	}
	
	
	public void setCurrentUserLists() {
		this.currentUserLists = this.listService.getAllUserListTitles(this.currentUser);
	}


	public ArrayList<UserList> getCurrentUserLists() {
		return currentUserLists;
	}
}
