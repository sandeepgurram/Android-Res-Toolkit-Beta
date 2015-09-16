package ea.andoridresourcevalues.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ea.andoridresourcevalues.EA_ValuesFolderManager;
import ea.andoridresourcevalues.XMLParser.EA_Updater;
import ea.andoridresourcevalues.models.EA_ValueToBeAdded;
import ea.andoridresourcevalues.ui.tables.EA_ValuesTable;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.utils.EA_StaticTableObjects;
import ea.andoridresourcevalues.utils.RowHeaderRenderer;
import ea.andoridresourcevalues.wirelessDegub.ui.EA_WinWirelessDebug;
import ea.andoridresourcevalues.utils.EA_Utils;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class EA_MainWindow implements WindowListener{

	private static Preferences prefs;
	private JFrame frmValuesManager;
	private JTextField txtfld_projectFolder;
	private EA_ValuesFolderManager valuesManger;
	private JLabel lblSelectTheWorking;
	private JLabel label;
	private JTable table;
	private ArrayList<EA_ValueToBeAdded> eaArrayList_NewValues;
	private EA_ValueToBeAdded ea_ValueToBeAdded;
	private JScrollPane scrollpane;
	private JButton eabtn_Update;
	private JButton eabtn_ModifyValue;
	private JButton eabtn_AddFolder;
	private JButton eabtn_AddValue;
	private JButton eabtn_ModifyFoder;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EA_MainWindow window = new EA_MainWindow();
					window.frmValuesManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public EA_MainWindow() {
		
		eaValuesIntiaze();
		initialize();
		
	}

	/**
	 * intialise the values
	 */
	private void eaValuesIntiaze() {
		
		prefs = Preferences.userRoot().node(this.getClass().getName()); 
		eaArrayList_NewValues = new ArrayList<>();
		EA_Constants.arrayList_WirelessConnectedDevicesIP = new ArrayList<>(); 
	}

	/**
	 * Initialize the contents of the frame.(GUI initialise)
	 */
	private void initialize() {
		
		
		
		frmValuesManager = new JFrame();
		frmValuesManager.setTitle("Android Res Toolkit");
		frmValuesManager.setBounds(100, 100, 850, 517);
		frmValuesManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmValuesManager.getContentPane().setLayout(null);

		txtfld_projectFolder = new JTextField();
		txtfld_projectFolder.setBounds(10, 36, 538, 20);
		frmValuesManager.getContentPane().add(txtfld_projectFolder);
		txtfld_projectFolder.setColumns(10);

		
		
		JButton eabtn_FileChooser = new JButton("Open");
		eabtn_FileChooser.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser;
				String eaTemp_workingPath = prefs.get(EA_Constants.Prefs.WorkingFolderPath, null); 
				if(eaTemp_workingPath.equals(null)){
					chooser = new JFileChooser();
				chooser.setCurrentDirectory(new java.io.File("."));
				}
				else{
					chooser = new JFileChooser(new File(eaTemp_workingPath));
					EA_Constants.Paths.eaStaticString_workingFolderPath = eaTemp_workingPath;
					txtfld_projectFolder.setText(eaTemp_workingPath);
				}
				
				
				chooser.setDialogTitle("Select Android Project Folder");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				if (chooser.showOpenDialog(eabtn_FileChooser) == JFileChooser.APPROVE_OPTION) {
					// System.out.println("getCurrentDirectory(): "
					// + chooser.getCurrentDirectory());
					// System.out.println("getSelectedFile() : "
					// + chooser.getSelectedFile());
					String eaStrTemp_WorkingFolderPath = chooser.getSelectedFile().toString();
					EA_Constants.Paths.eaStaticString_workingFolderPath = eaStrTemp_WorkingFolderPath;
					prefs.put(EA_Constants.Prefs.WorkingFolderPath, eaStrTemp_WorkingFolderPath);
					txtfld_projectFolder.setText(EA_Constants.Paths.eaStaticString_workingFolderPath);
					displayValues(); 

				}

			}
		});
		
		eabtn_FileChooser.setBounds(558, 35, 111, 23);
		frmValuesManager.getContentPane().add(eabtn_FileChooser);

		JLabel lblProjectFolder = new JLabel("Project Folder");
		lblProjectFolder.setBounds(10, 11, 142, 20);
		frmValuesManager.getContentPane().add(lblProjectFolder);

		JButton eabtn_Refresh = new JButton("Refresh");
		eabtn_Refresh.setToolTipText("Reload the values");
		eabtn_Refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				refreshTable();
				
				
			}

		});
		eabtn_Refresh.setBounds(580, 434, 89, 23);
		frmValuesManager.getContentPane().add(eabtn_Refresh);
		
		
		
		JLabel lblEugeneDevApps = new JLabel("Eugene Dev Apps");
		lblEugeneDevApps.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEugeneDevApps.setEnabled(false);
		lblEugeneDevApps.setBounds(255, 463, 414, 14);
		frmValuesManager.getContentPane().add(lblEugeneDevApps);
		
		lblSelectTheWorking = new JLabel("Select the working android project folder to display the values,");
		lblSelectTheWorking.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectTheWorking.setBounds(10, 169, 679, 56);
		frmValuesManager.getContentPane().add(lblSelectTheWorking);
		
		label = new JLabel("Direct the folder to \"app\" folder of your Android project. ");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 203, 679, 56);
		frmValuesManager.getContentPane().add(label);
		
		eabtn_Update = new JButton("Update");
		eabtn_Update.setToolTipText("Upadates the values in XML ");
		eabtn_Update.setEnabled(false);
		eabtn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				if(new EA_Updater().start(eaArrayList_NewValues)){
					eaArrayList_NewValues.clear();
					eabtn_Update.setEnabled(false);
				}
					
			}
		});
		eabtn_Update.setBounds(488, 434, 89, 23);
		frmValuesManager.getContentPane().add(eabtn_Update);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBorder(null);
		panel.setBounds(679, 0, 155, 477);
		frmValuesManager.getContentPane().add(panel);
		panel.setLayout(null);
		
		eabtn_ModifyFoder = new JButton("Modify");
		eabtn_ModifyFoder.setToolTipText("Change name of folder");
		eabtn_ModifyFoder.setBounds(34, 226, 89, 23);
		panel.add(eabtn_ModifyFoder);
		
		eabtn_AddFolder = new JButton("Add");
		eabtn_AddFolder.setBounds(34, 195, 85, 23);
		panel.add(eabtn_AddFolder);
		eabtn_AddFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EA_WinAddFolder().start().addWindowListener(EA_MainWindow.this);
		
			}
		});
		eabtn_AddFolder.setToolTipText("Add res folder");
		
		eabtn_ModifyValue = new JButton("Modify");
		eabtn_ModifyValue.setBounds(34, 94, 89, 23);
		panel.add(eabtn_ModifyValue);
		eabtn_ModifyValue.setToolTipText("Modify res value name");
		eabtn_ModifyValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new EA_WinModifyValueName().display().addWindowListener(EA_MainWindow.this);;
				
			}
		});
		eabtn_ModifyValue.setEnabled(false);
		
		eabtn_AddValue = new JButton("Add");
		eabtn_AddValue.setBounds(34, 60, 89, 23);
		panel.add(eabtn_AddValue);
		eabtn_AddValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new EA_WinAddValue().start().addWindowListener(EA_MainWindow.this);
				
			}
		});
		eabtn_AddValue.setToolTipText("Add new res value");
		
		JLabel lblValues = new JLabel("Values options");
		lblValues.setHorizontalAlignment(SwingConstants.CENTER);
		lblValues.setForeground(Color.WHITE);
		lblValues.setBounds(10, 35, 130, 14);
		panel.add(lblValues);
		
		JLabel lblResFolderOptions = new JLabel("Res Folder options");
		lblResFolderOptions.setHorizontalAlignment(SwingConstants.CENTER);
		lblResFolderOptions.setForeground(Color.WHITE);
		lblResFolderOptions.setBounds(10, 170, 130, 14);
		panel.add(lblResFolderOptions);
		
		JButton btnConverter = new JButton("Converter");
		btnConverter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnConverter.setBounds(34, 430, 89, 23);
		panel.add(btnConverter);
		
		JButton btnDebug = new JButton("Debug");
		btnDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							EA_WinWirelessDebug window = new EA_WinWirelessDebug();
							window.frmWirelessDebug.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		btnDebug.setToolTipText("Connect to device wireless and debug apps");
		btnDebug.setBounds(34, 395, 89, 23);
		panel.add(btnDebug);
		eabtn_ModifyFoder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new EA_ModifyFolders().start().addWindowListener(EA_MainWindow.this);
				
			}
		});

		
		
		try{
//		if(!eaTemp_workingPath.equals(null)){
			String eaTemp_workingPath = prefs.get(EA_Constants.Prefs.WorkingFolderPath, null);
			EA_Constants.Paths.eaStaticString_workingFolderPath = eaTemp_workingPath;
			txtfld_projectFolder.setText(eaTemp_workingPath);
			displayValues();
		}catch(NullPointerException e){
			eabtn_AddValue.setEnabled(false);
			eabtn_ModifyValue.setEnabled(false);
			eabtn_AddFolder.setEnabled(false);
			eabtn_ModifyFoder.setEnabled(false);
		}
