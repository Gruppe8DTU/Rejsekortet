package domain;

/*
 * Shows the report screen when a user finds content inapropriate
 * 
 */

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistance.SQL_Connect;
import presentation.AdminPopup;

public class ReportController {
	final AdminPopup ap = new AdminPopup();
	private SQL_Connect connect;
	private int intAction;
	private String userAction;
	protected ResultSet data;
	private int TOTAL_REPORTS;
	private int reportType;
	boolean pic;

	public ReportController(SQL_Connect connect) {
		this.connect = connect;
		ap.setVisible(false);
	}

	public void init(ResultSet res, boolean pic) {
		data = res;
		setSize();
		this.pic = pic;
		try {
			reportType = data.getInt(2);
			ap.setVisible(true);
			showReport();
			addActionListener();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showReport() throws Exception {
		ap.setLabel("Report " + data.getRow() + " of " + TOTAL_REPORTS
				+ " reports");
		if (pic == true) {
			setPicture();
		}
		ap.setText(reportText());
	}

	public void showChangeUserRigths() {
		ap.showChangeUserRigths();
	}

	private String reportText() throws SQLException {
		String reportedUser = null;
		String body;
		String footer;
		String msg = null;
		// if 1 or 2 the data has same format for reported texts as pics
		if (reportType == 1) {
			reportedUser = data.getString(5);
			String header = "Reported text by user : " + reportedUser + "\n";
			body = "Reported source :\n" + data.getString(3) + "\n"
					+ "Reason : " + data.getString(6) + "\n";
			footer = "Reported by : " + data.getString(7) + "\n";
			msg = header + body + footer;
			ap.setButton1("Delete text");
		} else if (reportType == 2) {
			reportedUser = data.getString(5);
			String header = "Reported picture by user : " + reportedUser + "\n";
			body = "Reason : " + data.getString(6) + "\n";
			footer = "Reported by : " + data.getString(7) + "\n";
			msg = header + body + footer;
			ap.setButton1("Delete picture");
		} else if (reportType == 3) {
			body = "Destination : " + data.getString(3) + "\n" + "In Street : "
					+ data.getString(4) + ", city : " + data.getString(5)
					+ ", country : " + data.getString(6) + "\n" + "Reason : \n"
					+ data.getString(7) + "\n";
			footer = "Reported by : " + data.getString(8);
			msg = body + footer;
			ap.setButton1("Delete destination");
		}
		return msg;
	}

	public void destroy() {
		ap.setVisible(false);
	}

	private void addActionListener() {
		ap.addButtonActionListener1(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				userAction = ((javax.swing.JButton) evt.getSource()).getName();
				menuAction();
			}
		});
	}

	private void menuAction() {
		intAction = Integer.parseInt(userAction);
		switch (intAction) {
		case 1:
			System.exit(0);
			break;
		case 2:
			destroy();
			break;
		case 3:
			nextReport();
			break;
		case 4:
			previousReport();
			break;
		case 5:

			deleteElement();
			break;
		case 6:
			deleteUser();
			break;
		case 7:
			System.out.println("you pressed 7"); // TODO
			break;
		}
	}

	private void nextReport() {
		try {
			if (!data.next())
				data.first();
			showReport();
		} catch (Exception e) {
		}

	}

	private void deleteElement() {
		try {
			if (reportType == 1) {
				connect.executeUpdate("DELETE FROM Rejsekortet.text WHERE text_ID = "
						+ data.getInt(8));
			}
			if (reportType == 2) {
				connect.executeUpdate("DELETE FROM Rejsekortet.pics WHERE picID = "
						+ data.getInt(8));
			}
			if (reportType == 3) {
				connect.executeUpdate("DELETE FROM Rejsekortet.destinations WHERE destID = "
						+ data.getInt(9));
			}
			connect.executeUpdate("DELETE FROM Rejsekortet.reports WHERE reportNo = "
					+ data.getInt(1));
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private void deleteUser() {
		try {
			if (reportType == 3)
				connect.executeUpdate("DELETE FROM Rejsekortet.users WHERE userName = '"
						+ data.getString(10) + "'");
			else
				connect.executeUpdate("DELETE FROM Rejsekortet.users WHERE userName = '"
						+ data.getString(5) + "'");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	private void previousReport() {
		try {
			if (!data.previous())
				data.last();
			showReport();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setButtons(String str1) {
		ap.setButton1("delete " + str1);
		ap.setButton2("delete user");
	}

	public void setPicture() throws Exception {
		InputStream is;
		is = data.getBinaryStream(3);
		ap.setPicture(is);
	}

	private void setSize() {
		int curRow;
		try {
			curRow = data.getRow();
			data.last();
			TOTAL_REPORTS = data.getRow();
			if (curRow == 0)
				data.first();
			else
				data.absolute(curRow);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void hideBtn() {
		ap.hideChangeUserRigths();
	}
}
