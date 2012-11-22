package domain;

import persistance.SQL_Connect;
import presentation.Boundary;
import presentation.ModScreen;
import data.UserData;

public class ModController extends UserController {

		public ModController(UserData user, Boundary bound, SQL_Connect connect){
			super(user, bound, connect);
		}
		
		// Moderatorer faar et begraenset admin panel der re-implementeres i AdminController
		protected void adminPanel(){
			final ModScreen ms = new ModScreen();
			ms.setVisible(true);
			
			// back button in the footer panel
			ms.addButtonActionListener1(
					new java.awt.event.ActionListener(){
						public void actionPerformed(java.awt.event.ActionEvent evt){	
							ms.setVisible(false);
						}
					}
			);
			// Exit button in the footer panel
			ms.addButtonActionListener2(
					new java.awt.event.ActionListener(){
						public void actionPerformed(java.awt.event.ActionEvent evt){	
							System.exit(0);
						}
					}
			);	
			// View reported posts
			ms.addButtonActionListener3(
					new java.awt.event.ActionListener(){
						public void actionPerformed(java.awt.event.ActionEvent evt){	
							viewReportedPosts();
						}
					}
			);	
			// View reported pics
			ms.addButtonActionListener4(
					new java.awt.event.ActionListener(){
						public void actionPerformed(java.awt.event.ActionEvent evt){	
							viewReportedPics();
						}
					}
			);	
			// View reported destinations
			ms.addButtonActionListener5(
					new java.awt.event.ActionListener(){
						public void actionPerformed(java.awt.event.ActionEvent evt){	
							viewReportedDestinations();
						}
					}
			);	
			// View reported users
			ms.addButtonActionListener6(
					new java.awt.event.ActionListener(){
						public void actionPerformed(java.awt.event.ActionEvent evt){	
							viewReportedDestinations();
						}
					}
			);	
			
		}
		
		protected void viewReportedPosts(){
			System.out.println("you want to view reported posts?");
		}
		protected void viewReportedPics(){
			
		}
		protected void viewReportedDestinations(){
			
		}
		
}
