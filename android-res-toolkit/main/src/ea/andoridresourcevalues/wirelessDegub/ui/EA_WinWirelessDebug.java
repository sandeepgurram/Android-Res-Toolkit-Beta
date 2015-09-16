package ea.andoridresourcevalues.wirelessDegub.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import ea.andoridresourcevalues.models.EA_DeviceInfoModel;
import ea.andoridresourcevalues.models.EA_ValueToBeAdded;
import ea.andoridresourcevalues.ui.tables.EA_DeviceList;
import ea.andoridresourcevalues.ui.tables.EA_ValuesTable;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.wirelessDegub.EA_WinCMDCmd;
import ea.andoridresourcevalues.wirelessDegub.EA_WinCMDControler;
import ea.andoridresourcevalues.wirelessDegub.stringParser.EA_DeviceInfo;

import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EA_WinWirelessDebug {

	
	public JFrame frmWirelessDebug;
	
	private Preferences prefs;
	private boolean eaBool_isSDKEvrmntVarSet;
	private JTable table;
	private JScrollPane scrollPane;
	private boolean eaBool_rowFirstSel;
	private JButton btnConnect;
	private JButton btnRefresh;
	private String eaStr_cmdDeviceInfo;
	private EA_WinCMDControler ea_WinCMDControler;
	private ArrayList<EA_DeviceInfoModel> eaArraylst_ConnectedDevices;

	//TODO create service so that when devices connected or disconnected we can know.
	/**
	 * Create the application.
	 */
	public EA_WinWirelessDebug() {
		
		prefs = Preferences.userRoot().node(this.getClass().getName()); 
		ea_WinCMDControler = new EA_WinCMDControler(eaBool_isSDKEvrmntVarSet, prefs);
		eaArraylst_ConnectedDevices = new ArrayList<>();
		
		boolean eaBool_isSDKder = false;
		do{
			eaBool_isSDKder = ea_WinCMDControler.ea_checkSDK();
			if(!eaBool_isSDKder)
				ea_askSDKLoc();
		}while(!eaBool_isSDKder);
		initialize();
		
	}

	private void ea_askSDKLoc() {
		
		Object[] options = {"Show"};
		JOptionPane.showOptionDialog(frmWirelessDebug,
				"Could not find SDK kindly show SDK location",
			    "SDK not found",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
		
		EA_Constants.Paths.staticString_SDKpath = prefs.get(EA_Constants.Prefs.SDK_Path, "");
		JFileChooser chooser;
		String eaTemp_SDkPath = EA_Constants.Paths.staticString_SDKpath; 
		if(eaTemp_SDkPath.equals("")){
			chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		}else{
			chooser = new JFileChooser(new File(eaTemp_SDkPath));
			EA_Constants.Paths.staticString_SDKpath = eaTemp_SDkPath;
			
		}
		
		
		chooser.setDialogTitle("Select SDK Folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(frmWirelessDebug) == JFileChooser.APPROVE_OPTION) {
			// System.out.println("getCurrentDirectory(): "
			// + chooser.getCurrentDirectory());
			// System.out.println("getSelectedFile() : "
			// + chooser.getSelectedFile());
			String eaStrTemp_SDKPath = chooser.getSelectedFile().toString();
			EA_Constants.Paths.eaStaticString_workingFolderPath = eaStrTemp_SDKPath;
			prefs.put(EA_Constants.Prefs.SDK_Path, eaStrTemp_SDKPath);

		}
		
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWirelessDebug = new JFrame();
		frmWirelessDebug.setTitle("Wireless Debug");
		frmWirelessDebug.setBounds(100, 100, 556, 434);
//		frmWirelessDebug.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWirelessDebug.getContentPane().setLayout(null);
		
		JButton btnChangeSdk = new JButton("SDK");
		btnChangeSdk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ea_askSDKLoc();
			}
		});
		btnChangeSdk.setToolTipText("point to Android SDK location");
		btnChangeSdk.setBounds(10, 219, 89, 23);
		frmWirelessDebug.getContentPane().add(btnChangeSdk);
		
		btnConnect = new JButton("Connect");
		btnConnect.setEnabled(false);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ea_WinCMDControler.wirelessConct(eaArraylst_ConnectedDevices.get(table.getSelectedRow()));
			}
		});
		btnConnect.setToolTipText("Connect to phone wireless");
		btnConnect.setBounds(441, 219, 89, 23);
		frmWirelessDebug.getContentPane().add(btnConnect);
		
