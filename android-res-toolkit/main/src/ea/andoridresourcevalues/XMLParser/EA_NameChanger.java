package ea.andoridresourcevalues.XMLParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ea.andoridresourcevalues.models.EA_NewName;
import ea.andoridresourcevalues.models.EA_ValueToBeAdded;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.utils.EA_Utils;

public class EA_NameChanger {

	

	private String eaStr_PathOfFile;

	public boolean start(ArrayList<EA_NewName> eaArrayList_Changes){
		
		for(EA_NewName ea_ChangedValue : eaArrayList_Changes){
			eaPreparer(ea_ChangedValue);
		}
		return true;
	}
	 
	/**
	 * sets the path of file where value is present and changes the value name in all files
	 * @param ea_ChangedValue Object of EA_ValueToBeAdded model.
	 */
	private void eaPreparer(EA_NewName ea_ChangedValue) {

		ArrayList<String> eaList_FolderNames = EA_Utils.getColumnsArrayList();
		
		for(String folderName : eaList_FolderNames )
		{
			eaStr_PathOfFile = EA_Constants.Paths.eaStaticString_workingFolderPath 
					+ EA_Constants.Paths.eaStaticString_relativePathToParentValuesFolder
					+ "\\"
					+ folderName
					+ "\\"
					+ EA_Constants.eaStaticMap_FileName.get(ea_ChangedValue.getEaInt_rowNo());
			
			boolean isAdded =  eaChangeName(eaStr_PathOfFile,ea_ChangedValue);
			if(!isAdded)
				System.out.println("Error while renamuing value "
						+ " at " + 
						"'" + eaStr_PathOfFile + "'" );
		}
	}

	private boolean eaChangeName(String pathOfFile, EA_NewName ea_ChangedValue) {

		try {

		
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(pathOfFile);
			Element rootElement = doc.getDocumentElement();
			NodeList nList = doc.getElementsByTagName(EA_Constants.XMLAttributes.dimensionTagNam);
			boolean ea_isValuePresent = false;
//			System.out.println("About to change value");
			//to change the value
			for (int temp = 0; temp < nList.getLength(); temp++) {
				
				Node nNode = nList.item(temp);
				
		 
					Element eElement = (Element) nNode;
//					eElement.getTagName();
//					
//					String eaStr_ValueName = eElement.getAttribute("name");
//					String eaStr_Value = nNode.getTextContent();
//					int rowNumber = 0;
//					

					String oldValueName = ea_ChangedValue.getEaStr_oldValueName();
//				System.out.println("Value read: " + eElement.getAttribute(EA_Constants.XMLAttributes.AttributeName));
				
					if (oldValueName.equals(eElement.getAttribute(EA_Constants.XMLAttributes.AttributeName))) {
						eElement.setAttribute(EA_Constants.XMLAttributes.AttributeName, ea_ChangedValue.getEaStr_newValueName());
//						System.out.println("value changed to "+ea_ChangedValue.getEaStr_newValueName());
						break;
					   }
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
