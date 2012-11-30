package data;
/*
 * This object holds the users data, so the controller don't have to
 */

import java.util.ArrayList;

public class UserData {
	private String uName, fName, lName, eMail, password, salt;
	private int type;
	public ArrayList<String> friendArrayList = new ArrayList<String>();
	public BinaryTree friends = new BinaryTree();
	private int DEFAULT_LENGTH = 30;

	public UserData(String uName, String fName, String lName, String eMail, String password, int type, String salt){
		this.uName = uName;
		this.fName = fName;
		this.lName = lName;
		this.eMail = eMail;
		this.password = password;
		this.type = type;
		this.salt = salt;
	}
	public boolean checkLengths(){
		if (uName.length() > DEFAULT_LENGTH || fName.length() > DEFAULT_LENGTH || lName.length() > DEFAULT_LENGTH || eMail.length() > DEFAULT_LENGTH)
			return false;
		else{
			return true;
		}
	}
	public String getUserName(){
		return uName;
	}
	
	public String getfirstName(){
		return fName;
	}
	
	public String getLastName(){
		return lName;
	}
	
	public String getEmail(){
		return eMail;
	}
	
	public String getPassword(){
		return password;
	}
	
	public int getType(){
		return type;
	}
	
	public void setSalt(String salt){
		this.salt = salt;
	}
	public String getSalt(){
		return salt;
	}
	
}
