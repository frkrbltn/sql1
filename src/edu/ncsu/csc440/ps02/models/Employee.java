package edu.ncsu.csc440.ps02.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.ncsu.csc440.ps02.connection.DatabaseHandler;


/**
 * Represents an interface for interacting with records from the Employee
 * table in the Chinook database. 
 * @author Adam Gaweda
 *
 */
public class Employee extends Model {
	private int EmployeeId;
	private String LastName;
	private String FirstName;
	private String Title;
	private Integer ReportsTo;
	private String BirthDate; 
	private String HireDate; 
	private String Address;
	private String City;
	private String State;
	private String Country;
	private String PostalCode;
	private String Phone;
	private String Fax;
	private String Email;
	private String Password;
	
	/**
	 * Problem Set 02 - For this problem set, you are tasked with creating the
	 * logic for the methods below. There are two constructors - one assuming
	 * that the Employee record already exists inside the Employee table; the
	 * second should create a new EmployeeID. 
	 */
	
	/**
	 * Creates a new Employee object with the EmployeeID equal to the EmployeeID 
	 * parameter. Should throw an IllegalArgumentException if the EmployeeID passed
	 * does not appear in the database.
	 * @param connection - the DB connection
	 * @param EmployeeID
	 */
	public Employee(Connection connection, int EmployeeID) {
		super(connection);
		// Insert logic to retrieve the Employee from the database
		// with the EmployeeID parameter as its primary key
		String query = "SELECT * FROM Employee WHERE EmployeeId = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, EmployeeID);
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				this.EmployeeId = results.getInt("EmployeeId");
				this.LastName = results.getString("LastName");
				this.FirstName = results.getString("FirstName");
				this.Title = results.getString("Title");
				int reportsTo = results.getInt("ReportsTo");
				this.ReportsTo = results.wasNull() ? null : reportsTo;
				this.BirthDate = results.getString("BirthDate");
				this.HireDate = results.getString("HireDate");
				this.Address = results.getString("Address");
				this.City = results.getString("City");
				this.State = results.getString("State");
				this.Country = results.getString("Country");
				this.PostalCode = results.getString("PostalCode");
				this.Phone = results.getString("Phone");
				this.Fax = results.getString("Fax");
				this.Email = results.getString("Email");
				this.Password = results.getString("Password");
			} else {
				throw new IllegalArgumentException("EmployeeID does not exist in the database");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a new Employee record entry in the database. If you review the chinook.sql code,
	 * you will see the EmployeeID attribute is set to AUTO_INCREMENT, meaning that
	 * for this does not need to be passed. Instead, you will pass it the parameters
	 * listed.
	 */
	public Employee(Connection connection, String LastName, String FirstName, String Title, int ReportsTo, 
			int BirthYear, int BirthMonth, int BirthDay, int BirthHour, int BirthMinute, int BirthSecond,
			int HireYear, int HireMonth, int HireDay, int HireHour, int HireMinute, int HireSecond, 
			String Address, String City, String State, String Country, String PostalCode,
			String Phone, String Fax, String Email, String Password) {
		super(connection);
		// Insert logic to create a new Employee in the database based
		// on the passed parameters.
		try {
//			String birthDateString = BirthYear + "-" + BirthMonth + "-" + BirthDay + " " + BirthHour + ":" + BirthMinute + ":" + BirthSecond;
//			String hireDateString = HireYear + "-" + HireMonth + "-" + HireDay + " " + HireHour + ":" + HireMinute + ":" + HireSecond;
			
			String query = "INSERT INTO chinook.employee (LastName, FirstName, Title, ReportsTo, BirthDate, HireDate, Address, City, State, Country, PostalCode, Phone, Fax, Email, Password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = super.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			this.LastName = LastName;
			this.FirstName = FirstName;
			this.Title = Title;
			this.ReportsTo = ReportsTo;
			this.BirthDate = BirthYear + "-" + BirthMonth + "-" + BirthDay + " " + BirthHour + ":" + BirthMinute + ":" + BirthSecond;
			this.HireDate = HireYear + "-" + HireMonth + "-" + HireDay + " " + HireHour + ":" + HireMinute + ":" + HireSecond;
			this.Address = Address;
			this.City = City;
			this.State = State;
			this.Country = Country;
			this.PostalCode = PostalCode;
			this.Phone = Phone;
			this.Fax = Fax;
			this.Email = Email;
			this.Password = Password;

	
			statement.setString(1, LastName);
			statement.setString(2, FirstName);
			statement.setString(3, Title);
			statement.setInt(4, ReportsTo);
			statement.setString(5, BirthYear + "-" + BirthMonth + "-" + BirthDay + " " + BirthHour + ":" + BirthMinute + ":" + BirthSecond);
			statement.setString(6, HireYear + "-" + HireMonth + "-" + HireDay + " " + HireHour + ":" + HireMinute + ":" + HireSecond);
			statement.setString(7, Address);
			statement.setString(8, City);
			statement.setString(9, State);
			statement.setString(10, Country);
			statement.setString(11, PostalCode);
			statement.setString(12, Phone);
			statement.setString(13, Fax);
			statement.setString(14, Email);
			statement.setString(15, Password);
			

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Creating employee failed, no rows affected.");
			}
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					this.EmployeeId = generatedKeys.getInt(1);
				} else {
					throw new SQLException("Creating employee failed, no ID obtained.");
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Updates the Last Name of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 20
	 * characters.
	 * @param LastName
	 */
	public void updateLastName(String LastName) {
	    // Insert logic here to update the Employee's LastName in the database
		if (LastName == null || LastName.length() > 20) {
			throw new IllegalArgumentException("LastName is too long");
		}
		try {
			String query = "UPDATE chinook.employee SET LastName = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, LastName);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the First Name of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 20
	 * characters.
	 * @param FirstName
	 */
	public void updateFirstName(String FirstName) {
	    // Insert logic here to update the Employee's FirstName in the database
		if (FirstName == null || FirstName.length() > 20 ) {
			throw new IllegalArgumentException("FirstName is too long");
		}

		try {
			String query = "UPDATE chinook.employee SET FirstName = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, FirstName);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the Title of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 30
	 * characters.
	 * @param Title
	 */
	public void updateTitle(String Title) {
	    // Insert logic here to update the Employee's Title in the database
		if (Title == null || Title.length() > 50) {
			throw new IllegalArgumentException("Title is too long");
		}

		try {
			String query = "UPDATE chinook.employee SET Title = ? WHERE EmployeeId = ?";
	        PreparedStatement statement = super.connection.prepareStatement(query);
	        statement.setString(1, Title);
	        statement.setInt(2, this.EmployeeId);
	        statement.executeUpdate();

	        // Update the Title field of the Employee object:
	        this.Title = Title;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the ID of this Employee's supervisor. If the passed integer does not
	 * exist in the database, the method should throw an IllegalArguementException.
	 * Note, some Employees may not have a supervisor (ReportsTo can be set to null).
	 * This is why the parameter is the Integer Wrapper class instead of int.
	 * @param ReportsTo
	 */
	public void updateReportsTo(Integer ReportsTo) {
		this.ReportsTo = null;
		
		// Insert logic here to update the Employee's ReportsTo in the database
		// the updateReportsTo method should only allow updates to EmployeeIDs that are already in the database or null.
		if (ReportsTo == null) {
			try {
				String query = "UPDATE chinook.employee SET ReportsTo = NULL WHERE EmployeeId = ?";
				PreparedStatement statement = super.connection.prepareStatement(query);
				statement.setInt(1, this.EmployeeId);
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				// If needed, you can throw a custom exception here
			}
		} else {
			// Check if the ReportsTo exists in the database
			try {
				String checkQuery = "SELECT EmployeeId FROM chinook.employee WHERE EmployeeId = ?";
				PreparedStatement checkStatement = super.connection.prepareStatement(checkQuery);
				checkStatement.setInt(1, ReportsTo);
				ResultSet results = checkStatement.executeQuery();
		
				if (results.next()) {
					// If it exists, update the ReportsTo column
					String updateQuery = "UPDATE chinook.employee SET ReportsTo = ? WHERE EmployeeId = ?";
					PreparedStatement updateStatement = super.connection.prepareStatement(updateQuery);
					updateStatement.setInt(1, ReportsTo);
					updateStatement.setInt(2, this.EmployeeId);
					updateStatement.executeUpdate();
				} else {
					// If it doesn't exist, throw an exception
					throw new IllegalArgumentException("ReportsTo EmployeeID does not exist in the database");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// If needed, you can throw a custom exception here
			}
		}
	}

	/**
	 * Updates the Birth Date of the Employee in the database. Should throw an
	 * IllegalArgumentException if the Date represented by the parameters passed
	 * is later than the current day. Consider using the Date, LocalTime, or Calendar
	 * classes in Java for this task.
	 * @param BirthDate
	 */
	public void updateBirthDate(int year, int month, int day, int hour, int minute, int second) {
	    // Insert logic here to update the Employee's BirthDate in the database
		LocalDateTime birthDate = LocalDateTime.of(year, month, day, hour, minute, second);
		if (birthDate.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("BirthDate cannot be in the future");
		}
		String formattedDate = birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		try {
			String query = "UPDATE chinook.employee SET BirthDate = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, formattedDate);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the Hire Date of the Employee in the database. Should throw an
	 * IllegalArgumentException if the Date represented by the parameters passed
	 * is later than the current day. Consider using the Date, LocalTime, or Calendar
	 * classes in Java for this task.
	 * @param HireDate
	 */
	public void updateHireDate(int year, int month, int day, int hour, int minute, int second) {
	    // Insert logic here to update the Employee's HireDate in the database
		LocalDateTime hireDate = LocalDateTime.of(year, month, day, hour, minute, second);
		if (hireDate.isAfter(LocalDateTime.now())) {
			throw new IllegalArgumentException("HireDate cannot be in the future");
		}
		String formattedDate = hireDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		try {
			String query = "UPDATE chinook.employee SET HireDate = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, formattedDate);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the Address of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 70
	 * characters.
	 * @param Address
	 */
	public void updateAddress(String Address) {
	    // Insert logic here to update the Employee's Address in the database
		if (Address == null || Address.length() > 70) {
			throw new IllegalArgumentException("Address is too long");
		}
		try {
			String query = "UPDATE chinook.employee SET Address = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, Address);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the City of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 40
	 * characters.
	 * @param City
	 */
	public void updateCity(String City) {
	    // Insert logic here to update the Employee's City in the database
		if ( City == null || City.length() > 40) {
			throw new IllegalArgumentException("City is too long");
		}
		try {
			String query = "UPDATE chinook.employee SET City = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, City);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the State of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 40
	 * characters.
	 * @param State
	 */
	public void updateState(String State) {
	    // Insert logic here to update the Employee's State in the database
		if (State == null || State.length() > 40) {
			throw new IllegalArgumentException("State is too long");
		}
		try {
			String query = "UPDATE chinook.employee SET State = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, State);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the Country of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 40
	 * characters.
	 * @param Country
	 */
	public void updateCountry(String Country) {
	    // Insert logic here to update the Employee's Country in the database
		if (Country == null || Country.length() > 40) {
			throw new IllegalArgumentException("Country is too long");
		}
		try {
			String query = "UPDATE chinook.employee SET Country = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, Country);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the Postal Code of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 10
	 * characters. While the records in the database have a specific format, you
	 * do not need to worry about this.
	 * @param PostalCode
	 */
	public void updatePostalCode(String PostalCode) {
	    // Insert logic here to update the Employee's PostalCode in the database
		if (PostalCode == null || PostalCode.length() > 10) {
			throw new IllegalArgumentException("PostalCode is too long");
		}
		try {
			String query = "UPDATE chinook.employee SET PostalCode = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, PostalCode);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the Phone Number of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 24
	 * characters. While the records in the database have a specific format, you
	 * do not need to worry about this.
	 * @param Phone
	 */
	public void updatePhone(String Phone) {
	    // Insert logic here to update the Employee's Phone in the database
		if (Phone == null || Phone.length() > 24) {
			throw new IllegalArgumentException("Phone number is too long");
		}
		try {
			String query = "UPDATE chinook.employee SET Phone = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, Phone);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the Fax Number of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 24
	 * characters. While the records in the database have a specific format, you
	 * do not need to worry about this.
	 * @param Fax
	 */
	public void updateFax(String Fax) {
	    // Insert logic here to update the Employee's Fax in the database
		if (Fax == null || Fax.length() > 24) {
			throw new IllegalArgumentException("Fax number is too long");
		}
		try {
			String query = "UPDATE chinook.employee SET Fax = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, Fax);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the Email Address of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 60
	 * characters. In addition, the method should throw an IllegalArgumentException
	 * if the parameter does not end with '@chinookcorp.com'. You are free to implement
	 * this with regular expressions or endswith().
	 * @param Email
	 */
	public void updateEmail(String Email) {
	    // Insert logic here to update the Employee's Email in the database
		if ( Email == null || !Email.endsWith("@chinookcorp.com") || Email.length() > 60) {
			throw new IllegalArgumentException("Email is invalid");
		}
		try {
			String query = "UPDATE chinook.employee SET Email = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, Email);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the Password of the Employee in the database. Should throw an
	 * IllegalArgumentException if the length of the String is greater than 60
	 * characters. For the purpose of this activity, do not encrypt the 
	 * password. We will learn how to tackle this later.
	 * @param Password
	 */
	public void updatePassword(String Password) {
	    // Insert logic here to update the Employee's Password in the database
		if (Password == null || Password.length() > 60) {
			throw new IllegalArgumentException("Password is too long");
		}
		try {
			String query = "UPDATE chinook.employee SET Password = ? WHERE EmployeeId = ?";
			PreparedStatement statement = super.connection.prepareStatement(query);
			statement.setString(1, Password);
			statement.setInt(2, this.EmployeeId);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Traditional getters - DO NOT MODIFY */
	public int getEmployeeId() { return this.EmployeeId; }
	public String getLastName() { return this.LastName; }
	public String getFirstName() { return this.FirstName; }
	public String getTitle() { return this.Title; }
	public int getReportsTo() { return this.ReportsTo; }
	public String getBirthDate() { return this.BirthDate; } 
	public String getHireDate() { return this.HireDate; } 
	public String getAddress() { return this.Address; }
	public String getCity() { return this.City; }
	public String getState() { return this.State; }
	public String getCountry() { return this.Country; }
	public String getPostalCode() { return this.PostalCode; }
	public String getPhone() { return this.Phone; }
	public String getFax() { return this.Fax; }
	public String getEmail() { return this.Email; }
	public String getPassword() { return this.Password; }
}
