package domain;

/*
 * This class handles the moderator control panel and also builds the ground for the admin panel
 * main contributor Niclas
 */

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import persistance.SQL_Connect;
import presentation.MessagePopup;
import presentation.ModScreen;
import presentation.AdminPopup;
import data.UserData;

public class ModController {
	final ModScreen ms = new ModScreen();

	protected SQL_Connect connect;
	protected UserData user;
	protected ReportController pc;
	protected ResultSet picData;
	protected ResultSet res = null;
	protected String userAction;
	protected String body;
	protected int currentReport = 1;
	protected ArrayList<Integer> picIDs;

	public ModController(UserData user, SQL_Connect connect) {
		this.user = user;
		this.connect = connect;
	}

	public void init() {
		/*
		 * It's a bit hacky but since the moderator can't use this button anyway
		 * it's hidden away. If we had more time we would have made a
		 * polymorphic boundary
		 */
		ms.hideUserRights();
		ms.setVisible(true);
		addActionListener();
	}

	// The action listener is added to the boundary
	public void addActionListener() {
		ms.addButtonActionListener1(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userAction = ((javax.swing.JButton) evt.getSource()).getName();
				menuAction();
			}
		});
	}

	// The switch case that handles the menu based on input from boundary
	protected void menuAction() {
		int intAction = 0;
		if (isNumeric(userAction))
			intAction = Integer.parseInt(userAction);
		pc = new ReportController(connect);
		switch (intAction) {
		case 1: // go back
			pc.destroy();
			ms.setVisible(false);
			break;
		case 2: // exit
			pc.destroy();
			System.exit(0);
			break;
		case 3:
			viewReportedPosts();
			if (isEmpty()) {
				new MessagePopup("There is no reports");
			} else {
				pc.hideBtn();
				pc.init(res, false);
			}
			break;
		case 4:
			viewReportedPics();
			if (isEmpty()) {
				new MessagePopup("There is no reports");
			} else {
				pc.hideBtn();
				pc.init(res, true);
			}
			break;
		case 5:
			viewReportedDestinations();
			if (isEmpty()) {
				new MessagePopup("There is no reports");
			} else {
				pc.hideBtn();
				pc.init(res, false);
			}
			break;
		}
	}

	protected void viewReportedPosts() {
		try {
			res = connect
					.select("SELECT reports.reportNo, reports.contentType, text.source, visits.visitID, visits.username, reports.reason, reports.reportedBy, text.text_ID "
							+ "FROM text, reports, visits "
							+ "WHERE reports.visitID = visits.visitID "
							+ "AND visits.textID = text.text_ID "
							+ "AND contentType = 1");

		} catch (Exception e) {
			System.out.println("connection error");
			e.printStackTrace();
		}
	}

	protected void viewReportedPics() {
		try {
			res = connect
					.select("SELECT reports.reportNo, reports.contentType, pics.picSource, visits.visitID, visits.username, reports.reason, reports.reportedBy, pics.PicID "
							+ "FROM pics, reports, visits "
							+ "WHERE reports.visitID = visits.visitID "
							+ "AND visits.picID = pics.picID "
							+ "AND contentType = 2");
		} catch (Exception e) {
			System.out.println("connection error " + e);
		}
	}

	protected void viewReportedDestinations() {
		try {
			res = connect
					.select("SELECT reports.reportNo, reports.contentType, destinations.name, destinations.street, destinations.city, destinations.country, reports.reason, reports.reportedBy, destinations.destID, visits.username "
							+ "FROM destinations, reports, visits "
							+ "WHERE destinations.destID = visits.destID "
							+ "AND reports.visitID = visits.visitID "
							+ "AND contentType = 3");
		} catch (Exception e) {
			System.out.println("connection error " + e);
		}
	}

	protected boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	// Convert resultset with pictures to stream
	protected InputStream convertResultSetToStream(ResultSet picData,
			InputStream is) throws Exception {
		if (picData.next())
			/* Saves the picture as a inputstream */
			is = picData.getBinaryStream(2);
		return is;
	}

	// check if the resultset is empty
	protected boolean isEmpty() {
		try {
			if (res.first()) {
				return false;
			} else
				return true;
		} catch (Exception e) {
			return true;
		}
	}
}
