package siima.app.operator;

import java.util.logging.Level;
import java.util.logging.Logger;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

//import siima.app.TaskCycleProcessor;
import siima.utils.ZipFileReader;

public class TxtFileReadOper {
	private static final Logger logger=Logger.getLogger(TxtFileReadOper.class.getName());
	private StringBuffer operErrorBuffer = new StringBuffer();
	private ZipFileReader zipper = new ZipFileReader();
	
	public String readTxtFile(String zipFilePath, String txtFilePathInZip){
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: readTxtFile()");	
		String txt = zipper.readTxtFile(zipFilePath, txtFilePathInZip);		
		if(txt!=null) operErrorBuffer.append("CLASS:" + getClass().getName() + " ERROR:" + "?NA?");
		return txt;
	}

	public StringBuffer getOperErrorBuffer() {
		return operErrorBuffer;
	}

	public void setOperErrorBuffer(StringBuffer operErrorBuffer) {
		this.operErrorBuffer = operErrorBuffer;
	}
	
	
}
