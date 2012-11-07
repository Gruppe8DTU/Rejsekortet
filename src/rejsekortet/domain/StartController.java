package rejsekortet.domain;

import java.sql.SQLException;

import rejsekortet.data.UserData;
import rejsekortet.presentation.Boundary;
import services.persistance.SQL_Connect;

public class StartController {
	private String userName, firstName, lastName, eMail, password;
	private UserData user;
	private Boundary bound = new Boundary();
	private SQL_Connect connect = new SQL_Connect();
	
	/*
	 * Opretter nye bruger i databasen
	 * Først kalder den getUserInfo som henter infoen om brugeren og assigner de nødvendige variabler.
	 * Derefter kalder den createUser i SQL_Connect klassen som gemmer brugeren i databasen
	 * sidst sætter den variablerne til empty String så den er klar til næste gang.
	 */
	private void createUser(){
		getUserInfo();
		new UserData(userName,firstName,lastName,eMail,password);
		try {
			connect.createUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * Prombts user for login till he enters correct login info
	 * if user enters '0' he will be redirected to create a new user
	 */
	private void getLogin(){
		bound.printLine("Log in or type 0 to create new user");
		boolean bool = false;
		do{
			userName = bound.prombtForString("Username: ");
			if(userName =="0")
				createUser();
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
		bound.printLine("Log in or type 0 to create new user");
		do{
			userName = bound.prombtForString("Type the username you want: ");
			if(userName == "0")
				getLogin();
			if (!isNameAvailable(userName))
				bound.printLine("User name is not available, try another one");
		}while(!isNameAvailable(userName));
		firstName = bound.prombtForString("Type your sir name: ");
		lastName = bound.prombtForString("Type your last name: ");
		eMail = bound.prombtForString("Type your e-mail address: ");
		password = bound.getPassword();
	}
	
}

