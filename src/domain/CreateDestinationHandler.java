package domain;

import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import persistance.SQL_Connect;
import presentation.Login;
import presentation.ModScreen;
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

	public CreateDestinationHandler(UserData user, SQL_Connect connect){
		this.user = user;
		this.connect = connect;
		destMenu  =  new NewDestMenu();
		destMenu.setVisible(true);
		addActionListener();
	}
	
	private void addActionListener(){
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
			case 1:
				checkDestAndUpdate();
				
				destMenu.setVisible(false);
				break;
			case 2:
				initializePic();
				break;
			case 3:
				
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
		
		// Call the getPicID function that asks the user if he wants to upload a picture to, returns '0' if he doesnt
		// and if he wants it returns the primary key related to the uploaded picture and initializes tempPicID to this value.
		// if tempPicID does not equal '0' it initializes picID to the value of the primary key for the picture uploaded
		if(pic != null)
			picID = getPicID();
		// Calls the getPost function that gives the user the possibility to attach a post to the his visit
		// It sets tempPostID to the return value of getPost which is '0' if he didnt want to attach a post.
		// And the primary key of the post in the text table in the database.
		if(!post.equals("") || !post.equals("Fill in"))
			textID = getPostID(post);	// if tempPostID does not equal '0' it initializes textID to the value of the primary key for the post uploaded
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
				connect.executeUpdate("insert into visits values('"+user.getUserName()+ "',"+destID+","+picID+","+textID+",CURRENT_TIMESTAMP;");
		
			}catch(ArrayIndexOutOfBoundsException e){
				//If the 2 dimensional array was empty means that the database does not contain that destination
				//So it will create a the destination in the database, and auto increment the destID
				connect.executeUpdate("INSERT INTO destinations(name,street,city,zip,country)" +
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
		JFileChooser chooser = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Image file (.jpg, .jpeg, .gif, .png)", new String[] {"jpg", "jpeg", "gif", "png"});
		chooser.setFileFilter(filter);
		int status = chooser.showOpenDialog(null);
		
		if(status == JFileChooser.APPROVE_OPTION)
			pic = chooser.getSelectedFile();				
	}
	
	private int getPostID(String post){
		int textID = 0;
		try{
			connect.executeUpdate("INSERT INTO text(source) VALUES('"+post+"')");
			Object[][] text = connect.executeQuery("SELECT max(text_ID) FROM text;");
			textID =(Integer)text[0][0];
		} catch (SQLException e) {
				e.printStackTrace();
		}
			
		return	textID;
	}
}