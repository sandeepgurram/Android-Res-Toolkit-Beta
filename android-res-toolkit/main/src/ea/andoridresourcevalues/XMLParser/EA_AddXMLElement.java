package ea.andoridresourcevalues.XMLParser;

import java.io.File;
import java.io.IOException;

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
import org.xml.sax.SAXException;

import ea.andoridresourcevalues.utils.EA_Constants;

public class EA_AddXMLElement {


//	String eastr_File, eaStr_ValueName, eaStr_Value;
	
	public EA_AddXMLElement() {
		super();
//		this.eastr_File = eastr_File;
//		this.eaStr_ValueName = eaStr_ValueName;
//		this.eaStr_Value = eaStr_Value;
		
//		add(eastr_File, eaStr_ValueName, eaStr_Value);
		
	}


	public void add(String eastr_File, String eaStr_ValueName, String eaStr_Value){
		try {

			File eaFile_WorkingFile = new File(eastr_File);
			if(!eaFile_WorkingFile.exists()){
				createNewFile(eaFile_WorkingFile,eastr_File, eaStr_ValueName, eaStr_Value);
				return;
			}
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.parse(eaFile_WorkingFile);
 
			Element rootElement = document.getDocumentElement();

			Element version = document.createElement(EA_Constants.XMLAttributes.dimensionTagNam);
			version.setAttribute(EA_Constants.XMLAttributes.AttributeName, eaStr_ValueName); 
			version.appendChild(document.createTextNode(eaStr_Value));
			rootElement.appendChild(version);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(eastr_File));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Creates new XML file
	 * @param eaFile_WorkingFile
	 * @param eastr_File
	 * @param eaStr_ValueName
	 * @param eaStr_Value
	 * @return
	 */
	boolean createNewFile(File eaFile_WorkingFile,String eastr_File, String eaStr_ValueName, String eaStr_Value) {

		try {
//		eaFile_WorkingFile.createNewFile();
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document document =docBuilder.newDocument();

		Element rootElement = document.createElement(EA_Constants.XMLAttributes.RootTag);
		document.appendChild(rootElement);

		Element eaTag_dimen = document.createElement(EA_Constants.XMLAttributes.dimensionTagNam);
		eaTag_dimen.setAttribute(EA_Constants.XMLAttributes.AttributeName, eaStr_ValueName); 
		eaTag_dimen.appendChild(document.createTextNode(eaStr_Value));
		rootElement.appendChild(eaTag_dimen);

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(new File(eastr_File));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
			return false;
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
			return false;
		}
		
		return true;
		
	}


	
}
