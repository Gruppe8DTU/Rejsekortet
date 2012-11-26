package domain;

import java.sql.SQLException;
import java.util.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import persistance.SQL_Connect;
import presentation.*;
import data.*;

public class UserController {

	CreateDestinationHandler newDest;
	final Home home = new Home();
	Boundary bound;
	UserData user;
	SQL_Connect connect;
	StartController start;
	ArrayList<String> friendArrayList = new ArrayList<String>();
	BinaryTree friends = new BinaryTree();
	protected String userAction;
	int intAction;
	String un = null;
	boolean add;
	
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
		home.setVisible(true);
		addActionListener(home);
	}
	
	protected void addActionListener(Home home){
		home.addButtonActionListener1(
				new java.awt.event.ActionListener(){
					public void actionPerformed(java.awt.event.ActionEvent evt){
						String userAction = ((javax.swing.JButton)evt.getSource()).getName();
						System.out.println(userAction);
						menuAction(userAction);
					}
				}
		);
	}
	
	private void menuAction(String userAction){
		int intAction = Integer.parseInt(userAction);
		switch (intAction){
			case 1:
				new CreateDestinationHandler(user, connect);
				break;
			case 2: 
				friendList();
				break;
			case 3: 
				new ShowDestHandler(friendArrayList, connect, user);
				break;
			case 4: 
				//new ShowDestHandler(connect, user.getUserName());
				//specificDest(user.getUserName());
				break;
			case 5: 
				getUsernameAndAdd("Enter the username of the person you want to add");
				break;
			case 6: 
				getUsernameAndSeeDest("Enter the Username that you want to browse destinations for");
				break;
			// redirect to option screen
			case 7: 
				redirectToOption();
				break;
			// logout button
			case 8: 	
				user = null;
				friends = null;
				friendArrayList = null;
				start = new StartController();
				start.addActionListener();	
				break;

			}			
	}
	
	
	/* 
	 * Redirects you to either moderator option site, administrator option site or tells you that you dont have the access
	 */
	private void redirectToOption() {
		int type = user.getType(); 
		System.out.println("your user type : " + type);
		if (type == 1){
			System.out.println("you don't have access noob!");
		} else if (type == 2){
			home.setVisible(false);
			ModController mc = new ModController(user, bound, connect);
			mc.init();
			System.out.println("new mod controller initialization complete");
		} else if (type == 3){
			home.setVisible(false);
			AdminController ac = new AdminController(user, bound, connect);
			ac.init();
			System.out.println("New admin controller initialization complete");
		}	
	}
	/*
	 * Displays all your friends.
	 */
	private void friendList(){
		final FriendList fl = new FriendList(friendArrayList);
		fl.setVisible(true);
		fl.buttonActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						userAction = ((javax.swing.JButton)evt.getSource()).getName();
						if(userAction.equals("1"))
							fl.setVisible(false);
					}
				});	
	}
	
	
	
	
	/*
	 * Pulls list of friends from DB, saves it in the 2 dimensional friend array
	 */
	private void getFriends(){
		
		Object[][] friendArray = null;
		String uName = user.getUserName();
		System.out.println(uName);
		try {
			/* Calls a stored procedure that will return a list of friends as a 2 dimensional array*/
			friendArray = connect.executeQuery("CALL Create_Friendlist('"+uName+"');");
			System.out.println("hmmm");
			/* If the user does not have any friends, stored procedure will not have returned anything and the 2 dimensional array will
			 * still equal null, and we will stop the method */
			if (friendArray == null){
				System.out.println("no friensa");
				return;
			}
			/* Passes the 2 dimensional array to intialize the friends arraylist and binarytree*/
			parseToArraylist(friendArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * Parsing the two dimensional array to a Arraylist of friends.
	 */
	private void parseToArraylist(Object[][] friendArray){
		/*Each friend in the 2 dimensional array will be saved in the friends arraylist and binarytree*/
		for(int i = 0; i < friendArray.length;i++){
			friends.add((String)friendArray[i][0]);
			friendArrayList.add((String)friendArray[i][0]);
			
		}
	}
	
	
	
	/*
	 * Adds a friend  to the database, arraylist and binarytree
	 */
	private void addFriend(String newFriend){
			try {
				connect.executeUpdate("INSERT INTO userRelations VALUES('"+user.getUserName()+"','"+newFriend+"')");
				friendArrayList.add(newFriend);
				friends.add(newFriend);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	/*
	 * Checks if a username is available, returns true if it is, false if not
	 */
	private boolean isNameAvailable(String userName){
		Object[][] nameAvailability = null;
		try {
			/* If the username exists in the database, it will save the username in nameAvailability, if not the value will stay null*/
			nameAvailability = connect.executeQuery("SELECT userName FROM users WHERE userName = '" + userName+"'");
			
			/* If the nameAvailabilty is empty there is no such name in the database and it will return true, else it will return false*/
			if(nameAvailability == null)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void getUsernameAndSeeDest(String prompt){
		final GetUserNameScreen usernameScreen = new GetUserNameScreen(prompt);
		usernameScreen.setVisible(true);
		usernameScreen.addButtonListeners(
				new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						String action = ((javax.swing.JButton)evt.getSource()).getName();
						if(action.equals("0"))
							usernameScreen.setVisible(false);
						if(action.equals("1")){
							String name = usernameScreen.getUserName();
							if(friends.contains(name)){
								usernameScreen.setVisible(false);
								new ShowDestHandler(connect, name);
							}else{
								usernameScreen.setVisible(false);
								getUsernameAndSeeDest(name+" is not a friend of you, try again");
							}		
						}
					}
				});	
	}
	
	private void getUsernameAndAdd(String prompt){
		final GetUserNameScreen usernameScreen = new GetUserNameScreen(prompt);
		usernameScreen.setVisible(true);
		usernameScreen.addButtonListeners(
				new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						String action = ((javax.swing.JButton)evt.getSource()).getName();
						if(action.equals("0"))
							usernameScreen.setVisible(false);
						if(action.equals("1")){
							String name = usernameScreen.getUserName();
							if(isNameAvailable(name)){
								usernameScreen.setVisible(false);
								new ShowDestHandler(connect, name);
							}else if(friends.contains(name)){
								usernameScreen.setVisible(false);
								getUsernameAndSeeDest(name+" is already your friend, try another name");
							}else{
								usernameScreen.setVisible(false);
								getUsernameAndSeeDest(name+" does not exist");
							}		
						}
					}
				});	
	}
	
	
}