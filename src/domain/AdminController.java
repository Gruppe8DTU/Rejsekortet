package domain;

import data.UserData;
import presentation.AdminScreen;
import persistance.SQL_Connect;
import presentation.Boundary;

public class AdminController extends ModController {
	final AdminScreen as = new AdminScreen();

	public AdminController(UserData user, Boundary bound, SQL_Connect connect){
		super (user, bound, connect);
		menu();
	}
	
	private void menu(){
		
	}
		
	private void deleteUser(String user){
		//connect.deleteUser(user);
	}
	
	private void deleteDestinations(String dest){
		//connect.deleteDestination(dest);
	}
	
	private void viewDeletedDestinations(){
		
	}
	
	// reimplementation of parent method
	protected void viewReportedPosts(){
		super.viewReportedPosts();
	}
	
	/*
	 * Denne metode skal vise anmeldte brugere, men samtidig vise dem der er blevet skjult af moderatorer oeverst derfor skal den re-implementeres.
	 */

	
	private void changeUserRights(UserData user){
		int userType = bound.promptForInt("");
		//connect.changeUserRights(user.getUserName(), userType);
	}
	
	
	
	
	
}
