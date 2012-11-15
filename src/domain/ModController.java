package domain;

import persistance.SQL_Connect;
import presentation.Boundary;
import data.UserData;

public class ModController extends UserController {

		public ModController(UserData user, Boundary bound, SQL_Connect connect){
			super(user, bound, connect);
		}
		
}
