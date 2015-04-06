package ui;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;

import main.Test;
import ontology.model.CallForHelpTweet;
import ontology.model.CasualtiesAndDamageTweet;
import ontology.model.CautionAndAdviceTweet;
import ontology.model.DonationTweet;
import ontology.retriever.OntologyRetriever;
import ontology.retriever.RetrievedTweet;
import ui.mainUI.importBtnListener;
import ui.mainUI.startBtnListener;
import javax.swing.JTextPane;
import java.awt.Font;

public class finalUI extends JPanel {
	private JTable CAtable;
	private JTable CDtable;
	private JTable CHtable;
	private JTable Dtable;
	private JPanel mainPanel;
	private JButton btnImportFile;
	private JButton btnStart;
	private JButton btnRefresh;
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
	private JTable locationtable;
	private DefaultTableModel locationModel;
	DefaultTableModel modelCA;
	DefaultTableModel modelCD;
	DefaultTableModel modelCH;
	DefaultTableModel modelD;
	
	private String path;
	private JScrollPane scrollPane;

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
		
		btnImportFile = new JButton("Import CSV File");
		btnImportFile.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnImportFile.setBounds(10, 31, 130, 23);
		btnImportFile.addActionListener(new importBtnListener());
		mainPanel.add(btnImportFile);
		
		btnStart = new JButton("Start Extraction");
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnStart.setBounds(10, 65, 130, 23);
		btnStart.addActionListener(new startBtnListener());
		mainPanel.add(btnStart);
		
		btnRefresh = new JButton("Refresh Tables");
		btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnRefresh.setBounds(10, 99, 130, 23);
		btnRefresh.addActionListener(new refreshBtnListener());
		mainPanel.add(btnRefresh);
		
		statusPanel = new JPanel();
		statusPanel.setBorder(new LineBorder(Color.GRAY));
		statusPanel.setBounds(10, 172, 150, 324);
		add(statusPanel);
		statusPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Guide:");
		lblNewLabel.setBounds(10, 11, 102, 14);
		statusPanel.add(lblNewLabel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 36, 130, 277);
		statusPanel.add(scrollPane);
		
		JTextPane txtpnFilietIeFor = new JTextPane();
		txtpnFilietIeFor.setEditable(false);
		scrollPane.setViewportView(txtpnFilietIeFor);
		txtpnFilietIeFor.setText("<+> FILIET: IE for Twitter <+>\r\n\r\n>> To get started with the extraction:\r\n      [a] To select tweet file, click Import CSV File.\r\n      [b] To start the extraction, click Start Extraction.\r\n      [c] (Optional) To refresh the tables, click Refresh Tables.\r\n\r\n>> To view information:\r\n      [a] Select a location from the Location list.");
		
		CAPanel = new JPanel();
		CAPanel.setBorder(new LineBorder(Color.GRAY));
		CAPanel.setBounds(421, 11, 444, 113);
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
		modelCA = new DefaultTableModel(
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
		};
		CAtable.setModel(modelCA);
		CAtable.getColumnModel().getColumn(0).setResizable(false);
		CAscrollPane.setViewportView(CAtable);
		
		lblCautionAndAdvice = new JLabel("CAUTION AND ADVICE CATEGORY - Extracted Information");
		lblCautionAndAdvice.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCautionAndAdvice.setBounds(10, 11, 388, 25);
		CAPanel.add(lblCautionAndAdvice);
		
		CDPanel = new JPanel();
		CDPanel.setLayout(null);
		CDPanel.setBorder(new LineBorder(Color.GRAY));
		CDPanel.setBounds(421, 135, 444, 113);
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
		modelCD = new DefaultTableModel(
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
		};
		CDtable.setModel(modelCD);
		CDtable.getColumnModel().getColumn(0).setResizable(false);
		CDtable.getColumnModel().getColumn(1).setResizable(false);
		CDtable.getColumnModel().getColumn(2).setResizable(false);
		CDscrollPane.setViewportView(CDtable);
		
		lblCasualtyAndDamage = new JLabel("CASUALTIES AND DAMAGE CATEGORY - Extracted Information");
		lblCasualtyAndDamage.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCasualtyAndDamage.setBounds(10, 10, 424, 25);
		CDPanel.add(lblCasualtyAndDamage);
		
		CHPanel = new JPanel();
		CHPanel.setLayout(null);
		CHPanel.setBorder(new LineBorder(Color.GRAY));
		CHPanel.setBounds(421, 259, 444, 113);
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
		modelCH = new DefaultTableModel(
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
		};
		CHtable.setModel(modelCH);
		CHtable.getColumnModel().getColumn(0).setResizable(false);
		CHscrollPane.setViewportView(CHtable);
		
