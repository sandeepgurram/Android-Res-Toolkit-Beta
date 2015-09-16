package ea.andoridresourcevalues.models;

/**
 * @author sandeepgurram
 *
 */
public class EA_TableColumns {

	String eaStr_FolderName; // column name
	int eaInt_FolderPosition ; // column position
	int eaInt_Row;
//	public EA_TableColumns() {
//		super();
//	}

	public EA_TableColumns(String eaStr_FolderName, int eaStr_FolderPosition) {
		super();
		this.eaStr_FolderName = eaStr_FolderName;
		this.eaInt_FolderPosition = eaStr_FolderPosition;
	}

	public String getEaStr_FolderName() {
		return eaStr_FolderName;
	}

	public void setEaStr_FolderName(String eaStr_FolderName) {
		this.eaStr_FolderName = eaStr_FolderName;
	}

	public int getEaInt_FolderPosition() {
		return eaInt_FolderPosition;
	}

	public void setEaInt_FolderPosition(int eaStr_FolderPosition) {
		this.eaInt_FolderPosition = eaStr_FolderPosition;
	}

	public int geteaInt_Row() {
		return 0;
	}
}
