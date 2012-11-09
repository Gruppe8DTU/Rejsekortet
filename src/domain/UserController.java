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
	 * initializes 
	 */
	public UserController(UserData user, Boundary bound, SQL_Connect connect){
		this.user = user;
		this.bound = bound;
		getFriends();
	}
	/*
	 * Displays menu for user, gets input and depending on the input decides which action to do next
	 */
	private void menu(){
		bound.displayLoggedInMenu();
		switch(bound.promptForInt("")){
			case 1: 
				createNewDest();
				break;
			case 2:
				friendList();
				break;
			case 3: 
				recentDestinations();
				break;
			case 4: // Log ud
				break;
		}
	}
	/*
	 * Displays all your friends.
	 */
	private void friendList(){
		for (String friend : friends)
			bound.printLine(friend);
		do{
			int input = bound.promptForInt("Type '0' to go to menu");
			if(input == 0)
				menu();
			else
				bound.printLine("Invalid input");
		}while(true);
		
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
		menu();
		
	}
	private void recentDestinations(){
		
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
			connect.executeQuery("insert into userPosts "+user.getUserName()+ ","+destID+", picture,"+post+",date");//picture and date needs to be implemented
			
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
