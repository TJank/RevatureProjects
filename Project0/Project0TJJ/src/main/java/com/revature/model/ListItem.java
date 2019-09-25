package com.revature.model;

public class ListItem {
	
	private int id;
	private String itemName;
	private int listId;
	
	public ListItem(int id, String itemName, int listId) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.listId = listId;
	}
	
	public ListItem(String itemName, int listId) {
		super();
		this.itemName = itemName;
		this.listId = listId;
	}

	public ListItem() {	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getListId() {
		return listId;
	}

	public void setListId(int listId) {
		this.listId = listId;
	}

	@Override
	public String toString() {
		return "ListItem [id=" + id + ", itemName=" + itemName + ", listId=" + listId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + listId;
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
		ListItem other = (ListItem) obj;
		if (id != other.id)
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (listId != other.listId)
			return false;
		return true;
	}
}
