package com.revature.repository;

import com.revature.model.ListItem;
import com.revature.model.UserList;

public interface ListItemRepo {
	
	public boolean updateUserListItem(UserList list, ListItem newListItem);
	public void insertNewListItem(ListItem newListItem);
	
}
