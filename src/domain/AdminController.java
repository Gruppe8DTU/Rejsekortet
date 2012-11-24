package domain;

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
		super.init();
	}
	public void addActionListener(){
		super.addActionListener();
	}
	public void menuAction(){
		super.menuAction();
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
	protected void viewReportedUsers(){
		super.viewReportedUsers();
	}
	private void deleteUser(String user){
		//TODO
	}			
	private void changeUserRights(UserData user){
		int userType = bound.promptForInt("");
		//connect.changeUserRights(user.getUserName(), userType);
	}	
}
