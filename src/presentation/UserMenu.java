package presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserMenu extends JFrame {
	public UserMenu(String username) {
		JFrame frame = new JFrame(username);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 1));
		
		JButton b1 = new JButton("Add Destination");
		panel.add(b1);
		
		JButton b2 = new JButton("Print all friends");
		panel.add(b2);
		
		JButton b3 = new JButton("See your friends latest destinations");
		panel.add(b3);
		
		JButton b4 = new JButton("See own destinations");
		panel.add(b4);
		
		JButton b5 = new JButton("Add friend");
		panel.add(b5);
		
		JButton b6 = new JButton("See specifik friends destinations");
		panel.add(b6);
		
		JButton b7 = new JButton("Log out");
		panel.add(b7);
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

}
