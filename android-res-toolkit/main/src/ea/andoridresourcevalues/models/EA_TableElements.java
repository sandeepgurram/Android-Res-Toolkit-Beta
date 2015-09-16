package ea.andoridresourcevalues.models;

public class EA_TableElements {

	int eaInt_columnNo, eaInt_RowNo;
	String eaStr_Value;
	
	public EA_TableElements(int eaInt_columnNo, int eaInt_RowNo, String eaStr_Value) {
		
		this.eaInt_columnNo = eaInt_columnNo;
		this.eaInt_RowNo = eaInt_RowNo;
		this.eaStr_Value = eaStr_Value;
		
	}
	
	
	
//	public EA_TableElements() {
//	super();
//}

	public int getEaInt_columnNo() {
		return eaInt_columnNo;
	}
	
	public void setEaInt_columnNo(int eaInt_columnNo) {
		this.eaInt_columnNo = eaInt_columnNo;
	}
	
	public int getEaInt_RowNo() {
		return eaInt_RowNo;
	}
	
	public void setEaInt_RowNo(int eaInt_RowNo) {
		this.eaInt_RowNo = eaInt_RowNo;
	}
	
	public String getEaStr_Value() {
		return eaStr_Value;
	}
	
	public void setEaStr_Value(String eaStr_Value) {
		this.eaStr_Value = eaStr_Value;
	}	
	
}
