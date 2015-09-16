package ea.andoridresourcevalues.models;

public class EA_TableRow {

	String eaStr_Name;
	int eaInt_rowNo,eaInt_ColumnNo;

//	public EA_TableRow() {
//		super();
//	}

	public EA_TableRow(String eaStr_Name, int eaInt_rowNo) {
		super();
		this.eaStr_Name = eaStr_Name;
		this.eaInt_rowNo = eaInt_rowNo;
	}

	public String getEaStr_Name() {
		return eaStr_Name;
	}

	public void setEaStr_Name(String eaStr_Name) {
		this.eaStr_Name = eaStr_Name;
	}

	public int getEaInt_rowNo() {
		return eaInt_rowNo;
	}

	public void setEaInt_rowNo(int eaInt_rowNo) {
		this.eaInt_rowNo = eaInt_rowNo;
	}

	public int geteaInt_ColumnNo() {
		return 0;
	}
	
}
