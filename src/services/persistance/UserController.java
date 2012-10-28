package services.persistance;

import java.sql.SQLException;

public class UserController {
	String userName, firstName, lastName, eMail, password;
	Boundary gui = new Boundary();
	SQL_Connect connect = new SQL_Connect();
	
	
	/*
	 * Creates a new user in the database
	 */
	public void createUser(){
		getUserInfo();
		try {
			connect.createUser(userName, firstName, lastName, eMail, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resetVar();
	}
	/*
	 * checks if the username is taken
	 */
	private boolean isNameAvailable(String userName){
		Object[][] nameAvailability;
		try {
			nameAvailability = connect.executeQuery("SELECT userName FROM users WHERE userName = " + userName);
			return nameAvailability == null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * gets user info from user and initializes the info
	 */
	private void getUserInfo(){
		do{
			userName = gui.getUserName();
			if (!isNameAvailable(userName))
				System.out.println("User name is not available, try another one");
		}while(!isNameAvailable(userName));
		firstName = gui.getFirstName();
		lastName = gui.getLastName();
		eMail = gui.getEMail();
		password = gui.getPassword();
	}
	/*
	 * resets variables to empty strings
	 */
	private void resetVar(){
		userName ="";
		firstName ="";
		lastName = "";
		eMail = "";
		password = "";
	}
}

