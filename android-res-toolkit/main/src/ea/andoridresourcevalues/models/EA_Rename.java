package ea.andoridresourcevalues.models;

public class EA_Rename {

	String eaStr_OldName, eaStr_NewName;
	int eaInt_rowNo;
	
	public EA_Rename(String eaStr_OldName, String eaStr_NewName, int eaInt_rowNo) {
		super();
		this.eaStr_OldName = eaStr_OldName;
		this.eaStr_NewName = eaStr_NewName;
		this.eaInt_rowNo = eaInt_rowNo;
	}
	public String getEaStr_OldName() {
		return eaStr_OldName;
	}
	public void setEaStr_OldName(String eaStr_OldName) {
		this.eaStr_OldName = eaStr_OldName;
	}
	public String getEaStr_NewName() {
		return eaStr_NewName;
	}
	public void setEaStr_NewName(String eaStr_NewName) {
		this.eaStr_NewName = eaStr_NewName;
	}
	public int getEaInt_rowNo() {
		return eaInt_rowNo;
	}
	public void setEaInt_rowNo(int eaInt_rowNo) {
		this.eaInt_rowNo = eaInt_rowNo;
	}
	
	
	
}
