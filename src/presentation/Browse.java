package presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Browse extends JFrame {

	private JPanel contentPane;

	
	public Browse() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		initContent();
		
		JFileChooser chooser = new JFileChooser();
		System.out.println();
		
	}


	private void initContent() {
		JFileChooser chooser = new JFileChooser();
		contentPane.add(comp)
		
	}

}
