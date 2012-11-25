package presentation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.ScrollPane;
import java.awt.Button;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JButton;

public class ListOfFriendDest extends JFrame {

	private JPanel contentPane;
	private ArrayList<JButton> buttons;
	private /*Object[][]*/ ResultSet visits;
	
	

	public ListOfFriendDest( /*Object[][]*/ ResultSet visits) {
		this.visits = visits;
		try {
			initComponents();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public void addButtonActionListeners(java.awt.event.ActionListener listener){
		for(JButton button : buttons){
			button.addActionListener(listener);	
		}
	}
	
	
	
	/*
	 * Initializes all the components in the window
	 */
	private void initComponents() throws SQLException{
		buttons = new ArrayList();
		setBounds(100, 100, 450,300 );
		contentPane = (JPanel)this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		/* -------------------- Sets Up a panel for the destinations -------------------- */
		JPanel destPanel = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		destPanel.setLayout(gridBagLayout);
		
		/* -------------------- Sets Up a panel for the destinations -------------------- */
		
		/* -------------------- Adds buttons and labels to the panel -------------------- */
		//for(int i = 0; i < visits.length; i++){
		int i = 0;
		visits.first();
		do{
			JLabel label = new JLabel();
			//label.setText((String)visits[i][2]+" visited "+(String)visits[i][3]+" in "+(String)visits[i][4]+", "+(String)visits[i][5]);
			label.setText(visits.getString(2)+" visited "+visits.getString(3)+" in "+visits.getString(4)+", "+visits.getString(5));
			GridBagConstraints gbcLabel = new GridBagConstraints();
			gbcLabel.insets = new Insets(0, 0, 5, 5);
			gbcLabel.gridx = 0;
			gbcLabel.gridy = i;
			destPanel.add(label, gbcLabel);
			
			JButton button = new JButton();
			GridBagConstraints gbcButton = new GridBagConstraints();
			gbcButton.gridx = 1;
			gbcButton.gridy = i;
			destPanel.add(button, gbcButton);
			button.setText("see details");
			//button.setName(""+(Integer)visits[i][1]);
			button.setName(""+visits.getInt(1));
			buttons.add(button);
			i++;
			
		}while(visits.next());
		
		/* -------------------- Adds a back button and adds everything to the window -------------------- */
		JLabel fake = new JLabel();
		GridBagConstraints gbcFake = new GridBagConstraints();
		gbcFake.insets = new Insets(0, 0, 5, 5);
		gbcFake.gridx = 0;
//		gbcFake.gridy = visits.length;
		gbcFake.gridy = buttons.size();
		destPanel.add(fake, gbcFake);
		
		JButton back = new JButton();
		back.setText("Back");
		back.setName("0");
		buttons.add(back);
		GridBagConstraints gbcBack = new GridBagConstraints();
		gbcBack.gridx = 1;
//		gbcBack.gridy = visits.length;
		gbcBack.gridy = buttons.size();
		destPanel.add(back, gbcBack);
		
		JScrollPane scrollPane = new JScrollPane(destPanel);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}
}
