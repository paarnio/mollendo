package siima.app.control;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import siima.app.model.StudentJaxbContainer;

//import org.apache.log4j.Level;
//import org.apache.log4j.Logger;

import siima.app.model.TaskFlowJaxbContainer;
import siima.app.model.TaskFlowMetaData;
import siima.app.operator.TxtFileReadOper;
import siima.app.operator.XMLValidationCheck;
import siima.app.operator.XMLWellFormedCheck;
import siima.model.jaxb.checker.student.ExerciseType;
import siima.model.jaxb.checker.student.StudentType;
import siima.model.jaxb.checker.taskflow.CheckerTaskFlowType;
import siima.model.jaxb.checker.taskflow.FlowType;
import siima.model.jaxb.checker.taskflow.OperationType;
import siima.model.jaxb.checker.taskflow.ParamValueListType;
import siima.model.jaxb.checker.taskflow.TestCaseType;
import siima.utils.Testing_diff_match_patch;

public class TaskCycleProcessor {
	private static final Logger logger=Logger.getLogger(TaskCycleProcessor.class.getName());
	private String studentDataExcel; // = "data/excel/students.xlsx";
	private String projectHome;
	private TaskFlowMetaData taskFlowMetaData;
	private CheckerTaskFlowType currentTaskflow;
	//private List<StudentExercise> studentsExerciseData;
	
	private StudentJaxbContainer studentContainer;
	private List<StudentType> students;
	//private int currentTaskFlowIdx = 0;
	private ExcelMng excel_mng; // = new ExcelMng("data/excel/students.xlsx");
	//private TestCaseContainer test_cc = new TestCaseContainer();
	//private TaskFlowJaxbContainer taskflow_cont; // = new TaskFlowJaxbContainer(); //test_cc
	
	
	private TransformController trans_ctrl = new TransformController();	
	private TextCompareController compare_ctrl = new TextCompareController();
	private XMLWellFormedCheck wf_oper = new XMLWellFormedCheck();
	private XMLValidationCheck valid_oper = new XMLValidationCheck();
	private TxtFileReadOper read_oper = new TxtFileReadOper();
	
	public StringBuffer checkResultBuffer;
	public List<String> testcaseResults; // = new ArrayList<String>();
	public List<String> operationErrors;
	public List<String> testcasePoints;
	
	private List<String> dirList = new ArrayList<String>();
	private List<String> fileList = new ArrayList<String>();
	private Map<String,Integer> dirKeyIndexMap = new HashMap<String,Integer>();
	private Map<String,Integer> fileKeyIndexMap = new HashMap<String,Integer>();
	private List<String> channelList = new ArrayList<String>();
	private Map<String,Integer> channelKeyIndexMap = new HashMap<String,Integer>();
	
	/* Constructor */
	public TaskCycleProcessor(){ //String studentsExcel){
		//this.studentDataExcel = studentsExcel;
		//excel_mng = new ExcelMng(this.studentDataExcel);
		
		dirKeyIndexMap.put("stuDir1", 0); 
		dirKeyIndexMap.put("stuDir2", 1);
		dirKeyIndexMap.put("refDir1", 2); 
		dirKeyIndexMap.put("refDir2", 3);
		
		fileKeyIndexMap.put("stuFile1", 0);
		fileKeyIndexMap.put("stuFile2", 1);
		fileKeyIndexMap.put("refFile1", 2);
		fileKeyIndexMap.put("refFile2", 3);
		
		channelKeyIndexMap.put("stuC001", 0); //studentChannel
		channelKeyIndexMap.put("stuC002", 1);
		channelKeyIndexMap.put("refC001", 2); //referenceChannel
		channelKeyIndexMap.put("refC002", 3);
		channelKeyIndexMap.put("merC001", 4); //mergeChannel
		channelKeyIndexMap.put("merC002", 5);
		
		 channelList.add("");
		 channelList.add("");
		 channelList.add("");
		 channelList.add("");
		 channelList.add("");
		 channelList.add("");
	}
	
	public String getChannelStringValue(String channelKey){
		/* 
		 **/
		String value = null;
		Integer channelIdx = channelKeyIndexMap.get(channelKey);
		value = channelList.get(channelIdx.intValue());
		return value;	
	}
	
	public void setChannelStringValue(String channelKey, String value){
		/* 
		 **/
		Integer channelIdx = channelKeyIndexMap.get(channelKey);
		channelList.set(channelIdx.intValue(), value);
			
	}
	
