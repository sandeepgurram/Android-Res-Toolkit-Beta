package ea.andoridresourcevalues.ui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import ea.andoridresourcevalues.EA_ValuesFolderManager;
import ea.andoridresourcevalues.XMLParser.EA_NameChanger;
import ea.andoridresourcevalues.XMLParser.EA_ValuesSetter;
import ea.andoridresourcevalues.models.EA_NewName;
import ea.andoridresourcevalues.models.EA_ValueToBeAdded;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.utils.EA_StaticTableObjects;
import ea.andoridresourcevalues.utils.EA_Utils;

public class EA_WinModifyValueName extends JFrame implements WindowListener{

	
	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private ArrayList<EA_NewName> eaArrayList_valuesToChange;
	private JButton eabtn_Update;

	public static JFrame display() {
		
		final EA_WinModifyValueName window = new EA_WinModifyValueName();
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
	public EA_WinModifyValueName() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 530, 424);
		frame.getContentPane().setLayout(null);
		
//		table = new JTable();
//		table.setBounds(225, 320, -220, -281);
//		frame.getContentPane().add(table);
		
		eaArrayList_valuesToChange = new ArrayList<>(); 
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new EA_WinAddValue().start().addWindowListener(EA_WinModifyValueName.this);;
				
			}
		});
		btnAdd.setBounds(10, 334, 89, 23);
		frame.getContentPane().add(btnAdd);
		
		JButton eaBtn_Close = new JButton("Close");
		eaBtn_Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(eaArrayList_valuesToChange.size()>0){
					
					int n = JOptionPane.showConfirmDialog(
						    frame,
						    "Do you want to save Changes",
						    "Save",
						    JOptionPane.YES_NO_OPTION);
//					System.out.println("selected: "+ n);
					 switch (n) {
					case 0:
						eaChangeName();
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
				eaArrayList_valuesToChange.clear();
			}
			
		});
		eaBtn_Close.setBounds(415, 334, 89, 23);
		frame.getContentPane().add(eaBtn_Close);
		
		refreshTable();
		
		eabtn_Update = new JButton("Update");
		eabtn_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//TODO update the value name
				eaChangeName();
				eabtn_Update.setEnabled(false);
				
			}
		});
		eabtn_Update.setBounds(324, 334, 89, 23);
		eabtn_Update.setEnabled(false);
		frame.getContentPane().add(eabtn_Update);
		
	}
	
	protected void eaChangeName() {

		new EA_NameChanger().start(eaArrayList_valuesToChange);
		eaArrayList_valuesToChange.clear();
		
	}

	private Object[][] getValuesArray() {

		EA_Utils.refreshList();
		
		ArrayList<String> eaArrayList_rowNames = EA_Utils.getRowsArrayList();  
		
		Object[][] obj = new Object[eaArrayList_rowNames.size()][2];
		
		for(int index = 0; index < eaArrayList_rowNames.size(); index++){
			obj[index][0] = eaArrayList_rowNames.get(index);
		}
		
		for(int index = 0; index < eaArrayList_rowNames.size(); index++){
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

//			    	System.out.println("clicked " + eaInt_SeletedRow);
			    	//TODO remove element code
			    	new EA_WinConfirmDelete().start(eaInt_SeletedRow).addWindowListener(EA_WinModifyValueName.this);;

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

	private void refreshTable() { 
		//TODO refresh table
		
		
		
		try{
		if(scrollPane.isAncestorOf(table)){
			frame.remove(scrollPane);
		  scrollPane.remove(table);
		  frame.validate();
		  frame.repaint();	
		  
		  
		}
		}catch(NullPointerException e){
			
		}
//			
		DefaultTableModel dm = new DefaultTableModel();
	    dm.setDataVector(getValuesArray(), new Object[] { "Value", "" });
 	    
	table = new JTable(dm);
	table.getColumn("").setMaxWidth(120);
	table.getColumn("").setCellRenderer(new ButtonRenderer());
    table.getColumn("").setCellEditor(
        new ButtonEditor(new JCheckBox()));
    
    table.getModel().addTableModelListener(new TableModelListener() {
		
		@Override
		public void tableChanged(TableModelEvent e) {

			//TODO add listner
			
			int row = e.getFirstRow();
	        int column = e.getColumn();
			 TableModel model = (TableModel)e.getSource();
		        String columnName = model.getColumnName(column);
		        String newValue = model.getValueAt(row, column).toString();
		        
		        ArrayList<String> rowsList = EA_Utils.getRowsArrayList();
		        
		        if(!newValue.equals(rowsList.get(row))){
			        EA_NewName temp = new EA_NewName(rowsList.get(row), newValue, row);
				eaArrayList_valuesToChange.add(temp);
				
				eabtn_Update.setEnabled(true);
		        }
		        eabtn_Update.setEnabled(false);
		        
			
		}
	});
    scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	scrollPane.setBounds(10, 11, 494, 312);
	frame.getContentPane().add(scrollPane);
		
	}
	
	
	@Override
	public void windowActivated(WindowEvent e) {
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
