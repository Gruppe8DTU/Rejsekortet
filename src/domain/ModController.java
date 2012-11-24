package domain;

import java.sql.SQLException;

import persistance.SQL_Connect;
import presentation.Boundary;
import presentation.Home;
import presentation.ModScreen;
import presentation.AdminPopup;
import data.UserData;

public class ModController extends UserController {

	final ModScreen ms = new ModScreen();
	Object[][] res;
	protected String body;
	protected int currentReport = 1;

		public ModController(UserData user, Boundary bound, SQL_Connect connect){
			super(user, bound, connect);
			menu();
		}
		
		private void menu(){
			ms.setVisible(true);
			addActionListener();
			menuAction();
		}
		
		protected void addActionListener(){
			System.out.println("action added in modcontroller");
			ms.addButtonActionListener1(
					new java.awt.event.ActionListener(){
						public void actionPerformed(java.awt.event.ActionEvent evt){
							userAction = ((javax.swing.JButton)evt.getSource()).getName();
							System.out.println(userAction);
						}
					}
			);
		}
		
		private void menuAction(){
			try {
			System.out.println("userAction : " + userAction);
			intAction = Integer.parseInt(userAction);
			System.out.println("intAction : " + intAction);
			} catch (NumberFormatException e){
				System.out.println("int action not converted from string " + e);
			}
			ms.setVisible(false);
			
			switch (intAction){
				case 1: 
					ms.setVisible(false); // go back
					break;
				case 2: 
					System.exit(0); // exit
					break;
				case 3: 
					viewReportedPosts();
					
					//ap.setText(header + body);
					//ap.setLabel("Reported posts");
					break;
				case 4:
					viewReportedPics();
				
					break;
				case 5: 
					viewReportedDestinations();
				
					break;
				case 6: 
					viewReportedUsers();
			
					break;
			}
		}
		
		protected void viewReportedPosts(){
			try {
				res = connect.executeQuery("SELECT * FROM text, reports WHERE text.text_Id = reports.textID");
				//pc.setData(res);
			} catch (SQLException e) {
				System.out.println("connection error");
				e.printStackTrace();
			}
		}
		protected void viewReportedPics(){
			try {
				res = connect.executeQuery("SELECT * FROM pics, reports WHERE pics.picID = reports.picID");
				//pc.setData(res);
			} catch (SQLException e) {
				System.out.println("connection error " + e);
			}
		}
		protected void viewReportedDestinations(){
			try {
				res = connect.executeQuery("SELECT * FROM destinations, reports WHERE destinations.destID = reports.destID");
				//pc.setData(res);
			} catch (SQLException e){
				System.out.println("connection error " + e);
			}
		}
		protected void viewReportedUsers(){
			try {
				res = connect.executeQuery("SELECT * FROM users, reports WHERE users.userName = reports.reportedUser");
				//pc.setData(res);
			} catch (SQLException e){
				System.out.println("connection error " + e);
			}
		}
		
}
