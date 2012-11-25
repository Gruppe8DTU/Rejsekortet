package domain;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.ResultSet;

import persistance.SQL_Connect;
import presentation.Boundary;
import presentation.FriendList;
import presentation.Home;
import presentation.ModScreen;
import presentation.AdminPopup;
import data.UserData;

public class ModController {
	SQL_Connect connect;
	Boundary bound;
	UserData user;
	final ModScreen ms = new ModScreen();
	PopupController pc;
	Object[][] res = null;
	ResultSet picData;
	protected String userAction;
	protected String body;
	protected int currentReport = 1;
	protected int[] picIDs;

		public ModController(UserData user, Boundary bound, SQL_Connect connect){
			this.user = user;
			this.bound = bound;
			this.connect = connect;
		}
		
		public void init(){
			ms.hideUserRights();
			ms.setVisible(true);
			addActionListener();
		}
		
		public void addActionListener(){
			ms.addButtonActionListener1(
					new java.awt.event.ActionListener(){
						public void actionPerformed(java.awt.event.ActionEvent evt){
							userAction = ((javax.swing.JButton)evt.getSource()).getName();
							menuAction();
						}
					}
			);
		}
		
		protected void menuAction(){
			int intAction = 0;
			if (isNumeric(userAction))
				intAction = Integer.parseInt(userAction);
			pc = new PopupController(connect);	
			pc.hideChangeUserRigths();
			switch (intAction){
				case 1: 
					pc.destroy();
					ms.setVisible(false); // go back
					break;
				case 2: 
					pc.destroy();
					System.out.println("you pressed exit");
					System.exit(0); // exit
					break;
				case 3: 
					viewReportedPosts();
					pc.init(res);
					break;
				case 4:
					viewReportedPics();
					pc.init(res);
					picIDs = new int[res.length];
					for (int i = 0; i < res.length; i++){
						picIDs[i] = Integer.parseInt( res[i][9].toString() );
					}
				try {
					pc.setPictures(getPics());
				} catch (IOException e) {
					System.out.println("IOException while trying to set picture " + e);
					e.printStackTrace();
				}
					break;
				case 5: 
					viewReportedDestinations();
					pc.init(res);
					break;
			}
		}
		
		protected void viewReportedPosts(){
			try {
				res = connect.executeQuery("SELECT * FROM text, reports WHERE text.text_Id = reports.textID");
			} catch (Exception e) {
				System.out.println("connection error");
				e.printStackTrace();
			}
		}
		protected void viewReportedPics(){
			try {
				res = connect.executeQuery("SELECT * FROM pics, reports WHERE pics.picID = reports.picID");
			} catch (Exception e) {
				System.out.println("connection error " + e);
			}
		}
		protected void viewReportedDestinations(){
			try {
				res = connect.executeQuery("SELECT * FROM destinations, reports WHERE destinations.destID = reports.destID");
			} catch (Exception e){
				System.out.println("connection error " + e);
			}
		}

		protected boolean isNumeric(String str){
			try{
				Integer.parseInt(str);
			}catch(NumberFormatException e){
				return false;
			}
			return true;
		}
		protected InputStream getPics(){
			InputStream is = null;
			String str = " OR picID = ";
			String query = "";
			for (int i = 0; i < picIDs.length; i++){
				int id = picIDs[i];
				query += id;
				if ( picIDs[i] != picIDs[picIDs.length-1])
					query += str;
			}
			System.out.println("Appended pic query = " + query);
			try {
				picData = connect.select("SELECT * FROM pics WHERE picID = " + query);
				System.out.println("Select query succesfull");
			} catch (Exception e) {
				System.out.println("Error while trying to get pics " + e);
			}
			try {
				is = convertResultSetToStream(picData, is);
			} catch (Exception e) {
				System.out.println("Error converting pic result set to stream " + e);
				e.printStackTrace();
			}
			return is;
		}
		
		protected InputStream convertResultSetToStream(ResultSet picData, InputStream is) throws Exception{
			if(picData.next())
				/* Saves the picture as a inputstream */
				is =  picData.getBinaryStream(2);
			return is;
		}
		
}
