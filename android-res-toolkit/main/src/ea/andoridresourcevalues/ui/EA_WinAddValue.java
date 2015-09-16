package ea.andoridresourcevalues.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import ea.andoridresourcevalues.XMLParser.EA_AddXMLElement;
import ea.andoridresourcevalues.XMLParser.EA_RemoveXMLElement;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.utils.EA_Utils;

public class EA_WinAddValue { 
	

	private JFrame frmAdd;
	private JTextField txtValueName;
	private ArrayList<String> eaArrayList_columnNames;
	private boolean[] eaSeletedCheckBoxs;
	private JCheckBox[] eaCheckBoxes;
	private JCheckBox eaChkbx_allFolders;

	public JFrame start(){
		
		final EA_WinAddValue window = new EA_WinAddValue();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					window.frmAdd.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		return window.frmAdd;
	}

	public EA_WinAddValue() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAdd = new JFrame();
		frmAdd.setTitle("Add Element");
		frmAdd.setBounds(100, 100, 308, 291);
		frmAdd.getContentPane().setLayout(null);
		
		JButton btnDelete = new JButton("Add");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EA_AddXMLElement ea_elementAdder = new EA_AddXMLElement();
				int eatemp_index = 0;
				String eaStr_filePath = null;
				
				for(String folderName : eaArrayList_columnNames ){
					if(eaChkbx_allFolders.isSelected() || eaCheckBoxes[eatemp_index].isSelected()){
						eaStr_filePath = EA_Constants.Paths.eaStaticString_workingFolderPath 
								+ EA_Constants.Paths.eaStaticString_relativePathToParentValuesFolder 
								+ "\\"
								+ folderName
								+ "\\"
								+ EA_Constants.eaStaticMap_FileName.get(0);
						ea_elementAdder.add(eaStr_filePath, txtValueName.getText(), "0dp");
						
						frmAdd.dispose();
						
//						System.out.println("Cmd to remove: "
//								+ getValueToRemove(eaInt_SeletedRow) 
//								+ " at "
//								+ eaStr_filePath 
//								+ " \nBoolean value = "
//								+ eaCheckBoxes[eatemp_index].isSelected()
//								);
//						
					}//if
					eatemp_index++;
					
					
				}//for
				
			}
		});
		btnDelete.setBounds(102, 219, 89, 23);
		frmAdd.getContentPane().add(btnDelete);
		
		JLabel lblValueName = new JLabel("Value Name:");
		lblValueName.setBounds(10, 11, 162, 14);
		frmAdd.getContentPane().add(lblValueName);
		
		txtValueName = new JTextField();
		txtValueName.setText("");
		txtValueName.setBounds(10, 26, 275, 20);
		frmAdd.getContentPane().add(txtValueName);
		txtValueName.setColumns(10);
		
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(pane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 104, 275, 102);
		frmAdd.getContentPane().add(scrollPane);
		
		JLabel lblDeleteInFolders = new JLabel("Add in Folders");
		lblDeleteInFolders.setBounds(10, 57, 275, 14);
		frmAdd.getContentPane().add(lblDeleteInFolders);
		
		eaChkbx_allFolders = new JCheckBox("In all folders"); 
		eaChkbx_allFolders.setBounds(10, 75, 97, 23);
		frmAdd.getContentPane().add(eaChkbx_allFolders);
		eaChkbx_allFolders.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (eaChkbx_allFolders.isSelected()) {
				  
					for(JCheckBox chkbx : eaCheckBoxes)
						chkbx.setEnabled(false);
				}else{
					for(JCheckBox chkbx : eaCheckBoxes)
						chkbx.setEnabled(true);
				}
				
				
			}
		});
		
		
		
		eaArrayList_columnNames = EA_Utils.getColumnsArrayList();
		
		int eaInt_index = 0;
		eaCheckBoxes = new JCheckBox[eaArrayList_columnNames.size()];
		eaSeletedCheckBoxs = new boolean[eaArrayList_columnNames.size()];
		for(String eaStr_FolderName: eaArrayList_columnNames){
			eaCheckBoxes[eaInt_index] = new JCheckBox(eaStr_FolderName);
			eaCheckBoxes[eaInt_index].setSelected(false);
			pane.add(eaCheckBoxes[eaInt_index]); 
			
			final int eaint_Finaltemp = eaInt_index;
			eaCheckBoxes[eaInt_index].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					eaSeletedCheckBoxs[eaint_Finaltemp] = eaCheckBoxes[eaint_Finaltemp].isSelected();
				}
			});
		
			eaInt_index++;
		}//for
		
	}
}
