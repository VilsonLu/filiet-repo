package ui;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

public class finalUI extends JPanel {
	private JTable CAtable;
	private JTable CDtable;
	private JTable CHtable;
	private JTable Dtable;
	private JPanel mainPanel;
	private JButton btnImportFile;
	private JButton btnStart;
	private JPanel statusPanel;
	private JPanel CAPanel;
	private JPanel CATablePanel;
	private JScrollPane CAscrollPane;
	private JLabel lblCautionAndAdvice;
	private JPanel CDPanel;
	private JPanel CDTablePanel;
	private JScrollPane CDscrollPane;
	private JLabel lblCasualtyAndDamage;
	private JPanel CHPanel;
	private JPanel CHTablePanel;
	private JScrollPane CHscrollPane;
	private JLabel lblCallForHelp;
	private JPanel DPanel;
	private JPanel DTablePanel;
	private JScrollPane DscrollPane;
	private JLabel lblDonation;
	private JPanel locationPanel;
	private JPanel fullTweetPanel;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public finalUI() {
		setLayout(null);
		
		mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(Color.GRAY));
		mainPanel.setBounds(10, 11, 150, 150);
		add(mainPanel);
		mainPanel.setLayout(null);
		
		btnImportFile = new JButton("Import File");
		btnImportFile.setBounds(34, 47, 83, 23);
		mainPanel.add(btnImportFile);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(47, 81, 57, 23);
		mainPanel.add(btnStart);
		
		statusPanel = new JPanel();
		statusPanel.setBorder(new LineBorder(Color.GRAY));
		statusPanel.setBounds(10, 172, 150, 417);
		add(statusPanel);
		statusPanel.setLayout(null);
		
		CAPanel = new JPanel();
		CAPanel.setBorder(new LineBorder(Color.GRAY));
		CAPanel.setBounds(296, 11, 444, 113);
		add(CAPanel);
		CAPanel.setLayout(null);
		
		CATablePanel = new JPanel();
		CATablePanel.setBorder(new LineBorder(Color.GRAY));
		CATablePanel.setBounds(10, 34, 424, 68);
		CAPanel.add(CATablePanel);
		CATablePanel.setLayout(null);
		
		CAscrollPane = new JScrollPane();
		CAscrollPane.setBounds(0, 0, 424, 68);
		CATablePanel.add(CAscrollPane);
		
		CAtable = new JTable();
		CAtable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Tweet Advice"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		CAtable.getColumnModel().getColumn(0).setResizable(false);
		CAscrollPane.setViewportView(CAtable);
		
		lblCautionAndAdvice = new JLabel("Caution and Advice");
		lblCautionAndAdvice.setBounds(10, 11, 125, 25);
		CAPanel.add(lblCautionAndAdvice);
		
		CDPanel = new JPanel();
		CDPanel.setLayout(null);
		CDPanel.setBorder(new LineBorder(Color.GRAY));
		CDPanel.setBounds(296, 135, 444, 113);
		add(CDPanel);
		
		CDTablePanel = new JPanel();
		CDTablePanel.setBorder(new LineBorder(Color.GRAY));
		CDTablePanel.setBounds(10, 34, 424, 68);
		CDPanel.add(CDTablePanel);
		CDTablePanel.setLayout(null);
		
		CDscrollPane = new JScrollPane();
		CDscrollPane.setBounds(0, 0, 424, 68);
		CDTablePanel.add(CDscrollPane);
		
		CDtable = new JTable();
		CDtable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Victim Name", "Object Name", "Object Detail"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		CDtable.getColumnModel().getColumn(0).setResizable(false);
		CDtable.getColumnModel().getColumn(1).setResizable(false);
		CDtable.getColumnModel().getColumn(2).setResizable(false);
		CDscrollPane.setViewportView(CDtable);
		
		lblCasualtyAndDamage = new JLabel("Casualty and Damage");
		lblCasualtyAndDamage.setBounds(10, 10, 125, 25);
		CDPanel.add(lblCasualtyAndDamage);
		
		CHPanel = new JPanel();
		CHPanel.setLayout(null);
		CHPanel.setBorder(new LineBorder(Color.GRAY));
		CHPanel.setBounds(296, 259, 444, 113);
		add(CHPanel);
		
		CHTablePanel = new JPanel();
		CHTablePanel.setBorder(new LineBorder(Color.GRAY));
		CHTablePanel.setBounds(10, 34, 424, 68);
		CHPanel.add(CHTablePanel);
		CHTablePanel.setLayout(null);
		
		CHscrollPane = new JScrollPane();
		CHscrollPane.setBounds(0, 0, 424, 68);
		CHTablePanel.add(CHscrollPane);
		
		CHtable = new JTable();
		CHtable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Victim Name"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		CHtable.getColumnModel().getColumn(0).setResizable(false);
		CHscrollPane.setViewportView(CHtable);
		
		lblCallForHelp = new JLabel("Call for Help");
		lblCallForHelp.setBounds(10, 10, 125, 25);
		CHPanel.add(lblCallForHelp);
		
		DPanel = new JPanel();
		DPanel.setLayout(null);
		DPanel.setBorder(new LineBorder(Color.GRAY));
		DPanel.setBounds(296, 383, 444, 113);
		add(DPanel);
		
		DTablePanel = new JPanel();
		DTablePanel.setBorder(new LineBorder(Color.GRAY));
		DTablePanel.setBounds(10, 34, 424, 68);
		DPanel.add(DTablePanel);
		DTablePanel.setLayout(null);
		
		DscrollPane = new JScrollPane();
		DscrollPane.setBounds(0, 0, 424, 68);
		DTablePanel.add(DscrollPane);
		
		Dtable = new JTable();
		Dtable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Resource Name", "Resource Detail"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		Dtable.getColumnModel().getColumn(0).setResizable(false);
		Dtable.getColumnModel().getColumn(1).setResizable(false);
		DscrollPane.setViewportView(Dtable);
		
		lblDonation = new JLabel("Donation");
		lblDonation.setBounds(10, 10, 125, 25);
		DPanel.add(lblDonation);
		
		locationPanel = new JPanel();
		locationPanel.setBorder(new LineBorder(Color.GRAY));
		locationPanel.setBounds(170, 11, 116, 485);
		add(locationPanel);
		
		fullTweetPanel = new JPanel();
		fullTweetPanel.setBorder(new LineBorder(Color.GRAY));
		fullTweetPanel.setBounds(170, 507, 570, 82);
		add(fullTweetPanel);
		fullTweetPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 31, 550, 40);
		textField.setEditable(false);
		fullTweetPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblFullTweet = new JLabel("Full Tweet:");
		lblFullTweet.setBounds(10, 11, 125, 14);
		fullTweetPanel.add(lblFullTweet);

	}
}
