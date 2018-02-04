/* MainAppController.java
 * 
 * Note: Maven resources
 * Reading from src/main/resources 		
   ClassLoader classLoader = getClass().getClassLoader();
   File file = new File(classLoader.getResource("file/test.xml").getFile());
   InputStream inputStream = new FileInputStream(file);
*/


package siima.app.control;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import siima.app.gui.MainFrame;
import siima.app.model.StudentJaxbContainer;
import siima.app.model.TaskFlowJaxbContainer;
import siima.app.model.TaskFlowMetaData;
import siima.app.model.TriptychContent;
import siima.app.model.tree.ElementModel;
import siima.app.model.tree.ElementNode;
import siima.app.model.tree.ElementTree;
import siima.app.operator.TxtFileReadOper;
import siima.app.xaddon.ContextXSLTransform;
import siima.model.jaxb.checker.student.ExerciseType;
import siima.model.jaxb.checker.student.StudentType;
import siima.model.jaxb.checker.taskflow.CheckerTaskFlowType;
import siima.model.jaxb.checker.taskflow.FlowType;
import siima.model.jaxb.checker.taskflow.OperationType;
import siima.model.jaxb.checker.taskflow.ParamValueListType;
import siima.model.jaxb.checker.taskflow.TestCaseType;

public class MainAppController {
	private static final Logger logger=Logger.getLogger(MainAppController.class.getName());
	private String projectExcelFile = "data/project_U1_sub1/students_U1.xlsx";
	private String projectHome;
	private MainFrame viewFrame;
	private TaskFlowJaxbContainer graphbuilder;	
	private StudentJaxbContainer studentContainer;	
	private TaskCycleProcessor taskCycleProcessor;
	private ExcelMng excel_mng;
	//New Addon
	private ContextXSLTransform contextXsltAddon = new ContextXSLTransform();
	
	
	// TREE:TaskFlows Hierarchy
	public ElementTree taskFlowsTree;
	public ElementModel taskFlowsTreeModel;
	
	private List<TaskFlowMetaData> taskFlowMetaDataList;
	private CheckerTaskFlowType selectedTaskflowObject;
	private int selectedTaskflowIndex = -1;
	private int selectedTestCaseIndex = -1;
	private String selectedStuSolutionObject;
	private String selectedRefSolutionObject;
	private int selectedStudentIndex = -1;
	private String selectedStudentZipFile;
	
	private TxtFileReadOper read_oper = new TxtFileReadOper();
	
	public MainAppController(MainFrame viewFrame) {
		this.viewFrame = viewFrame; // if needed
		graphbuilder = new TaskFlowJaxbContainer();	
		
	}

	public boolean loadStudentDataFromXMLFile(File studentXML){
		//NEW:
		Path path = studentXML.toPath(); 
		boolean ok = this.studentContainer.unmarshalStudentSubmitsType(path);
		return ok;
	}
	
	public void openProjectExcel(File project_xlsx, boolean loadStudentData ){
		logger.info("======================================================");
		logger.info("=====  openProjectExcel() Opening Project Excel  =====");
		boolean ok = true;
		this.projectExcelFile = project_xlsx.getAbsolutePath();
		this.projectHome = project_xlsx.getParent();
		this.initApplication(loadStudentData);
		System.out.println("===HOME=== " + this.projectHome);
		//return ok;
	}
	
	public void closeProjectExcel(){
		//Remember to close excel
		excel_mng.saveAndCloseResultsExcel(true);
	}
	
	public void initApplication(boolean loadStudentData){
		/* IF loadStudentData == true: Student data is read from project excel
		 * IF loadStudentData == false: Student data can be later read from 
		 * earlier Student Results xml files by unmarshalling.
		 */
		
		excel_mng = new ExcelMng(this.projectExcelFile);
		this.studentContainer = new StudentJaxbContainer(excel_mng);
		if(loadStudentData) this.studentContainer.readStudentBaseData();
		
		this.taskFlowMetaDataList = excel_mng.getTaskFlowMetaDataList();
		taskCycleProcessor = new TaskCycleProcessor(); //this.projectExcel);
		if((taskFlowMetaDataList!=null)&&(!taskFlowMetaDataList.isEmpty())){
			
			for(TaskFlowMetaData taskFlowMetaData: taskFlowMetaDataList ){
			//TaskFlowMetaData taskFlowMetaData = taskFlowMetaDataList.get(0);
			String taskFlowXmlfile = this.projectHome + "/" + taskFlowMetaData.getTaskFlowXmlFile();
			openTaskFlowFile(taskFlowXmlfile);			
			}
		}
		
	}
	
