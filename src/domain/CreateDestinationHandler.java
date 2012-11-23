package domain;

import presentation.NewDestMenu;
import data.UserData;

public class CreateDestinationHandler {
	UserData user;
	String destName, street, city, country;

	public CreateDestinationHandler(UserData user){
		this.user = user;
		newDest();
	}
	
	private void newDest(){
		final NewDestMenu newDesti = new NewDestMenu();
		/**/
		newDesti.addSubmitListener(
				new java.awt.event.ActionListener(){
					public void actionPerformed(java.awt.event.ActionEvent evt){
						destName = newDesti.getDestName();
						street
				
					}
				});
	}
	
}