/* XMLValidationCheck.java
 * From project XMLValidationSimple
 * See: com.journaldev.xml.XMLValidation.java
 * See: http://www.journaldev.com/895/how-to-validate-xml-against-xsd-in-java
 * TOIMII HYVÄ!!!
 * !!! Benefit of using java XML validation API is that 
 * we don’t need to parse the file and there are no third party APIs used.
 *  
 * !!! Benefit of using java XML validation API is that 
 * we don’t need to parse the file and there are no third party APIs used.
 * 
 * Notice that Employee XSD contains two root element and namespace also,
 */
package siima.app.operator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import java.util.zip.ZipFile;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
//NEW 2018-02-05
import javax.xml.transform.stream.StreamResult;



import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

//import siima.app.TaskCycleProcessor;
import siima.utils.ZipFileReader;

public class XMLValidationCheck {
	private static final Logger logger=LogManager.getLogger(XMLValidationCheck.class.getName());
	private StringBuffer operErrorBuffer = new StringBuffer();
	private ZipFileReader zipper = new ZipFileReader();
	
	public boolean validateXMLSchema(String zippath1, String xsdPath, String zippath2, String xmlPath){ 
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: validateXMLSchema()");
		boolean ok = false;
		StreamSource xsdSource = null;
		StreamSource xmlSource = null;
		//StreamResult validationResult = new StreamResult();
		
		String xmluri = null; // Not available 
		
		//System.out.println("?????? validateXMLSchema: zippath1:" + zippath1 + " xsdPath:" + xsdPath);
		//System.out.println("?????? validateXMLSchema: zippath2:" + zippath2 + " xmlPath:" + xmlPath);
		
		try {
			ZipFile zip1 = new ZipFile(zippath1);
			ZipFile zip2 = new ZipFile(zippath2);
			InputStream inputStreamXSD = zipper.readInputStreamFromZipFile(xsdPath, zip1, null);
			InputStream inputStreamXML = zipper.readInputStreamFromZipFile(xmlPath, zip2, null);
			
			if (xmluri != null) {
				xsdSource = new StreamSource(inputStreamXSD, xmluri);
				xmlSource = new StreamSource(inputStreamXML, xmluri);
			} else {
				xsdSource = new StreamSource(inputStreamXSD);
				xmlSource = new StreamSource(inputStreamXML);
			}
			
			if((xsdSource!=null)&&(xmlSource!=null)){		
				ok = validateXMLSchema(xsdSource, xmlSource);						
			}
			
		
			
		} catch (IOException e) {
			logger.log(Level.ERROR,  "MSG:" + e.getMessage());
			operErrorBuffer.append("OPER:" + getClass().getSimpleName() + ":ERROR:" + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return ok;
	}

	private boolean validateXMLSchema(Source xsdSource, Source xmlSource){ 
		//Test 2018-02-05  StreamResult validationResult ei toimi
		//when using validationResult usage: ERROR:setResult() must be called prior to startDocument().)
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(xsdSource);
			Validator validator = schema.newValidator();
			validator.validate(xmlSource); //validator.validate(xmlSource, validationResult)
		} catch (SAXException e) {
			logger.log(Level.ERROR, "MSG:" + e.getMessage());
			operErrorBuffer.append("OPER:" + getClass().getSimpleName() + ":ERROR:" + e.getMessage());
			System.out.println("Exception: " + e.getMessage());
			// e.printStackTrace();
			return false;

		} catch (IOException e) {
			logger.log(Level.ERROR, "MSG:" + e.getMessage());
			operErrorBuffer.append("OPER:" + getClass().getSimpleName() + ":ERROR:" + e.getMessage());
			System.out.println("Exception: " + e.getMessage());
			// e.printStackTrace();
			return false;
		}

		return true;
	}
	
	private boolean validateXMLSchema(String xsdPath, String xmlPath){ // orig. static
		boolean ok = false;
        try {
            SchemaFactory factory = 
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
            ok = true;
        } catch (SAXException e) {
			logger.log(Level.ERROR, "MSG:" + e.getMessage());
			operErrorBuffer.append("OPER:" + getClass().getSimpleName() + ":ERROR:" + e.getMessage());
			System.out.println("Exception: " + e.getMessage());
			// e.printStackTrace();
			return false;

		} catch (IOException e) {
			logger.log(Level.ERROR, "MSG:" + e.getMessage());
			operErrorBuffer.append("OPER:" + getClass().getSimpleName() + ":ERROR:" + e.getMessage());
			System.out.println("Exception: " + e.getMessage());
			// e.printStackTrace();
			return false;
		}
        return ok;
    }


	public StringBuffer getOperErrorBuffer() {
		return operErrorBuffer;
	}

	public void setOperErrorBuffer(StringBuffer operErrorBuffer) {
		this.operErrorBuffer = operErrorBuffer;
	}


	public static void main(String[] args) {
		XMLValidationCheck valid = new XMLValidationCheck();
		// Original
	      System.out.println("EmployeeRequest.xml validates against Employee.xsd? "+valid.validateXMLSchema("data/tests/valid/Employee.xsd", "data/tests/valid/EmployeeRequest.xml"));
	      System.out.println("EmployeeResponse.xml validates against Employee.xsd? "+valid.validateXMLSchema("data/tests/valid/Employee.xsd", "data/tests/valid/EmployeeResponse.xml"));
	      System.out.println("employee.xml validates against Employee.xsd? "+valid.validateXMLSchema("data/tests/valid/Employee.xsd", "data/tests/valid/employee.xml"));
	     
	}

}
