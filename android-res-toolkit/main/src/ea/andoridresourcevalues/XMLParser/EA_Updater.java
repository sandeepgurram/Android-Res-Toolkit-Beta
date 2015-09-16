package ea.andoridresourcevalues.XMLParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ea.andoridresourcevalues.models.EA_TableElements;
import ea.andoridresourcevalues.models.EA_ValueToBeAdded;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.utils.EA_StaticTableObjects;
import ea.andoridresourcevalues.utils.EA_Utils;

public class EA_Updater {

	String eaStr_PathOfFile;
	
	public EA_Updater() {

//		this.eaStr_ProjectPath = EA_Constants.Paths.eaStaticString_workingFolderPath ;
		
		
		
	}
	
	public boolean start(ArrayList<EA_ValueToBeAdded> eaArrayList_NewValues){
		
		for(EA_ValueToBeAdded ea_ValueToBeAdded : eaArrayList_NewValues){
			eaAddValuesIntialiser(ea_ValueToBeAdded);
		}
		return true;
	}
	
	/**
	 * identifies the value file to be edited and value to be changed
	 * @param ea_ValueToBeAdded Object of EA_ValueToBeAdded model.
	 */
	private void eaAddValuesIntialiser(EA_ValueToBeAdded ea_ValueToBeAdded) {

		eaStr_PathOfFile = EA_Constants.Paths.eaStaticString_workingFolderPath 
				+ EA_Constants.Paths.eaStaticString_relativePathToParentValuesFolder
				+ "\\"
				+ ea_ValueToBeAdded.getEaStr_ColumnName()
				+ "\\"
				+ EA_Constants.eaStaticMap_FileName.get(ea_ValueToBeAdded.getEaInt_RowNo());

		String newValue = ea_ValueToBeAdded.getEaStr_value();
		String valueName = getValueName(ea_ValueToBeAdded.getEaInt_RowNo());
		
		boolean isAdded = eaUpdateValue(eaStr_PathOfFile,newValue,valueName);
		
		if(!isAdded)
			System.out.println("Error while changing value "
					+ valueName + " to " + newValue + " at " + 
					"'" + eaStr_PathOfFile + "'" );
	}



	/**
	 * Gets the value name to be modified
	 * @param eaInt_RowNo row number in Table where value is changed
	 * @return name of value
	 */
	private String getValueName(int eaInt_RowNo) {

		 ArrayList<String> ea_RowHeaders =  EA_Utils.getRowsArrayList();

		return ea_RowHeaders.get(eaInt_RowNo);
	}

	/**
	 * Does changes in XML 
	 * @param pathOfFile path of XML file
	 * @param newValue value to be updated
	 * @param valueName name of value to be updated
	 * @return true is update is successful
	 */
	private boolean eaUpdateValue(String pathOfFile, String newValue, String valueName) {

		try {

			File eaFile_WorkingFile = new File(pathOfFile);
			if(!eaFile_WorkingFile.exists()){
				new EA_AddXMLElement().createNewFile(eaFile_WorkingFile, pathOfFile, valueName, newValue);
				return true;
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(pathOfFile);
			Element rootElement = doc.getDocumentElement();
			NodeList nList = doc.getElementsByTagName(EA_Constants.XMLAttributes.dimensionTagNam);
			boolean ea_isValuePresent = false;
			System.out.println("About to change value");
			//to change the value
			for (int temp = 0; temp < nList.getLength(); temp++) {
				
				Node nNode = nList.item(temp);
				
//				System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
					Element eElement = (Element) nNode;
//					eElement.getTagName();
//					
//					String eaStr_ValueName = eElement.getAttribute("name");
//					String eaStr_Value = nNode.getTextContent();
//					int rowNumber = 0;
//					

				System.out.println("Value read: " + eElement.getAttribute(EA_Constants.XMLAttributes.AttributeName));
				
					if (valueName.equals(eElement.getAttribute(EA_Constants.XMLAttributes.AttributeName))) {
						nNode.setTextContent(newValue);
						System.out.println("value changed to "+newValue);
						ea_isValuePresent = true;
						break;
					   }
			}
			
			
			//if element is not found create it.
			if(!ea_isValuePresent){
				
				Element version = doc.createElement(EA_Constants.XMLAttributes.dimensionTagNam);
				version.setAttribute(EA_Constants.XMLAttributes.AttributeName, valueName); 
				version.appendChild(doc.createTextNode(newValue));
				rootElement.appendChild(version);
				
			}

			// write the content back into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(pathOfFile));

			transformer.transform(source, result);
			
			return true;

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
}