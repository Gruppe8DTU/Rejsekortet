package domain;

import persistance.SQL_Connect;
import presentation.Boundary;
import presentation.Home;
import presentation.ModScreen;
import data.UserData;

public class ModController extends UserController {

	final ModScreen ms = new ModScreen();

		public ModController(UserData user, Boundary bound, SQL_Connect connect){
			super(user, bound, connect);	
			menu();
		}
		
		private void menu(){
			ms.setVisible(true);
			addActionListener(ms);
		}
		
		protected void addActionListener(ModScreen gui){
			ms.addButtonActionListener1(
					new java.awt.event.ActionListener(){
						public void actionPerformed(java.awt.event.ActionEvent evt){
							userAction = ((javax.swing.JButton)evt.getSource()).getName();
							System.out.println(userAction);
							menuAction();
						}
					}
			);
		}
		
		private void menuAction(){
			if ( super.isNumeric(userAction)){
				System.out.println(userAction);
				intAction = Integer.parseInt(userAction);
			}
			switch (intAction){
				case 1: 
					ms.setVisible(false); // go back
					break;
				case 2: 
					System.exit(0); // exit
					break;
				case 3: 
					viewReportedPosts();
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
			System.out.println("you want to view reported posts?");
		}
		protected void viewReportedPics(){
			System.out.println("viewReportedPics()");
		}
		protected void viewReportedDestinations(){
			System.out.println("reported destinations");
		}
		protected void viewReportedUsers(){
			System.out.println("reported users");
		}
		
}
