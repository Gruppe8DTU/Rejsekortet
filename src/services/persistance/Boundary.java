package services.persistance;
import java.util.Scanner;

public class Boundary {
	Scanner scan = new Scanner(System.in);
	
	/*
	 * Gets user name from user
	 */
	public String getUserName(){
		System.out.println("Type the username you want: ");
		String uName = scan.nextLine();
		return uName;
	}
	
	/*
	 * gets first name from user
	 */
	public String getFirstName(){
		System.out.println("Type your sir name: ");
		String fName = scan.nextLine();
		return fName;
	}
	
	/*
	 * Gets last name from user
	 */
	public String getLastName(){
		System.out.println("Type your last name: ");
		String lName = scan.nextLine();
		return lName;
	}
	
	/*
	 * Gets email from user
	 */
	public String getEMail(){
		System.out.println("Type your e-mail address: ");
		String email = scan.nextLine();
		return email;
	}
	
	/*
	 * Gets a password from user, and makes sure that he typed it right by making him type it twice
	 */
	public String getPassword(){
		String pass1, pass2;
		do{
			System.out.println("Type a password: ");
			pass1 = scan.nextLine();
			System.out.println("Repeat the password: ");
			pass2 = scan.nextLine();
			if(!pass1.equals(pass2))
				System.out.println("They do not match. Try again");
		}while(!pass1.equals(pass2));
		return pass1;
	}
}