	public void invokeCheckingProcess(){
		logger.log(Level.INFO,"invokeCheckingProcess()");		
		if(runConditions()){
			
			taskCycleProcessor.initProcessor(this.projectHome, this.selectedTaskflowIndex, taskFlowMetaDataList.get(this.selectedTaskflowIndex), selectedTaskflowObject, studentContainer, excel_mng);
			taskCycleProcessor.runTaskCycles();
		
		}
		
	}
	
	public boolean runConditions(){
		boolean ok = false;
		if((selectedTaskflowObject!=null)&&(this.taskFlowMetaDataList!=null)&&(this.selectedTaskflowIndex>=0)
				&&(this.selectedTaskflowIndex < this.taskFlowMetaDataList.size())){
			
			ok = true;
		}
		
		return ok;
	}
	
	
	public void openTaskFlowFile(String taskFlowXmlfile){
		boolean ok = true;
		buildJaxbModel(taskFlowXmlfile);
		//return ok;
	}
	
	public void buildJaxbModel(String xmlfile) {
		/*
		 * See: project ERAmlHandler
		 * 
		 */
		logger.log(Level.INFO,"buildJaxbModel()");
		//ElementTree
		// Example file: "data/caex_exs/RunningExample_SimpleIH.aml"
		Path path = Paths.get(xmlfile);
		// this.graphbuilder = new JaxbContainer(path);
		graphbuilder.loadData(path);
		graphbuilder.buildElementGraphFromXML();
		
		// Construct the jtree for TaskFlows hierarchy.
		ElementNode taskFlowsRootElement = graphbuilder.getTaskFlowsRootElement();
		int tfsccount = taskFlowsRootElement.getChildCount();
		if (tfsccount > 0) {
			this.taskFlowsTreeModel = new ElementModel(graphbuilder.getTaskFlowsRootElement());
			this.taskFlowsTree = new ElementTree(this.taskFlowsTreeModel);
		}
	
	}
	
	public void invokeSelectedTestCaseForStudent(){
		/* Running a selected testCase for a single selected student
		* One Student have to be selected from Student table and
		* one testCase have to be selected from TaskFlow tree
		* Displaying testcase's student flow results in: rightTopLeftTextArea
		* Displaying testcase's reference flow results in: rightTopRightTextArea
		* Displaying merge flow's difference results in Result tab
		*/
		logger.log(Level.INFO,"invokeSelectedTestCaseForStudent()");
		if (runConditions()) {
			if ((this.selectedTestCaseIndex >= 0) && (this.selectedStudentIndex >= 0)) {
				taskCycleProcessor.initProcessor(this.projectHome, this.selectedTaskflowIndex, taskFlowMetaDataList.get(this.selectedTaskflowIndex), selectedTaskflowObject, studentContainer, excel_mng);
				taskCycleProcessor.runTestCaseForOneStudent(this.selectedTestCaseIndex, this.selectedStudentIndex);
				System.out.println("???TODO: RUN SELECTED TESTCASE: " + (selectedTestCaseIndex + 1)
						+ " FOR SELECTED STUDENT: " + (selectedStudentIndex + 1));
				TriptychContent displaycontent = taskCycleProcessor.getSingleStudentCompareResults();
				//Moving the text before the EQU# DEL# INS# parts at the end of the result string
				String compare_res = displaycontent.getCompareResult();
				int minx = -1;
				int eix = compare_res.indexOf("EQU#");
				int dix = compare_res.indexOf("DEL#");
				int iix = compare_res.indexOf("INS#");
				
				//Search for the min index value that is >=0
				if(eix>=0){
					if(eix<=dix) minx = eix;
					else if(dix>=0) minx = dix;			
					if((iix>=0)&&(iix<minx)) minx = iix;					
				} else if(dix>=0){
					if(dix<=iix) minx = dix;
					else if(iix>=0) minx = iix;				
				}  else if(iix>=0){
					minx = iix;
				}
				String diff_part ="";
				String prefix_part ="";
				
				if(minx>=0){
					diff_part = compare_res.substring(minx);
					prefix_part = compare_res.substring(0,minx);
					//Moving prefix part to the end of the string
					displaycontent.setCompareResult(diff_part + prefix_part);
				}
				
				if(displaycontent!=null)this.viewFrame.displaySolutionCompareResults(displaycontent);
				
			} else {
				System.out.println("?? TESTCASE OR STUDENT NOT SELECTED ??");
			}

		}
	}
	