	public String operParamFilePathValue(String parameter){
		/* Getting Operation parameter's target value
		 * now only filepath build
		 * e.g. parameter= 'stuDir1/stuFile1'
		 **/
		String value = null;
							
		String[] ppath = parameter.split("/");
		if(ppath.length>1){ // filepath?
			String dirKey = ppath[0];
			String fileKey = ppath[1];
			System.out.println("          Operation parameter 1 keys: " + dirKey + "/" + fileKey);					
			Integer dirIdx = dirKeyIndexMap.get(dirKey);
			Integer fileIdx = fileKeyIndexMap.get(fileKey);
			System.out.println("          Operation parameter 1 Indexes: " + dirIdx + "/" + fileIdx);	
			
			if(dirList.get(dirIdx.intValue()).endsWith("/")){
				 value = dirList.get(dirIdx.intValue()) + fileList.get(fileIdx.intValue());
				} else {
					 value = dirList.get(dirIdx.intValue()) + "/" + fileList.get(fileIdx.intValue());					
				}
			
		} else {// Channel?		
		}
		
		
		return value;
	}
	
	public void initProcessor(String projectHome, TaskFlowMetaData taskFlowMD, ExcelMng excel_mng, CheckerTaskFlowType currentTaskflow, StudentJaxbContainer studentContainer) {
		//Excel MainInfo contains file paths relative to project home
		this.excel_mng = excel_mng;
		this.excel_mng.openResultsExcel();
		this.projectHome = projectHome;
		this.taskFlowMetaData = taskFlowMD;
		this.currentTaskflow = currentTaskflow;
		//this.studentsExerciseData = readStudentBaseData();
		//TODO: NEW
		this.studentContainer = studentContainer;
		this.students = studentContainer.getStudents();
	}
	
	public void runTaskCycles() {
		/*
		 * 
		 */
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: runTaskCycles()");
		boolean oper_ok = true;
		String taskFlowXmlFile = this.projectHome + "/" + taskFlowMetaData.getTaskFlowXmlFile();
		//List<String> zips = readSubmitZipNames(); //zipFilesSheet);
		System.out.println("???? runTaskCycles(): taskFlowXmlFile: " + taskFlowXmlFile );
		List<TestCaseType> testcases = readTestCases(); //taskFlowXmlFile);
		//TODO:NEW Reading the base info of all students
		//List<StudentExercise> allStudents = readStudentBaseData();
		//System.out.println("???? runTaskCycles(): all Students #" + allStudents.size() );
		List<String> studentzips= new ArrayList<String>();
		//for(StudentExercise student : this.studentsExerciseData){
		for(StudentType student : this.students){
			String szip = student.getSubmitZip();
			studentzips.add(szip);
		}
		
		//this.runTaskCycles(testcases, zips);
		this.runTaskCycles(testcases, studentzips);
	}
	
