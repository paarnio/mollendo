/* XMLWellFormedCheck.java
 * Operator
 * See XMLValidationSimple project
 * Well-formed xml:
 * from: http://stackoverflow.com/questions/38255981/stax-well-formedness-check-of-xml
 */

package siima.app.operator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.zip.ZipFile;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

//import siima.app.XSLTransformer;
import siima.utils.ZipFileReader;

public class XMLWellFormedCheck {
	private static final Logger logger=Logger.getLogger(XMLWellFormedCheck.class.getName());
	private StringBuffer operErrorBuffer = new StringBuffer();
	private ZipFileReader zipper = new ZipFileReader();
	
	
	/* Constructor */
	public XMLWellFormedCheck(){
		
	}
	
	public boolean checkWellFormedZipXML(String zippath, String fullXMLPathInZip){
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: checkWellFormedZipXML()");
		boolean ok = false;
		try {
		XMLInputFactory factory = XMLInputFactory.newInstance();
		ZipFile zip = new ZipFile(zippath);
		InputStream inputStream= zipper.readInputStreamFromZipFile(fullXMLPathInZip, zip, null);		
			// Instantiate a reader parsing:
			XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
			while (reader.hasNext()) {
				// check to be implemented??
				reader.next();
			}
			inputStream.close();
			ok = true;

		} catch (FileNotFoundException e) {
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			operErrorBuffer.append("CLASS:" + getClass().getName() + " ERROR:" + e.getMessage());
			ok = false;
			e.printStackTrace();
		} catch (XMLStreamException e) {			
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			operErrorBuffer.append("CLASS:" + getClass().getName() + " ERROR:" + e.getMessage());
			ok = false;
			//e.printStackTrace();		
		} catch (IOException e) {
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			operErrorBuffer.append("CLASS:" + getClass().getName() + " ERROR:" + e.getMessage());
			ok = false;
			e.printStackTrace();
		}
		
		System.out.println("---checkWellFormedZipXML() ok=" + ok + ": " + operErrorBuffer.toString());
		return ok;
	}

	
	public boolean checkWellFormedness(String xmlFile){
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: checkWellFormedness()");
		boolean ok = false;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		File f = new File(xmlFile); //"data/tests/simple_not_wf.xml");
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(f);
			// Instantiate a reader parsing:
			XMLStreamReader reader = factory.createXMLStreamReader(inputStream);
			while (reader.hasNext()) {
				// check to be implemented??
				reader.next();
			}
			inputStream.close();
			ok = true;

		} catch (FileNotFoundException e) {
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			operErrorBuffer.append("CLASS:" + getClass().getName() + " ERROR:" + e.getMessage());
			ok = false;
			e.printStackTrace();
		} catch (XMLStreamException e) {			
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			operErrorBuffer.append("CLASS:" + getClass().getName() + " ERROR:" + e.getMessage());
			ok = false;
			//e.printStackTrace();		
		} catch (IOException e) {
			logger.log(Level.ERROR,  "MSG:\n" + e.getMessage());
			operErrorBuffer.append("CLASS:" + getClass().getName() + " ERROR:" + e.getMessage());
			ok = false;
			e.printStackTrace();
		}
		
		System.out.println("END: " + operErrorBuffer.toString());
		return ok;
	}

	
	
	
	public StringBuffer getOperErrorBuffer() {
		return operErrorBuffer;
	}

	public void setOperErrorBuffer(StringBuffer operErrorBuffer) {
		this.operErrorBuffer = operErrorBuffer;
	}

	public static void main(String[] args) {
		
		XMLWellFormedCheck wfchecker = new XMLWellFormedCheck();
		wfchecker.checkWellFormedness("data/tests/plant_catalog.xsl"); //"data/tests/simple_not_wf.xml");

	}

}
