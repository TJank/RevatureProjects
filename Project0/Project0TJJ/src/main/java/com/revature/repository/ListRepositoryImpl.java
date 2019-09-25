package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;

import com.revature.model.ListItem;
import com.revature.model.User;
import com.revature.model.UserList;
import com.revature.service.ListItemService;
import com.revature.util.ConnectionClosers;
import com.revature.util.ConnectionFactory;

public class ListRepositoryImpl implements ListRepository {

	@Override
	public int insertUserList(UserList userList) {
		Connection conn = null;
		PreparedStatement stmt = null; // used to insert a list item into the list table
		PreparedStatement stmt2 = null; // used to insert the list id and user id
		ResultSet set = null;
		boolean success = false;
		int listId = -1;
		int tempUserId = userList.getUserId();

		try {
			conn = ConnectionFactory.getConnection();
			// insert the list into the list table
			stmt = conn.prepareStatement("insert into list values(default, ?, ?) returning id");
			stmt.setString(1, userList.getTitle());
			stmt.setBoolean(2, userList.isSharable());
			set = stmt.executeQuery();

			while (set.next()) {
				listId = set.getInt(1);
			}

			if (listId > -1) {
				// insert the user id and list id into the join table
				stmt2 = conn.prepareStatement("insert into user_account_list values( ?, ?)");
				stmt2.setInt(1, tempUserId);
				stmt2.setInt(2, listId);
				stmt2.execute();
			}

		} catch (PSQLException e) {
//			e.printStackTrace();
			System.out.println("Error inserting the list");
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("Error inserting the list");
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeStatement(stmt2);
			ConnectionClosers.closeResultSet(set);
		}
		
		return listId;
	}