	private void runTaskCycles(List<TestCaseType> testcases, List<String> zips) {
		/*
		 *  TODO: read reference files from a reference ZIP
		 */
		logger.log(Level.INFO, "Entering: " + getClass().getName() + " method: runTaskCycles()");
		boolean testcase_ok = true;
		boolean stuFlow_ok = true;
		boolean refFlow_ok = true;
		boolean merFlow_ok = true;
		boolean oper_ok = true;
		/*String resultFileDir = "data/zips";		
		String taskFlowXmlFile = "data/taskflow/taskflow3_U1E1_1_sub2.xml"; //"data/taskflow/taskflow2_2.xml";
		List<String> zips = readSubmitZipNames();
		List<TestCaseType> testcases = readTestCases(taskFlowXmlFile);
		*/
		
		System.out.println("\n********** runTaskCycles() ********\n");
		
		int submitcnt =0;
		/* --- Student Submit Loop --- */
		for (String zip : zips) {
			submitcnt++;
			System.out.println("\n-----------------------------------");
			System.out.println("+ Submit Loop #" + submitcnt);
			System.out.println("  Submit zip: " + zip);
			testcaseResults = new ArrayList<String>();	
			operationErrors = new ArrayList<String>();
			testcasePoints = new ArrayList<String>();
		/* --- TestCase Loop --- */
		testcase_ok = true;
		int testcasecount=0;
		for (TestCaseType tcase : testcases) {
			testcasecount++;
			checkResultBuffer = new StringBuffer();
			dirList = new ArrayList<String>();
			fileList = new ArrayList<String>();
			System.out.println("--+ TestCase Loop #" + testcasecount);
			
			String sdir1= tcase.getStuDir1();
				if(sdir1==null) sdir1 = "NA/";
			String sdir2= tcase.getStuDir2();
				if(sdir2==null) sdir2 = "NA/";
			String sfile1= tcase.getStuFile1();
				if(sfile1==null) sfile1 = "NA";
			String sfile2= tcase.getStuFile2();
				if(sfile2==null) sfile2 = "NA";
			
			String rdir1= tcase.getRefDir1();
				if(rdir1==null) rdir1 = "NA/";
			String rdir2= tcase.getRefDir2();
				if(rdir2==null) rdir2 = "NA/";
			String rfile1= tcase.getRefFile1();
				if(rfile1==null) rfile1 = "NA";
			String rfile2= tcase.getRefFile2();
				if(rfile2==null) rfile2 = "NA";
			 
			
			 dirList.add(sdir1);
			 dirList.add(sdir2); //for stuDir2
			 dirList.add(rdir1);
			 dirList.add(rdir2); //for refDir2
			 
			 fileList.add(sfile1);
			 fileList.add(sfile2);
			 fileList.add(rfile1);
			 fileList.add(rfile2);
			 
			 for(int i=0;i<channelList.size();i++){
				 channelList.set(i, ""); //clear channels
			 }
			 
			stuFlow_ok = true;
			refFlow_ok = true;
			merFlow_ok = true;
			List<FlowType> flows = tcase.getFlow();
			for (FlowType flow : flows) {
				System.out.println("--+--+ Flow Loop --- ");
				System.out.println("       Flow type: " + flow.getType());
				System.out.println("       Flow name: " + flow.getName());
				StringBuffer operErrorBuffer;
				oper_ok = true;
				List<OperationType> operations = flow.getOperation();
				for (OperationType oper : operations) {
					System.out.println("--+--+--+ Operation Loop --- ");
					System.out.println("          Operation type: " + oper.getType());
					System.out.println("          Operation name: " + oper.getName());
					System.out.println("          Operation return channel: " + oper.getReturn());
					operErrorBuffer = new StringBuffer();
					List<String> paramlist = null;
					List<String> valuelist = null;
					
					String par1 =oper.getPar1();
					String par2 =oper.getPar2();
					String returnChannel = oper.getReturn();
					//NEW
					ParamValueListType parvallist = oper.getParamValueList();
					if(parvallist!=null){
						paramlist = parvallist.getParamList();
						valuelist = parvallist.getValueList();
						System.out.println("          Operation (Param:Value) (" + paramlist.get(0) + ":" + valuelist.get(0) + ")");
					}
					
					String refzip = taskFlowMetaData.getReferenceZipFile(); // "RoundU1_sub2_reference.zip"; // reference Zip file
					String studentZipFolder= this.projectHome + "/" + taskFlowMetaData.getStudentZipFileFolder(); //"data/zips/RoundU1/";
					String referenceZipFolder= this.projectHome + "/" + taskFlowMetaData.getReferenceZipFileFolder(); //"data/zips/RoundU1/";
					String zippath1 = null;
					String zippath2 = null;
					
					if(par1!=null){
						if(par1.startsWith("stuDir")) zippath1=studentZipFolder + zip; 
						else if(par1.startsWith("refDir")) zippath1=referenceZipFolder + refzip;
					}
					if(par2!=null){
						if(par2.startsWith("stuDir")) zippath2=studentZipFolder + zip; 
						else if(par2.startsWith("refDir")) zippath2=referenceZipFolder + refzip;
					}
						/* --- Flow Branch --- */						
						String flowType = flow.getType();
						
						if(("studentFlow".equals(flowType))||("referenceFlow".equals(flowType))){
						
						/* --- Operation Branch --- */
							String operationType = oper.getType();
							switch (operationType) {
							case "XSLTransform": {
								System.out.println("................ XSLTransform ");		
								//oper_ok = true;
								trans_ctrl.setOperErrorBuffer(new StringBuffer());
								
								//Transformation source files									
								String fullXSLPathInZip = operParamFilePathValue(par1);
								String fullXMLPathInZip = operParamFilePathValue(par2);
								
								System.out.println("                 XSL file: " + fullXSLPathInZip);
								System.out.println("                 XML file: " + fullXMLPathInZip);
					
								oper_ok = trans_ctrl.prepareXSLTransformWithImputStreams(zippath1, fullXSLPathInZip, zippath2, fullXMLPathInZip);		
								if(oper_ok){
									
									
								/* OPTION Results to File 
									String resultFilePath = null;
									String[] splits = zip.split(".zip");
									if("studentFlow".equals(flowType))
										resultFilePath = resultFileDir + "/" + returnChannel + "_student_" + splits[0] + ".xml";
									else if("referenceFlow".equals(flowType))
										resultFilePath = resultFileDir + "/" + returnChannel + "_reference_" + splits[0] + ".xml";
									trans_ctrl.runTransformToFile(resultFilePath,  null,null);
									System.out.println("                 resultfile: " + resultFilePath);
								*/
									
								//OPTION Results to String
								ByteArrayOutputStream resultOutputStream = new ByteArrayOutputStream();
								String retStr = trans_ctrl.runTransformToString(resultOutputStream,  paramlist, valuelist);			
								setChannelStringValue(returnChannel, retStr);
								} else {
								operErrorBuffer = trans_ctrl.getOperErrorBuffer();
								}
							}
								break;
							case "XSDValidation": { //TODO: Test with other zipset
								System.out.println("................ XSDValidation ");
								//oper_ok = true;
								valid_oper.setOperErrorBuffer(new StringBuffer());
								String fullXSDPathInZip = operParamFilePathValue(par1);
								String fullXMLPathInZip = operParamFilePathValue(par2);
								oper_ok = valid_oper.validateXMLSchema(zippath1, fullXSDPathInZip, zippath2, fullXMLPathInZip);
								
								if(!oper_ok) {
									setChannelStringValue(returnChannel, "INVALID");
									operErrorBuffer = valid_oper.getOperErrorBuffer();
								} else {
									setChannelStringValue(returnChannel, "VALID");
								}
							}
								break;
							case "XMLWellFormed": { 
								System.out.println("................ XMLWellFormed ");		
								wf_oper.setOperErrorBuffer(new StringBuffer());
								
								String fullXMLPathInZip = operParamFilePathValue(par1);
								System.out.println("                 XML file: " + fullXMLPathInZip);
								
								oper_ok = wf_oper.checkWellFormedZipXML(zippath1, fullXMLPathInZip);
								if(!oper_ok){
									setChannelStringValue(returnChannel, "NON-WELLFORMED");
									operErrorBuffer = wf_oper.getOperErrorBuffer();
								} else {
									setChannelStringValue(returnChannel, "WELLFORMED");
								}
							}
								break;
							case "ReadTxtContent": { 
								System.out.println("................ ReadTxtContent ");	
								read_oper.setOperErrorBuffer(new StringBuffer());
								
								String fullTXTPathInZip = operParamFilePathValue(par1);
								System.out.println("                 TXT file: " + fullTXTPathInZip);
								String txtContent = read_oper.readTxtFile(zippath1, fullTXTPathInZip);
								if(txtContent==null){
									setChannelStringValue(returnChannel, "");
									operErrorBuffer = read_oper.getOperErrorBuffer();
								} else {
									setChannelStringValue(returnChannel, txtContent);
								}
								
							}
							break;
							}
		
							
						} else if("mergeFlow".equals(flowType)){
							
							System.out.println("\n==================================");
							System.out.println(".............mergeFlow ...........");
							System.out.println("..................................\n");
							//oper_ok = true;
							/* --- Operation Branch --- */
							String operationType = oper.getType();
							
							switch (operationType) {
							case "StringCompare": { 
								
								if(stuFlow_ok && refFlow_ok){//compare only successful flows
								System.out.println("................ StringCompare ");
								String arg1str = null;
								String arg2str = null;
							
								arg1str = getChannelStringValue(par1);
								arg2str = getChannelStringValue(par2);
								
								String result = "EQUAL";
								compare_ctrl.setUp();															
								boolean isequal = compare_ctrl.compareTextLines(arg1str, arg2str);
								if(!isequal){
									operErrorBuffer = compare_ctrl.getFilteredResults("DELETE", 0, 100); //minLength, cutLength
									result = "NOT-EQUAL";
									oper_ok = false;
								} 
								System.out.println("====== MERGE RESULT: " + result + "============\n");	
								setChannelStringValue(returnChannel, result);
								checkResultBuffer.append("TESTCASE(" + testcasecount + "):" + result + "\n");
								} else { // stuFlow or refFlow not succesfull
									checkResultBuffer.append("TESTCASE(" + testcasecount + "):" + "NOT-COMPARED" + "\n");
								}
							}
								break;
							case "NA": {
								System.out.println("................NA??? ");
							}
								break;
							}
							
						}
						if(operErrorBuffer.length()>0)
							operationErrors.add("ERROR: SUBMIT(" + submitcnt + ") TESTCASE(" + testcasecount + ") MSG:(" + operErrorBuffer.toString() + ")");
						/* Flow success ? */
						if(!oper_ok){
							if("studentFlow".equals(flowType)) stuFlow_ok = false;
							if("referenceFlow".equals(flowType)) refFlow_ok = false;
							if("mergeFlow".equals(flowType)) merFlow_ok = false;
						}
					} // Operation loop
				} // Flow loop
			String points = tcase.getPoints();		
			testcaseResults.add("RESULT(" + submitcnt + ") TCASE(" + testcasecount + ")  FLOW(" + stuFlow_ok + ") MSG(" + checkResultBuffer.toString() + ")");
			if((stuFlow_ok)&&(merFlow_ok)) testcasePoints.add(points); //TODO points as string
				else testcasePoints.add("0");
			}// TestCase Loop ---
		setStudentData(submitcnt, testcasecount, testcaseResults, operationErrors, testcasePoints);
		writeSubmitTestCaseResults(submitcnt, testcasecount, testcaseResults, operationErrors, testcasePoints);
		}//Student zip loop	---	
		writeAllResultsAndCloseExcel(true); 
	 
	}
	
