	package domain;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.io.*;
import persistance.SQL_Connect;
import presentation.*;
import data.*;


public class UserController {
	Boundary bound;
	UserData user;
	SQL_Connect connect;
	ArrayList<String> friendArrayList = new ArrayList();
	BinaryTree friends = new BinaryTree();
	
	/*
	 * initializes 
	 */
	public UserController(UserData user, Boundary bound, SQL_Connect connect){
		this.user = user;
		this.bound = bound;
		this.connect = connect;
		getFriends();
		menu();
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
//			case 4: 
//				addFriend();
				
			// case 5 tilfoej ven
			// case 6 egne destinationer
			// 
				//break;
		}
	}
	/*
	 * Displays all your friends.
	 */
	private void friendList(){
		for(int i = 0; i < friendArrayList.size(); i++){
			bound.printLine(friendArrayList.get(i));
		}
		do{
			bound.seperator();
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
		bound.printLine("Create new destination. \nEnter address: ");
		String name =bound.promptForString("Name: ");
		String street =bound.promptForString("Street: ");
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
			visits = connect.executeQuery("CALL create_Friend_Visits('"+user.getUserName()+"')");
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
		Integer picID = null;
		Integer textID = null;
		
		// Call the getPicID function that asks the user if he wants to upload a picture to, returns '0' if he doesnt
		// and if he wants it returns the primary key related to the uploaded picture and initializes tempPicID to this value.
		int tempPicID = getPicID();
		if(tempPicID != 0)
			picID = tempPicID; // if tempPicID does not equal '0' it initializes picID to the value of the primary key for the picture uploaded
		
		// Calls the getPost function that gives the user the possibility to attach a post to the his visit
		// It sets tempPostID to the return value of getPost which is '0' if he didnt want to attach a post.
		// And the primary key of the post in the text table in the database.
		int tempPostID = getPost();
		if(tempPostID != 0)
			textID = tempPostID;	// if tempPostID does not equal '0' it initializes textID to the value of the primary key for the post uploaded
		try {
			// If the destination that the user has visited already exists in the database it will initialize a 2 dimensianal array to contain
			// only the value of the corrosponding destination ID.
			Object[][] dest = connect.executeQuery("SELECT destID FROM destinations WHERE name ='"+name+"' AND street ='"+street+"' AND city ='"+city+
									"' AND zip ="+zip+" AND country ='"+country+"';");
			
			try{
				// initializes destID to the value in the 2 dimensional array if it is not empty if it is empty it will throw an exception
				int destID =(Integer) dest[0][0];
				System.out.println();
				// Inserts username, destID, picID, textID, and the date of the upload will also be saved in the database
				connect.executeUpdate("insert into visits values('"+user.getUserName()+ "',"+destID+","+picID+","+textID+",CURRENT_TIMESTAMP;");
		
			}catch(ArrayIndexOutOfBoundsException e){
				//If the 2 dimensional array was empty means that the database does not contain that destination
				//So it will create a the destination in the database, and auto increment the destID
				connect.executeUpdate("INSERT INTO destinations(name,street,city,zip,country)" +
									  "VALUES('"+name+"','"+street+"','"+city+"',"+zip+",'"+country+"');");
				//Creates a 2 dimensional array that will contain the the biggest destID which will be the ID of the destination
				//which was just created. And it will insert this destID in the visits table with the username, picID, textID and the timestamp
				Object[][] mDest = connect.executeQuery("SELECT MAX(destID) FROM destinations;");
				int maxDest = (Integer)mDest[0][0];
				connect.executeUpdate("insert into visits values('"+user.getUserName()+ "',"+maxDest+","+picID+","+textID+",CURRENT_TIMESTAMP)");
			}
		} catch (SQLException e) {
			
		}
	}
	/*
	 * gets post from user
	 */
	private int getPost(){
		int textID = 0;
		boolean repeat = true;
		do{
			String post = bound.promptForString("Write your post dont press enter till you're done\nTo cancel enter '0': ");
			if (post.equals("0"))
				return 0;
			try {
				connect.executeUpdate("INSERT INTO text(source) VALUES('"+post+"')");
				Object[][] text = connect.executeQuery("SELECT max(text_ID) FROM text;");
				textID =(Integer)text[0][0];
				repeat = false;
				System.out.println(repeat);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}while(repeat);
			
		return	textID;
	}
	/*
	 * Pulls list of friends from DB, saves it in the 2 dimensional friend array
	 */
	private void getFriends(){
		String uName = user.getUserName();
		try {
			Object[][] friendArray = connect.executeQuery("CALL Create_Friendlist('"+uName+"');");
			parseToArraylist(friendArray);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	/*
	 * Parsing the two dimensional array to a Arraylist of friends.
	 */
	private void parseToArraylist(Object[][] friendArray){
		for(int i = 0; i < friendArray.length;i++){
			friends.add((String)friendArray[i][0]);
			friendArrayList.add((String)friendArray[i][0]);
			
		}
	}
	/*
	 * Prints the next ten destinations in the visits table
	 */
	private void showTenDest(int index, Object[][] visits){
		int limit;
		if(index+10 > visits.length)
			limit= visits.length;
		else
			limit = index +10;
		for(int i = index; i < limit;i++){
			for(int j = 0; j < visits[0].length; j++){
				System.out.print(visits[i][j]+", ");
			}
			System.out.println();
		}
		menu();
	}
	
	private void addFriend(){
		bound.printLine("Add Friend");
		String newFriend = bound.promptForString("Enter the username of the friend you want to add: ");
		if(!isNameAvailable(newFriend)){
			try {
				connect.executeUpdate("INSERT INTO userRelations VALUES('"+user.getUserName()+"','"+newFriend+"')");
				friendArrayList.add(newFriend);
				friends.add(newFriend);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isNameAvailable(String userName){
		Object[][] nameAvailability;
		try {
			nameAvailability = connect.executeQuery("SELECT userName FROM users WHERE userName = '" + userName+"'");
			try{
				if(nameAvailability[0].length>0)
					return false;
			}catch(ArrayIndexOutOfBoundsException e){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/*
	 * getPicID Asks user if he wants to upload a picture returns 0, if he doesn't. Asks for the address of the picture
	 * If the address is correct it will save it in the database and return the related primarykey.
	 * 
	 * By: Jacob Espersen
	 */
	private int getPicID(){
		boolean repeat = true;
		int picID = 0;
		do{
			String address = bound.promptForString("Write the address of the picture\nEnter '0' to cancel");
			if (address.equals("0")) 	//Returns zero if The user will not upload a picture
				return 0;
			if(address.charAt(0) != '/'){	//If the address that the user typed It will go to the next iteration of the while loop
				bound.printLine("The address has to start with '/'");
				continue;
			}
			FileInputStream fis = null;
			try{
				File file = new File(address); 	//Creates the file
				fis = new FileInputStream(file);	// reads the file and creates a stream of bytes
				connect.insertPic(fis, file); 	// inserts the byte stream in the database
				Object[][] maxPicID = connect.executeQuery("SELECT MAX(picID) FROM pics"); // creates a 2 dimensional array with only the max value of picID
				picID = (Integer)maxPicID[0][0]; // Passes the max value of picID to a integer
				repeat = false;
			}catch(Exception e){
				System.out.println(e);
				System.out.println("File not found try again");
			}
		}while(repeat);
		return picID; // returns the picID
	}
	
	private void reportPost(){
		
	}
	

}