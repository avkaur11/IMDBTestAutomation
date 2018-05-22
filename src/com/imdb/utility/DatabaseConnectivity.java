package com.imdb.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.testng.annotations.Test;

public class DatabaseConnectivity {
	private static Connection con = null;
	//private static Properties properties = null;
	//Creating database and establishing a connection with it.
	public Connection getConnection() throws ClassNotFoundException, SQLException
	{
		try{
		 Class.forName("org.sqlite.JDBC");
		 con = DriverManager.getConnection("jdbc:sqlite:testMovie.db");
		}
		catch(Exception e)
		{
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
		}
		//System.out.println("Opened database successfully");
		return con;
	}

}
