package presentation;
import java.util.Scanner;

public class Boundary {
	Scanner scan = new Scanner(System.in);
	/*
	 * prompts user for an integer. if you dont need a message passed, you have to pass an empty string
	 */
	public int promptForInt(String str){
		String stringInt;
		do{
			System.out.println(str);
			stringInt = scan.nextLine();
			if(!isNumeric(stringInt))
				System.out.println("Invalid input, Enter a number");
		}while(!isNumeric(stringInt));
		int integer = Integer.parseInt(stringInt);
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
	public String promptForString(String str){
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
	
	/*
	 * displayLoggedInMenu displays menu when you are logged in
	 */
	public void displayLoggedInMenu(){
		seperator();
		System.out.println("Enter the value of your choice \n\n" +
						   "(1) - Add Destination \n" +
						   "(2) - See a list of friends \n" +
						   "(3) - See your friends latest destination \n" +
						   "(4) - See own destinations\n" +
						   "(5) - Add friend\n" +
						   "(6) - Search for specifik friends destinations\n" +
						   "(7) - Log out");
		seperator();
	}
	
	/*
	 * Prints out seperator line
	 */
	public void seperator(){
		System.out.println("-----------------------------");
	}
	
	/*
	 * Checks if a string is a number
	 */
	private boolean isNumeric(String str){
		try{
			Integer.parseInt(str);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
		
	}
}
