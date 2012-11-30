package domain;

/*
 * This class controls the admin control panel and extends the moderators
 * control panel with "change user rights" and "delete user"
 * Main contributer Niclas
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.UserData;
import presentation.ModScreen;
import persistance.SQL_Connect;
import presentation.MessagePopup;
import presentation.ReportScreen;

public class AdminController extends ModController {

	public AdminController(UserData user, SQL_Connect connect) {
		super(user, connect);
	}

	public void init() {
		ms.setVisible(true);
		addActionListener();
	}

	// Action listener is added to boundary
	public void addActionListener() {
		ms.addButtonActionListener1(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userAction = ((javax.swing.JButton) evt.getSource()).getName();
				menuAction();
			}
		});
	}

	// Actual menu that is reimplemented because it has more options
	protected void menuAction() {
		int intAction = 0;
		if (isNumeric(userAction))
			intAction = Integer.parseInt(userAction);
		pc = new ReportController(connect);
		switch (intAction) {
		case 1:
			pc.destroy();
			ms.setVisible(false); // go back
			break;
		case 2:
			pc.destroy();
			System.exit(0); // exit
			break;
		case 3:
			viewReportedPosts();
			if (isEmpty()) {
				new MessagePopup("There is no reports");
			} else {
				pc.init(res, false);
			}
			break;
		case 4:
			viewReportedPics();
			if (isEmpty()) {
				new MessagePopup("There is no reports");
			} else {
				pc.init(res, true);
			}
			break;
		case 5:
			viewReportedDestinations();
			if (isEmpty()) {
				new MessagePopup("There is no reports");
			} else {
				pc.init(res, false);
			}
			break;
		// reimplementation needed because of case 6
		case 6:
			String user = "";
			try {
				changeUserRights(user);
			} catch (Exception e) {
				System.out.println("Couldn't change user rigths " + e);
				e.printStackTrace();
			}
			break;
		}
	}

	protected void viewReportedPosts() {
		super.viewReportedPosts();
	}

	protected void viewReportedPics() {
		super.viewReportedPics();
	}

	protected void viewReportedDestinations() {
		super.viewReportedDestinations();
	}

	// Method that is admin specific and doesn't exist in mod controller.
	// Another boundary is created
	private void changeUserRights(String prompt) {
		final ReportScreen changeRight = new ReportScreen();
		// the report screen is reused, and has added a method so we can change
		// the name of the radiobuttons and label
		changeRight.setRadioButtonText("User", "Moderator", "Admin");
		changeRight
				.setLabelText("Type the name of the user which rights you want to change");
		changeRight.setVisible(true);
		changeRight.addButtonListeners(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// Get the level of user rights
				int rights = changeRight.getType();
				// Get the user which rights are to be changed
				String action = ((javax.swing.JButton) evt.getSource())
						.getName();
				if (action.equals("0"))
					changeRight.setVisible(false);
				if (action.equals("1")) {
					String name = changeRight.getReportText();
					try {
						// Help method below does the actual stuff
						executePrivilege(name, rights);
					} catch (Exception e) {
						System.out.println("Couldn't execute privileges");
						e.printStackTrace();
					}
					changeRight.setVisible(false);
				}
			}
		});
	}

	// The actual help method that changes user privileges
	private boolean executePrivilege(String uName, int right) throws Exception {
		try {
			connect.setRights(uName, right);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Warning, deleting a user also deletes the users visits and the reports
	// regarding the user (Marsela)
	public void deleteUser(String uName) throws SQLException {
		connect.executeUpdate("DELETE " + uName + " FROM users");
		connect.executeUpdate("DELETE * FROM reports WHERE reports.reportedUser = "
				+ uName);
		connect.executeUpdate("DELETE * FROM visits WHERE visits.username = "
				+ uName);
	}

	protected boolean isNumeric(String str) {
		return super.isNumeric(str);
	}

	protected InputStream convertResultSetToStream(ResultSet picData,
			InputStream is) throws Exception {
		return super.convertResultSetToStream(picData, is);
	}
}
