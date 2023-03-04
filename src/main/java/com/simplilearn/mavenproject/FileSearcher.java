package com.simplilearn.mavenproject;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

class FileSearcher extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String header[] = {"file name"};
	int width = 500;
	int height = 1800;	

	File dir[];
	
	JTextField search;
	JFileChooser jfc = new JFileChooser();
	
	JButton btnOpen = new JButton("select folders");
	JButton btnInsert = new JButton("insert start");
	JPanel openPanel = new JPanel();
	JPanel tablePanel = new JPanel();
	JPanel insertPanel = new JPanel();
	
	JScrollPane scrolledTable;
	static JTable table;
	
	public FileSearcher() throws Exception {
		this.setTitle("JPGMultiInserterToPPT");
		this.setSize(500, 500);
		this.setLocation(100, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.panelinit();
		this.tableinit();
		this.set();
		this.setVisible(true);
	}
	
	//set panel in main panel
	public void panelinit() {
		openPanel.add(btnOpen);
		insertPanel.add(btnInsert);
		add(openPanel,BorderLayout.NORTH);
		add(insertPanel,BorderLayout.SOUTH);
	}
	
	// set table in main panel
	public void tableinit() {
		DefaultTableModel model = new DefaultTableModel(header,0);
		
		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrolledTable = new JScrollPane(table);
		scrolledTable.setBorder(BorderFactory.createEmptyBorder(0,30,0,30));
		
		tablePanel.add(scrolledTable);
		add(tablePanel,BorderLayout.CENTER);
	}
	
	public void set() {
		btnOpen.addActionListener(this);
		btnInsert.addActionListener(this);
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setMultiSelectionEnabled(true);
	}
	
	// when the botton is pressed
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == btnOpen) {
			resetTable();
			if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				dir = jfc.getSelectedFiles();
				for (File file : dir) {
					setTable(file.getName());
				}
			}
		} else if (arg0.getSource() == btnInsert) {
			for (File file : dir) {
				try {
					PPT.writePPT(width, height, file.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	// resetting table
	public static void resetTable() {
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		int rownum = model.getRowCount();
		for (int i=0; i<rownum;i++) {model.removeRow(0);}
		
	}
	
	// setting table
	public static void setTable(String dirname) {
		
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		
		String[] record = new String[1];
		record[0] = dirname;
		
		model.addRow(record);
		
	}
	
}