/* ContextXSLTransform.java
 * Addon helper class for Results modifications
 * For results.xml transformation to csv format.
 * 
 * From XSLTransform.java in ERAmlHandler project
 * Tested in egit: TRAXExamples/PlainTransform
 */
package siima.app.xaddon;
import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;

public class ContextXSLTransform {
	private static final Logger logger=LogManager.getLogger(ContextXSLTransform.class.getName());
	private static String RESULTS2CSV ="xslt/results2mc_csv.xsl";
	private static String RESULTS2XML ="xslt/results2students_xml.xsl";
	private TransformerFactory factory = TransformerFactory.newInstance();
	
	private String xslContextSrcFile; //source .xml
	private String xslContextXslFile; //.xsl
	private String xslContextTroutFile; //transform result file .trout
	

	public void doSpecificTransform(String transformtype, String sourcefile, String troutfile) {
		/*
		 * Implemented specific transform types:"results2csv"; "results2students"; 
		 * Implemented generic transform: "contextSrc2Trout"
		 * 
		 */
		System.out.println("-XSLTransform: doTransform(): " + transformtype);

		String results2csv_xsl = "configure/xslt/results2mc_csv.xsl";
		String results2students_xsl = "configure/xslt/results2students_xml.xsl";
		Source xslstream = null;
		
		//Get XSL files from resources folder
    	ClassLoader classLoader = getClass().getClassLoader();

		if ("results2csv".equalsIgnoreCase(transformtype)) {
			xslstream = new StreamSource(new File(classLoader.getResource(RESULTS2CSV).getFile()));
			//xslstream = new StreamSource(results2csv_xsl);
		} else if ("results2students".equalsIgnoreCase(transformtype)) {
			xslstream = new StreamSource(new File(classLoader.getResource(RESULTS2XML).getFile()));
			//xslstream = new StreamSource(results2students_xsl);
		} else if ("contextSrc2Trout".equalsIgnoreCase(transformtype)) {
			sourcefile = this.getXslContextSrcFile();
			troutfile = this.getXslContextTroutFile();
			String xslfile = this.getXslContextXslFile();
			xslstream = new StreamSource(this.getXslContextXslFile());
			if((xslfile==null)||(sourcefile==null)){
				logger.info("doSpecificTransform: All XSL Context files not specified for transform: " + transformtype);
				return;
			}			
			if(troutfile==null){ //Transform result/out file name based on source name extended with .trout
			troutfile = sourcefile.replaceAll("\\.xml", "_trout.trout");
				
			}
			
		} 

		if (xslstream != null) {
			Transformer transformer;
			try {
				transformer = factory.newTransformer(xslstream);
				Source xml = new StreamSource(sourcefile); // "data/caex_lego/Lego_example_mod1.aml"
				Result result = new StreamResult(troutfile); // "data/generated/CAEXLego2monkey3_results.xml");
				transformer.transform(xml, result);
				logger.info("doSpecificTransform: Transformation result file (.trout) saved: " + troutfile);

			} catch (TransformerConfigurationException e) {
				logger.log(Level.ERROR, e.getMessage());
				e.printStackTrace();
			} catch (TransformerException e) {
				logger.log(Level.ERROR, e.getMessage());
				e.printStackTrace();
			}

		}
	}

	public void setXslContex(File[] ctxfiles) {
		boolean srcbool = false;
		boolean xslbool = false;
		boolean troutbool = false;
		
		if (ctxfiles != null) {
			
			for (int i = 0; i < ctxfiles.length; i++) {
				File file = ctxfiles[i];
				String path = file.getAbsolutePath();
				if ((path.contains(".xml"))||(path.contains(".aml"))) {
					setXslContextSrcFile(path);
					srcbool = true;
				} else if (path.contains(".xsl")) {
					setXslContextXslFile(path);
					xslbool = true;
				} else if (path.contains(".trout")) {
					setXslContextTroutFile(path);
					troutbool = true;
				}			
			}
			
		}
		logger.info("setXslContex: loaded (.xml/aml;.xsl;.trout):(" + srcbool + ";" + xslbool + ";" + troutbool + ")");
	}
	
	public String getXslContextSrcFile() {
		return xslContextSrcFile;
	}


	public void setXslContextSrcFile(String xslContextSrcFile) {
		this.xslContextSrcFile = xslContextSrcFile;
	}


	public String getXslContextXslFile() {
		return xslContextXslFile;
	}


	public void setXslContextXslFile(String xslContextXslFile) {
		this.xslContextXslFile = xslContextXslFile;
	}


	public String getXslContextTroutFile() {
		return xslContextTroutFile;
	}


	public void setXslContextTroutFile(String xslContextTroutFile) {
		this.xslContextTroutFile = xslContextTroutFile;
	}

}
