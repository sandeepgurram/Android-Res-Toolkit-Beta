package ea.andoridresourcevalues;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import ea.andoridresourcevalues.XMLParser.EA_ValuesSetter;
import ea.andoridresourcevalues.models.EA_TableColumns;
import ea.andoridresourcevalues.models.EA_TableElements;
import ea.andoridresourcevalues.models.EA_TableRow;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.utils.EA_StaticTableObjects;

public class EA_ValuesFolderManager {

//	private String eaStr_WorkingFolderPath;
//	private ArrayList<EA_TableColumns> eaArrayList_valueFolderDetails;
//	private ArrayList<EA_TableElements> eaArrayList_TableElements;
	private EA_ValuesSetter eaObj_ValuesSetter;
	

	public EA_ValuesFolderManager() {
		
		intialise();

	}

	private void intialise() {

		
		//initailising static table objects, so that they can be used anywhere.
		if(EA_StaticTableObjects.eaStaticMapObj_TableColumnsHeader == null){
			EA_StaticTableObjects.eaStaticMapObj_TableColumnsHeader = new HashMap<>();
		}
		
		if(EA_StaticTableObjects.eaStaticMapObj_TableRowsHeader == null)
			EA_StaticTableObjects.eaStaticMapObj_TableRowsHeader = new HashMap<>();
		
		if(EA_StaticTableObjects.eaStaticArrayObj_TableElements == null)
			EA_StaticTableObjects.eaStaticArrayObj_TableElements = new ArrayList<>();
		
		if(EA_Constants.eaStaticMap_FileName == null)
			EA_Constants.eaStaticMap_FileName = new HashMap<>();
		
	}

	public boolean eaValuesFolderManager_startCheckingForValues(){
		
		eaSetColumnHeaders();
		eaCheckForValuesFiles();
		
		return true;
	}
	
	
	/**
	 * checks the values folders in the project and sets the columns. "eaArrayList_valueFolderDetails" stores the value folder names.
	 */
	private void eaSetColumnHeaders() {

		File directory = new File(
				EA_Constants.Paths.eaStaticString_workingFolderPath 
				+ EA_Constants.Paths.eaStaticString_relativePathToParentValuesFolder);
		File[] subdirs = directory.listFiles();
		int eaInt_columnNo = 0;
		EA_TableColumns eaTableColumns_valueFolderDetails;
		for (File dir : subdirs) {
			if (eaIsValueFolder(dir.getName())) {
				
//				eaTableColumns_valueFolderDetails = new EA_TableColumns(dir.getName(), i);
				EA_StaticTableObjects.eaStaticMapObj_TableColumnsHeader.put(dir.getName(), eaInt_columnNo);
				eaInt_columnNo++;
			}
		}
	}

	/**
	 * Checks if individual folder is values folder or not
	 * @param name name of the folder.
	 * @return true if folder is value folder.
	 */
	private boolean eaIsValueFolder(String name) {

		String eastr_SubName = null;
		if (name.length() > 5) {
			eastr_SubName = name.substring(0, 5);
			if (eastr_SubName.equals(EA_Constants.eaStaticString_ValeFolderName))
				return true;
		}
		return false;
	}

	/**
	 * Checks if the value res files in value folder 
	 * @param eaArrayList_valueFolderDetails
	 */
	private void eaCheckForValuesFiles() {

//		int eaInt_position = 0; 
		
		Iterator<Entry<String, Integer>> it = EA_StaticTableObjects.eaStaticMapObj_TableColumnsHeader.entrySet().iterator();
	    while (it.hasNext()) {
	    	HashMap.Entry<String, Integer> pair = (HashMap.Entry<String, Integer>)it.next();
//	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        eaCheckIndivdualFolder(pair.getKey(),pair.getValue());
//	        eaInt_position++;
//	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
//		for(EA_TableColumns eaValuesFolder: eaHashmap_valueFolderDetails ){
//		eaCheckIndivdualFolder(eaValuesFolder.getEaStr_FolderName(),eaInt_position);
//		eaInt_position++;
//		}
	}
 
	/**
	 * Checks for value res files in individual value folder and sets the Table elements
	 * @param eaValuesFolderName
	 * @param eaInt_ColumnPosition
	 */
	private void eaCheckIndivdualFolder(String eaValuesFolderName, int eaInt_ColumnPosition) {
	
		eaObj_ValuesSetter = new EA_ValuesSetter(EA_Constants.Paths.eaStaticString_workingFolderPath 
				+ EA_Constants.Paths.eaStaticString_relativePathToParentValuesFolder
				+ "\\"
				+ eaValuesFolderName,
				 eaInt_ColumnPosition);
		eaObj_ValuesSetter.ea_SetValues();
		
		
	}

	public void eaAddValues() {
		// new EA_AddElement();
	}

}
