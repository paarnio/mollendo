/* TaskFlowMetaInfo.java
 * 
 * Contains meta data related to one (1) task flow (i.e. one exercise).
 * This data is populated by ExcelMng object reading it
 * from the project excel file (.xlsx)
 * One project excel may contain several taskFlow definitions.
 */
package siima.app.model;

public class TaskFlowMetaData {
	
	public String taskFlowMetaId; //e.g. U1E1_1
	public String taskFlowXmlFile;
		
	public String studentZipFileFolder;
	public String referenceZipFileFolder;
	public String referenceZipFile;
	public int zipFileCount;
	
	public String zipFilesSheet;
	public String resultsSheet;
	
	
	
	public String getTaskFlowMetaId() {
		return taskFlowMetaId;
	}
	public void setTaskFlowMetaId(String taskFlowMetaId) {
		this.taskFlowMetaId = taskFlowMetaId;
	}
	public String getTaskFlowXmlFile() {
		return taskFlowXmlFile;
	}
	public void setTaskFlowXmlFile(String taskFlowXmlFile) {
		this.taskFlowXmlFile = taskFlowXmlFile;
	}
	public String getStudentZipFileFolder() {
		return studentZipFileFolder;
	}
	public void setStudentZipFileFolder(String studentZipFileFolder) {
		this.studentZipFileFolder = studentZipFileFolder;
	}
	public String getReferenceZipFileFolder() {
		return referenceZipFileFolder;
	}
	public void setReferenceZipFileFolder(String referenceZipFileFolder) {
		this.referenceZipFileFolder = referenceZipFileFolder;
	}
	public String getReferenceZipFile() {
		return referenceZipFile;
	}
	public void setReferenceZipFile(String referenceZipFile) {
		this.referenceZipFile = referenceZipFile;
	}
	public int getZipFileCount() {
		return zipFileCount;
	}
	public void setZipFileCount(int zipFileCount) {
		this.zipFileCount = zipFileCount;
	}
	public String getZipFilesSheet() {
		return zipFilesSheet;
	}
	public void setZipFilesSheet(String zipFilesSheet) {
		this.zipFilesSheet = zipFilesSheet;
	}
	public String getResultsSheet() {
		return resultsSheet;
	}
	public void setResultsSheet(String resultsSheet) {
		this.resultsSheet = resultsSheet;
	}
	
	
	

}
