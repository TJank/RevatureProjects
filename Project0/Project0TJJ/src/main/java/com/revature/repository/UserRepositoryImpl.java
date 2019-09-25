package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;
import org.postgresql.util.PSQLException;

import com.revature.model.User;
import com.revature.util.ConnectionClosers;
import com.revature.util.ConnectionFactory;

public class UserRepositoryImpl implements UserRepository {

	@SuppressWarnings("finally")
	@Override
	public boolean insertUser(String username, String password) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean failed = false;
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("insert into user_account values(default, ?, ?)");
			
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.execute();
		} catch (PSQLException e) {
			System.out.print("That username already exists. Starting over..\n");
			failed = true;
		}
		catch(SQLException e) {
			failed = true;
			System.out.print("ERROR saving user to database. Please Retry..\n");
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			if(failed) {
				return false;
			} else {
				return true; // means user creating was successful
			}
		}
		
	}

	@Override
	public User getUserByUsername(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User logUserIn(String username, String password) {
		User returnedUser = null;
		boolean failed = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		String databasePassword = "";
		
		try {
			conn = ConnectionFactory.getConnection();
			stmt = conn.prepareStatement("select * from user_account where username = ?");
			stmt.setString(1, username);
			set = stmt.executeQuery(); // this returns a result
			
			while(set.next()) {
				databasePassword = set.getString(3);
				if(password.equals(databasePassword)) {
					returnedUser = new User(
							set.getString(2),
							set.getString(3)
							);
					returnedUser.setId(set.getInt(1));
				}
				else {
					failed = true;
				}
			}			
			
		} catch (PSQLException e) {
			failed = true;
		}
		catch(SQLException e) {
			failed = true;
		} finally {
			ConnectionClosers.closeConnection(conn);
			ConnectionClosers.closeStatement(stmt);
			ConnectionClosers.closeResultSet(set);
			if(failed) {
				// some how exit application and re-ask for the user info
				System.out.println("Failed Logging in please retry..");
				returnedUser = null;
			}
		}
		return returnedUser;
	}
}

	