		lblCallForHelp = new JLabel("CALL FOR HELP CATEGORY - Extracted Information");
		lblCallForHelp.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCallForHelp.setBounds(10, 10, 424, 25);
		CHPanel.add(lblCallForHelp);
		
		DPanel = new JPanel();
		DPanel.setLayout(null);
		DPanel.setBorder(new LineBorder(Color.GRAY));
		DPanel.setBounds(421, 383, 444, 113);
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
		modelD = new DefaultTableModel(
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
		};
		Dtable.setModel(modelD);
		Dtable.getColumnModel().getColumn(0).setResizable(false);
		Dtable.getColumnModel().getColumn(1).setResizable(false);
		DscrollPane.setViewportView(Dtable);
		
		lblDonation = new JLabel("DONATION CATEGORY - Extracted Information");
		lblDonation.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDonation.setBounds(10, 10, 424, 25);
		DPanel.add(lblDonation);
		
		locationPanel = new JPanel();
		locationPanel.setBorder(new LineBorder(Color.GRAY));
		locationPanel.setBounds(170, 11, 241, 485);
		add(locationPanel);
		locationPanel.setLayout(null);
		
		JScrollPane locationscrollPane = new JScrollPane();
		locationscrollPane.setBounds(10, 11, 221, 463);
		locationPanel.add(locationscrollPane);
		
		locationtable = new JTable();
		locationModel = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Location"
			}
		){
			boolean[] columnEditables = new boolean[] {
					false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		locationtable.setModel(locationModel);
		locationscrollPane.setViewportView(locationtable);
		locationtable.setCellSelectionEnabled(true);
	    ListSelectionModel cellSelectionModel = locationtable.getSelectionModel();
	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
	      public void valueChanged(ListSelectionEvent e) {
	        String selectedData = null;

	        int[] selectedRow = locationtable.getSelectedRows();
	        int[] selectedColumns = locationtable.getSelectedColumns();

	        for (int i = 0; i < selectedRow.length; i++) {
	          for (int j = 0; j < selectedColumns.length; j++) {
	            selectedData = (String) locationtable.getValueAt(selectedRow[i], selectedColumns[j]);
	          }
	        }
	        populateTable(selectedData);
	      }

	    });

		populateLocationTable();
	}
	class importBtnListener implements ActionListener{
		private JFileChooser chooser;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			chooser = new JFileChooser();
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("FILIET: Import CSV File");
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
	class refreshBtnListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {	
			locationModel.setRowCount(0);
			populateLocationTable();   
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
	public void populateTable(String location){
		RetrievedTweet rt = new RetrievedTweet();
		OntologyRetriever or = new OntologyRetriever();
		try {
			or.loadOntology("./resources/ontology/Ruby_OWL.owl");
			rt = or.getStoredTweets();
			or.removeOntologyFromManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelD.setRowCount(0);
		modelCA.setRowCount(0);
		modelCH.setRowCount(0);
		modelCD.setRowCount(0);
		ArrayList<CasualtiesAndDamageTweet> CD = rt.getRetrievedCADTweets();
		ArrayList<CallForHelpTweet> CH = rt.getRetrievedCFHTweets();
		ArrayList<CautionAndAdviceTweet> CA = rt.getRetrievedCATweets();
		ArrayList<DonationTweet> D = rt.getRetrievedDTweets();
		for (DonationTweet d :D){
			if(d.getLocationInTweet().contains(location))
				modelD.addRow(new Object[]{d.getResourceName(),d.getResourceDetails()});
		}
		for (CautionAndAdviceTweet ca :CA){
			if(ca.getLocationInTweet().contains(location))
				modelCA.addRow(new Object[]{ca.getTweetAdvice()});
		}
		for (CasualtiesAndDamageTweet cd :CD){
			if(cd.getLocationInTweet().contains(location))
				modelCD.addRow(new Object[]{cd.getVictimName(), cd.getObjectName(), cd.getObjectDetails()});
		}
		for (CallForHelpTweet ch :CH){
			if(ch.getLocationInTweet().contains(location))
				modelCH.addRow(new Object[]{ch.getVictimName()});
		}
	}
	public void populateLocationTable(){
		RetrievedTweet rt = new RetrievedTweet();
		OntologyRetriever or = new OntologyRetriever();
		ArrayList<String> locations = new ArrayList<String>();
		try {
			or.loadOntology("./resources/ontology/Ruby_OWL.owl");
			locations = or.getLocationInstances();
			or.removeOntologyFromManager();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (String l : locations) {
			locationModel.addRow(new Object[] {l});
		}
	}
}
