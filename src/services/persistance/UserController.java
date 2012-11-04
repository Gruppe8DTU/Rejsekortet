package services.persistance;

import java.sql.SQLException;

public class UserController {
	String userName, firstName, lastName, eMail, password;
	User user;
	Boundary gui = new Boundary();
	SQL_Connect connect = new SQL_Connect();
	
	
	/*
	 * Opretter nye bruger i databasen
	 * F�rst kalder den getUserInfo som henter infoen om brugeren og assigner de n�dvendige variabler.
	 * Derefter kalder den createUser i SQL_Connect klassen som gemmer brugeren i databasen
	 * sidst s�tter den variablerne til empty String s� den er klar til n�ste gang.
	 */
	public void createUser(){
		getUserInfo();
		new User(userName,firstName,lastName,eMail,password);
		try {
			connect.createUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resetVar();
	}
	/*
	 * isNameAvailable tjekker om det userName der er blevet indtastet allerede er i brug retunere true hvis det er ledigt
	 * Den kalder executeQuery i SQL_Connect retunere bruger antal r�kker brugernavnet findes i hvis det allerede eksistere
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

