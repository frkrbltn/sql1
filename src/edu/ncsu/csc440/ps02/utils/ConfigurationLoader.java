package edu.ncsu.csc440.ps02.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads contents from a configuration file as a hash map. The
 * config file is structured with 1 environment variable per line.
 * For example, config/credentials.txt included in this project
 * is DB_USERNAME=ACCOUNT_NAME, where "DB_USERNAME" is the environment
 * variable and "ACCOUNT_NAME" is the value.
 * @param filename
 * @return the property/values from the filename parameter.
 * DO NOT MODIFY
 */
public class ConfigurationLoader {
	
	public static Properties loadConfiguration(String filename) {
		Properties properties = new Properties();
		
		try {
			FileInputStream propsInput = new FileInputStream(filename);
			properties.load(propsInput);
		} catch (FileNotFoundException fnf) {
			String errorMsg = "%s is not a valid filename\n";
			System.out.printf(errorMsg, filename);
			fnf.printStackTrace();
			System.exit(0);
		} catch (IOException ioe) {
			String errorMsg = "An error occurred when attempting to load %s\n";
			System.out.printf(errorMsg, filename);
			ioe.printStackTrace();
			System.exit(0);
		}
		
		return properties;
	}

}
