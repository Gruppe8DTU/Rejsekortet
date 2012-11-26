package domain;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.UserData;
import presentation.ModScreen;
import persistance.SQL_Connect;
import presentation.Boundary;

public class AdminController extends ModController {
	final ModScreen ms = new ModScreen();
	Object[][] res;
	protected String body;
	protected int currentReport = 1;
	
	public AdminController(UserData user, Boundary bound, SQL_Connect connect){
		super (user, bound, connect);
	}
	public void init(){
		System.out.println("init");
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
		System.out.println("LOL");
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
			// reimplementation needed because of case 6
			case 6:
				String user = "";
				int type = 1;
				try {
					changeUserRights(user, type);
				} catch (SQLException e) {
					System.out.println("Couldn't change user rigths " + e);
				e.printStackTrace();
			}
				break;
		}
	}
	protected void viewReportedPosts(){
		super.viewReportedPosts();
	}
	protected void viewReportedPics(){
		super.viewReportedPics();
	}
	protected void viewReportedDestinations(){
		super.viewReportedDestinations();
	}
	// Method that is admin specific and doesn't exist in mod controller
	private void changeUserRights(String uName, int type) throws SQLException{
		if (type == 1 || type == 2 || type ==3)
			connect.executeUpdate("UPDATE users SET users.type = " + type + " WHERE users.userName = " + uName);
	}	
	// Warning, deleting a user also deletes the users visits and the reports regarding the user
	public void deleteUser(String uName) throws SQLException {
		connect.executeUpdate("DELETE " + uName + " FROM users");
		connect.executeUpdate("DELETE * FROM reports WHERE reports.reportedUser = " +uName);
		connect.executeUpdate("DELETE * FROM visits WHERE visits.username = " +uName);
	}
	protected boolean isNumeric(String str){
		return super.isNumeric(str);
	}
	protected InputStream getPics(){
		return super.getPics();
	}
	protected InputStream convertResultSetToStream(ResultSet picData, InputStream is) throws Exception{
		return super.convertResultSetToStream(picData, is);
	}
}
