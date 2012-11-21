package domain;

import data.UserData;
import presentation.AdminScreen;
import persistance.SQL_Connect;
import presentation.Boundary;

public class AdminController extends ModController {

	public AdminController(UserData user, Boundary bound, SQL_Connect connect){
		super (user, bound, connect);
	}
	
	// Samme som i ModController men udvidet
	protected void adminPanel(){
		super.adminPanel();
		final AdminScreen as = new AdminScreen();
		System.out.println("Admin screen initialized");
		
		// This is "view reported users" button
		as.addButtonActionListener1(
			new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt){
					
				}
			}
		);	
		as.addButtonActionListener2(
				new java.awt.event.ActionListener(){
					public void actionPerformed(java.awt.event.ActionEvent evt){
						//some action
					}
				}
			);	
	}
		
	private void deleteUser(String user){
		connect.deleteUser(user);
	}
	
	private void deleteDestinations(String dest){
		connect.deleteDestination(dest);
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
	private void viewReportedUsers(){
	
	}
	
	private void changeUserRights(UserData user){
		int userType = bound.promptForInt("");
		//connect.changeUserRights(user.getUserName(), userType);
	}
	
	
	
	
	
}
