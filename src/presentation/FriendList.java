package presentation;

import java.awt.BorderLayout;
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

	
	public FriendList(ArrayList<String> friends) {
		setBounds(100, 100, 100, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(6, 6, 116, 140);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(6, 6, 116, 140);
		contentPane.add(scrollPane);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(5, 149, 117, 29);
		contentPane.add(btnBack);
		
		btnBack.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						FriendList.setVisible(false);
					}
				});
		
		for(String friend: friends){
		    textArea.append(friend+"\n");
		}
	}
}
