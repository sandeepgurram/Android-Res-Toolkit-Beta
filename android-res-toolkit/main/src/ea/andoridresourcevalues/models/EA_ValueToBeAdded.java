package ea.andoridresourcevalues.models;

public class EA_ValueToBeAdded {

	String eaStr_value, eaStr_ColumnName;
	int eaInt_RowNo, eaInt_ColumnNo;
	
	public EA_ValueToBeAdded(String eaStr_value, int eaInt_RowNo, int eaInt_ColumnNo, String columnName) {
		super();
		this.eaStr_value = eaStr_value;
		this.eaInt_RowNo = eaInt_RowNo;
		this.eaInt_ColumnNo = eaInt_ColumnNo;
		this.eaStr_ColumnName = columnName;
	}

	public String getEaStr_value() {
		return eaStr_value;
	}

	public void setEaStr_value(String eaStr_value) {
		this.eaStr_value = eaStr_value;
	}

	public int getEaInt_RowNo() {
		return eaInt_RowNo;
	}

	public void setEaInt_RowNo(int eaInt_RowNo) {
		this.eaInt_RowNo = eaInt_RowNo;
	}

	public int getEaInt_ColumnNo() {
		return eaInt_ColumnNo;
	}

	public void setEaInt_ColumnNo(int eaInt_ColumnNo) {
		this.eaInt_ColumnNo = eaInt_ColumnNo;
	}

	public String getEaStr_ColumnName() {
		return eaStr_ColumnName;
	}

	public void setEaStr_ColumnName(String eaStr_ColumnName) {
		this.eaStr_ColumnName = eaStr_ColumnName;
	}
	
	
	
}
