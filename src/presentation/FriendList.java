package presentation;
/*
 * This class handles the popup from the user menu that presents the user with a list of friends
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument.Iterator;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class FriendList extends JFrame {

	private JPanel contentPane;
	JButton btnBack;
	JTextArea textArea;
	
	public void buttonActionListener(ActionListener listener){
		btnBack.addActionListener(listener);
	}
	
	public FriendList(ArrayList<String> friends) {
		setBounds(100, 100, 100, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/* -------------------- Sets the textarea -------------------- */
		textArea = new JTextArea();
		textArea.setBounds(6, 6, 116, 140);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(6, 6, 116, 140);
		contentPane.add(scrollPane);
		
		/* -------------------- Sets the back button -------------------- */
		btnBack = new JButton("Back");
		btnBack.setBounds(5, 149, 117, 29);
		btnBack.setName("1");
		contentPane.add(btnBack);
		
		/* -------------------- Initializes the content of the textarea --------------------*/
		if(friends.isEmpty()){
			textArea.append("You dont have\nany friends yet");
			textArea.setForeground(Color.red);
		}else{
			for(String friend: friends){
		    	textArea.append(friend+"\n");
			}
		}
	}
}
