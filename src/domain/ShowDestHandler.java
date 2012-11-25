package domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.UserData;

import persistance.SQL_Connect;
import presentation.ListOfFriendDest;
import presentation.ShowDest;

public class ShowDestHandler {
	
	ArrayList<String> friendArray;
	SQL_Connect connect;
	UserData user;
	String visitID;
	String userAction;
	
	public ShowDestHandler(ArrayList<String> friendArray, SQL_Connect connect, UserData user, String username){
		this.friendArray = friendArray;
		this.connect = connect;
		this.user = user;
	}
	public ShowDestHandler(ArrayList<String> friendArray, SQL_Connect connect, UserData user){
		this.friendArray = friendArray;
		this.connect = connect;
		this.user = user;
		recentFriendDestinations();
		
	}
	
	/*
	 * Gets a table from the database with all the destinations your friend have visited.
	 */
	private void recentFriendDestinations(){
		Object[][] visits = null;
		try {
			/* Initializes  visits to a 2 dimensional array with all destinations the users friends has visited ordered by date*/
			visits = connect.executeQuery("CALL create_Friend_Visits('"+user.getUserName()+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/* If the 2 dimensional array is empty, it will let the user know there is no destinations and return to menu
		 * If it is not empty it will print the destinations 10 at a time */
		if(visits == null){
			System.out.println("There are no destinations");
			return;
		}else
			for(int i = 0; i < visits.length;i++){
			}
			addActionListener(visits);
	}
	
	private void addActionListener(Object[][] visits){
		final ListOfFriendDest ls = new ListOfFriendDest(visits);
		ls.setVisible(true);
		/* When the buttons are pushed they will return their name which is a number,
		 * from that number we can decide what to do. */ 
		ls.addButtonActionListeners(
				new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						visitID = ((javax.swing.JButton)evt.getSource()).getName();
						int intVisit = Integer.parseInt(visitID);
						if(intVisit == 0)
							ls.setVisible(false);
						else
							initVisitInfo(intVisit);
					}
				});
	}
	/*
	 * Initializes the information of the visit.
	 */
	private void initVisitInfo(int visitID){
		/* Declaration of the variables*/
		System.out.println("visitID =" +visitID);
		String userName = null;
		String post = null;
		String destName = null;
		String city = null;
		String country = null;
		InputStream is = null;
		
		ResultSet visitResult = null;
		ResultSet destResult = null;
		try{
			/* -------------------- retrieves the row for the visit from the database --------------------*/
			visitResult = connect.select("SELECT * FROM visits WHERE visitID = "+visitID);
			/* If the row exists it will retrieve the corresponding picture and text if they exist and the username.  */
			if(visitResult.next()){
				is = getPicStream(visitResult);
				post = getPost(visitResult);
				userName = visitResult.getString(2);
			}
			/* Retrieves the row of the corresponding destination from the database */
			destResult = connect.select("SELECT * FROM destinations WHERE destID = "+visitResult.getInt(3));
			/* If the row exists it will intialize destName, city and country to the values from the database*/
			if(destResult.next()){
				destName = destResult.getString(2);
				city = destResult.getString(4);
				country = destResult.getString(5);
			}
			/* Calls show visits with the values retrieved so it can display the visit*/
			showVisit(userName,post,destName,city,country,is);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	/* Retrieves a post from the database and returns it as a string*/
	private String getPost(ResultSet visitResult) throws Exception{
		String post = null;
		/* Gets the row in the text table where textID is equal to the ID of the post we want*/
		ResultSet postResult = connect.select("SELECT * FROM text WHERE text_ID = "+visitResult.getInt(5));
		if(postResult.next())
			/* Saves the text in the post variable and returns it*/
			post = postResult.getString(2);
		return post;
	}
	
	/* Retrieves a picture from the database and returns as a InputStream*/
	private InputStream getPicStream(ResultSet visitResult) throws Exception{
		InputStream is = null;
		/* Gets the row in the pics table where picID is equal to the ID of the picture we want*/
		ResultSet picResult = connect.select("SELECT * FROM pics WHERE picID = "+visitResult.getInt(4));
		if(picResult.next())
			/* Saves the picture as a inputstream */
			is =  picResult.getBinaryStream(2);
		return is;
	}
	
	private void showVisit(String userName, String post, String destName, String city, String country, InputStream is) throws IOException{
		final ShowDest sd = new ShowDest(userName, city, destName, country, post, is);
		sd.setVisible(true);
		sd.buttonListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						userAction = ((javax.swing.JButton)evt.getSource()).getName();
						if(userAction.equals("0"))
							sd.setVisible(false);
						if(userAction.equals("1"))
							report();
					}
				});	
	}
	private void report(){
		
	}
	
	
	

}