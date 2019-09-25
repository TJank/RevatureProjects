package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import com.revature.model.ListItem;
import com.revature.model.UserList;
import com.revature.util.ConnectionClosers;
import com.revature.util.ConnectionFactory;

public class ListItemRepoImpl implements ListItemRepo {

	@Override
	public boolean updateUserListItem(UserList list, ListItem newListItem) {
		Connection conn = null;
		PreparedStatement stmt = null; // used to update a list item into the list item table based off list  item id
		boolean success = false;
		try {
			conn = ConnectionFactory.getConnection();
			// insert the list into the list table
			stmt = conn.prepareStatement("update list_item set item_title = ?  where id = ? and list_id = ?;");
			stmt.setString(1, newListItem.getItemName());
			stmt.setInt(2, newListItem.getId());
			stmt.setInt(3, list.getId());
			success = stmt.execute();

		} catch (PSQLException e) {
//			e.printStackTrace();
			System.out.println("Error inserting the list item");
			success = false;
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("Error inserting the list item");
			success = false;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
		}
		return success;
	}

	@Override
	public void insertNewListItem(ListItem newListItem) {
		Connection conn = null;
		PreparedStatement stmt = null; // used to insert a new list item into the list item table
		ResultSet set = null;
		int currentListId = newListItem.getListId();
		
		try {
			conn = ConnectionFactory.getConnection();
			// insert the list into the list table
			stmt = conn.prepareStatement("insert into list_item values(default, ? , ?)");
			stmt.setString(1, newListItem.getItemName());
			stmt.setInt(2, currentListId);
			stmt.execute();
			

		} catch (PSQLException e) {
//			e.printStackTrace();
			System.out.println("Error inserting the list item");
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("Error inserting the list item");
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
		}
	}

}
