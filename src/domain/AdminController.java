package domain;

import data.UserData;
import persistance.SQL_Connect;
import presentation.Boundary;

public class AdminController extends ModController {

	public AdminController(UserData user, Boundary bound, SQL_Connect connect){
		super (user, bound, connect);
	}
	
	// Samme som i ModController men udvidet
	private void adminPanel(){
		bound.displayAdminMenu();
		switch(bound.promptForInt("")){
		case 1: 
			viewReportedUsers();
			break;
		case 2:
			viewReportedPosts();
			break;
		
		}
	}
		
	private void deleteUser(String user){
		connect.deleteUser(user);
	}
	
	private void deleteDestinations(String dest){
		connect.deleteDestination(dest);
	}
	
	private void viewDeletedDestinations(){
		
	}
	
	/*
	 * Denne metode skal vise anmeldte brugere, men samtidig vise dem der er blevet skjult af moderatorer oeverst derfor skal den re-implementeres.
	 */
	private void viewReportedUsers(){
		
	}
	
	private void changeUserRights(UserData user){
		int userType = bound.promptForInt("");
		connect.changeUserRights(user.getUserName(), userType);
	}
	
	
	
	
	
}
