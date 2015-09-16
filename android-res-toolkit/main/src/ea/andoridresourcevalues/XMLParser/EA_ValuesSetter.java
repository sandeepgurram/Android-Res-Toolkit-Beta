package ea.andoridresourcevalues.XMLParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ea.andoridresourcevalues.models.EA_TableElements;
import ea.andoridresourcevalues.models.EA_TableRow;
import ea.andoridresourcevalues.utils.EA_Constants;
import ea.andoridresourcevalues.utils.EA_StaticTableObjects;

public class EA_ValuesSetter {

	private String eaStr_PathToXMLFiles;
	private ArrayList<String> eaStrArray_ValuesFiles;
	private int eaInt_ColumnNumber;
	
	public EA_ValuesSetter(String eaStr_PathToXMLFiles, int eaInt_ColumnPosition) {

		this.eaStr_PathToXMLFiles = eaStr_PathToXMLFiles;
		this.eaInt_ColumnNumber = eaInt_ColumnPosition;
		eaStrArray_ValuesFiles = new ArrayList<>();
		
		
	}

	public void ea_SetValues() {
		
		ArrayList<String> eaTempStrArray_ValuesFiles = new ArrayList<>(); 
		
		File directory = new File(eaStr_PathToXMLFiles);
		File[] subdirs = directory.listFiles();
//		System.out.println("values files");
//		int eaInt_RowNumber = 0;
		for (File file : subdirs) {
			ea_SetValueDetails(eaStr_PathToXMLFiles, file.getName());
//				eaTempStrArray_ValuesFiles.add(file.getName());
//				System.out.println(file.getName());
//				eaInt_RowNumber++;
			
		}
		
	}

		private boolean ea_SetValueDetails(String eaStr_PathToCheckingXML,String presentFileName) {
			try {
			File fXmlFile = new File(eaStr_PathToCheckingXML+"\\"+presentFileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(EA_Constants.XMLAttributes.dimensionTagNam);
			
			//check if it is values file or not.
			if(nList.getLength() > 0){
				
				for (int temp = 0; temp < nList.getLength(); temp++) {
					
					Node nNode = nList.item(temp);
					
//					System.out.println("\nCurrent Element :" + nNode.getNodeName());
			 
						Element eElement = (Element) nNode;
						eElement.getTagName();
						
						String eaStr_ValueName = eElement.getAttribute("name");
						String eaStr_Value = nNode.getTextContent();
						int rowNumber = 0; 
						
						try{
						rowNumber = EA_StaticTableObjects.eaStaticMapObj_TableRowsHeader.get(eaStr_ValueName);
						}catch(NullPointerException e){
							rowNumber = EA_Constants.eaStaticInteger_RowNumber;
							EA_Constants.eaStaticInteger_RowNumber++;
							EA_StaticTableObjects.eaStaticMapObj_TableRowsHeader.put(eaStr_ValueName, 
									rowNumber);
						}
						finally {
						
						EA_Constants.eaStaticMap_FileName.put(rowNumber, presentFileName);	
						EA_TableElements eaObj_TableElements = new EA_TableElements(eaInt_ColumnNumber, 
								rowNumber,
								eaStr_Value);
						EA_StaticTableObjects.eaStaticArrayObj_TableElements.add(eaObj_TableElements);
						
//						System.out.println("Name : " + eaStr_ValueName);
//						System.out.println("Value : " + eaStr_Value);
//						System.out.println("row : " + rowNumber);
//						System.out.println("Column : " + eaInt_ColumnNumber);
						
						}//finally
				}
				
				return true;
			}
			else return false;
			
			} catch (SAXException | IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			
			return false;
		}
		
		
}

	
