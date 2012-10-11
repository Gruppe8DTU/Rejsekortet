package services.persistance;

public class UserController {
	String userName, firstName, lastName, eMail, password;
	Boundary gui = new Boundary();
	SQL_Connect connect = new SQL_Connect();
	
	public void createUser(){
		userName = gui.getUserName();
		firstName = gui.getFirstName();
		lastName = gui.getLastName();
		eMail = gui.getEMail();
		password = gui.getPassword();
	}
	// checks if the username is taken
	private boolean isNameAvailable(String userName){
		Object[][] nameAvailability = connect.executeQuery("SELECT userName FROM users WHERE userName = " + userName);
		return nameAvailability == null;
	}
}
