package services.persistance;

import java.sql.SQLException;

public class StartController {
	String userName, firstName, lastName, eMail, password;
	UserData user;
	Boundary bound = new Boundary();
	SQL_Connect connect = new SQL_Connect();
	
	
	/*
	 * Opretter nye bruger i databasen
	 * Først kalder den getUserInfo som henter infoen om brugeren og assigner de nødvendige variabler.
	 * Derefter kalder den createUser i SQL_Connect klassen som gemmer brugeren i databasen
	 * sidst sætter den variablerne til empty String så den er klar til næste gang.
	 */
	public void createUser(){
		getUserInfo();
		new UserData(userName,firstName,lastName,eMail,password);
		try {
			connect.createUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resetVar();
	}
	
	private void Login(){
		boolean bool = false;
		do{
			userName = bound.prombtForString("Username: ");
			password = bound.prombtForString("Password: ");
			if (isLoginValid(userName, password)==true){
				System.out.println("Logged in");
				bool = true;	
			}
			else {
				System.out.println("Incorrect username or password");
			}
		}while(bool == false);
	}
	
	/*
	 * isNameAvailable tjekker om det userName der er blevet indtastet allerede er i brug retunere true hvis det er ledigt
	 * Den kalder executeQuery i SQL_Connect retunere bruger antal rækker brugernavnet findes i hvis det allerede eksistere
	 * og null hvis brugernavnet ikke eksistere. 
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
	 * Calls checkLogin on SQL_connect and that returns true if password and username mathces
	 */
	private boolean isLoginValid(String userName, String password){
		boolean accepted = false; 
		try {
		if (connect.checkLogin(userName, password)==true){
			accepted = true;
		}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accepted;
	}
	/*
	 * getUserInfo prombter brugeren for info checker om brugernavn er ledigt
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

