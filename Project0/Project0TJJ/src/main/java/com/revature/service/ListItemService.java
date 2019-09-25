package com.revature.service;

import com.revature.model.ListItem;
import com.revature.model.UserList;
import com.revature.repository.ListItemRepoImpl;

public class ListItemService {
	public boolean updateUserListItem(UserList list, ListItem newListItem) {
		return new ListItemRepoImpl().updateUserListItem(list, newListItem);
	}

	public void insertNewListItem(ListItem newListItem) {
		new ListItemRepoImpl().insertNewListItem(newListItem);
	}
}
