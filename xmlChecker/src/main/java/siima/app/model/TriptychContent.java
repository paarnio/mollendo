package siima.app.model;

public class TriptychContent {
	/*
	 *  String contents for three windows
	 *  Used for displaying Compared Student and Reference strings
	 *  and compare results
	 */
	
	private String studentContent; //West window
	private String referenceContent; //East window
	private String compareResult; //South window
	
	public String getStudentContent() {
		return studentContent;
	}
	public void setStudentContent(String studentContent) {
		this.studentContent = studentContent;
	}
	public String getReferenceContent() {
		return referenceContent;
	}
	public void setReferenceContent(String referenceContent) {
		this.referenceContent = referenceContent;
	}
	public String getCompareResult() {
		return compareResult;
	}
	public void setCompareResult(String compareResult) {
		this.compareResult = compareResult;
	}
	

}
