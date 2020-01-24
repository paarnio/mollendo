/* XSLTransformer.java
 * 2017-09-29 TOIMII
 * (See orig idea VPA: XMLTransformExample.java in TRAXExamples project)
 * 
 * FROM: http://stackoverflow.com/questions/23864828/provide-trax-parser-example-in-java-xml-to-xml-xslt-transformation
 * JAVADOC: http://docs.oracle.com/javase/6/docs/api/javax/xml/transform/package-summary.html
 * ------------------------
 * TODO: Which of these Sources to use:  
 * javax.xml.transform.Source
 * javax.xml.transform.stax.StAXSource
 * ----------
 * Note: Streams are normally read only one time.
 * But they can be saved in a byte array to avoid reading 
 * again from the same file
 * See: https://stackoverflow.com/questions/9501237/read-stream-twice
 * ---
 * Javadoc  NOTE:Note: Due to their internal use of either a Reader or InputStream instance, 
 * StreamSource instances may only be used once!!!
 * 
 */
package siima.app.operator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

public class XSLTransformer {
	private static final Logger logger=LogManager.getLogger(XSLTransformer.class.getName());
	private StringBuffer operErrorBuffer = new StringBuffer();

	private TransformerFactory factory;	
	private Source xmlSource;	
	private Source xslSource;
	//XSL InputStream saved to a Byte Array and ByteArrayInputStream
	private byte[] xslInputBytes;	
	private ByteArrayInputStream xslBAInputStream;
	
	//XML InputStream saved to a Byte Array and ByteArrayInputStream
	private byte[] xmlInputBytes;	
	private ByteArrayInputStream xmlBAInputStream;
	
	/* PIPE: Intermediate XSL Transform XML results saved 
	 * to a Byte Array and ByteArrayInputStream
	 */
	private byte[] xmlTransformResultBytes;
	private ByteArrayInputStream xmlTransformResultInputStream;
	
	private Templates template;	
	private Transformer transformer;
	
	
	
	/* Constructor */
	public XSLTransformer(){		
		factory = TransformerFactory.newInstance();		
	}
	
	public InputStream getSavedBAInputStream(String XSL_or_XML){
		/* Param xsl_or_xml allowed values: "XSL" OR "XML"
		 * Resets the existing saved xslBAInputStream.reset(); and returns it.
		 * https://stackoverflow.com/questions/9501237/read-stream-twice
		 * 
		 * // either TOIMII
			while (needToReadAgain) {
    		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
    		yourReadMethodHere(bais);
			}

			// or TOIMII
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			while (needToReadAgain) {
    		bais.reset();
    		yourReadMethodHere(bais);
			}
		 * 	
		 */
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: getSavedBAInputStream()");

		InputStream requestedInputStream = null;
		
		if ("XSL".equalsIgnoreCase(XSL_or_XML)) {
			// XML InputStream requested
			if (this.xslBAInputStream == null) {
				// Option 1
				this.xslBAInputStream = new ByteArrayInputStream(this.xslInputBytes);
			} else {
				// Option 2
				this.xslBAInputStream.reset();
			}
			requestedInputStream = this.xslBAInputStream;

		} else if ("XML".equalsIgnoreCase(XSL_or_XML)) {
			// XML InputStream requested
			if (this.xmlBAInputStream == null) {
				// Option 1
				this.xmlBAInputStream = new ByteArrayInputStream(this.xmlInputBytes);
			} else {
				// Option 2
				this.xmlBAInputStream.reset();
			}
			requestedInputStream = this.xslBAInputStream;
		}
	 return requestedInputStream;
	}
	
