package domain;
/*
 * This class the login process and create user
 * Niclas & Jacob
 */

import java.sql.SQLException;

import persistance.Encrypter;
import persistance.SQL_Connect;
import presentation.Login;
import presentation.CreateUser;
import data.UserData;


public class StartController {
	private String userName, firstName, lastName, eMail, password, encryptedPass;
	private String salt;
	private int DEFAULT_USER_TYPE = 1;
	private UserData user;
	private SQL_Connect connect = new SQL_Connect();
	private CreateUser cu;
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
	 * F¿rst kalder den getUserInfo som henter infoen om brugeren og assigner de n¿dvendige variabler.
	 * Derefter kalder den createUser i SQL_Connect klassen som gemmer brugeren i databasen
	 * sidst s¾tter den variablerne til empty String sŒ den er klar til n¾ste gang.
	 */
	private void initCreateUser() throws Exception {
		cu = new CreateUser();
		cu.setVisible(true);
		
		cu.addButtonActionListener1(
				new java.awt.event.ActionListener(){
					public void actionPerformed(java.awt.event.ActionEvent evt){
						try {
							createUser();
						} catch (Exception e) {
							e.printStackTrace();
							System.exit(1);
						}
					}
				}
		);
	}
	public void createUser() {
		String pass1 = cu.getPass1();
		String pass2 = cu.getPass2();
		System.out.println("length : " + cu.getUserName().length());
		// Stop the method if input is too long
		if (cu.getUserName().length() > 30)
			return;
		Encrypter crypt;
		System.out.println("username : " + cu.getUserName());
		if (pass1.equals(pass2)  && isNameAvailable(cu.getUserName()) && pass1.length() != 0){
			System.out.println("isNAme available : " + isNameAvailable(pass1));
			try {
				crypt = new Encrypter(pass1, Encrypter.generateSalt());
				System.out.println("crypt created");
				user = new UserData(cu.getUserName(), cu.getFirstName(), cu.getSurName(), cu.getMail(), crypt.getKey().substring(32), DEFAULT_USER_TYPE, crypt.getSalt());
				// Stop method if userdata attributes are too long
				if (user.checkLengths())
					connect.createUser(user);
				else return;
				redirectToController();
				cu.setVisible(false);
			} catch (Exception e) {
				System.out.println("createUser failed, could be various reasons");
				e.printStackTrace();
			}
		} else if(!pass1.equals(pass2) || pass1.length() == 0){
			cu.wrongPass();
		}else{
			cu.wrongName();
		}
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
		int intAction = Integer.parseInt(userAction);
		switch(intAction){
			case 1:
				getLogin();
				break;
			case 2:
				System.exit(0);	
				break;				
			case 3:
				try {
					initCreateUser();	
				} catch (Exception e) {
					System.exit(1);
					System.out.println(e);
				e.printStackTrace();
			}
				login.setVisible(false);
				break;
		}
	}
	/*
	 * isNameAvailable tjekker om det userName der er blevet indtastet allerede er i brug.
	 * Den pr¿ver at hente en r¾kke fra databasen med det username som pr¿ves at oprettes og gemmer dem i det 2 dimensionelle
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
	 * Prompts user for login till he enters correct login info
	 * if user enters '0' he will be redirected to create a new user
	 */
	public void getLogin(){		
		// for at holde mvc laegges eventet her i controlleren. Dette er login knappen		
		
		userName = login.getUserName();
		password = login.getPass();
		// isLoginValid takes care of getting the salt and encrypting
		if (isLoginValid(userName, password)){
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
		new UserController(user, connect);
	}
	

	/*
	 * Calls checkLogin on SQL_connect and that returns true if password and username mathces
	 */
	public boolean isLoginValid(String userName, String password){
		Encrypter crypt;
		try {
			crypt = new Encrypter(password, connect.getSalt(userName));
			encryptedPass = crypt.getKey().substring(32);
		} catch (Exception e) {
			System.out.println("Error getting salt while creating encrypter");
			e.printStackTrace();
		}
		if (encryptedPass == null)
			System.out.println("encrypted pass equals null");
		
		boolean accepted = false; 
		try {
			if (connect.checkLogin(userName, encryptedPass)==true){
				accepted = true;
			} else {
				System.out.println("Login denied");
			}
		} catch (SQLException e) {
			System.out.println("login error in isLoginValid");
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
			Object[][] res = connect.executeQuery("select * from users where userName = '"+ userName+"'");
			Object[] userRow = res[0];
			userName = (String) userRow[0];
			firstName = (String) userRow[1];
			lastName = (String) userRow[2];
			eMail = (String) userRow[3];
			password = (String) userRow[4];
			int uType = (Integer) userRow[5];
			String salt = (String) userRow[6];
			
			user = new UserData(userName,firstName,lastName,eMail,password,uType,salt);
			return user;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	
}

