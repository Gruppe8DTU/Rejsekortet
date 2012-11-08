package domain;

import java.sql.SQLException;

import persistance.SQL_Connect;
import presentation.Boundary;

import data.UserData;


public class UserController {
	Boundary bound;
	UserData user;
	SQL_Connect connect;
	
	/*
	 * displays users options and redirects to the right methods
	 */
	public UserController(UserData user, Boundary bound, SQL_Connect connect){
		this.user = user;
		this.bound = bound;
		// Displays menu for user, gets input and depending on the input decides which action to do next;
		bound.displayLoggedInMenu();
		switch(bound.promptForInt("")){
			case 1: 
				createNewDest();
				break;
			case 2: // find Venner
				break;
			case 3: // Se venners profiler
				break;
			case 4: // Log ud
				break;
		}		
	}
	/*
	 * gets address from user and creates destionation.  
	 */
	private void createNewDest(){
		bound.printLine("Create new destination. \n Enter address: ");
		String name = bound.promptForString("Name of the place: ");
		String street = bound.promptForString("Street: ");
		String city = bound.promptForString("City: ");
		int zip = bound.promptForInt("Zip-code: ");
		String country = bound.promptForString("Country: ");
		checkDestAndUpdate(name,street,city,zip,country);
		
	}
	/*
	 * Check is if destination already exists, opret destination i destinations tabel hvis den ikke eksistere,
	 * Opret nyt besøg.
	 */
	private void checkDestAndUpdate(String name, String street, String city, int zip, String country){
		// get pictures method
		String post = getPost();
		try {
			// skal implementeres at name skal være case insensitive så der ikke kommer flere af samme destination i databasen
			Object[][] dest = connect.executeQuery("SELECT destID from destinations WHERE name ="+name+"street ="+street+"AND city ="+city+
									"AND zip ="+zip+"country ="+country);
			int destID =(Integer) dest[0][0];
			connect.executeQuery("insert into userPosts "+user.getUserName()+ ","+destID+", picture,"+post);
			
		} catch (SQLException e) {
			//create new destination ID and save destinations, brug stored procedures til at finde det næste destID
			
		}
	}
	/*
	 * gets post from user
	 */
	private String getPost(){
		String post = bound.promptForString("Write your post: ");
		return post;
	}
	
	

	
}
