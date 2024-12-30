package com.app.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.PropertyResourceBundle;



public class DBConfig {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs,rs1;
	private static DBConfig db;
	
	private DBConfig() {
		try {
			File file = new File("");
			String path = file.getAbsolutePath()+ "\\src\\main\\resources\\DBConnection.properties";
			
			FileInputStream fis = new FileInputStream(path);
			Properties property = new Properties();
			property.load(fis);
			
			String url = property.getProperty("url");
			String username = property.getProperty("username");
			String password = property.getProperty("password");
				
			conn = DriverManager.getConnection(url,username,password);  
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	 public static DBConfig getInstance() {
	        if (db == null) {
	            db = new DBConfig();
	        }
	        return db;
	    }
	
	 public Connection getConnection()
	 {
		 return conn;
	 }
	 
	 public PreparedStatement getPreparedStatement()
	 {
		 return pstmt;
	 }
	 
	 public ResultSet getResultSet()
	 {
		 return rs;
	 }
	 
	 public ResultSet getOtherResultSet()
	 {
		 return rs1;
	 }
	 
//	 public static void main(String x[])
//	 {
//		 new DBConfig();
//	 }
}
