package data;

public class UserData {
	private String uName, fName, lName, eMail, password;
	private int type;

	public UserData(String uName, String fName, String lName, String eMail, String password, int type){
		this.uName = uName;
		this.fName = fName;
		this.lName = lName;
		this.eMail = eMail;
		this.password = password;
		this.type = type;
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
}
