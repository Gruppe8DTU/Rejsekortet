package domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.UserData;
import presentation.ModScreen;
import persistance.SQL_Connect;
import presentation.Boundary;
import presentation.MessagePopup;
import presentation.ReportScreen;

public class AdminController extends ModController {
	final ModScreen ms = new ModScreen();
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
			// reimplementation needed because of case 6
			case 6:
				String user = "";
				int type = 1;
				try {
					changeUserRights(user);
				} catch (Exception e) {
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
	private void changeUserRights(String prompt){
		final ReportScreen changeRight = new ReportScreen();
		changeRight.setRadioButtonText("User", "Moderator", "Admin");
		changeRight.setLabelText("Type the name of the user which rights you want to change");
		changeRight.setVisible(true);
		changeRight.addButtonListeners(
				new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						int rights = changeRight.getType();
						String action = ((javax.swing.JButton)evt.getSource()).getName();
						System.out.println("ACTION : " + action);
						
						if(action.equals("0"))
							changeRight.setVisible(false);
						if(action.equals("1")){
							String name = changeRight.getReportText();
							try {
								executePrivilege(name, rights);
							} catch (Exception e) {
								System.out.println("Couldn't execute privileges");
								e.printStackTrace();
							}
							changeRight.setVisible(false);
						}
						
					}
				}
		);
	}
	private boolean executePrivilege(String uName, int right) throws Exception{
		try {
			connect.setRights(uName, right);
			System.out.println("WIN WIN WIN");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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
