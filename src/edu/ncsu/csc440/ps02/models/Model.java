package edu.ncsu.csc440.ps02.models;

import java.sql.Connection;

/**
 * Represents an abstract class for Models to the database. You 
 * should be able to use the getConnection method to retrieve how
 * to connect to the database.
 * @author Adam Gaweda
 * DO NOT MODIFY
 */
public abstract class Model {
	Connection connection;
	
	public Model(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
}
