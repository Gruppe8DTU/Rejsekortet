package domain;

import persistance.SQL_Connect;
import presentation.Boundary;
import data.UserData;

public class ModController extends UserController {

		public ModController(UserData user, Boundary bound, SQL_Connect connect){
			super(user, bound, connect);
		}
		
		// Moderatorer faar et begraenset admin panel der re-implementeres i AdminController
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
		
		private void hidePost(){
			
		}
		
		private void hideUser(){
			
		}
		
		private void viewReportedPosts(){
			
		}
		
}
