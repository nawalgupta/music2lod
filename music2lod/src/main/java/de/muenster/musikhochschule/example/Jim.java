package de.muenster.musikhochschule.example;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

import de.muenster.musikhochschule.core.Score;

public class Jim {


	public static void main(String[] args) {

		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setValidating(false);
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();

			builder.setEntityResolver(new EntityResolver() {

				@Override
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {

					return new InputSource(new StringReader(""));

				}
			});


			Document document = builder.parse(new File("scores/xmlsamples/DebuMandSample.xml"));




			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			NodeList subfields = (NodeList) xpath.evaluate("//score-partwise/part-list/score-part/@id", document,XPathConstants.NODESET);

			for (int i = 0; i < subfields.getLength(); i++) {

				System.out.println(subfields.item(i).getTextContent());
				
			}















		} catch (SAXException | IOException | ParserConfigurationException | XPathExpressionException e) {
			e.printStackTrace();
		} 

	}

}
