package ea.andoridresourcevalues.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.FileChooserUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.text.Utilities;

import ea.andoridresourcevalues.models.EA_Rename;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.utils.EA_StaticTableObjects;
import ea.andoridresourcevalues.utils.EA_Utils;


public class EA_ModifyFolders extends JFrame implements WindowListener {

	private JFrame frame;
	private JTable table;
	ArrayList<EA_Rename> eaArrayList_RenamededFolders;
	String eaStr_ValuesFolderPath;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public JFrame start() {
		final EA_ModifyFolders window = new EA_ModifyFolders();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		return window.frame;
	}
	
	

	/**
	 * Create the application.
	 */
	public EA_ModifyFolders() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		eaArrayList_RenamededFolders = new ArrayList<>();
		eaStr_ValuesFolderPath = EA_Constants.Paths.eaStaticString_workingFolderPath 
				+ EA_Constants.Paths.eaStaticString_relativePathToParentValuesFolder + "\\";
		
		frame = new JFrame();
		frame.setBounds(100, 100, 407, 300);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(eaArrayList_RenamededFolders.size()>0){
					int n = JOptionPane.showConfirmDialog(
						    frame,
						    "Do you want to save Changes",
						    "Save",
						    JOptionPane.YES_NO_OPTION);
//					System.out.println("selected: "+ n);
					 switch (n) {
					case 0:
						eaRename();
						frame.dispose();
						break;
					case 1:
						frame.dispose();
						break;
					case -1:
						frame.dispose();
						break;
					default:
						frame.dispose();
						break;
					}
					
				}else{
					frame.dispose();
				}
				eaArrayList_RenamededFolders.clear();
				
			}
		});
		btnClose.setBounds(292, 228, 89, 23);
		frame.getContentPane().add(btnClose);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				eaRename();
				eaArrayList_RenamededFolders.clear();
				
				
			}
		});
		btnUpdate.setBounds(193, 228, 89, 23);
		frame.getContentPane().add(btnUpdate);
		
		refreshTable();
		
		
	
	JButton btnAdd = new JButton("Add");
	btnAdd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new EA_WinAddFolder().start().addWindowListener(EA_ModifyFolders.this);
		}
	});
	btnAdd.setBounds(20, 228, 89, 23);
	frame.getContentPane().add(btnAdd);
		
	}

	private void refreshTable() {

		
		try{
			if(scrollPane.isAncestorOf(table)){
				frame.remove(scrollPane);
			  scrollPane.remove(table);
			  frame.validate();
			  frame.repaint();	
			  
			  
			}
			}catch(NullPointerException e){
				
			}
		
		DefaultTableModel dm = new DefaultTableModel();
	    dm.setDataVector(getValuesArray(), new Object[] { "Name", "" });
 	    
	table = new JTable(dm);
	table.getColumn("").setMaxWidth(120);
	table.getColumn("").setCellRenderer(new ButtonRenderer());
    table.getColumn("").setCellEditor(
	new ButtonEditor(new JCheckBox()));
    
    table.getModel().addTableModelListener(new TableModelListener() {
		
		@Override
		public void tableChanged(TableModelEvent e) {

			int row = e.getFirstRow();
	        int column = e.getColumn();
			 TableModel model = (TableModel)e.getSource();
		        String columnName = model.getColumnName(column); 
		        String newValue = model.getValueAt(row, column).toString();
		        
			eaArrayList_RenamededFolders.add(new EA_Rename(EA_Utils.getColumnsArrayList().get(row), newValue, row));
			
		}
	});
    
    scrollPane = new JScrollPane(table);
	scrollPane.setBounds(10, 11, 371, 206);
	frame.getContentPane().add(scrollPane);
		
	}



	protected void eaRename() {
		
		for(EA_Rename folder : eaArrayList_RenamededFolders){
			

			
			if(folder.getEaStr_OldName().equals(folder.getEaStr_NewName())){
			File oldFile =new File( eaStr_ValuesFolderPath + folder.getEaStr_OldName());
		      File newFile = new File( eaStr_ValuesFolderPath + folder.getEaStr_NewName() );
		      oldFile.renameTo(newFile);
		}
		}//for
		
		eaArrayList_RenamededFolders.clear();
		
			
	}



	private Object[][] getValuesArray() {
		
		EA_Utils.refreshList();
		
		ArrayList<String> eaArrayList_ColumnsNames = EA_Utils.getColumnsArrayList();  
		
		Object[][] obj = new Object[eaArrayList_ColumnsNames.size()][2];
		
		for(int index = 0; index < eaArrayList_ColumnsNames.size(); index++){
			obj[index][0] = eaArrayList_ColumnsNames.get(index);
		}
		
		for(int index = 0; index < eaArrayList_ColumnsNames.size(); index++){
			obj[index][1] = "Delete";
		}

		return obj;
	}
	
	class ButtonRenderer extends JButton implements TableCellRenderer {

		  public ButtonRenderer() {
		    setOpaque(true);
		  }

		  public Component getTableCellRendererComponent(JTable table, Object value,
		      boolean isSelected, boolean hasFocus, int row, int column) {
		    if (isSelected) {
		      setForeground(table.getSelectionForeground());
		      setBackground(table.getSelectionBackground());
		    } else {
		      setForeground(table.getForeground());
		      setBackground(UIManager.getColor("Button.background"));
		    }
		    setText((value == null) ? "" : value.toString());
		    return this;
		  }
		}
	
	class ButtonEditor extends DefaultCellEditor {
		  protected JButton button;
	
		  private String label;
		  private int eaInt_SeletedRow;

		  private boolean isPushed;

		  public ButtonEditor(JCheckBox checkBox) {
		    super(checkBox);
		    button = new JButton();
		    button.setOpaque(true);
		    button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		    });
		   
		  }

		  public Component getTableCellEditorComponent(JTable table, Object value,
			      boolean isSelected, int row, int column) {
			    if (isSelected) {
			      button.setForeground(table.getSelectionForeground());
			      button.setBackground(table.getSelectionBackground());
			    } else {
			      button.setForeground(table.getForeground());
			      button.setBackground(table.getBackground());
			    }
			    label = "Delete";
			    eaInt_SeletedRow = row;
			    isPushed = true;
			    return button;
			  }

			  public Object getCellEditorValue() {
			    if (isPushed) {

			    	int n = JOptionPane.showConfirmDialog(
						    frame,
						    "Confirm Delete",
						    "Delete",
						    JOptionPane.YES_NO_OPTION);
//					System.out.println("selected: "+ n);
					 switch (n) {
					case 0:
						eaDeleteFolder(eaInt_SeletedRow);
						break;
					case 1:
						break;
					case -1:
						break;
					default:
						break;
					}

			    }
			    isPushed = false;
			    return new String(label);
			  }

			  
			public boolean stopCellEditing() {
			    isPushed = false;
			    return super.stopCellEditing();
			  }

			  protected void fireEditingStopped() {
			    super.fireEditingStopped();
			  }
}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	public void eaDeleteFolder(int eaInt_SeletedRow) {
		
		ArrayList<String> folders = EA_Utils.getColumnsArrayList();
		String path = eaStr_ValuesFolderPath + folders.get(eaInt_SeletedRow);
		File eaDelFile = new File(path);
		
		 EA_Utils.eaDeleteFile(eaDelFile);
		 EA_Utils.refreshList();

		 refreshTable();
		
	}

	


	@Override
	public void windowClosed(WindowEvent e) {
		EA_Utils.refreshList();
		refreshTable();
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
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
