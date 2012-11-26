package domain;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;

import persistance.SQL_Connect;
import presentation.AdminPopup;

public class PopupController {
	final AdminPopup ap = new AdminPopup();
	SQL_Connect connect;
	private int intAction;
	private String userAction;
	Object[][] data;
	Object[] row;
	private int currentReport = 0;
	private int TOTAL_REPORTS;
	private String body;
	private int reportType;
	
	public PopupController(SQL_Connect connect){
		this.connect = connect;
		ap.setVisible(false);
	}
	public void init(Object[][] res){
		data = res;
		row = data[0];

		TOTAL_REPORTS = data.length;
		// The tables are a bit different when querying for destinations
		try {
			reportType = Integer.parseInt(row[5].toString());
		} catch (Exception e){
			reportType = 3;
		}
		ap.setVisible(true);
		showReport();
		addActionListener();
	}
	private void showReport(){
		ap.setLabel("Report " + (currentReport+1) + " of " + TOTAL_REPORTS + " reports");
		ap.setText(reportText());
	}
	public void hideChangeUserRigths(){
		System.out.println("trying to hide user rigths");
		ap.hideChangeUserRigths();
	}
	public void showChangeUserRigths(){
		System.out.println("woooooop");
		ap.showChangeUserRigths();
	}
	private String reportText(){
		String reportedUser = "";
		String body;
		String footer;
		String msg = "";
		// if 1 or 2 the data has same format for reported texts as pics
		if (reportType == 1 || reportType == 2){
				reportedUser = row[4].toString();
				String header =	 "Reported " + typeToString(reportType) + " by user : " + reportedUser + "\n";
				body =	 		 "Reported source :\n" + row[1].toString() + "\n" +
								 "Reason : " + row[6].toString() + "\n";
				footer =		 "Reported by : " + row[3].toString() + "\n"; 
				msg = header + body + footer;
		} else if (reportType == 3) {
				body = 			"Destination : " + row[1].toString() + "\n" +
								"In Street : " + row[2].toString() + ", zip : " + row[4].toString() + ", country : " + row[5].toString() + "\n" +
								"Reason : \n" + row[10].toString() + "\n";
				footer  = 		"Reported by : " + row[7].toString();
				msg = body + footer;
		} else if (reportType == 4){
				//TODO
				msg = "hej";
		}
		return msg;
	}
	public void destroy(){
		ap.setVisible(false);
	}
	
	private void addActionListener(){
		ap.addButtonActionListener1(
				new java.awt.event.ActionListener(){
					public void actionPerformed(java.awt.event.ActionEvent evt){
						userAction = ((javax.swing.JButton)evt.getSource()).getName();
						System.out.println("popup action : " + userAction);
						menuAction();
					}
				}
		);
	}
	
	private void menuAction(){
		intAction = Integer.parseInt(userAction);
		switch (intAction){
			case 1:
				System.exit(0);
				break;
			case 2:
				destroy();
				break;
			case 3:
				System.out.println("next");
				nextReport();
				break;
			case 4:
				System.out.println("previous");
				previousReport();
				break;
			case 5:
				System.out.println("you pressed 5");
				break;
			case 6:
				System.out.println("you pressed 6");
				break;
			case 7:
				System.out.println("you pressed 7");
				break;	
		}
	}	
	private void nextReport(){
		System.out.println("pressing next report");
		// overflow condition
		if (currentReport+2 > data.length){
			System.out.println("condition : " + currentReport);
			System.out.println(data.length);
			row = data[0];
			currentReport = 0;
		}
		// normal condition
		else if (currentReport+2 <= data.length){
			System.out.println("Condition : " + currentReport);
			System.out.println(data.length);
			row = data[currentReport + 1];
			currentReport++;
		}
		showReport();
	}
	private void previousReport(){
		System.out.println("pressing previous report");
		// underflow condition
		if (currentReport-1 < 0){
			row = data[data.length-1];
			currentReport = data.length;
		}
		// normal condition
		else if (currentReport-1 >= 0)
			row = data[currentReport - 1];
		currentReport--;
		showReport();
	}
	
	public void setData(Object[][] res){
		data = res;
	}
	private String getReportNumber(){

		String str = "Report no " + currentReport + " of " + data.length + "\n";
		return str;
	}
	// this toString method is a little hacky and sets the button texts according to report type
	private String typeToString(int repType){
		String str = "";
		if (repType != 2)
			ap.hidePanel();
		switch(repType){
		case 1: 
			str = "text";
			setButtons(str);
			break;
		case 2: 
			str = "picture";
			setButtons(str);
			break;
		case 3: 
			str = "destination";
			setButtons(str);
			break;
		case 4: 
			str = "user";
			setButtons(str);
			break;
		}
		return str;
	}
	private void setButtons(String str1){
		ap.setButton1("delete " + str1);
		ap.setButton2("delete user");
	}
	public void setPictures(InputStream is) throws IOException {
		ap.setPicture(is);
	}
}