	public List<Integer> checkFileExistenceInZip(){
		/*
		 * Solution existence check done for all students
		 */
		logger.log(Level.INFO, "" + getClass().getSimpleName() + " checkFileExistenceInZip()");
		boolean check_ok = true;
		List<Integer> zipProblems = new ArrayList<Integer>();
		StringBuffer operErrorBuffer = new StringBuffer();
		taskCycleProcessor.getRead_oper().setOperErrorBuffer(new StringBuffer());
		
		String stuFilePathInZip = (String) this.selectedStuSolutionObject; // "Round_U1/U1E1_1/src/CDcatalog.xml";
		if (stuFilePathInZip != null) {
			List<StudentType> students = studentContainer.getStudents();
			String studentZipFolder = this.projectHome + "/"
					+ taskFlowMetaDataList.get(this.selectedTaskflowIndex).getStudentZipFileFolder(); // "data/zips/RoundU1/";
			int stucnt = 0;
			for (StudentType stu : students) {
				stucnt++;
				String stuZipFile = stu.getSubmitZip();
				String stuZipFilePath = studentZipFolder + stuZipFile;

				if ((stuZipFilePath != null) && (!"null".equals(stuZipFilePath))) {
					boolean exists = taskCycleProcessor.getRead_oper().checkFileExistenceInZip(stuZipFilePath,
							stuFilePathInZip);
					if (!exists) {
						operErrorBuffer = taskCycleProcessor.getRead_oper().getOperErrorBuffer();
						System.out.println("???checkFileExistenceInZip():" + operErrorBuffer.toString());
						check_ok = false;
						zipProblems.add(Integer.valueOf(stucnt));
						logger.log(Level.INFO,"checkFileExistenceInZip(): Student's (" + stucnt
										+ ") solution NOT EXISTS:" + stuFilePathInZip + " in zip:" + stuZipFilePath
										+ " EXISTS(false)");
					}
				}
			}
		} else {
			logger.log(Level.INFO,"checkFileExistenceInZip(): stuSolution NOT SELECTED??");
			check_ok = false;
			System.out.println("???? stuSolution NOT SELECTED: Select it and run checkFileExistenceInZip() again! "); 
		}
		return zipProblems;
	}
	
