package ea.andoridresourcevalues.models;

public class EA_NewName {

	String eaStr_oldValueName,eaStr_newValueName;
	int eaInt_rowNo;
	
	
	
	public EA_NewName(String eaStr_oldValueName, String eaStr_newValueName, int eaInt_rowNo) {
		super();
		this.eaStr_oldValueName = eaStr_oldValueName;
		this.eaStr_newValueName = eaStr_newValueName;
		this.eaInt_rowNo = eaInt_rowNo;
	}
	
	public String getEaStr_oldValueName() {
		return eaStr_oldValueName;
	}
	public void setEaStr_oldValueName(String eaStr_oldValueName) {
		this.eaStr_oldValueName = eaStr_oldValueName;
	}
	public String getEaStr_newValueName() {
		return eaStr_newValueName;
	}
	public void setEaStr_newValueName(String eaStr_newValueName) {
		this.eaStr_newValueName = eaStr_newValueName;
	}
	public int getEaInt_rowNo() {
		return eaInt_rowNo;
	}
	public void setEaInt_rowNo(int eaInt_rowNo) {
		this.eaInt_rowNo = eaInt_rowNo;
	}
	
	
	

}
