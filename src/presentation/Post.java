package presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class Post extends JFrame {

	private JPanel contentPane;
	
	public Post() {
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblWriteYourPost = new JLabel("Write your post");
		lblWriteYourPost.setBounds(6, 6, 109, 16);
		contentPane.add(lblWriteYourPost);
		
		JTextArea txtrFillIn = new JTextArea(5, 20);
		txtrFillIn.setText("  \n  Fill in...");
		contentPane.add(txtrFillIn);
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(16, 243, 117, 29);
		contentPane.add(btnCancel);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(313, 243, 117, 29);
		contentPane.add(btnSubmit);
		
	}
}