	public void compareSolutionFiles() {
		/* Difference of selected students solution with the reference solution
		// One Student have to be selected from Student table and
		// one stuSolution have to be selected from TaskFlow tree
		// Displaying student file content in: rightTopLeftTextArea
		// Displaying reference file content in: rightTopRightTextArea
		// Computing difference
		// Displaying difference results in Result tab
		*/
		logger.log(Level.INFO,"compareSolutionFiles()");
		boolean oper_ok = true;
		StringBuffer operErrorBuffer = new StringBuffer();
		//System.out.println("..compareSolutionFiles():");
		taskCycleProcessor.getRead_oper().setOperErrorBuffer(new StringBuffer());

		// String stuZipFilePath =
		// "data/project_U1_sub1/submit/Round_U1_sub1_100000.zip";
		//String refZipFilePath = "data/project_U1_sub1/submit/Round_U1_sub1_reference.zip";
		String studentZipFolder= this.projectHome + "/" + taskFlowMetaDataList.get(this.selectedTaskflowIndex).getStudentZipFileFolder(); //"data/zips/RoundU1/";
		String stuZipFilePath = studentZipFolder + this.selectedStudentZipFile; //getSelectedStudentZipFile();
		String referenceZipFolder= this.projectHome + "/" + taskFlowMetaDataList.get(this.selectedTaskflowIndex).getReferenceZipFileFolder(); //"data/zips/RoundU1/";		
		String refzipFile = taskFlowMetaDataList.get(this.selectedTaskflowIndex).getReferenceZipFile();
		String refZipFilePath = referenceZipFolder + refzipFile;
		
		if ((stuZipFilePath != null) && (!"null".equals(stuZipFilePath))) {
			String stuFilePathInZip = (String) this.selectedStuSolutionObject; //"Round_U1/U1E1_1/src/CDcatalog_X_hidden.xml";
			String refFilePathInZip = (String) this.selectedRefSolutionObject; //"Round_U1/U1E1_1/test/CDcatalog_3_hidden.xml";
			logger.log(Level.INFO,"compareSolutionFiles(): Student's solution in zip:" + stuFilePathInZip);
			//System.out.println("???? ReadTxtContent from TXT file: " + stuFilePathInZip);
			String stuTxtContent = taskCycleProcessor.getRead_oper().readTxtFile(stuZipFilePath, stuFilePathInZip);
			String refTxtContent = taskCycleProcessor.getRead_oper().readTxtFile(refZipFilePath, refFilePathInZip);

			//System.out.println("???? Comaparing TxtContents: ");
			String result = "EQUAL";
			taskCycleProcessor.getCompare_ctrl().setUp();
			boolean isequal = taskCycleProcessor.getCompare_ctrl().compareTextLines(stuTxtContent, refTxtContent);
			if (!isequal) {
				operErrorBuffer = taskCycleProcessor.getCompare_ctrl().getFilteredResults("ALL", 0, 1000, null); // DELETE_INSERT minLength, cutLength
																												
				result = "NOT-EQUAL";
				oper_ok = false;
			}
			logger.log(Level.INFO,"compareSolutionFiles(): Compare Result:" + result);
			//System.out.println("====== COMPARE RESULT: " + result + "============\n");
			if(isequal) operErrorBuffer.append("EQUAL");
			TriptychContent displaycontent = new TriptychContent();
			displaycontent.setStudentContent(stuTxtContent);
			displaycontent.setReferenceContent(refTxtContent);
			displaycontent.setCompareResult(operErrorBuffer.toString());
			this.viewFrame.displaySolutionCompareResults(displaycontent); //("STUDENT", "REFERENCE", "DIFFERENCE");
		}

	}
	
/*	public String getSelectedStudentZipFile(){
		
		//Call MainFrame method getSelectedStudentTableRow()
		//Selected row from StudenttablePanel
		StringBuffer infobuff = new StringBuffer();		
		int studentRowIdx = this.viewFrame.getSelectedStudentTableRow();
		if(studentRowIdx>=0){
			List<StudentType> students = this.studentContainer.getStudents();
			StudentType student = students.get(studentRowIdx);
			infobuff.append(student.getSubmitZip());
		}
		
		System.out.println("???????? Selected student row index: " + studentRowIdx);
		
		return infobuff.toString();
	} */
	
	
	public void setStudentExFeedback(String feedback){
		//TODO:
		int studentRowIdx = this.viewFrame.getSelectedStudentTableRow();
		int studentColIdx = this.viewFrame.getSelectedStudentTableCol(); 
		
		if(studentRowIdx>=0){
			List<StudentType> students = this.studentContainer.getStudents();
			StudentType student = students.get(studentRowIdx);
			if(studentColIdx>=4){//If >=4 exercise is selected
				int exerciseIdx = studentColIdx - 4;
				List<ExerciseType> exes = student.getExercise();				
				if(exes.size()>=exerciseIdx+1){
					ExerciseType selEx = exes.get(exerciseIdx);
					selEx.setFeedback(feedback);
				}
			}
		}
		
		
	}
	
