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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ea.andoridresourcevalues.utils.EA_Constants;

public class EA_RemoveXMLElement {

	

/**
 * Removes the element form xml sheet
 * @param pathOfFile
 * @param valueName
 * @return
 */
public boolean removeElement(String pathOfFile,String valueName){
	

	try {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(pathOfFile);
		Element rootElement = doc.getDocumentElement();
		NodeList nList = doc.getElementsByTagName(EA_Constants.XMLAttributes.dimensionTagNam);
//		System.out.println("About to Delete");

		//to delete the value
		for (int temp = 0; temp < nList.getLength(); temp++) {
			
			Node nNode = nList.item(temp);
			
				Element eElement = (Element) nNode;


//			System.out.println("Value read: " + eElement.getAttribute(EA_Constants.XMLAttributes.AttributeName));
			
				if (valueName.equals(eElement.getAttribute(EA_Constants.XMLAttributes.AttributeName))) {
					rootElement.removeChild(nNode);
					break;
				   }
		}
		
		


		// write the content back into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(pathOfFile));

		transformer.transform(source, result);
//		System.out.println("Removed. ");
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