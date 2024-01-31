package edu.ncsu.csc440.ps02.core;

import java.sql.*;

import edu.ncsu.csc440.ps02.connection.DatabaseHandler;
import edu.ncsu.csc440.ps02.models.Employee;

/**
 * Feel free to use this file to test your implementations.
 */
public class Demo {
	public static void main(String[] args) throws SQLException {
		Connection connection = DatabaseHandler.getInstance();
		Employee michaelMitchell = new Employee(connection, 6);
		// Should print out "EmployeeId: 6"
		System.out.println("EmployeeId: " + michaelMitchell.getEmployeeId());
		// Should print out "LastName: Mitchell"
		System.out.println("LastName: " + michaelMitchell.getLastName());
		// Should print out "FirstName: Michael"
		System.out.println("FirstName: " + michaelMitchell.getFirstName());
		// Should print out "Title: IT Manager"
		System.out.println("Title: " + michaelMitchell.getTitle());
		// Should print out "ReportsTo: 1"
		System.out.println("ReportsTo: " + michaelMitchell.getReportsTo());
		// Should print out "BirthDate: 1973-07-01 00:00:00"
		System.out.println("BirthDate: " + michaelMitchell.getBirthDate());
		// Should print out "HireDate: 2003-10-17 00:00:00"
		System.out.println("HireDate: " + michaelMitchell.getHireDate());
		// Should print out "Address: 5827 Bowness Road NW"
		System.out.println("Address: " + michaelMitchell.getAddress());
		// Should print out "City: Calgary"
		System.out.println("City: " + michaelMitchell.getCity());
		// Should print out "State: AB"
		System.out.println("State: " + michaelMitchell.getState());
		// Should print out "Country: Canada"
		System.out.println("Country: " + michaelMitchell.getCountry());
		// Should print out "PostalCode: T3B 0C5"
		System.out.println("PostalCode: " + michaelMitchell.getPostalCode());
		// Should print out "Phone: +1 (403) 246-9887"
		System.out.println("Phone: " + michaelMitchell.getPhone());
		// Should print out "Fax: +1 (403) 246-9899"
		System.out.println("Fax: " + michaelMitchell.getFax());
		// Should print out "Email: michael@chinookcorp.com"
		System.out.println("Email: " + michaelMitchell.getEmail());
		// Should print out "Password: bubbles"
		System.out.println("Password: " + michaelMitchell.getPassword());
		
		// Should update Michael's address in the database
		michaelMitchell.updateAddress("123 Not Real St");
		// Should print out "Address: 123 Not Real St"
		System.out.println("Address: " + michaelMitchell.getAddress());
		
		System.out.println("");
		try {
			michaelMitchell.updateReportsTo(-1); // Non-existent Employee
			System.out.println("This should have thrown an Exception");
		} catch (IllegalArgumentException iae) {
			System.out.println("EmployeeID does not exist in database");
		}
		
		String newLastName = "Jobs";
		String newFirstName = "Steve";
		String newTitle = "Custodian";
		int newReportsTo = 1;
		int newBirthYear = 1955;
		int newBirthMonth = 2;
		int newBirthDay = 24;
		int newBirthHour = 0;
		int newBirthMinute = 0;
		int newBirthSecond = 0;
		int newHireYear = 2023;
		int newHireMonth = 9;
		int newHireDay = 1;
		int newHireHour = 0;
		int newHireMinute = 0;
		int newHireSecond = 0;
		String newAddress = "123 Not Real St";
		String newCity = "Palo Alto";
		String newState = "California";
		String newCountry = "USA";
		String newPostalCode = "94304";
		String newPhone = "+1 (555) 555-5555";
		String newFax = "+1 (555) 555-5555";
		String newEmail = "stevejobs@chinookcorp.com";
		String newPassword = "BetterThanWindows";
		Employee newHire = new Employee(connection, newLastName, newFirstName, newTitle, newReportsTo,
				newBirthYear, newBirthMonth, newBirthDay, newBirthHour, newBirthMinute, newBirthSecond,
				newHireYear, newHireMonth, newHireDay, newHireHour, newHireMinute, newHireSecond,
				newAddress, newCity, newState, newCountry, newPostalCode, newPhone, newFax, newEmail,
				newPassword);
		newHire.updateReportsTo(null); // This should be allowed
		newHire.updateTitle("Chief Executive Officer");
		
		System.out.println("");
		// Should print out "EmployeeId: 9"
		System.out.println("EmployeeId: " + newHire.getEmployeeId());
		// Should print out "LastName: Jobs"
		System.out.println("LastName: " + newHire.getLastName());
		// Should print out "FirstName: Steve"
		System.out.println("FirstName: " + newHire.getFirstName());
		// Should print out "Title: Chief Executive Officer"
		System.out.println("Title: " + newHire.getTitle());
	}
}
