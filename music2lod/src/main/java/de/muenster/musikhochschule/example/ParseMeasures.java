package de.muenster.musikhochschule.example;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParseMeasures {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setValidating(false);
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();

			builder.setEntityResolver(new EntityResolver() {

				@Override
				public InputSource resolveEntity(String publicId, String systemId)
						throws SAXException, IOException {
					//System.out.println("Ignoring " + publicId + ", " + systemId);
					return new InputSource(new StringReader(""));
				}
			});

			Document document = builder.parse(new File("scores/xmlsamples/Elgar_cello_Concerto_Op.85.xml"));       

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			
			NodeList nodeNotes = (NodeList) xpath.evaluate("//score-partwise/part[@id='P1']/measure[@number='74']", document,XPathConstants.NODESET);

			
			int noteID = 0;
			
			for (int i = 0; i < nodeNotes.item(0).getChildNodes().getLength(); i++) {

				
				if(nodeNotes.item(0).getChildNodes().item(i).getNodeName().equals("note")){
				
					noteID++;
					System.out.println(noteID);
					
				}

				if(nodeNotes.item(0).getChildNodes().item(i).getNodeName().equals("direction")){

					System.out.println("direction to note: "+noteID);
					
				}
				
			}
			


			

		    
		    
		    

		} catch (MalformedURLException ex) {                   
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (XPathExpressionException ex) {
			ex.printStackTrace();
		}

	}

	
	
	private static String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
		 Transformer t = TransformerFactory.newInstance().newTransformer();
		 t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		 t.setOutputProperty(OutputKeys.INDENT, "yes");
		 t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
		 System.out.println("nodeToString Transformer Exception");
		}
		return sw.toString();
		}
}
