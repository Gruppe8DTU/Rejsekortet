package presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class ReportScreen extends JFrame {

	private JPanel contentPane;
	private JButton submit;
	private JButton cancel;
	private JTextArea textArea;
	private JRadioButton rdbtnArticle;
	private JRadioButton rdbtnDest;
	private JRadioButton rdbtnPic;
	private ButtonGroup group;
	private int type;
	private JLabel lblNewLabel;
	

	public ReportScreen() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Reason of report");
		lblNewLabel.setBounds(6, 133, 438, 25);
		contentPane.add(lblNewLabel);
		
		textArea = new JTextArea();
		textArea.setBounds(6, 170, 438, 61);
		contentPane.add(textArea);
		
		cancel = new JButton("Cancel");
		cancel.setBounds(16, 243, 117, 29);
		cancel.setName("0");
		contentPane.add(cancel);
		
		submit = new JButton("Submit");
		submit.setBounds(308, 243, 117, 29);
		submit.setName("1");
		contentPane.add(submit);
		
		group = new ButtonGroup();
		
		rdbtnArticle = new JRadioButton("Report Article");
		rdbtnArticle.setBounds(16, 88, 141, 23);
		rdbtnArticle.setActionCommand("1");
		rdbtnArticle.addActionListener(new RdbtnListener());
		group.add(rdbtnArticle);
		contentPane.add(rdbtnArticle);
		
		rdbtnPic = new JRadioButton("Report picture");
		rdbtnPic.setBounds(16, 18, 141, 23);
		rdbtnPic.setActionCommand("2");
		rdbtnPic.addActionListener(new RdbtnListener());
		group.add(rdbtnPic);
		contentPane.add(rdbtnPic);
		
		rdbtnDest = new JRadioButton("Report destination");
		rdbtnDest.setBounds(16, 53, 175, 23);
		rdbtnDest.setActionCommand("3");
		rdbtnDest.addActionListener(new RdbtnListener());
		group.add(rdbtnDest);
		contentPane.add(rdbtnDest);				
	}
	
	public int getType(){
		return type;
	}
	
	public String getReportText(){
		return textArea.getText();
	}
	
	public void addButtonListeners(ActionListener listener){
		cancel.addActionListener(listener);
		submit.addActionListener(listener);
	}
	public void setRadioButtonText(String str1, String str2, String str3){
		rdbtnArticle.setText(str1);
		rdbtnDest.setText(str2);
		rdbtnPic.setText(str3);
	}
	public void setLabelText(String str){
		lblNewLabel.setText(str);
	}

	private class RdbtnListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			type = Integer.parseInt(e.getActionCommand());
		}
	}
}
