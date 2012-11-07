package services.persistance;
import java.util.Scanner;

public class Boundary {
	Scanner scan = new Scanner(System.in);
	
	public int getInt(){
		int integer = scan.nextInt();
		return integer;
	}
	/*
	 * Prints out a string
	 */
	public void printLine(String str){
		System.out.println(str);
	}
	/*
	 * Display prombtinfo and returns input
	 */
	public String prombtForString(String str){
		System.out.println(str);
		String input = scan.nextLine();
		return input;
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
	
	public String getUserNameLogIn(){
		System.out.println("Username: ");
		String uName = scan.nextLine();
		return uName;
	}
	
	/*
	 * displayLoggedInMenu displays menu when you are logged in
	 */
	public void displayLoggedInMenu(){
		System.out.println("Indtast tallet på dit valg \n\n" +
						   "(1) - Tilføj Destination \n" +
						   "(2) - Find Venner \n" +
						   "(3) - Se Venners Profiler \n" +
						   "(4) - Log Ud");
	}
	
}
