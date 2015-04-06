package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import ontology.model.CallForHelpTweet;
import ontology.model.CasualtiesAndDamageTweet;
import ontology.model.CautionAndAdviceTweet;
import ontology.model.DonationTweet;
import ontology.retriever.OntologyRetriever;
import ontology.retriever.RetrievedTweet;
import main.Test;


public class mainUI extends JPanel {
	
	private JButton importBtn;
	private JButton btnStart;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JTabbedPane tpaneCat;
	private JScrollPane spaneCA;
	private JScrollPane spaneCD;
	private JScrollPane spaneCH;
	private JScrollPane spaneD;
	private JTable tblCA;
	private JTable tblCD;
	private JTable tblCH;
	private JTable tblD;
	public JTable getTblD() {
		return tblD;
	}
	public void setTblD(JTable tblD) {
		this.tblD = tblD;
	}
	private JTextField textField;
	private JLabel lblNewLabel_1;
	private JTextField textField_1;
	private JLabel lblTweetInformation;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextPane textPane_1;
	private JPanel panel_4;
	private JLabel lblNewLabel_2;
	DefaultTableModel modelCA;
	DefaultTableModel modelCD;
	DefaultTableModel modelCH;
	DefaultTableModel modelD;
	private String path;

	/**
	 * Create the panel.
	 */
	public mainUI() {
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setLayout(null);
		
		importBtn = new JButton("Import File");
		importBtn.setBounds(20, 291, 100, 25);
		importBtn.addActionListener(new importBtnListener());
		add(importBtn);
		
		btnStart = new JButton("Start");
		btnStart.setBounds(20, 327, 100, 25);
		btnStart.addActionListener(new startBtnListener());
		add(btnStart);
		
		JPanel tablePanel = new JPanel();
		tablePanel.setForeground(Color.LIGHT_GRAY);
		tablePanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		tablePanel.setBounds(139, 11, 600, 350);
		add(tablePanel);
		tablePanel.setLayout(null);
		
		tpaneCat = new JTabbedPane(JTabbedPane.TOP);
		tpaneCat.setBounds(10, 11, 580, 328);
		tablePanel.add(tpaneCat);
		
		spaneCA = new JScrollPane();
		tpaneCat.addTab("CAUTION AND ADVICE", null, spaneCA, null);
		
		modelCA = new DefaultTableModel(
				new Object[][] {},
				new String[] {
					"Tweet Advice"
				}
			);
		tblCA = new JTable();
		tblCA.setModel(modelCA);
		tblCA.setEnabled(false);
		tblCA.setRowSelectionAllowed(true);
		tblCA.getColumnModel().getColumn(0).setResizable(false);
		tblCA.getTableHeader().setReorderingAllowed(false);
		tblCA.setCellSelectionEnabled(true);
		spaneCA.setViewportView(tblCA);
		
		spaneCD = new JScrollPane();
		tpaneCat.addTab("CASUALTIES AND DAMAGE", null, spaneCD, null);
		
		modelCD = new DefaultTableModel(
				new Object[][] {},
				new String[] {
					"Victim Name", "Object Name", "Object Details"
				}
			);
		tblCD = new JTable();
		tblCD.setModel(modelCD);
		tblCD.setEnabled(false);
		tblCD.setRowSelectionAllowed(true);
		tblCD.getColumnModel().getColumn(0).setResizable(false);
		tblCD.getColumnModel().getColumn(1).setResizable(false);
		tblCD.getColumnModel().getColumn(2).setResizable(false);
		tblCD.getTableHeader().setReorderingAllowed(false);
		tblCD.setCellSelectionEnabled(true);
		spaneCD.setViewportView(tblCD);
		
		spaneCH = new JScrollPane();
		tpaneCat.addTab("CALL FOR HELP", null, spaneCH, null);
		
		modelCH = new DefaultTableModel(
				new Object[][] {},
				new String[] {
					"Victim Name"
				}
			);
		tblCH = new JTable();
		tblCH.setModel(modelCH);
		tblCH.setEnabled(false);
		tblCH.setRowSelectionAllowed(true);
		tblCH.getColumnModel().getColumn(0).setResizable(false);
		tblCH.getTableHeader().setReorderingAllowed(false);
		tblCH.setCellSelectionEnabled(true);
		spaneCH.setViewportView(tblCH);
		
		spaneD = new JScrollPane();
		tpaneCat.addTab("DONATION", null, spaneD, null);
		
		modelD = new DefaultTableModel(new Object[][] {},new String[] {"Resource Name", "Resource Details"});
		tblD = new JTable();
		tblD.setModel(modelD);
		tblD.setFocusable(false);
		tblD.setRowSelectionAllowed(true);
		tblD.getColumnModel().getColumn(0).setResizable(false);
		tblD.getColumnModel().getColumn(1).setResizable(false);
		tblD.getTableHeader().setReorderingAllowed(false);
		tblD.setCellSelectionEnabled(true);
		spaneD.setViewportView(tblD);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(10, 372, 120, 217);
		add(panel_1);
		panel_1.setLayout(null);
		
		textPane_1 = new JTextPane();
		textPane_1.setEditable(false);
		textPane_1.setBounds(10, 31, 100, 176);
		panel_1.add(textPane_1);
		
		panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4.setBounds(9, 30, 102, 178);
		panel_1.add(panel_4);
		
		lblNewLabel_2 = new JLabel("Status:");
		lblNewLabel_2.setBounds(10, 5, 46, 20);
		panel_1.add(lblNewLabel_2);
		
		panel_2 = new JPanel();
		panel_2.setBounds(10, 11, 120, 350);
		add(panel_2);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_3.setBounds(139, 372, 599, 217);
		add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tweet Content:");
		lblNewLabel.setBounds(10, 86, 120, 20);
		panel_3.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setForeground(Color.LIGHT_GRAY);
		textField.setBackground(Color.WHITE);
		textField.setEditable(false);
		textField.setBounds(107, 54, 180, 20);
		panel_3.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Tweet Handle:");
		lblNewLabel_1.setBounds(10, 55, 120, 20);
		panel_3.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBackground(Color.WHITE);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(409, 55, 180, 20);
		panel_3.add(textField_1);
		
		lblTweetInformation = new JLabel("Tweet Information:");
		lblTweetInformation.setBounds(10, 11, 120, 20);
		panel_3.add(lblTweetInformation);
		
		JTextPane textPane = new JTextPane();
		textPane.setBackground(Color.WHITE);
		textPane.setForeground(Color.LIGHT_GRAY);
		textPane.setEditable(false);
		textPane.setBounds(109, 86, 178, 81);
		panel_3.add(textPane);
		
		JLabel lblGeolocation = new JLabel("GeoLocation:");
		lblGeolocation.setBounds(293, 54, 120, 20);
		panel_3.add(lblGeolocation);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBackground(Color.WHITE);
		textField_2.setBounds(409, 86, 180, 20);
		panel_3.add(textField_2);
		
		JLabel lblLocationInTweet = new JLabel("Location in Tweet:");
		lblLocationInTweet.setBounds(293, 85, 120, 20);
		panel_3.add(lblLocationInTweet);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBackground(Color.WHITE);
		textField_3.setBounds(409, 117, 180, 20);
		panel_3.add(textField_3);
		
		JLabel lblTimestamp = new JLabel("Tweet Timestamp:");
		lblTimestamp.setBounds(293, 116, 120, 20);
		panel_3.add(lblTimestamp);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBackground(Color.WHITE);
		textField_4.setBounds(409, 148, 180, 20);
		panel_3.add(textField_4);
		
		JLabel lblTweetDate = new JLabel("Tweet Date:");
		lblTweetDate.setBounds(293, 147, 120, 20);
		panel_3.add(lblTweetDate);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(107, 85, 181, 83);
		panel_3.add(panel);
		
		populateTable();
	}
	class importBtnListener implements ActionListener{
		private JFileChooser chooser;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			chooser = new JFileChooser();
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Import CSV");
		    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(".csv", "csv");
		    chooser.setFileFilter(filter);
		    chooser.setAcceptAllFileFilterUsed(false);

		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
		      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
		      path = chooser.getSelectedFile().toString();
		      path = path.replaceAll("\\\\", "/");
		    } else {
		      System.out.println("No Selection ");
		    }
		    
		   
		}

	}
	class startBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Test test = new Test();
			try {
				test.startIE(path);
			} catch (Exception e) {
				//TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
			}
		}

	}
	public void populateTable(){
		RetrievedTweet rt = new RetrievedTweet();
		OntologyRetriever or = new OntologyRetriever();
		try {
			or.loadOntology("./resources/ontology/Ruby_OWL.owl");
			rt = or.getStoredTweets();
			or.removeOntologyFromManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<CasualtiesAndDamageTweet> CD = rt.getRetrievedCADTweets();
		ArrayList<CallForHelpTweet> CH = rt.getRetrievedCFHTweets();
		ArrayList<CautionAndAdviceTweet> CA = rt.getRetrievedCATweets();
		ArrayList<DonationTweet> D = rt.getRetrievedDTweets();
		for (DonationTweet d :D){
			modelD.addRow(new Object[]{d.getResourceName(),d.getResourceDetails()});
		}
		for (CautionAndAdviceTweet ca :CA){
			modelCA.addRow(new Object[]{ca.getTweetAdvice()});
		}
		for (CasualtiesAndDamageTweet cd :CD){
			modelCD.addRow(new Object[]{cd.getVictimName(), cd.getObjectName(), cd.getObjectDetails()});
		}
		for (CallForHelpTweet ch :CH){
			modelCH.addRow(new Object[]{ch.getVictimName()});
		}
	}
}


