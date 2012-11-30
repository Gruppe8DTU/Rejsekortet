package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import javax.swing.JButton;
import data.images.*;

/*
 * Class ShowDest, shows the destination post selected
 * Written by: Jacob Espersen
 */

public class ShowDest extends JFrame {

	private JPanel contentPane;
	private BufferedImage image;
	private String userName, city, destName, country, text;
	private InputStream is;
	private JButton back;
	private JButton report;
	File file;

	/*
	 * Constructor initializes the variables and calls initComponents
	 */
	public ShowDest(String userName, String city, String destName,
			String country, String post, InputStream is) throws IOException {
		this.userName = userName;
		this.city = city;
		this.destName = destName;
		this.country = country;
		System.out.println();
		/*
		 * Sets the text to "No Article" if there is no post saved in the
		 * database
		 */
		if (post == null)
			this.text = "No Article";
		else
			this.text = post;
		if (is != null)
			/* Sets the picture to the picture saved in the database */
			image = ImageIO.read(is);
		else {
			/* Sets a default picture if no picture is added to the destination */
			file = new File(
				"/Users/Jacob/Documents/workspace/Rejsekortet/src/data/images/1353790262_iPhoto.png");
			image = ImageIO.read(file);
		}
		initComponents();
	}

	/*
	 * -------------------- Adds Action listeners to the two buttons
	 * --------------------
	 */
	public void buttonListener(ActionListener listener) {
		back.addActionListener(listener);
		report.addActionListener(listener);
	}

	/*
	 * Initializes the components that is gonna be shown..
	 */
	private void initComponents() {
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/* -------------------- Sets the picture -------------------- */
		JLabel pic = new JLabel(new ImageIcon(image));
		pic.setBounds(6, 6, 438, 251);
		contentPane.add(pic);

		/*
		 * -------------------- Sets the headline of the destination post
		 * --------------------
		 */
		JLabel headLine = new JLabel(userName + " visited " + destName + " in "
				+ city + ", " + country);
		headLine.setBounds(6, 269, 438, 16);
		contentPane.add(headLine);

		/* -------------------- Sets the post area -------------------- */
		TextArea postArea = new TextArea(text);
		postArea.setBounds(6, 297, 438, 146);
		postArea.setEditable(false);
		contentPane.add(postArea);

		/* -------------------- Sets the back button -------------------- */
		report = new JButton("Report");
		report.setBounds(48, 443, 117, 29);
		report.setName("1");
		contentPane.add(report);

		/* -------------------- Sets the report button -------------------- */
		back = new JButton("Back");
		back.setBounds(285, 443, 117, 29);
		back.setName("0");
		contentPane.add(back);

	}
}
