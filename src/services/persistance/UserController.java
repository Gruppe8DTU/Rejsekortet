package services.persistance;

public class UserController {
	Boundary bound;
	
<<<<<<< HEAD
	public UserController(){
		bound = new Boundary();
=======
	/*
	 * aaaaaad
	 * Opretter nye bruger i databasen
	 * F¿rst kalder den getUserInfo som henter infoen om brugeren og assigner de n¿dvendige variabler.
	 * Derefter kalder den createUser i SQL_Connect klassen som gemmer brugeren i databasen
	 * sidst s¾tter den variablerne til empty String sŒ den er klar til n¾ste gang.
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
>>>>>>> test
	}
	
	public void loggedInMenu(User user){
		bound.displayLoggedInMenu();
		switch(bound.getInt()){
			case 1: // Tilf¿j destination
					break;
			case 2: // find Venner
					break;
			case 3: // Se venners profiler
					break;
			case 4: // Log ud
					break;
		}
	}
}
