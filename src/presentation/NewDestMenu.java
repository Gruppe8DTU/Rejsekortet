package presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class NewDestMenu extends JFrame {

	private JPanel contentPane;
	private JTextField destTextField;
	private JTextField streetTextField;
	private JTextField cityTextField;
	private JTextField zipTextField;
	private JTextField countryTextField;
	private JButton btnSubmit;
	private JButton btnCancel;
	private JButton btnText;
	private JButton btnBrowse;
	
	public NewDestMenu(){
		initComponents();
	}
	
	public void addSubmitListener(java.awt.event.ActionListener listener){
		btnSubmit.addActionListener(listener);
	}
	
	public String getDestName(){
		String name = destTextField.getText();
		return name;
	}
	
	public String getStreet(){
		String street = streetTextField.getText();
		return street;
	}
	
	public String getCity(){
		return cityTextField.getText();
	}
	
	public String getZip(){
		return zipTextField.getText();
	}
	
	public String getCountry(){
		return countryTextField.getText();
	}
	

	/*
	 * Initializes the GUI window
	 */
	private void initComponents() {
		getContentPane().setLayout(null);
		
		/* -------------------- Name of destination panel -------------------- */
		JPanel panel1 = new JPanel();
		panel1.setBounds(6, 6, 438, 28);
		getContentPane().add(panel1);
		panel1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel name = new JLabel("  Name of destination:");
		panel1.add(name);
		
		destTextField = new JTextField();
		panel1.add(destTextField);
		destTextField.setColumns(10);
		
		/* -------------------- Address  panel -------------------- */
		JPanel panel2 = new JPanel();
		panel2.setBounds(6, 38, 438, 28);
		getContentPane().add(panel2);
		panel2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel street = new JLabel("  Street:");
		panel2.add(street);
		
		streetTextField = new JTextField();
		panel2.add(streetTextField);
		streetTextField.setColumns(10);
		
		/* -------------------- City panel --------------------*/
		JPanel panel3 = new JPanel();
		panel3.setBounds(6, 72, 438, 28);
		getContentPane().add(panel3);
		panel3.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("  City:");
		panel3.add(lblNewLabel_1);
		
		cityTextField = new JTextField();
		panel3.add(cityTextField);
		cityTextField.setColumns(10);
		
		/* -------------------- Zip code panel -------------------- */
		JPanel panel4 = new JPanel();
		panel4.setBounds(6, 105, 438, 28);
		getContentPane().add(panel4);
		panel4.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("  Zip code:");
		panel4.add(lblNewLabel_2);
		
		zipTextField = new JTextField();
		panel4.add(zipTextField);
		zipTextField.setColumns(10);
		
		/* -------------------- Country panel -------------------- */
		JPanel panel5 = new JPanel();
		panel5.setBounds(6, 139, 438, 28);
		getContentPane().add(panel5);
		panel5.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_3 = new JLabel("  Country:");
		panel5.add(lblNewLabel_3);
		
		countryTextField = new JTextField();
		panel5.add(countryTextField);
		countryTextField.setColumns(10);

		/* -------------------- Add pic panel -------------------- */
		JPanel panel6 = new JPanel();
		panel6.setBounds(6, 172, 438, 28);
		getContentPane().add(panel6);
		panel6.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblAddPic = new JLabel("  Add Pic:");
		panel6.add(lblAddPic);
		
		btnBrowse = new JButton("Browse");
		panel6.add(btnBrowse);
		
		/* -------------------- Add post panel -------------------- */
		JPanel panel7 = new JPanel();
		panel7.setBounds(6, 203, 438, 28);
		getContentPane().add(panel7);
		panel7.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblAddPost = new JLabel("  Add post: ");
		panel7.add(lblAddPost);
		
		btnText = new JButton("Open text editor");
		panel7.add(btnText);
		
		/* -------------------- Submit and cancel buttons -------------------- */
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(43, 243, 160, 29);
		getContentPane().add(btnCancel);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(238, 243, 160, 29);
		getContentPane().add(btnSubmit);
		
	}
		
	
}
