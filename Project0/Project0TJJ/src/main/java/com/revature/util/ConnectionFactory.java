package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	private static Connection conn;
	
	public static Connection getConnection() {
		
		try {
			/*
			 * You can include this if the application can't find the
			 * Driver class on the build path. This will tell it to 
			 * locate this class. 
			 */
			Class.forName("org.postgresql.Driver");

			/*
			 * Hard-coding your credentials is bad. Don't do this!
			 * We should instead obscure our credentials by using
			 * environment variables or a Properties file. 
			 */
			conn = DriverManager.getConnection(
					System.getenv("demodbURL"),
					System.getenv("demodbUserName"),
					System.getenv("demodbPassword")
					);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
