package domain;

import java.sql.SQLException;

import persistance.SQL_Connect;
import presentation.Boundary;

import data.UserData;


public class StartController {
	private String userName, firstName, lastName, eMail, password;
	private int type;
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
		user = new UserData(userName, firstName,lastName,eMail,password,type);
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
	public void getLogin(){
		bound.printLine("Log in or type 0 to create new user");
		UserData user;
		boolean bool = false;
		do{
			userName = bound.promptForString("Username: ");
			if(userName.equals("0")){
				createUser();
				break;
			}
			password = bound.promptForString("Password: ");
			if (isLoginValid(userName, password)==true){
				bound.printLine("Logged in");
				user = getUser(userName);
				System.out.println("yaaaay you logged in as"+user.getUserName());
				//redirectToController(user);
				bool = true;	
			}
			else {
				System.out.println("Incorrect username or password");
			}
		}while(bool == false);
	}
	/*
	 * Checks which type of user that has logged in and redirects the user to the right controller
	 */
	/*private void redirectToController(UserData user){
		switch(user.getType()){
			case 1:
				new UserController(user, bound, connect);
				break;
			case 2:
				new AdminController(user);
				break;
			case 3:
				new ModController(user);
				break;
		}
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
		bound.printLine("Create new user or type '0' to login");
		//do{
			userName = bound.promptForString("Type the username you want: ");
			//if(userName.equals("0"))
				//getLogin();
			//if (!isNameAvailable(userName))
				//bound.printLine("User name is not available, try another one");
		//}while(!isNameAvailable(userName));
		firstName = bound.promptForString("Type your sir name: ");
		lastName = bound.promptForString("Type your last name: ");
		eMail = bound.promptForString("Type your e-mail address: ");
		password = bound.getPassword();
		type = 1;
	}
	/*
	 * creates a new object of the type user and inititializes the values
	 */
	private UserData getUser(String userName){
		UserData user;
		try {
			Object[][] res = connect.executeQuery("select * from users where userName = '"+ userName+"'");
			Object[] userRow = res[0];
			userName = (String) userRow[0];
			firstName = (String) userRow[1];
			lastName = (String) userRow[2];
			eMail = (String) userRow[3];
			password = (String) userRow[4];
			type = (Integer) userRow[5];
			
			user = new UserData(userName,firstName,lastName,eMail,password,type);
			return user;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}

