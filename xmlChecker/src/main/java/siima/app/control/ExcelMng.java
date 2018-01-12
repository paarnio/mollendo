/* ExcelMng
 * FROM: CourseScheduler.java
 * 205-03-23 from HP
 * Configuration in excel courses_real.xlsx 
 */

package siima.app.control;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import siima.app.model.TaskFlowMetaData;
import siima.model.jaxb.checker.student.StudentType;
import siima.utils.ExcelToStringArray;


public class ExcelMng {
	private static final Logger logger=Logger.getLogger(ExcelMng.class.getName());
	private ExcelToStringArray ex2s;
	private String studentDataExcel;
	private Map<String, String> configuremap = null;
	private Map<String, String> codeperiodmap = null;

	public static String mainInfoSheet = "MainInfo";
	public int mainInfoKeyCol = 1;
	public int mainInfoKeyFirstRow = 20; //set in readMainInfo()
	public int mainInfoKeyLastRow = 30; //set in readMainInfo()
	
	public String taskFlowXmlFile;
	public String zipFilesSheet;
	
	private int submitZipCol = 4;
	private int submitZipFirstRow = 10;
	private int submitZipCount = 0; //read from MainInfo Sheet
	private String studentZipFileFolder;
	private String referenceZipFileFolder;
	private String referenceZipFile;
	
	private String resultsSheet;
	//Cells reserved for 20 testcases: 20 points, 20 results, 20 errors
	private int resultFirstCol = 6;
	private int resultFirstRow = 10;
	private int pointsFirstCol = 26; //+20
	private int pointsFirstRow = 10;
	private int errorMsgFirstCol = 46; //+20
	private int errorMsgFirstRow = 10;
	
	//NEW
	private List<TaskFlowMetaData> taskFlowMetaDataList;
	//private List<StudentType> studentList;
	
	public ExcelMng(String excelfile){
		this.studentDataExcel = excelfile;
		this.ex2s = new ExcelToStringArray(excelfile);

		//this.studentList = new ArrayList<StudentType>();
		//this.readStudentsBaseData();
		this.taskFlowMetaDataList = new ArrayList<TaskFlowMetaData>();
		this.readTaskFlowMainInfo();
	}
	
	public List<StudentType> readStudentsBaseData(){
		logger.log(Level.INFO,"readStudentsBaseData()");
		this.ex2s.setSheetind(this.ex2s.getSheetIndex(mainInfoSheet));
		int colIdx = 1;
		
		String searchKey = "StudentSheet";
		int rowIdx = this.ex2s.searchString(searchKey, colIdx, 0, 2);
		if(rowIdx>=0){
			this.zipFilesSheet = this.ex2s.getCellValue(colIdx+1, rowIdx);
			
		}
		/* 
		 * Reading Students sheet (same as submit sheet)
		 */
		List<StudentType> students = new ArrayList<StudentType>();
		
		// Expecting all lists the same size
		List<String> surnames = this.readStudentSurname(); 
		List<String> firstnames = this.readStudentFirstname();
		List<String> studentIds = this.readStudentId();
		List<String> studentZips = this.readSubmitZipNames();
		
		for(int i = 0; i<surnames.size(); i++){
			StudentType student = new StudentType();
			student.setSurname(surnames.get(i));
			student.setFirstname(firstnames.get(i));
			student.setStudentId(studentIds.get(i));
			student.setSubmitZip(studentZips.get(i));
			
			students.add(student);
		}
		return students;
	}
	
	public void readTaskFlowMainInfo(){
		logger.log(Level.INFO,"readTaskFlowMainInfo()");
		//String taskFlowXmlFile = null;
		this.ex2s.setSheetind(this.ex2s.getSheetIndex(mainInfoSheet));
		int colIdx = 1;
		/* SELECTED TASKFLOW BLOCK? 
		 * Reading the selected TaskFlow Block row indexes
		 */
		
		String searchKey = "KeyFirstRow";
		int rowIdx = this.ex2s.searchString(searchKey, colIdx, 4, 5);
		if(rowIdx>=0){
			String keyFirstRow = this.ex2s.getCellValue(colIdx+1, rowIdx);
			this.mainInfoKeyFirstRow = Integer.parseInt(keyFirstRow);
		}
		searchKey = "KeyLastRow";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, 4, 5);
		if(rowIdx>=0){
			String keyLastRow = this.ex2s.getCellValue(colIdx+1, rowIdx);
			this.mainInfoKeyLastRow = Integer.parseInt(keyLastRow);
		}
		
