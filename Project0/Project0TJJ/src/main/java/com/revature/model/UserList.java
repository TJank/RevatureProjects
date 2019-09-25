package com.revature.model;

import java.util.ArrayList;
import java.util.List;

public class UserList {
	
	private int id;
	private String title;
	private ArrayList<ListItem> listItems = new ArrayList<ListItem>();
	private boolean sharable;
	private int userId;
	
	public UserList() {
		super();
	}

	public UserList(String title) {
		super();
		this.title = title;
		this.sharable = false;
	}
	
	public UserList(String title, int userId) {
		super();
		this.title = title;
		this.sharable = false;
		this.userId = userId;
	}
	
	public UserList(String title, boolean sharable) {
		super();
		this.title = title;
		this.sharable = sharable;
	}

	public UserList(int id, String title, boolean bool) {
		super();
		this.id = id;
		this.title = title;
		this.sharable = bool;
	}

	public UserList(String title, ArrayList<ListItem> listItems, boolean sharable, int userId) {
		super();
		this.title = title;
		this.listItems = listItems;
		this.sharable = sharable;
		this.userId = userId;
	}

	public void addListItem(ListItem listItem) {
//		ListItem tempItem = new ListItem(listItem.getId(), listItem.getItemName(), listItem.getListId());
		this.listItems.add(listItem);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<ListItem> getListItems() {
		return listItems;
	}

	public void setListItems(ArrayList<ListItem> listItems) {
		this.listItems = listItems;
	}

	public boolean isSharable() {
		return sharable;
	}

	public void setSharable(boolean sharable) {
		this.sharable = sharable;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((listItems == null) ? 0 : listItems.hashCode());
		result = prime * result + (sharable ? 1231 : 1237);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserList other = (UserList) obj;
		if (id != other.id)
			return false;
		if (listItems == null) {
			if (other.listItems != null)
				return false;
		} else if (!listItems.equals(other.listItems))
			return false;
		if (sharable != other.sharable)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String returnString = "UserList Title :" + this.title + "\n";
		returnString += "List items : \n";
		for(ListItem listItem : this.listItems) {
			returnString += listItem.getItemName() + "\n";
		}
		return  returnString;
				
	}

}
