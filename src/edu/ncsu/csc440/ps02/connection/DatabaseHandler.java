package edu.ncsu.csc440.ps02.connection;

import java.sql.*;
import java.util.Properties;

import edu.ncsu.csc440.ps02.utils.ConfigurationLoader;

public abstract class DatabaseHandler {
	static final String configFile = "config/credentials.txt";
	static final String DB_URL_TEMPLATE = "jdbc:%s://%s:%s/%s";
	private static Connection connection;
	
	public static Connection getInstance() throws SQLException {
		if (connection == null) {
			Properties properties = ConfigurationLoader.loadConfiguration(configFile);
			String DB_USER = properties.getProperty("DB_USER");
			String DB_PASS = properties.getProperty("DB_PASS");
			String DB_TYPE = properties.getProperty("DB_TYPE");
			String DB_HOST = properties.getProperty("DB_HOST");
			String DB_PORT = properties.getProperty("DB_PORT");
			String DB_NAME = properties.getProperty("DB_NAME");
			String DB_URL = String.format(DB_URL_TEMPLATE, DB_TYPE, DB_HOST, DB_PORT, DB_NAME);
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		}
		return connection;
	}
	
	public static void closeDatabaseConnection() throws SQLException {
		connection.close();
	}
}