		/*
		 *  How many TASKFLOWs have been declared
		 * 
		 */
		List<Integer> tfRows = new ArrayList<Integer>();
		boolean more = true;
		int tfcnt = 0;
		while(more){
			
			searchKey = "TASKFLOW";
			rowIdx = this.ex2s.searchString(searchKey, colIdx, this.mainInfoKeyFirstRow + tfcnt*10, this.mainInfoKeyLastRow + tfcnt*10);
			if(rowIdx>=0){
				tfRows.add(Integer.valueOf(rowIdx));			
			} else more=false;			
			tfcnt++;
		}
		//System.out.println("???MAININFO taskFlow count: " + tfRows.size());
		
		for(Integer taskFlowFirstRow : tfRows){
			this.mainInfoKeyFirstRow = taskFlowFirstRow.intValue();
			this.mainInfoKeyLastRow = taskFlowFirstRow.intValue() + 9;
		/* TASKFLOW BLOCK's KEY VALUES
		 * Reading Key Values
		 */
		TaskFlowMetaData taskFlowMD = new TaskFlowMetaData();
		//taskFlowMD.setTaskFlowId("TASKFLOW_X");
		
		searchKey = "TASKFLOW";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, this.mainInfoKeyFirstRow, this.mainInfoKeyLastRow);
		if(rowIdx>=0){
		 String taskFlowMetaId = this.ex2s.getCellValue(colIdx+1, rowIdx);
		 taskFlowMD.setTaskFlowMetaId(taskFlowMetaId);
		}
		logger.log(Level.INFO,"readTaskFlowMainInfo():taskFlowId: " + taskFlowMD.getTaskFlowMetaId());
		//System.out.println("???MAININFO taskFlowId: " + taskFlowMD.getTaskFlowMetaId());
		
		
		searchKey = "TaskFlowXmlFile";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, this.mainInfoKeyFirstRow, this.mainInfoKeyLastRow);
		if(rowIdx>=0){
		 this.taskFlowXmlFile = this.ex2s.getCellValue(colIdx+1, rowIdx);
		 taskFlowMD.setTaskFlowXmlFile(this.taskFlowXmlFile);
		}
		logger.log(Level.INFO,"readTaskFlowMainInfo():taskFlowXmlFile: " + taskFlowXmlFile);
		//System.out.println("???MAININFO taskFlowXmlFile: " + taskFlowXmlFile);
		
		searchKey = "ZipFilesSheet";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, this.mainInfoKeyFirstRow, this.mainInfoKeyLastRow);
		if(rowIdx>=0){
		 this.zipFilesSheet = this.ex2s.getCellValue(colIdx+1, rowIdx);
		 taskFlowMD.setZipFilesSheet(this.zipFilesSheet);
		}
		
		searchKey = "ZipFileCount";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, this.mainInfoKeyFirstRow, this.mainInfoKeyLastRow);
		if(rowIdx>=0){
		 String zipFileCountStr = this.ex2s.getCellValue(colIdx+1, rowIdx);
		 if(zipFileCountStr!=null) {
			 this.submitZipCount = Integer.parseInt(zipFileCountStr);
			 taskFlowMD.setZipFileCount(this.submitZipCount);
		 }
		}
		logger.log(Level.INFO,"readTaskFlowMainInfo():submitZipCount: " + submitZipCount);
		
		searchKey = "StudentZipFileFolder";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, this.mainInfoKeyFirstRow, this.mainInfoKeyLastRow);
		if(rowIdx>=0){
		 this.studentZipFileFolder = this.ex2s.getCellValue(colIdx+1, rowIdx);
		 taskFlowMD.setStudentZipFileFolder(this.studentZipFileFolder);
		}
		logger.log(Level.INFO,"readTaskFlowMainInfo():studentZipFileFolder: " + studentZipFileFolder);
		
		searchKey = "ReferenceZipFileFolder";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, this.mainInfoKeyFirstRow, this.mainInfoKeyLastRow);
		if(rowIdx>=0){
		 this.referenceZipFileFolder = this.ex2s.getCellValue(colIdx+1, rowIdx);
		 taskFlowMD.setReferenceZipFileFolder(this.referenceZipFileFolder);
		}
		logger.log(Level.INFO,"readTaskFlowMainInfo():referenceZipFileFolder: " + referenceZipFileFolder);	
		//System.out.println("???MAININFO referenceZipFileFolder: " + referenceZipFileFolder);
		
		searchKey = "ReferenceZipFile";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, this.mainInfoKeyFirstRow, this.mainInfoKeyLastRow);
		if(rowIdx>=0){
		 this.referenceZipFile = this.ex2s.getCellValue(colIdx+1, rowIdx);
		 taskFlowMD.setReferenceZipFile(this.referenceZipFile);
		}
		logger.log(Level.INFO,"readTaskFlowMainInfo():referenceZipFile: " + referenceZipFile);	
		//System.out.println("???MAININFO referenceZipFile: " + referenceZipFile);
		
		searchKey = "ResultsSheet";
		rowIdx = this.ex2s.searchString(searchKey, colIdx, this.mainInfoKeyFirstRow, this.mainInfoKeyLastRow);
		if(rowIdx>=0){
		 this.resultsSheet = this.ex2s.getCellValue(colIdx+1, rowIdx);
		 taskFlowMD.setResultsSheet(this.resultsSheet);
		}
		
		this.taskFlowMetaDataList.add(taskFlowMD);
		} // end for
		logger.log(Level.INFO,"readTaskFlowMainInfo():taskFlowMetaDataList:size: " + taskFlowMetaDataList.size());	
		//System.out.println("???MAININFO taskFlowMetaDataList: size: " + taskFlowMetaDataList.size());
	}
	public List<String> readStudentSurname(){ //TaskFlowMetaData taskFlowMD){
		/*
		 * Columns: OrderNr; Surname; Firstname; StudentId; SubmitZip	
		 */
		
		List<String> surnames=null;
		this.ex2s.setSheetind(this.ex2s.getSheetIndex(this.zipFilesSheet));//taskFlowMD.getZipFilesSheet())); //this.zipFilesSheet));
		this.ex2s.setCellArea(submitZipCol-3, submitZipCol-3, submitZipFirstRow, submitZipFirstRow+submitZipCount-1); //
		surnames = this.ex2s.toStringList(false);
		
		return surnames;
	}
	
	public List<String> readStudentFirstname(){ //TaskFlowMetaData taskFlowMD){
		/*
		 * Columns: OrderNr; Surname; Firstname; StudentId; SubmitZip	
		 */
		
		List<String> firstnames=null;
		this.ex2s.setSheetind(this.ex2s.getSheetIndex(this.zipFilesSheet));//taskFlowMD.getZipFilesSheet())); //this.zipFilesSheet));
		this.ex2s.setCellArea(submitZipCol-2, submitZipCol-2, submitZipFirstRow, submitZipFirstRow+submitZipCount-1); //
		firstnames = this.ex2s.toStringList(false);
		
		return firstnames;
	}
	
	public List<String> readStudentId(){ //TaskFlowMetaData taskFlowMD){
		/*
		 * Columns: OrderNr; Surname; Firstname; StudentId; SubmitZip
		 * 
		 * NOTE: When accessed StudentId string will contain extra .0 (e.g. 123456.0)
		 * This .0 must be filtered out 	
		 */
		
		List<String> studentIds=new ArrayList<String>();
		this.ex2s.setSheetind(this.ex2s.getSheetIndex(this.zipFilesSheet));//taskFlowMD.getZipFilesSheet())); //this.zipFilesSheet));
		this.ex2s.setCellArea(submitZipCol-1, submitZipCol-1, submitZipFirstRow, submitZipFirstRow+submitZipCount-1); //
		List<String> idlist = this.ex2s.toStringList(false);
		for(String idstr : idlist){
			//Filtering extra .0
			studentIds.add(idstr.split("\\.")[0]);			
		}
		
		return studentIds;
	}
	
	
	public List<String> readSubmitZipNames(){ //TaskFlowMetaData taskFlowMD){
		/*
		 * Columns: OrderNr; Surname; Firstname; StudentId; SubmitZip	
		 */
		List<String> zips=null;
		this.ex2s.setSheetind(this.ex2s.getSheetIndex(this.zipFilesSheet));//taskFlowMD.getZipFilesSheet())); //this.zipFilesSheet));
		this.ex2s.setCellArea(submitZipCol, submitZipCol, submitZipFirstRow, submitZipFirstRow+submitZipCount-1); //
		zips = this.ex2s.toStringList(false);
		
		return zips;
	}
	public void writeTestcaseResults(List<String> results, List<String> tcPoints, int submitCount, TaskFlowMetaData taskFlowMD){
		/* Writing all the testcase results of one student submit 
		 * into the students row in excel file 
		 */
		logger.log(Level.INFO,"writeTestcaseResults()");
		//System.out.println("??????? ResultsSheet()" + taskFlowMD.getResultsSheet());
		int sheetidx = this.ex2s.getSheetIndex(taskFlowMD.getResultsSheet()); //this.resultsSheet);
		this.ex2s.setSheetind(sheetidx);
		this.ex2s.writeStringListToColumnOrRowField(true,sheetidx, this.resultFirstCol, this.resultFirstRow + submitCount -1, results, true);
		this.ex2s.writeStringListToColumnOrRowField(true,sheetidx, this.pointsFirstCol, this.pointsFirstRow + submitCount -1, tcPoints, true);		
		
	}
	
	public void writeOperErrorMsgs(List<String> results, int submitCount, TaskFlowMetaData taskFlowMD){
		/* Writing all the operation error messages of one student submit 
		 * into the students row in excel file 
		 */
		logger.log(Level.INFO,"writeOperErrorMsgs()");
		int sheetidx = this.ex2s.getSheetIndex(taskFlowMD.getResultsSheet()); //this.resultsSheet);
		this.ex2s.setSheetind(sheetidx);
		this.ex2s.writeStringListToColumnOrRowField(true,sheetidx, this.errorMsgFirstCol, this.errorMsgFirstRow + submitCount -1, results, true);				
	}
	
	public void saveAndCloseResultsExcel(boolean close){
		this.ex2s.saveWorkbook(studentDataExcel, close);
		
	}
	
	public void openResultsExcel(){
		//Closes and Opens again the current excel workbook
		this.ex2s.openWorkbook();
		
	}
	
	/*
	 * GETTERS SETTERS
	 */
	
	
	

	public List<TaskFlowMetaData> getTaskFlowMetaDataList() {
		return taskFlowMetaDataList;
	}


	/*
	public String getTaskFlowXmlFile() {
		return taskFlowXmlFile;
	}
	
	
	public String getZipFilesSheet() {
		return zipFilesSheet;
	}

	public String getResultsSheet() {
		return resultsSheet;
	}

	public String getStudentDataExcel() {
		return studentDataExcel;
	}

	public void setStudentDataExcel(String studentDataExcel) {
		this.studentDataExcel = studentDataExcel;
	}

	public int getSubmitZipCol() {
		return submitZipCol;
	}

	public int getSubmitZipFirstRow() {
		return submitZipFirstRow;
	}

	public int getSubmitZipCount() {
		return submitZipCount;
	}

	public String getStudentZipFileFolder() {
		return studentZipFileFolder;
	}

	public String getReferenceZipFileFolder() {
		return referenceZipFileFolder;
	}

	public String getReferenceZipFile() {
		return referenceZipFile;
	}
*/
	public static void main(String[] args) {
		
		/*ExcelMng mng = new ExcelMng("data/excel/test.xlsx");
		String[] sched = mng.readPredefinedSchedulesFromExcel("season", 1, "Sheet1", 1, 6, 10, 19);
		for(int i=0; i<sched.length ; i++)
		System.out.println("OUT" + sched[i]);
		
		List<String> zips = mng.readSubmitZipNames(0); //"Sheet1", 4, 5, 10, 13);
		for(String zip : zips) System.out.println("OUT" + zip);
		
		mng = new ExcelMng("data/excel/students.xlsx");
		mng.readMainInfo();
		//System.out.println("MAININFO TaskFlowXmlFile: " + mng.getTaskFlowXmlFile());
		*/
	}

	
	
}
