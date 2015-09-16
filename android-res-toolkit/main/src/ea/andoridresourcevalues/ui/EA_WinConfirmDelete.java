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

import ea.andoridresourcevalues.EA_ValuesFolderManager;
import ea.andoridresourcevalues.XMLParser.EA_RemoveXMLElement;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.utils.EA_StaticTableObjects;
import ea.andoridresourcevalues.utils.EA_Utils;

public class EA_WinConfirmDelete {

	private JFrame frmDelete;
	private JTextField txtValueName;
	private ArrayList<String> eaArrayList_columnNames;
	private boolean[] eaSeletedCheckBoxs;
	private JCheckBox[] eaCheckBoxes;
	private JCheckBox chckbxDeleteAll;

	public JFrame start(int eaInt_SeletedRow){
		
		final EA_WinConfirmDelete window = new EA_WinConfirmDelete(eaInt_SeletedRow);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					window.frmDelete.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		return window.frmDelete;
	}
	
	private String getValueToRemove(int eaInt_SeletedRow) {
		
		ArrayList<String> eaArrayList_Rows = EA_Utils.getRowsArrayList();
//		System.out.println("Delete row : "+ eaInt_SeletedRow + " array size : "+ eaArrayList_Rows.size());
		return eaArrayList_Rows.get(eaInt_SeletedRow);
	}

	/**
	 * Create the application.
	 * @wbp.parser.constructor
	 */
	public EA_WinConfirmDelete(int eaInt_SeletedRow) {
		initialize(eaInt_SeletedRow);
	}


	public EA_WinConfirmDelete() {
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int eaInt_SeletedRow) {
		frmDelete = new JFrame();
		frmDelete.setTitle("Delete");
		frmDelete.setBounds(100, 100, 308, 293);
		frmDelete.getContentPane().setLayout(null);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EA_RemoveXMLElement remover = new EA_RemoveXMLElement();
				int eatemp_index = 0;
				String eaStr_filePath = null;
				
				for(String folderName : eaArrayList_columnNames ){
					String eaStr_RemoveValue = getValueToRemove(eaInt_SeletedRow);
					if(chckbxDeleteAll.isSelected() || eaCheckBoxes[eatemp_index].isSelected()){
						eaStr_filePath = EA_Constants.Paths.eaStaticString_workingFolderPath 
								+ EA_Constants.Paths.eaStaticString_relativePathToParentValuesFolder 
								+ "//"
								+ folderName
								+ "//"
								+ EA_Constants.eaStaticMap_FileName.get(0);
						remover.removeElement(eaStr_filePath,eaStr_RemoveValue);
						
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
				
				frmDelete.dispose();
				EA_Utils.refreshList();
				
			}
		});
		btnDelete.setBounds(92, 221, 89, 23);
		frmDelete.getContentPane().add(btnDelete);
		
		JLabel lblValueName = new JLabel("Value Name:");
		lblValueName.setBounds(10, 11, 162, 14);
		frmDelete.getContentPane().add(lblValueName);
		
		txtValueName = new JTextField();
		txtValueName.setText(getValueToRemove(eaInt_SeletedRow));
		txtValueName.setEditable(false);
		txtValueName.setBounds(10, 26, 275, 20);
		frmDelete.getContentPane().add(txtValueName);
		txtValueName.setColumns(10);
		
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane(pane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 101, 275, 109);
		frmDelete.getContentPane().add(scrollPane);
		
		JLabel lblDeleteInFolders = new JLabel("Delete in Folders");
		lblDeleteInFolders.setBounds(10, 57, 149, 14);
		frmDelete.getContentPane().add(lblDeleteInFolders);
		
		chckbxDeleteAll = new JCheckBox("Delete in All folders");
		chckbxDeleteAll.setBounds(6, 74, 279, 23);
		frmDelete.getContentPane().add(chckbxDeleteAll);
		chckbxDeleteAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (chckbxDeleteAll.isSelected()) {
					  
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
