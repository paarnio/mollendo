package siima.app.operator;

//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

//import siima.app.TaskCycleProcessor;
import siima.utils.ZipFileReader;

public class TxtFileReadOper {
	private static final Logger logger=Logger.getLogger(TxtFileReadOper.class.getName());
	private StringBuffer operErrorBuffer = new StringBuffer();
	private ZipFileReader zipper = new ZipFileReader();
	
	public String readTxtFile(String zipFilePath, String txtFilePathInZip){
		logger.log(Level.INFO, "Entering: " + getClass().getSimpleName() + " method: readTxtFile()");	
		String txt = zipper.readTxtFile(zipFilePath, txtFilePathInZip);		
		if(txt==null) operErrorBuffer.append("OPER:" + getClass().getSimpleName() + ":ERROR:" + "?NA?");
		return txt;
	}

	public boolean checkFileExistenceInZip(String zipFilePath, String txtFilePathInZip){
		logger.log(Level.INFO, "Entering: " + getClass().getSimpleName() + " method: checkFileExistenceInZip()");	
		boolean exist = zipper.checkPathExistenceInZip(zipFilePath, txtFilePathInZip);		
		if(!exist){
			operErrorBuffer.append("CHECK" + getClass().getSimpleName() + ":Existence check:ERROR: Folder/File: " + txtFilePathInZip + " or Zip: " + zipFilePath + " DOES NOT EXIST?");
			logger.log(Level.INFO, "CHECK: " + getClass().getSimpleName() + " method: checkFileExistenceInZip(): ERROR: Folder/File: " + txtFilePathInZip + " or Zip: " + zipFilePath + " DOES NOT EXIST?");	
		}
		return exist;
	}
	public StringBuffer getOperErrorBuffer() {
		return operErrorBuffer;
	}

	public void setOperErrorBuffer(StringBuffer operErrorBuffer) {
		this.operErrorBuffer = operErrorBuffer;
	}
	
	
	
}
