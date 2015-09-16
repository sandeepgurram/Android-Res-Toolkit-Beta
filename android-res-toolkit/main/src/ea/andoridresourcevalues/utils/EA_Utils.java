package ea.andoridresourcevalues.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ea.andoridresourcevalues.EA_ValuesFolderManager;

public class EA_Utils {

	public static <K, V extends Comparable<? super V>> HashMap<K, V> ea_sortMapByValue( HashMap<K, V> map )
	{
	    List<HashMap.Entry<K, V>> list =
	        new LinkedList<>( map.entrySet() );
	    Collections.sort( list, new Comparator<Map.Entry<K, V>>()
	    {
	        @Override
	        public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
	        {
	            return (o1.getValue()).compareTo( o2.getValue() );
	        }
	    } );

	    Map<K, V> result = new LinkedHashMap<>();
	    for (Map.Entry<K, V> entry : list)
	    {
	        result.put( entry.getKey(), entry.getValue() );
	    }
	    return (HashMap<K, V>) result;
	}

	public static ArrayList<String> getRowsArrayList() {

		HashMap<String,Integer> sortedMap = ea_sortMapByValue( EA_StaticTableObjects.eaStaticMapObj_TableRowsHeader );
		ArrayList<String> ea_RowHeaders =  new ArrayList<>(sortedMap.keySet());

		return ea_RowHeaders;
	}
	
	public static ArrayList<String> getColumnsArrayList() {

		HashMap<String,Integer> sortedMap = ea_sortMapByValue( EA_StaticTableObjects.eaStaticMapObj_TableColumnsHeader );
		ArrayList<String> ea_ColumnsHeaders =  new ArrayList<>(sortedMap.keySet());

		return ea_ColumnsHeaders;
	}
	
	/**
	 * clears the static values.
	 */
	public static void clearLists(){
		EA_StaticTableObjects.eaStaticArrayObj_TableElements.clear();
		EA_StaticTableObjects.eaStaticMapObj_TableColumnsHeader.clear();
		EA_StaticTableObjects.eaStaticMapObj_TableRowsHeader.clear();
		EA_Constants.eaStaticMap_FileName.clear();
		EA_Constants.eaStaticInteger_RowNumber = 0;
	}
	
	/**
	 * Re-reads the values from file system and refreshs the maps and arraylists;
	 */
	public static void refreshList(){
		
		clearLists();
		new EA_ValuesFolderManager().eaValuesFolderManager_startCheckingForValues();
		
	}
	
	public static boolean eaDeleteFile(File file)
	    	{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	 
	    		   file.delete();
	    		  
	    		   return true;
	 
	    		}else{
	 
	    		   //list all the directory contents
	        	   String files[] = file.list();
	 
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	 
	        	      //recursive delete
	        	      eaDeleteFile(fileDelete);
	        	   }
	 
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	   
	        	     return true;
	        	   }
	    		}
	 
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    	
	    		return true;
	    	}
	    	return false;
	    }
	
	
}