	public String getSelectedStudentInfo(){
		//Call MainFrame method getSelectedStudentTableRow()
		//Selected row from StudenttablePanel
		this.selectedStudentZipFile=null;
		this.selectedStudentIndex=-1;
		StringBuffer infobuff = new StringBuffer();		
		int studentRowIdx = this.viewFrame.getSelectedStudentTableRow();
		int studentColIdx = this.viewFrame.getSelectedStudentTableCol(); 
		
		if(studentRowIdx>=0){
			List<StudentType> students = this.studentContainer.getStudents();
			StudentType student = students.get(studentRowIdx);
			infobuff.append("---- SELECTED STUDENT (" + (studentRowIdx+1) + ") ----");
			infobuff.append("\nSURNAME: \t" + student.getSurname());
			infobuff.append("\nFIRST NAME: \t" + student.getFirstname());
			infobuff.append("\nSTUDENT ID: \t" + student.getStudentId());
			infobuff.append("\nSUBMIT ZIP: \t" + student.getSubmitZip());
			this.selectedStudentZipFile=student.getSubmitZip();
			this.selectedStudentIndex=studentRowIdx;
			if(studentColIdx>=4){//If >=4 exercise is selected
				int exerciseIdx = studentColIdx - 4;
				List<ExerciseType> exes = student.getExercise();				
				if(exes.size()>=exerciseIdx+1){
					ExerciseType selEx = exes.get(exerciseIdx);
					String excode = selEx.getExerciseId();
					String feedback = selEx.getFeedback();
					List<Integer> tcPoints = selEx.getPointsOfTestCases();
					infobuff.append("\n--------------------------------");
					infobuff.append("\nEXERCISE(" + (exerciseIdx+1) + "): " + excode + "\nTCPOINTS:");
					for(Integer point : tcPoints) infobuff.append(" " + point);
					
					List<String> errors = selEx.getErrorsOfTestCases();
					infobuff.append("\nERRORS:");
					for(String errmsg : errors) infobuff.append("\n" + errmsg + "");
					
					infobuff.append("\nFEEDBACK(" + (exerciseIdx+1) + "): " + feedback);
				}
			}
			
		}
		logger.log(Level.INFO,"getSelectedStudentInfo(): Selected student row index: " + studentRowIdx);
		//System.out.println("???????? Selected student row index: " + studentRowIdx);
		
		return infobuff.toString();
	}
	
	
	public String getSelectedElementInfo(){
		logger.log(Level.INFO,"getSelectedElementInfo()");
		StringBuffer infobuff = new StringBuffer();
		this.selectedTaskflowObject = null;
		this.selectedTaskflowIndex = -1;
		this.selectedTestCaseIndex = -1;
		this.selectedStuSolutionObject = null;
		this.selectedRefSolutionObject = null;
		
		ElementNode node = this.taskFlowsTreeModel.getLastSelectedNode();
		String nodetype = node.getNodetype();
		infobuff.append("---- SELECTED TASKFLOW NODE ----");
		
		if("CheckerTaskFlowType".equals(nodetype)){
			CheckerTaskFlowType taskflow = (CheckerTaskFlowType)node.getJaxbObject();
			this.selectedTaskflowObject = taskflow;
			this.selectedTaskflowIndex = node.getParent().getIndexOfChild(node);
			
			infobuff.append("\nNODE TYPE: \t" + nodetype);
			infobuff.append("\nROUND: \t" + taskflow.getRound());
			infobuff.append("\nEXERCISE: \t" + taskflow.getExercise());
			infobuff.append("\nDESCRIPTION: " + taskflow.getDescription());
			infobuff.append("\nSTUDENT SOLUTION: " + taskflow.getStuSolution());
			infobuff.append("\nREFERENCE SOLUTION: " + taskflow.getRefSolution());
			infobuff.append("\nSTUDENT ZIP prefix: " + taskflow.getStuZip());
			infobuff.append("\nREFERENCE ZIP: " + taskflow.getRefZip());
			
			infobuff.append("\n\n(Checking enabled)");
			
		} else if("TestCaseType".equals(nodetype)){
			TestCaseType testcase = (TestCaseType)node.getJaxbObject();
			infobuff.append("\nNODE TYPE: \t" + nodetype);
			infobuff.append("\nNUMBER: \t" + testcase.getNumber());
			infobuff.append("\nNAME: " + testcase.getName());
			infobuff.append("\nDESCRIPTION: " + testcase.getDescription());
			infobuff.append("\nPOINTS: \t" + testcase.getPoints());
			infobuff.append("\nSTUDENT DIR1: " + testcase.getStuDir1());
			infobuff.append("\nSTUDENT DIR2: " + testcase.getStuDir2());
			infobuff.append("\nSTUDENT FILE1: " + testcase.getStuFile1());
			infobuff.append("\nSTUDENT FILE2: " + testcase.getStuFile2());
			infobuff.append("\nREFERENCE DIR1: " + testcase.getRefDir1());
			infobuff.append("\nREFERENCE DIR2: " + testcase.getRefDir2());
			infobuff.append("\nREFERENCE FILE1: " + testcase.getRefFile1());
			infobuff.append("\nREFERENCE FILE2: " + testcase.getRefFile2());			
			
			this.selectedTestCaseIndex = testcase.getNumber().intValue()-1;
			//Selected TestCase's parent is a TaskFlow
			ElementNode parentTaskflowNode = node.getParent();
			CheckerTaskFlowType parenttaskflow = (CheckerTaskFlowType)parentTaskflowNode.getJaxbObject();
			this.selectedTaskflowObject = parenttaskflow;
			this.selectedTaskflowIndex = parentTaskflowNode.getParent().getIndexOfChild(parentTaskflowNode);
			infobuff.append("\nPARENT TASKFLOW: " + (this.selectedTaskflowIndex + 1));
		} else if("FlowType".equals(nodetype)){
			FlowType flow = (FlowType)node.getJaxbObject();
			infobuff.append("\nNODE TYPE: \t" + nodetype);
			infobuff.append("\nTYPE: \t" + flow.getType());
			infobuff.append("\nNAME: \t" + flow.getName());
			infobuff.append("\nDESCRIPTION: " + flow.getDescription());
			infobuff.append("\nIN CHANNEL: \t" + flow.getInChannel());
			infobuff.append("\nOUT CHANNEL: \t" + flow.getOutChannel());
			infobuff.append("\nOPERATIONS: #" + flow.getOperation().size());
		} else if("OperationType".equals(nodetype)){
			OperationType oper = (OperationType)node.getJaxbObject();
			infobuff.append("\nNODE TYPE: \t" + nodetype);
			infobuff.append("\nTYPE: \t" + oper.getType());
			infobuff.append("\nNAME: \t" + oper.getName());
			infobuff.append("\nDESCRIPTION: " + oper.getDescription());
			infobuff.append("\nPARAMETER 1: \t" + oper.getPar1());
			infobuff.append("\nPARAMETER 2: \t" + oper.getPar2());
			
			ParamValueListType parvallist = oper.getParamValueList();
			if(parvallist!=null){
				List<String> parkeys = parvallist.getParamList();
				List<String> parvalues = parvallist.getValueList();				
				infobuff.append("\nPARAMVALUELIST:");
				infobuff.append("\nPARAM_NAMES: \t");
				for(String param : parkeys){
					infobuff.append("" + param + ", ");					
				}
				infobuff.append("\nPARAM_VALUES: \t");
				for(String value : parvalues){
					infobuff.append("" + value + ", ");					
				}				
			}			
						
			List<String> options = oper.getOpt();
			if(options!=null){
				infobuff.append("\nOPT: \t");		
				for(String opt : options){
					infobuff.append("" + opt + ", ");					
				}				
			}			
			infobuff.append("\nRETURN: " + oper.getReturn());
			
		} else if("StuSolution".equals(nodetype)){
			ElementNode parentTaskflowNode = node.getParent();
			CheckerTaskFlowType parenttaskflow = (CheckerTaskFlowType)parentTaskflowNode.getJaxbObject();
			this.selectedTaskflowObject = parenttaskflow;
			this.selectedTaskflowIndex = parentTaskflowNode.getParent().getIndexOfChild(parentTaskflowNode);
			String stusol = (String)node.getJaxbObject();
			this.selectedStuSolutionObject = stusol;
			int stusolidx = parentTaskflowNode.getIndexOfChild(node);
			//Next child should be RefSolution
			ElementNode refsolnode = parentTaskflowNode.getChildAt(stusolidx+1);
			if("RefSolution".equals(refsolnode.getNodetype())){
				String refsol = (String)refsolnode.getJaxbObject();
				this.selectedRefSolutionObject = refsol;
			}
			
			infobuff.append("\nNODE TYPE: \t" + nodetype);
			infobuff.append("\nSTUDENT SOLUTION: " + stusol);
			infobuff.append("\nPARENT TASKFLOW: " + (this.selectedTaskflowIndex + 1));
			
		} else {			
			infobuff.append("\nNODE TYPE: \t" + nodetype);
			//infobuff.append("\n\n(Checking disabled)");
		}
	
		return infobuff.toString();
	}
	