	public void setStudentData(int submitcnt, int testcasecount, List<String> tcResults, List<String> operErrors, List<String> tcPoints){
		//Saving results to Student's ExerciseType object
		int studentIdx = submitcnt-1;
		//StudentType student = studentContainer.getStudent(studentIdx);
		String exerciseId = this.currentTaskflow.getExercise();
		//Create a new ExerciseType object
		ExerciseType exercise = new ExerciseType();
		exercise.setExerciseId(exerciseId);
		exercise.getResultsOfTestCases().addAll(tcResults);
		exercise.getErrorsOfTestCases().addAll(operErrors);
		for(String strPoint : tcPoints){
			exercise.getPointsOfTestCases().add(Integer.valueOf(strPoint));
		}
		
		this.studentContainer.addStudentExercise(studentIdx, exercise);
	}
	

	public void  writeAllResultsAndCloseExcel(boolean closeExcel){
		excel_mng.saveAndCloseResultsExcel(closeExcel);
	
	}
	public void writeSubmitTestCaseResults(int submitcnt, int testcasecount, List<String> tcResults, List<String> operErrors, List<String> tcPoints){
	/* Writing one student's results into project excel
	 * NOTE: DO NOT write [ ] into excel: problems occur
	 * testcasesResultsLists	
	 */
		//Testcase Results
		System.out.println("testcaseResults #" + tcResults.size());
		//writeTestcaseResults(List<String> results, String sheetname, int colind, int rowind)
		excel_mng.writeTestcaseResults(tcResults, tcPoints, submitcnt, this.taskFlowMetaData);
		
		//Error messages
		System.out.println("operationErrors #" + operErrors.size());
		if(operErrors.size()>0){
			System.out.println("operationErrors:" + operErrors.get(0));
			
			excel_mng.writeOperErrorMsgs(operErrors, submitcnt, this.taskFlowMetaData);
		}
		
	}
	
	
	
	public List<String> readSubmitZipNames() { //String sheetname){
		//String sheetname = "ZipFiles";
		List<String> zips;
		ExcelMng mng = getExcel_mng();
		zips = mng.readSubmitZipNames();//this.taskFlowMetaData); //sheetname, 4, 5, 10, 13);
		return zips;
	}
	

	public List<TestCaseType> readTestCases(){ //String taskFlowXmlFile){
		List<TestCaseType> cases = null;
		if(this.currentTaskflow!=null)
			cases = this.currentTaskflow.getTestCase();
		else
			System.out.println("???? readTestCases(): taskflow: null");
		return cases;
	}
	
	
	public ExcelMng getExcel_mng() {
		return excel_mng;
	}

	public TransformController getTrans_ctrl() {
		return trans_ctrl;
	}

	public TextCompareController getCompare_ctrl() {
		return compare_ctrl;
	}

	public TxtFileReadOper getRead_oper() {
		return read_oper;
	}

	
}
