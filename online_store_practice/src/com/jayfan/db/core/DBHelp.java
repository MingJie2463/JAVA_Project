package com.jayfan.db.core;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHelp {
	static Properties infor = new Properties();
	static {
		InputStream in = DBHelp.class.getClassLoader().getResourceAsStream("config.properties");
		try {
			infor.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	Private static String url = "jdbc:mysql://localhost:3306/store?serverTimezone=UTC";
	private static String user = "root";
	private static String password = "Xsx7591532463";
	*/
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(infor.getProperty("driver"));
		Connection conn = DriverManager.getConnection(infor.getProperty("url"),infor);
		return conn;
		
	}
}
