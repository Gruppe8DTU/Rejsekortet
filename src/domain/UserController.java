package domain;

import java.sql.SQLException;
import java.util.*;

import persistance.SQL_Connect;
import presentation.Boundary;

import data.UserData;


public class UserController {
	Boundary bound;
	UserData user;
	SQL_Connect connect;
	ArrayList<String> friends = new ArrayList<String>();
	
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
			case 2:
				getFriends();
				for (String friend : friends)
					bound.printLine(friend);
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
	 * Opret nyt bes�g.
	 */
	private void checkDestAndUpdate(String name, String street, String city, int zip, String country){
		// get pictures method
		String post = getPost();
		try {
			// skal implementeres at name skal v�re case insensitive s� der ikke kommer flere af samme destination i databasen
			Object[][] dest = connect.executeQuery("SELECT destID from destinations WHERE name ="+name+"street ="+street+"AND city ="+city+
									"AND zip ="+zip+"country ="+country);
			int destID =(Integer) dest[0][0];
			connect.executeQuery("insert into userPosts "+user.getUserName()+ ","+destID+", picture,"+post);
			
		} catch (SQLException e) {
			//create new destination ID and save destinations, brug stored procedures til at finde det n�ste destID
			
		}
	}
	/*
	 * gets post from user
	 */
	private String getPost(){
		String post = bound.promptForString("Write your post: ");
		return post;
	}
	/*
	 * Pulls list of friends from DB
	 */
	private void getFriends(){
		String uName = user.getUserName();
		try {
			Object[][] userRelations = connect.executeQuery("SELECT * FROM userRelations WHERE userName1 = " + uName + "OR userName2 =" + uName);
			parseFriends(userRelations, uName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Help method for extracting friends from userRelations from DB
	final void parseFriends(Object[][] userRelations, String uName){
		for (int i = 0; i < userRelations.length; i++){
			for (int k = 0; k < userRelations[i].length; k++){
				if (userRelations[i][k] != uName){
					friends.add( (String) userRelations[i][k] );
				}
			}
		}
	}
		
	
}