	@Override
	public void deleteUserList(UserList userList, User u) {
		Connection conn = null;
		PreparedStatement stmt = null; // used to select the completed list with list items

		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("delete from list where id = ?;");
			stmt.setInt(1, userList.getId());
			stmt.execute();

		} catch (PSQLException e) {
			e.printStackTrace();
			System.out.println("Error deleting specific list");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error deleting specific list");
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);

		}
	}

	@Override
	public UserList getCompleteUserList(UserList userList, User u) {
		Connection conn = null;
		PreparedStatement stmt = null; // used to select the completed list with list items
		PreparedStatement stmt2 = null;
		ResultSet set = null;
		ResultSet set2 = null;
		boolean success = false;
		ListItem tempListItem = null;
		UserList tempList = null;
		UserList returnList = null; // completed list to be returned

		try {
			conn = ConnectionFactory.getConnection();

			stmt = conn.prepareStatement(
					"SELECT L.id, L.title, L.sharable FROM list as L INNER JOIN user_account_list as UL ON L.id = UL.list_id WHERE L.id = ? and UL.user_id = ?;");
			stmt.setInt(1, userList.getId());
			stmt.setInt(2, u.getId());
			set = stmt.executeQuery();

			while (set.next()) {
				tempList = new UserList(set.getInt(1), set.getString(2), set.getBoolean(3));
			}
			// get the list items to input into list
			stmt2 = conn.prepareStatement(
					"SELECT LI.id, LI.item_title FROM list as L INNER JOIN user_account_list as UL ON L.id = UL.list_id full join list_item as LI on L.id = LI.list_id WHERE L.id = ? and UL.user_id = ?;");
			stmt2.setInt(1, userList.getId());
			stmt2.setInt(2, u.getId());
			set2 = stmt2.executeQuery();

			while (set2.next()) {
				tempListItem = new ListItem(set2.getInt(1), set2.getString(2), tempList.getId());
				tempList.addListItem(tempListItem); // input into list
			}

		} catch (PSQLException e) {
			e.printStackTrace();
			System.out.println("Error retrieving specific list");
			success = false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error retrieving specific list");
			success = false;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);

			if (tempList != null) {
				success = true;
				returnList = tempList;
				returnList.setUserId(u.getId());
			}

		}

		return returnList;
	}

	@Override
	public ArrayList<UserList> getAllSharableLists() {
		Connection conn = null;
		PreparedStatement stmt = null; // used to select all lists that have the column sharable set to true
		ResultSet set = null;
		boolean success = false;
		UserList tempList = null;
		ArrayList<ListItem> tempListItems = null;
		ArrayList<UserList> returnLists = new ArrayList<UserList>();

		try {
			conn = ConnectionFactory.getConnection();
			// select ALL lists titles where the list sharable is true
			stmt = conn.prepareStatement(
					"SELECT L.id, L.title, L.sharable FROM list as L WHERE L.sharable = true order by L.title;");
			set = stmt.executeQuery();

			// while the set that returns the list records has a record
			while (set.next()) {
				tempList = new UserList(set.getInt(1), set.getString(2), set.getBoolean(3));
				returnLists.add(tempList);
			}

			tempListItems = getAllSharableListItems();

			for (UserList userList : returnLists) {
				for(ListItem listItem : tempListItems) {
					if(userList.getId() == listItem.getListId()) {
						userList.addListItem(listItem);
					}
				}
			}

		} catch (PSQLException e) {
			e.printStackTrace();
			System.out.println("Error retrieving all the lists");
			success = false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error retrieving all the lists");
			success = false;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		return returnLists;
	}
	
	
	// second method to return the sharable list items to be set for the sharable LISTS
	private ArrayList<ListItem> getAllSharableListItems() {
		Connection conn = null;
		PreparedStatement stmt = null; // used to select all list items
		ResultSet set = null;
		ListItem tempListItem = null;
		ArrayList<ListItem> returnListItems = new ArrayList<ListItem>();
		
		try {
			conn = ConnectionFactory.getConnection();
			// select ALL lists titles where the list sharable is true
			stmt = conn.prepareStatement(
					"select L.id, LI.id, LI.item_title FROM list as L INNER JOIN user_account_list as UL ON L.id = UL.list_id full join list_item as LI on L.id = LI.list_id WHERE L.sharable = true  order by L.title;");
			set = stmt.executeQuery();

			// while the set that returns the list records has a record
			while (set.next()) {
				tempListItem = new ListItem(set.getInt(2), set.getString(3), set.getInt(1));
				returnListItems.add(tempListItem);
			}

		} catch (PSQLException e) {
//			e.printStackTrace();
			System.out.println("Error retrieving all the list items");
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("Error retrieving all the list items");
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		return returnListItems;
	}

	@Override
	public ArrayList<UserList> getAllUserListTitles(User u) {
		Connection conn = null;
		PreparedStatement stmt = null; // used to select all lists from list table JOINING list items from list item
										// table where userid = current user id
		ResultSet set = null;
		boolean success = false;
		UserList tempList = null;
		ArrayList<UserList> returnLists = new ArrayList<UserList>();

		try {
			conn = ConnectionFactory.getConnection();
			// select ALL lists titles where user id = current user id
			stmt = conn.prepareStatement(
					"SELECT L.id, L.title, L.sharable FROM list as L INNER JOIN user_account_list as UL ON L.id = UL.list_id WHERE UL.user_id = ?;");
			stmt.setInt(1, u.getId());
			set = stmt.executeQuery();

			while (set.next()) {
				tempList = new UserList(set.getInt(1), set.getString(2), set.getBoolean(3));
				returnLists.add(tempList);
			}

		} catch (PSQLException e) {
			e.printStackTrace();
			System.out.println("Error retrieving all the lists");
			success = false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error retrieving all the lists");
			success = false;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
		}
		return returnLists;
	}

	@Override
	public void updateUserList(UserList selectedList) {
		Connection conn = null;
		PreparedStatement stmt = null; // updates the list item based off of the list id and user id
		boolean success = false;
		UserList tempList = null;
		ArrayList<UserList> returnLists = new ArrayList<UserList>();

		try {
			conn = ConnectionFactory.getConnection();
			// select ALL lists titles where user id = current user id
			stmt = conn.prepareStatement(
					"update list as L set sharable = ? from user_account_list as UL where L.id = ? and UL.list_id = ? and UL.user_id = ?;");
			stmt.setBoolean(1, selectedList.isSharable());
			stmt.setInt(2, selectedList.getId());
			stmt.setInt(3, selectedList.getId());
			stmt.setInt(4, selectedList.getUserId());
			stmt.execute();

		} catch (PSQLException e) {
			e.printStackTrace();
			System.out.println("Error retrieving all the lists");
			success = false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error retrieving all the lists");
			success = false;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
		}

	}

	public void inserSharedList(UserList sharedList, User currentUser) {
		boolean success = false;
		ListItemService listItemService = new ListItemService();
		UserList insertList = new UserList(sharedList.getTitle(), currentUser.getId());
		int newListId = -1;
		ListItem tempListItem = null;
		
		
		newListId = insertUserList(insertList);
		if(newListId > -1) {
			for(ListItem listItem : sharedList.getListItems()) {
				tempListItem = new ListItem(listItem.getItemName(), newListId);
				listItemService.insertNewListItem(tempListItem);
			}
		}
		
		
		
	}

}
