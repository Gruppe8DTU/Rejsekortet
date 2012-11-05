package services.persistance;

public class UserData {
	private String uName, fName, lName, eMail, password;

	public UserData(String uName, String fName, String lName, String eMail, String password){
		this.uName = uName;
		this.fName = fName;
		this.lName = lName;
		this.eMail = eMail;
		this.password = password;
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
}