	public boolean saveInputStreamAsByteArray(InputStream xslinput, String XSL_or_XML){
	/* Saving XSL OR XML InputStream into byte array
	 * Param xsl_or_xml allowed values: "XSL" OR "XML"
	 * https://stackoverflow.com/questions/9501237/read-stream-twice
	 * TOIMII	
	 */
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: saveInputStreamAsByteArray()");
		//operErrorBuffer = new StringBuffer();
		boolean ok = false;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			org.apache.commons.io.IOUtils.copy(xslinput, baos);
		
			if("XSL".equalsIgnoreCase(XSL_or_XML)){
				this.xslInputBytes = baos.toByteArray();
				ok = true;
			} else if("XML".equalsIgnoreCase(XSL_or_XML)){
				this.xmlInputBytes = baos.toByteArray();
				ok = true;
			}
			
		} catch (IOException e) {
			logger.log(Level.ERROR, "MSG:" + e.getMessage());
			operErrorBuffer.append("OPER:" + getClass().getSimpleName() + ":ERROR:" + e.getMessage());
			//e.printStackTrace();
			ok = false;
		}
		return ok;
	}
	
	
	public boolean saveTransformResultAsByteArray(OutputStream xslResultStream){
	/* NEW 2018-01-26 Saving Transform pipeline's intermediate result OutputStream (XML/TEXT) into byte array.
	 * Used for pipelining two transform operation: interim pipe 
	 * (Not used: Param xsl_or_xml allowed values: "XML" OR "TEXT")
	 * https://stackoverflow.com/questions/26960997/convert-outputstream-to-bytearrayoutputstream
	 * TOIMII	
	 */
		logger.log(Level.INFO, "Entering:  method: saveOutputStreamAsByteArray()");
		// operErrorBuffer = new StringBuffer();
		boolean ok = false;

		ByteArrayOutputStream baos = (ByteArrayOutputStream) xslResultStream;

		if (xslResultStream != null) {
			this.xmlTransformResultBytes = baos.toByteArray();
			this.xmlTransformResultInputStream = new ByteArrayInputStream(this.xmlTransformResultBytes);
			ok = true;
			logger.log(Level.INFO, "saveOutputStreamAsByteArray(): XSL Transform's intermediate result saved into ByteArray pipe!");
			String str = new String(this.xmlTransformResultBytes); // Arrays.toString(this.xmlTransformResultBytes);
			System.out.println(" ----- XSLT TRANSFORM'S INTERMEDIATE RESULT SAVED AS BYTE ARRAY ------\n");
			//System.out.println("" + str);
			//System.out.println("\n--- END PIPE---");
		} else {
			ok = false;
			logger.log(Level.INFO, "ERROR: saveOutputStreamAsByteArray(): Nothing to save: xslResultStream is null");
		}

		return ok;
	}	
	
	
	public boolean invokeXSLTransform(OutputStream outputstream, List<String> params, List<String> values) {

		/* Note: params and values can be null
		 * (?) whenever you need to execute this transformation, create // a new
		 * Transformer instance from the Templates instance
		 * 
		 */
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: invokeXSLTransform()");
		//operErrorBuffer = new StringBuffer();
		boolean ok = false;
		try {
			//Transformer transformer;
			transformer = template.newTransformer();

			if ((params != null) && (values != null) && (params.size() == values.size())) {
				// transformer.setParameter("MinSalary", "2000");
				for(int i=0; i<params.size(); i++){
					transformer.setParameter(params.get(i), values.get(i));
				}							
			}
			Result result = new StreamResult(outputstream); // ("data/out2.xml");
			transformer.transform(xmlSource, result);
			ok = true;
		} catch (TransformerConfigurationException e) {
			logger.log(Level.ERROR, "MSG&LOC:\n" + e.getMessageAndLocation());
			operErrorBuffer.append("OPER:" + getClass().getSimpleName() + ":ERROR:" + e.getMessageAndLocation());
			//e.printStackTrace();
			ok = false;
		} catch (TransformerException e) {
			logger.log(Level.ERROR, "MSG&LOC:\n" + e.getMessageAndLocation());
			operErrorBuffer.append("OPER:" + getClass().getSimpleName() + ":ERROR:" + e.getMessageAndLocation());
			//e.printStackTrace();
			ok = false;
		}
		return ok;
	}
	
	public boolean createNewTemplate(InputStream xslinput, String xsluri) {
		/*
		 * process the XSLT stylesheet into a Templates instance // with our
		 * TransformerFactory instance // whenever you need to execute this
		 * transformation, create // a new Transformer instance from the
		 * Templates instance
		 */
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: createNewTemplate()");
		//operErrorBuffer = new StringBuffer();
		boolean ok = false;
		try {
			
			setXSLSource(xslinput, xsluri);
			template = factory.newTemplates(xslSource);
			if(template!=null)ok = true;
		} catch (TransformerConfigurationException e) {
			logger.log(Level.ERROR, "MSG&LOC:\n" + e.getMessageAndLocation());
			operErrorBuffer.append("OPER:" + getClass().getSimpleName() + ":ERROR:" + e.getMessageAndLocation());
			//System.out.println("MyLog ERROR: XSLTransformer: createNewTemplate() MSG&LOC:\n" + e.getMessageAndLocation());
			//e.printStackTrace();
			ok=false;
		}

		return ok;
	}
	
	/* XML SPECIFIC METHOD */
	public boolean setTransformerXMLSource(InputStream xmlinput, String xmluri){
		
		return setXMLSource(xmlinput, xmluri);
	}
	
	/* XML SPECIFIC METHOD */
	private boolean setXMLSource(InputStream xmlinput, String xmluri){
		/* IF xmluri==null OPT1
		 * OPT1: StreamSource(xmlinput)
		 * 
		 * OPT2: StreamSource(xmlinput, xmluri)
		 * This constructor allows the systemID to be set in addition to the input stream, 
		 * which allows relative URIs to be processed. 
		 * Parameters:inputStream - A valid InputStream reference to an XML stream.
		 * systemId - Must be a String that conforms to the URI syntax.
		 * 
		 * NOTE:Note: Due to their internal use of either a Reader or InputStream instance, 
		 * StreamSource instances may only be used once!!! 
		 * 
		 */
		boolean ok = false;
		if(xmluri!=null){
			xmlSource = new StreamSource(xmlinput, xmluri);
		} else {
			xmlSource = new StreamSource(xmlinput);
		}
		if(xmlSource!=null) ok = true;
		return ok;
	}
	
	/* XSL SPECIFIC METHOD */
	private boolean setXSLSource(InputStream xslinput, String xsluri){
		/* NOTE: Private
		 * IF xsluri==null OPT1
		 * OPT1: StreamSource(xmlinput)
		 * 
		 * OPT2: StreamSource(xmlinput, xmluri)
		 * This constructor allows the systemID to be set in addition to the input stream, 
		 * which allows relative URIs to be processed. 
		 * Parameters:inputStream - A valid InputStream reference to an XML stream.
		 * systemId - Must be a String that conforms to the URI syntax.
		 */
		boolean ok = false;
		if(xsluri!=null){
			xslSource = new StreamSource(xslinput, xsluri);
		} else {
			xslSource = new StreamSource(xslinput);
		}
		if(xslSource!=null) ok = true;
		return ok;
	}

	/*
	 * GETTERS AND SETTERS AND CLEARERS
	 */
	
	public void clearInterimResultPipe(){
		//Clear Pipe
		setXmlTransformResultBytes(null);
		setXmlTransformResultInputStream(null);
	}
	
	
	public StringBuffer getOperErrorBuffer() {
		return operErrorBuffer;
	}

	public void setXmlTransformResultBytes(byte[] xmlTransformResultBytes) {
		this.xmlTransformResultBytes = xmlTransformResultBytes;
	}

	public void setXmlTransformResultInputStream(ByteArrayInputStream xmlTransformResultInputStream) {
		this.xmlTransformResultInputStream = xmlTransformResultInputStream;
	}

	public ByteArrayInputStream getXmlTransformResultInputStream() {
		return xmlTransformResultInputStream;
	}

	public void setOperErrorBuffer(StringBuffer operErrorBuffer) {
		this.operErrorBuffer = operErrorBuffer;
	}

	public static void main(String[] args) throws Exception {
		  
		/*
		 * Source xsl = new StreamSource("data/input.xsl"); Transformer
		 * transformer = factory.newTransformer(xsl); Source xml = new
		 * StreamSource("data/data.xml"); Result result = new
		 * StreamResult("data/out.xml");
		 */
		  
		  InputStream xmlinput = new FileInputStream("data/tmp/data.xml");
		  InputStream xslinput = new FileInputStream("data/tmp/input.xsl");
		  OutputStream resultoutput = new FileOutputStream("data/tmp/out.xml");
		  
		  XSLTransformer serTrans = new XSLTransformer();
		  serTrans.setXMLSource(xmlinput, null);
		  serTrans.createNewTemplate(xslinput, null);
		  
		  //Setting xsl params
		  List<String> params = new ArrayList<String>(); 
		  List<String> values = new ArrayList<String>();
		  params.add("MinSalary");
		  values.add("2000");
		  
		  serTrans.invokeXSLTransform(resultoutput, params, values);
		  
	  }
	  
		
}
