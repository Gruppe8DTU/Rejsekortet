package domain;

import java.sql.SQLException;
import java.sql.ResultSet;

import persistance.SQL_Connect;
import presentation.Boundary;
import presentation.FriendList;
import presentation.Home;
import presentation.ModScreen;
import presentation.AdminPopup;
import data.UserData;

public class ModController extends UserController {
	final ModScreen ms = new ModScreen();
	ResultSet res = null;
	//Object[][] res;
	protected String body;
	protected int currentReport = 1;

		public ModController(UserData user, Boundary bound, SQL_Connect connect){
			super(user, bound, connect);
		}
		
		public void init(){
			ms.setVisible(true);
			addActionListener();
		}
		
		public void addActionListener(){
			System.out.println("action added in modcontroller");
			ms.addButtonActionListener1(
					new java.awt.event.ActionListener(){
						public void actionPerformed(java.awt.event.ActionEvent evt){
							userAction = ((javax.swing.JButton)evt.getSource()).getName();
							System.out.println("user action in mod controller : " + userAction);
							menuAction();
						}
					}
			);
		}
		
		protected void menuAction(){
			int intAction = 0;
			if (isNumeric(userAction))
				intAction = Integer.parseInt(userAction);
			PopupController pc = new PopupController();
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
					break;
				case 5: 
					viewReportedDestinations();
					pc.init(res);
					break;
				case 6: 
					viewReportedUsers();
					pc.init(res);
					break;
			}
		}
		
		protected void viewReportedPosts(){
			try {
				res = connect.select("SELECT * FROM text, reports WHERE text.text_Id = reports.textID");
			} catch (Exception e) {
				System.out.println("connection error");
				e.printStackTrace();
			}
		}
		protected void viewReportedPics(){
			try {
				res = connect.select("SELECT * FROM pics, reports WHERE pics.picID = reports.picID");
			} catch (Exception e) {
				System.out.println("connection error " + e);
			}
		}
		protected void viewReportedDestinations(){
			try {
				res = connect.select("SELECT * FROM destinations, reports WHERE destinations.destID = reports.destID");
			} catch (Exception e){
				System.out.println("connection error " + e);
			}
		}
		protected void viewReportedUsers(){
			try {
				res = connect.select("SELECT * FROM users, reports WHERE users.userName = reports.reportedUser");
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
		
}