//		}else{
//			
//		}
		
		

	}

	protected void refreshTable() {
		try{

			eSetTableVisiblity(false);
			
			}catch(NullPointerException e1){
				
			}
			finally{
				
				EA_Utils.clearLists();
				eabtn_Update.setEnabled(false);
				displayValues();
				
			}
		
	}


	/**
	 * to display or hide the table from user
	 * @param visibility displays table if true
	 */
	private void eSetTableVisiblity(boolean visibility) {
		if(visibility){
			eaSetTable();
		}else{
			try{
			frmValuesManager.remove(scrollpane);
			frmValuesManager.validate();
			frmValuesManager.repaint();
			}
			catch(NullPointerException e){
				
			}
		}
			
		eabtn_AddValue.setEnabled(visibility);
		eabtn_ModifyValue.setEnabled(visibility);
		eabtn_AddFolder.setEnabled(visibility);
		eabtn_ModifyFoder.setEnabled(visibility);
	}

	/**
	 * displays values in window
	 */
	protected void displayValues() {

		try{
			
		EA_Constants.Paths.eaStaticString_workingFolderPath = txtfld_projectFolder.getText();
		prefs.put(EA_Constants.Prefs.WorkingFolderPath,txtfld_projectFolder.getText());
		eabtn_ModifyValue.setEnabled(true);
	
		valuesManger = new EA_ValuesFolderManager();
		
		lblSelectTheWorking.setText("");
		label.setText("");
		
		if(valuesManger.eaValuesFolderManager_startCheckingForValues())
			eSetTableVisiblity(true);
		}catch(NullPointerException e){
			lblSelectTheWorking.setText("Select the correct working folder");
			label.setText("For example: \"C:\\Users\\User Name\\Android Projects\\Project Name\\app\"");
			
			eSetTableVisiblity(false);
		}
	}

	/**
	 *  Sets and displays values on screen(Table)
	 */
	protected void eaSetTable() {
		
		
		
        table = new JTable(new EA_ValuesTable());
		
		scrollpane = new JScrollPane (table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setBounds(10, 67, 659, 361);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(17);
		table.setCellSelectionEnabled(true);
		
		table.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {

				
				int row = e.getFirstRow();
		        int column = e.getColumn();
		        TableModel model = (TableModel)e.getSource();
		        String columnName = model.getColumnName(column);
		        String data = model.getValueAt(row, column).toString();
		        
//		        System.out.println("value changed: " + data);
		        
		        ea_ValueToBeAdded = new EA_ValueToBeAdded(data, row, column, columnName);
		        eaArrayList_NewValues.add(ea_ValueToBeAdded);
		        eabtn_Update.setEnabled(true);
				
			}
		});
		
		scrollpane.setRowHeaderView(getRowHeader(table));
		frmValuesManager.getContentPane().add(scrollpane);
		
