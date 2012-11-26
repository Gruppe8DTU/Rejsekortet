package presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GetUserNameScreen extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton submit;
	private JButton cancel;
	
	public String getUserName(){
		return textField.getText();
	}
	
	public void addButtonListeners(ActionListener listener){
		submit.addActionListener(listener);
		cancel.addActionListener(listener);
	}

	public GetUserNameScreen(String str) {
		setBounds(100, 100, 400, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(str);
		lblNewLabel.setBounds(6, 6, 388, 43);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(6, 51, 388, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		submit = new JButton("Submit");
		submit.setBounds(277, 91, 117, 29);
		submit.setName("1");
		contentPane.add(submit);
		
		cancel = new JButton("Cancel");
		cancel.setBounds(6, 91, 117, 29);
		cancel.setName("0");
		contentPane.add(cancel);
	}
}