	public void serializeResultsToXML(String filePath){
		logger.log(Level.INFO,"serializeResultsToXML()");
		studentContainer.serializeToXML(filePath);
		
	}
	
	public List<List<Object>> getStudentDataForTableRows(){
		/*
		 * columnNames = {"Last Name","First Name","Student ID","P1","P2","P3","P4","P5","P6" }; 
		 * All String type
		 * Note: points of one Exercise is the sum(testcase points)
		 */
		logger.log(Level.INFO,"getStudentDataForTableRows()");
		List<List<Object>> studentRowsList = new ArrayList<List<Object>>();
		int exerCount = 6;
		List<StudentType> students = this.studentContainer.getStudents();
		int rowcnt = 0;
		for(StudentType student: students){
			rowcnt++;
			List<Object> tableRow = new ArrayList<Object>();
			tableRow.add(""+ rowcnt); //Order number
			tableRow.add(student.getSurname());
			tableRow.add(student.getFirstname());
			tableRow.add(student.getStudentId());
			List<ExerciseType> exercises = student.getExercise();
			Integer totalPoints = 0;
			for(int i=0; i<exerCount ;i++){
				
				if((exercises!=null)&&(!exercises.isEmpty())&&(i < exercises.size())){
					List<Integer> testcasepoints = exercises.get(i).getPointsOfTestCases();
					//Exercise points = sum(testcase points)
					Integer exPoints = 0;
					for(Integer tcp : testcasepoints){
						exPoints = exPoints + tcp;
					}
					tableRow.add(exPoints.toString());
					totalPoints = totalPoints + exPoints;
				} else {
					
					tableRow.add("");
				}				
			}
			tableRow.add(totalPoints.toString());
			studentRowsList.add(tableRow);
		}
		
		return studentRowsList;
	}
	
