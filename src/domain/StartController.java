package domain;

import java.sql.SQLException;

import persistance.SQL_Connect;
import presentation.Boundary;
import presentation.Login;
import presentation.CreateUser;
import data.UserData;


public class StartController {
	private String userName, firstName, lastName, eMail, password;
	private int DEFAULT_USER_TYPE = 1;
	private UserData user;
	private Boundary bound = new Boundary();
	private SQL_Connect connect = new SQL_Connect();
	final Login login = new Login();
	String userAction;
	
	public StartController(){
		login.setVisible(true);
		addActionListener();
	}
	
	public void setUserName(String name){
		this.userName = name;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	/*
	 * Opretter nye bruger i databasenn
	 * Først kalder den getUserInfo som henter infoen om brugeren og assigner de nødvendige variabler.
	 * Derefter kalder den createUser i SQL_Connect klassen som gemmer brugeren i databasen
	 * sidst sætter den variablerne til empty String så den er klar til næste gang.
	 */
	private void createUser(){
		final CreateUser cu = new CreateUser();
		cu.setVisible(true);
		
		cu.addButtonActionListener1(
				new java.awt.event.ActionListener(){
					public void actionPerformed(java.awt.event.ActionEvent evt){
						String pass1 = cu.getPass1();
						String pass2 = cu.getPass2();
						if ( pass1.equals(pass2) ){
							user = new UserData(cu.getUserName(), cu.getFirstName(), cu.getSurName(), cu.getMail(), pass1, DEFAULT_USER_TYPE);
							System.out.println("password check succesfull");
						} else {
							System.out.println("password check failed");
						}
						// check username availability
						if (isNameAvailable(user.getUserName()) ){
							System.out.println("user name available");
							// now the actual insertion into db
							try {
								connect.createUser(user);
							} catch (SQLException e) {
								System.out.println("User not created, might be connection error");
								e.printStackTrace();
							}
						} else {
							System.out.println("username not availabe or passwords inconsistent");
						}
						System.out.println("redirecting to controller");
						redirectToController();
						cu.setVisible(false);
					}
				}
		);
	}
	
	public void addActionListener(){
		login.addButtonActionListener(
				new java.awt.event.ActionListener(){
					public void actionPerformed(java.awt.event.ActionEvent evt){
						userAction = ((javax.swing.JButton)evt.getSource()).getName();
						System.out.println(userAction);
						action();
					}
				}
		);
	}
	
	private void action(){
<<<<<<< HEAD
			
			int intAction = Integer.parseInt(userAction);
		
			switch(intAction){
				case 1:
					getLogin();
					break;
				case 2:
					System.exit(0);	
					break;
				case 3:
					createUser();
					login.setVisible(false);
			}		
=======
		int intAction = Integer.parseInt(userAction);
		switch(intAction){
			case 1:
				System.out.println("get login");
				login.setVisible(false);
				getLogin();
				break;
<<<<<<< HEAD
			case 2:
				System.exit(0);	
				break;
			case 3:
				createUser();
				login.setVisible(false);				
=======
			case 3:
				createUser();
				login.setVisible(false);
				break;
			case 2:
				System.exit(0);			
>>>>>>> Friendlist done
		}
>>>>>>> cfe450faeeb7d557d8215ab1c0de1313a01bb989
	}
	/*
	 * Prombts user for login till he enters correct login info
	 * if user enters '0' he will be redirected to create a new user
	 */
	
	public void getLogin(){		
		// for at holde mvc laegges eventet her i controlleren. Dette er login knappen		
		
		userName = login.getUserName();
		System.out.println(userName);
		password = login.getPass();
		System.out.println("credentials saved in start controller");
		if (isLoginValid(userName, password)){
			System.out.println("login is valid");
			login.setVisible(false);
			user = getUser(userName);	
			redirectToController();
		} else {
			login.setText("TRY AGAIN");
		}
	}	
	
	
	/*
	 * redirects the user to the right controller
	 */
	public void redirectToController(){
		System.out.println(user.getType());
		new UserController(user, bound, connect);
	}
	
	/*
	 * isNameAvailable tjekker om det userName der er blevet indtastet allerede er i brug.
	 * Den prøver at hente en række fra databasen med det username som prøves at oprettes og gemmer dem i det 2 dimensionelle
	 * array 'nameAvailability'.
	 * Hvis dette array ikke er tomt vil der retuneres false, og hvis den er tom vil der retuneres true.
	 */
	private boolean isNameAvailable(String userName){
		Object[][] nameAvailability;
		try {
			nameAvailability = connect.executeQuery("SELECT userName FROM users WHERE userName = '" + userName+"'");
			try{
				if(nameAvailability[0].length>0)
					return false;
			}catch(ArrayIndexOutOfBoundsException e){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/*
	 * Calls checkLogin on SQL_connect and that returns true if password and username mathces
	 */
	public boolean isLoginValid(String userName, String password){
		boolean accepted = false; 
		try {
		if (connect.checkLogin(userName, password)==true){
			System.out.println("login succesfull");
			accepted = true;
		}
			
		} catch (SQLException e) {
			System.out.println("login failed");
			e.printStackTrace();
		}
		
		return accepted;
	}
	/*
	 * creates a new object of the type user and inititializes the values
	 */
	private UserData getUser(String userName){
		UserData user;
		try {
			System.out.println(userName);
			Object[][] res = connect.executeQuery("select * from users where userName = '"+ userName+"'");
			Object[] userRow = res[0];
			userName = (String) userRow[0];
			firstName = (String) userRow[1];
			lastName = (String) userRow[2];
			eMail = (String) userRow[3];
			password = (String) userRow[4];
			int uType = (Integer) userRow[5];
			
			user = new UserData(userName,firstName,lastName,eMail,password,uType);
			return user;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}

