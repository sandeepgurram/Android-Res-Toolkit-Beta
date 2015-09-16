package ea.andoridresourcevalues.ui.tables;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import ea.andoridresourcevalues.models.EA_TableElements;
import ea.andoridresourcevalues.utils.EA_StaticTableObjects;
import ea.andoridresourcevalues.utils.EA_Utils;

public class EA_ValuesTable extends AbstractTableModel  {

	ArrayList<String> eaArrayList_columnNames ;     
	Object[] columnNames ;
	String[][] eaStrArray_TableElements;
	
	
	 public EA_ValuesTable() {
		super();
		this.eaArrayList_columnNames = EA_Utils.getColumnsArrayList();
		this.columnNames = eaArrayList_columnNames.toArray();
		this.eaStrArray_TableElements = new String[getRowCount()][getColumnCount()];
		setValues();
	}

	public int getColumnCount() {
	        return columnNames.length;
	    }

	    public int getRowCount() {
	        return EA_StaticTableObjects.eaStaticMapObj_TableRowsHeader.size();
	    }

	    public String getColumnName(int col) {
	        return (String) columnNames[col];
	    }

	    @Override
	    public Object getValueAt(int row, int col) {
	    	return eaStrArray_TableElements[row][col];
	    }

	    private void setValues() {

	    	for(EA_TableElements tableElements : EA_StaticTableObjects.eaStaticArrayObj_TableElements ){
	    		eaStrArray_TableElements  [tableElements.getEaInt_RowNo()] [tableElements.getEaInt_columnNo()] = tableElements.getEaStr_Value();
	    		
	    	}

		}

	    public boolean isCellEditable(int row, int col)
        { return true; }

	    public void setValueAt(Object value, int row, int col) {
	    	eaStrArray_TableElements[row][col] = value.toString();
	        fireTableCellUpdated(row, col);
	    }



}
