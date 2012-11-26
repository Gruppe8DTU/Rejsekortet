package presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class MessagePopup extends JFrame {

	private JPanel contentPane;

	public MessagePopup(String str) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(str);
		lblNewLabel.setBounds(6, 6, 288, 37);
		contentPane.add(lblNewLabel);
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(6, 43, 117, 29);
		btnClose.addActionListener(new ButtonListener());
		contentPane.add(btnClose);
		setVisible(true);
	}
	
	private class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
			
		}
	}
}