	public void setResultsWriteOption(boolean toResultExcel){
		logger.log(Level.INFO,"setResultsWriteOption()");
		this.taskCycleProcessor.setWriteToStudentExcel(toResultExcel);
	}
	
	/*
	 * ADDONS
	 * 
	 */
	
	public boolean invokeSpecificTransform(String transformtype, File[] files) {
		/*
		 * Implemented specific transform types:"results2csv"; "results2students"; 
		 * Implemented generic transform: "contextSrc2Trout"
		 * 
		 */
		boolean ok = false;
		String sourcefile=null; //xml-file
		String troutfile=null; //output file .trout/.csv
		
		if(files.length>1){
			File file1 = files[0];
			File file2 = files[1];
			String file1path = file1.getAbsolutePath();
			String file2path = file2.getAbsolutePath();
			if(file1path.endsWith("xml")) {
				sourcefile = file1path;
				troutfile = file2path;
			} else if(file2path.endsWith("xml")) {
				sourcefile = file2path;
				troutfile = file1path;
			}
		} else if(files.length==1){
			File file1 = files[0];
			String file1path = file1.getAbsolutePath();
			if(file1path.endsWith("xml")) {
				sourcefile = file1path;
				String newoutfile = sourcefile.substring(0,sourcefile.length()-4);
				if("results2csv".equalsIgnoreCase(transformtype))
					newoutfile = newoutfile + "_csv.csv";
				else if("results2students".equalsIgnoreCase(transformtype))
					newoutfile = newoutfile + "_xml.trout";
				else if("contextSrc2Trout".equalsIgnoreCase(transformtype))
					newoutfile = newoutfile + ".trout";
				troutfile = newoutfile;
			}
		}
		
		if((sourcefile!=null)&&(troutfile!=null)){
			this.contextXsltAddon.doSpecificTransform(transformtype, sourcefile, troutfile);
			ok = true;
		}
		
		return ok;
		
	}
	
	
	public void invokeXslContextTransform() {

		this.contextXsltAddon.doSpecificTransform("contextSrc2Trout", null, null);
		logger.info("invokeXslContextTransform()");
	}

	public void initXslContext(File[] xslContextfiles) {

		this.contextXsltAddon.setXslContex(xslContextfiles);
		logger.info("initXslContext()");
	}

	
	
	/*
	 *  GETTERS SETTERS
	 * 
	 */
	
	
	public ElementTree getTaskFlowsTree() {
		return taskFlowsTree;
	}

	public ElementModel getTaskFlowsTreeModel() {
		return taskFlowsTreeModel;
	}

	/*
	public static void main(String[] args) {
		String taskFlowXmlFile = "data/project_U1_sub1/taskflow/taskflow_U1E1_1_sub1.xml";
		
		MainAppController app = new MainAppController();
		app.openTaskFlowFile(taskFlowXmlFile);
		
	} */
}