//		Action delete = new AbstractAction()
//		{
//		    public void actionPerformed(ActionEvent e)
//		    {
//		        JTable table = (JTable)e.getSource();
//		        int modelRow = Integer.valueOf( e.getActionCommand() );
//		        ((DefaultTableModel)table.getModel()).removeRow(modelRow);
//		    }
//		};
//		ButtonColumn buttonColumn = new ButtonColumn(table, delete, 2);
		
	}

	private Component getRowHeader(JTable table) {

		ListModel lm = new AbstractListModel() {
			
			ArrayList<String> ea_RowHeaders =  EA_Utils.getRowsArrayList();
			
		      public int getSize() {
		        return ea_RowHeaders.size();
		      }

		      public Object getElementAt(int index) {
		        return ea_RowHeaders.get(index);
		      }
		      
		      
		      
		    };
		  
		    
		 JList<String> rowHeader = new JList(lm)
//		 ;
		 {

			@Override
			public String getToolTipText(MouseEvent event) {

				 int index = locationToIndex(event.getPoint());

				 Object item = getModel().getElementAt(index);
			        
				return item.toString();
			}
			 
			 
			 
		 };
				 
		 rowHeader.setVisibleRowCount(20);
		 rowHeader.setValueIsAdjusting(true);
		 rowHeader.setFocusable(true);
		 rowHeader.setAutoscrolls(true);
		    rowHeader.setFixedCellWidth(125);
		    rowHeader.setFixedCellHeight(17);
		    
		   rowHeader.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				 JList l = (JList)e.getSource();
		            ListModel m = l.getModel();
		            int index = l.locationToIndex(e.getPoint());
		            if( index>-1 ) {
		                l.setToolTipText(m.getElementAt(index).toString());
		            }
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		   

//		    rowHeader.setFixedCellHeight(table.getRowHeight()
//		        + table.getRowMargin());
		    //                           + table.getIntercellSpacing().height);
		    rowHeader.setCellRenderer(new RowHeaderRenderer(table));
		    
		return rowHeader;
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		refreshTable();
	}

	@Override
	public void windowClosing(WindowEvent e) {
		refreshTable();
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
}
