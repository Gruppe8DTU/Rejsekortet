package rejsekortet.domain;

import rejsekortet.data.UserData;
import rejsekortet.presentation.Boundary;

public class UserController {
	Boundary bound;
	

	
	public void loggedInMenu(UserData user){
		bound.displayLoggedInMenu();
		switch(bound.getInt()){
			case 1: // Tilf�j destination
					break;
			case 2: // find Venner
					break;
			case 3: // Se venners profiler
					break;
			case 4: // Log ud
					break;
		}
	}
}