//		scrollPane = new JScrollPane();
//		scrollPane.setBounds(10, 31, 520, 177);
//		frmWirelessDebug.getContentPane().add(scrollPane);
		
		JLabel lblConnectedDevices = DefaultComponentFactory.getInstance().createTitle("Connected Devices");
		lblConnectedDevices.setBounds(11, 11, 413, 14);
		frmWirelessDebug.getContentPane().add(lblConnectedDevices);
		
		JLabel lblNote = new JLabel("Note : Steps to Debug wirelessly");
		lblNote.setBounds(10, 250, 414, 14);
		frmWirelessDebug.getContentPane().add(lblNote);
		
		String eaStr_NoteContent = "1. Connect your device via USB. "
				+ "\n2. Select the device."
				+ "\n3. Click \"Connect\" button."
				+ "\n4. Unplug the device."
				+ "\n5. Open Android Studio or eclipse or any other tool and run your application as if you run when it is connected through USB";
		
		JTextArea txtNote = new JTextArea();
		txtNote.setEnabled(false);
		txtNote.setLineWrap(true);
		txtNote.setText(eaStr_NoteContent);
		txtNote.setBounds(10, 266, 520, 108);
		frmWirelessDebug.getContentPane().add(txtNote);
		
		JLabel lblStatus = DefaultComponentFactory.getInstance().createLabel("Status");
		lblStatus.setBounds(10, 379, 414, 14);
		frmWirelessDebug.getContentPane().add(lblStatus);
		
		btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				refreshTable();
				
				
			}

			
		});
		btnRefresh.setBounds(342, 219, 89, 23);
		frmWirelessDebug.getContentPane().add(btnRefresh);
		
		eaArraylst_ConnectedDevices = ea_WinCMDControler.getConnectedDevicesList();
		drawTable(eaArraylst_ConnectedDevices);

		
	}

	private void drawTable(ArrayList<EA_DeviceInfoModel> eaArrayList_DeviceInfo) {

		table = new JTable(new EA_DeviceList(eaArrayList_DeviceInfo));
		
		scrollPane = new JScrollPane (table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setBounds(10, 31, 520, 177);
		table.setRowHeight(17);
		table.clearSelection();
		setColumnWidht(table.getColumnModel());
		eaBool_rowFirstSel = true;
		 ListSelectionModel cellSelectionModel = table.getSelectionModel();
		 
		    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		    cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent e) {
		        if(eaBool_rowFirstSel){
		        	btnConnect.setEnabled(true);
		    	    eaBool_rowFirstSel = false;
		        }else
		        	eaBool_rowFirstSel = true;
		      }
		      

		    });
		
		frmWirelessDebug.getContentPane().add(scrollPane);
		
	}

	private void setColumnWidht(TableColumnModel columnModel) {

		columnModel.getColumn(0).setPreferredWidth(160);
		columnModel.getColumn(1).setPreferredWidth(150);
		columnModel.getColumn(2).setPreferredWidth(50);
		columnModel.getColumn(3).setPreferredWidth(50);
		columnModel.getColumn(4).setPreferredWidth(100);
		columnModel.getColumn(5).setPreferredWidth(50);

	}
	
	private void refreshTable() {

		eaArraylst_ConnectedDevices = ea_WinCMDControler.getConnectedDevicesList();
		btnConnect.setEnabled(false);
		
		frmWirelessDebug.remove(scrollPane);
		eaArraylst_ConnectedDevices = ea_WinCMDControler.getConnectedDevicesList();
		
		
		if(!ea_WinCMDControler.tryConnectToWirelessDevices(eaArraylst_ConnectedDevices))
			eaArraylst_ConnectedDevices = ea_WinCMDControler.getConnectedDevicesList();
			
		drawTable(eaArraylst_ConnectedDevices);
	}
}
