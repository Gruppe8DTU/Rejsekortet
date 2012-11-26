package domain;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import persistance.SQL_Connect;
import presentation.FriendList;
import presentation.Home;
import presentation.MessagePopup;
import presentation.ModScreen;
import presentation.AdminPopup;
import data.UserData;

public class ModController {
	SQL_Connect connect;
	UserData user;
	final ModScreen ms = new ModScreen();
	PopupController pc;
	protected ResultSet res = null;
	ResultSet picData;
	protected String userAction;
	protected String body;
	protected int currentReport = 1;
	protected ArrayList<Integer> picIDs;

		public ModController(UserData user, SQL_Connect connect){
			this.user = user;
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
			switch (intAction){
				case 1: 
					pc.destroy();
					ms.setVisible(false); // go back
					break;
				case 2: 
					pc.destroy();
					System.exit(0); // exit
					break;
				case 3: 
					viewReportedPosts();
					if(isEmpty()){
						new MessagePopup("There is no reports");
					}else{
						pc.init(res, false);
					}
					break;
				case 4:
					viewReportedPics();
					if(isEmpty()){
						new MessagePopup("There is no reports");
					}else{
					pc.init(res, true);
					}
					break;
				case 5: 
					viewReportedDestinations();
					if(isEmpty()){
						new MessagePopup("There is no reports");
					}else{
					pc.init(res, false);
					}
					break;
			}
		}
		
		protected void viewReportedPosts(){
			try {
				res = connect.select("SELECT reports.contentType, text.source, visits.visitID, visits.username, reports.reason, reports.reportedBy " +
										   "FROM text, reports, visits " +
										   "WHERE reports.visitID = visits.visitID " +
										   "AND visits.textID = text.text_ID " +
										   "AND contentType = 1");
				
			} catch (Exception e) {
				System.out.println("connection error");
				e.printStackTrace();
			}
		}
		
		protected void viewReportedPics(){
			try {
				res = connect.select("SELECT reports.contentType, pics.picSource, visits.visitID, visits.username, reports.reason, reports.reportedBy " +
										   "FROM pics, reports, visits " +
										   "WHERE reports.visitID = visits.visitID " +
						   				   "AND visits.picID = pics.picID " +
						   				   "AND contentType = 2");
			} catch (Exception e) {
				System.out.println("connection error " + e);
			}
		}
		
		

		protected void viewReportedDestinations(){
			try {
				res = connect.select("SELECT reports.contentType, destinations.name, destinations.street, destinations.city, destinations.country, reports.reason, reports.reportedBy " +
										   "FROM destinations, reports, visits " +
										   "WHERE destinations.destID = visits.destID " +
										   "AND reports.visitID = visits.visitID " +
										   "AND contentType = 3");
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
		
		
		protected InputStream convertResultSetToStream(ResultSet picData, InputStream is) throws Exception{
			if(picData.next())
				/* Saves the picture as a inputstream */
				is =  picData.getBinaryStream(2);
			return is;
		}
		
		protected boolean isEmpty(){
			try{
				if(res.first()){
					return false;
				}else
					return true;
			}catch(Exception e){
				return true;
			}
		}
		
}
