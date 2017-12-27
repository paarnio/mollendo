/*
 * See XMLSimple project QueryXML.java
 * http://www.vogella.com/tutorials/JavaXML/article.html
 * 
 */

package siima.app.operator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XPathQuery {
	//TODO:
	private static final Logger logger=Logger.getLogger(XPathQuery.class.getName());
	private StringBuffer operErrorBuffer = new StringBuffer();
	
	/* *
	 * 
	 */
	public void queryXML(String xmlFile, String xpathExpression, String resultType ){
		/*
		 * resultType: NODESET NUMBER BOOLEAN
		 * NODESET: "//person[firstname='Lars']/lastname/text()"
		 * NUMBER: "count(//person[firstname='Lars'])
		 * BOOLEAN: "count(//person[firstname='Lars'])>2"
		 */
		InputStream xmlinput;
		try {
			
			// Original 
			// standard for reading an XML file
			   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			   factory.setNamespaceAware(true);
			   DocumentBuilder builder;
			   Document doc = null;
			   XPathExpression expr = null;
			   builder = factory.newDocumentBuilder();
			   doc = builder.parse(xmlFile); //"data/tests/person.xml");

			   // create an XPathFactory
			   XPathFactory xFactory = XPathFactory.newInstance();
			   // create an XPath object
			   XPath xpath = xFactory.newXPath();
			   // compile the XPath expression
			   expr = xpath.compile(xpathExpression); //"//person[firstname='Lars']/lastname/text()");
			   
			   switch(resultType){ 
			   case "NODESET": {
				   // run the query and get a nodeset		   
				   Object result = expr.evaluate(doc, XPathConstants.NODESET);
				   // cast the result to a DOM NodeList
				   NodeList nodes = (NodeList) result;
				   for (int i=0; i<nodes.getLength();i++){
				     System.out.println(nodes.item(i).getNodeValue());
				   }
				   
			   } break;
			   case "NUMBER": {
				   
				   Double number = (Double) expr.evaluate(doc, XPathConstants.NUMBER);
				   System.out.println("Number of objects " +number);

				   
			   } break;
			   case "BOOLEAN": {
				 
				   Boolean check = (Boolean) expr.evaluate(doc, XPathConstants.BOOLEAN);
				   System.out.println(check);
	   
			   } break;
			   			   
			   }
			
			
		} catch (FileNotFoundException e) {
			operErrorBuffer.append("CLASS:" + getClass().getName() + "(exc1) ERROR:" + e.getMessage());
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			operErrorBuffer.append("CLASS:" + getClass().getName() + "(exc2) ERROR:" + e.getMessage());
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			e.printStackTrace();
		} catch (SAXException e) {
			operErrorBuffer.append("CLASS:" + getClass().getName() + "(exc3) ERROR:" + e.getMessage());
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			operErrorBuffer.append("CLASS:" + getClass().getName() + "(exc4) ERROR:" + e.getMessage());
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			operErrorBuffer.append("CLASS:" + getClass().getName() + "(exc5) ERROR:" + e.getMessage());
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public StringBuffer getOperErrorBuffer() {
		return operErrorBuffer;
	}



	public void setOperErrorBuffer(StringBuffer operErrorBuffer) {
		this.operErrorBuffer = operErrorBuffer;
	}



	public static void main(String[] args) {
		String xmlFile = "data/tests/person.xml";
		String xpath1 = "//person[firstname='Lars']/lastname/text()"; //NODESET
		String xpath2 = "count(//person[firstname='Lars'])"; // NUMBER
		String xpath3 = "count(//person[firstname='Lars']) >2"; //BOOLEAN
		
		XPathQuery process = new XPathQuery();
		   process.queryXML(xmlFile, xpath1, "NODESET");
		   process.queryXML(xmlFile, xpath2, "NUMBER");
		   process.queryXML(xmlFile, xpath3, "BOOLEAN");
	}

}
