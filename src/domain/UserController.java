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
	Object[][] friends;
	
	/*
	 * initializes 
	 */
	public UserController(UserData user, Boundary bound, SQL_Connect connect){
		this.user = user;
		this.bound = bound;
		this.connect = connect;
		System.out.println("HEEEEEEY");
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
				recentFriendDestinations();
				break;
			case 4: // Log ud
				
			// case 5 tilfoej ven
			// case 6 egne destinationer
			// 
				break;
		}
	}
	/*
	 * Displays all your friends.
	 */
	private void friendList(){
		for(int i = 0; i < friends.length; i++){
			bound.printLine((String)friends[i][0]);
		}
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
		checkDestAndUpdate(name.toLowerCase(),street.toLowerCase(),city.toLowerCase(),zip,country.toLowerCase());
		menu();
		
	}
	
	/*
	 * Gets a table from the database with all the destinations your friend have visited.
	 * Prints the 10 most recent and if you want to see more you can ask to see 10 more.
	 */
	private void recentFriendDestinations(){
		Object[][] visits = null;
		try {
			connect.executeUpdate("CALL create_Friend_Visits('"+user.getUserName()+"')");
			visits = connect.executeQuery("SELECT tempVisits.username,destinations.name FROM tempVisits INNER JOIN destinations"+
										  "ON tempVisits.destID = destinations.destID ORDER BY tempVisits.date DESC");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(visits != null){
			int index = 0;
			showTenDest(index, visits);
			do{
				index += 10;
				int desition = bound.promptForInt("Type '0' to print 10 more friend destinations \n"+
									  "Type any other key to go back to menu");
				if(desition==0)
					showTenDest(index,visits);
				else{
					break;
				}
				
			}while(true);
		}
	}
	
	/*
	 * Check is if destination already exists, opret destination i destinations tabel hvis den ikke eksistere,
	 * Opret nyt bes¿g.
	 */
	private void checkDestAndUpdate(String name, String street, String city, int zip, String country){
		// get pictures method
		String post = getPost();
		try {
			// skal implementeres at name skal v¾re case insensitive sŒ der ikke kommer flere af samme destination i databasen
			Object[][] dest = connect.executeQuery("SELECT destID FROM destinations WHERE name ="+name+"street ="+street+"AND city ="+city+
									"AND zip ="+zip+"country ="+country);
			
			int destID =(Integer) dest[0][0];
			connect.executeQuery("insert into userPosts "+user.getUserName()+ ","+destID+", picture,"+post+",date");//picture and date needs to be implemented
			
		} catch (SQLException e) {
			//create new destination ID and save destinations, brug stored procedures til at finde det n¾ste destID
			
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
	 * Pulls list of friends from DB, saves it in the 2 dimensional friend array
	 */
	private void getFriends(){
		String uName = user.getUserName();
		try {
			// whaaaaaat?
			friends = connect.executeQuery("CALL Create_Friendlist('"+uName+"');\n" +
										   "SELECT * FROM TempFriendlist;");
			System.out.println("heeeey");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	/*
	 * Prints the next ten destinations in the visits table
	 */
	private void showTenDest(int index, Object[][] visits){
		System.out.println("Username\t| Destination\t|");
		for(int i = index; i < index+10;index++){
			for(int j = 0; j < visits[0].length; j++){
				System.out.print(visits[i][j]+"\t|");
			}
			System.out.print("Check content");
			System.out.println();
		}
	}
	
}
