package domain;
/*
 * Class CreateDestinationHandler
 * 
 * Responsible for creating new destinations and saving them in the database
 * 
 * By: Jacob Espersen
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import persistance.SQL_Connect;
import presentation.NewDestMenu;
import data.UserData;

public class CreateDestinationHandler {
	UserData user;
	String userAction;
	File pic = null;
	String text;
	NewDestMenu destMenu;
	UserController uc;
	SQL_Connect connect;

	/* Initializes user connect and destMenu
	 * shows the destmenu and */
	public CreateDestinationHandler(UserData user, SQL_Connect connect){
		this.user = user;
		this.connect = connect;
		destMenu  =  new NewDestMenu();
		destMenu.setVisible(true);
		addActionListener();
	}
	
	private void addActionListener(){
		/* When the buttons are pushed they will return their name which is a number,
		 * from that number we can decide what to do. */ 
		destMenu.addButtonActionListener(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					userAction = ((javax.swing.JButton)evt.getSource()).getName();
					System.out.println(userAction);
					action();
				}
			});
	}
	
	/* 
	 * Decides which method you should be redirected to based on the button pushed
	 */
	private void action(){
		int actionInt = Integer.parseInt(userAction);
		switch (actionInt){
			case 1:								// If Submit is pushed, saves the destination and returns to user menu
				checkDestAndUpdate();
				destMenu.setVisible(false);
				break;
			case 2:								// if Cancel is pushed returns to usermenu
				destMenu.setVisible(false);
				break;		
			case 3:								// if browse is pushed, a directory is opened and the user can choose a picture
				initializePic();
				break;
		}
	}
	
	/*
	 * Check is if destination already exists, opret destination i destinations tabel hvis den ikke eksistere,
	 * Opret nyt bes¿g.
	 */
	private void checkDestAndUpdate(){
		String destName = destMenu.getDestName();
		String street = destMenu.getStreet();
		String city = destMenu.getCity();
		String country = destMenu.getCountry();
		String post = destMenu.getPost();
		Integer picID = null;
		Integer textID = null;
		
		/* If the user uploaded a picture, getPicID will save it in the pics table and return the primarykey for that picture*/
		if(pic != null)
			picID = getPicID();
		/* If the user entered a post text, getPostID will save it in the text table and return the primarykey for that text*/
		if(!post.equals("") && !post.equals("Fill in")){
			textID = getPostID(post);	// if tempPostID does not equal '0' it initializes textID to the value of the primary key for the post uploaded
		}
		try {
			// If the destination that the user has visited already exists in the database it will initialize a 2 dimensianal array to contain
			// only the value of the corrosponding destination ID.
			Object[][] dest = connect.executeQuery("SELECT destID FROM destinations WHERE name ='"+destName+"' AND street ='"+street+"' AND city ='"+city+
									"' AND country ='"+country+"';");
			
			try{
				// initializes destID to the value in the 2 dimensional array if it is not empty if it is empty it will throw an exception
				int destID =(Integer) dest[0][0];
				System.out.println();
				// Inserts username, destID, picID, textID, and the date of the upload will also be saved in the database
				connect.executeUpdate("insert into visits values('"+user.getUserName()+ "',"+destID+","+picID+","+textID+",CURRENT_TIMESTAMP);");
		
			}catch(ArrayIndexOutOfBoundsException e){
				//If the 2 dimensional array was empty means that the database does not contain that destination
				//So it will create a the destination in the database, and auto increment the destID
				connect.executeUpdate("INSERT INTO destinations(name,street,city,country)" +
									  "VALUES('"+destName+"','"+street+"','"+city+"','"+country+"');");
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
	 * getPicID Asks user if he wants to upload a picture returns 0, if he doesn't. Asks for the address of the picture
	 * If the address is correct it will save it in the database and return the related primarykey.
	 * 
	 * By: Jacob Espersen
	 */
	private int getPicID(){
		int picID = 0;
		FileInputStream fis = null;
		try{ 
			fis = new FileInputStream(pic);	// reads the pic file and creates a stream of bytes
			connect.insertPic(fis, pic); 	// inserts the byte stream in the database
			Object[][] maxPicID = connect.executeQuery("SELECT MAX(picID) FROM pics"); // creates a 2 dimensional array with only the max value of picID
			picID = (Integer)maxPicID[0][0]; // Passes the max value of picID to a integer				repeat = false;
		}catch(Exception e){
			System.out.println(e);
		}
		return picID; // returns the picID
	}
	
	/*
	 * Shows a window that can browse you directory and initalizes pic to the file you choose
	 * filter the browse window so yo can only choose pictures
	 */
	private void initializePic(){
		/* Creates a directory window */
		JFileChooser chooser = new JFileChooser();
		/* Creates a filter so only  pictures can be chosen */
		FileFilter filter = new FileNameExtensionFilter("Image file (.jpg, .jpeg, .gif, .png)", new String[] {"jpg", "jpeg", "gif", "png"});
		/* implements the filter on the directory*/
		chooser.setFileFilter(filter);
		int status = chooser.showOpenDialog(null);
		/* If the file is approved the pic variable will initialized to the picture chosen*/
		if(status == JFileChooser.APPROVE_OPTION)
			pic = chooser.getSelectedFile();				
	}
	
	/*
	 * Inserts the post in the table and returns the corresponding ID
	 */
	private int getPostID(String post){
		int textID = 0;
		try{
			/* Inserts the post in the  text table*/
			connect.executeUpdate("INSERT INTO text(source) VALUES('"+post+"')");
			/* Gets the Primary key for the text inserted*/
			Object[][] text = connect.executeQuery("SELECT max(text_ID) FROM text;");
			textID =(Integer)text[0][0];
		} catch (SQLException e) {
				e.printStackTrace();
		}
			
		return	textID;
	}
}