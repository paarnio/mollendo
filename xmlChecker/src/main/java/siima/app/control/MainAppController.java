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
import java.util.logging.Logger;

import siima.app.gui.MainFrame;
import siima.app.model.StudentJaxbContainer;
import siima.app.model.TaskFlowJaxbContainer;
import siima.app.model.TaskFlowMetaData;
import siima.app.model.tree.ElementModel;
import siima.app.model.tree.ElementNode;
import siima.app.model.tree.ElementTree;
import siima.model.jaxb.checker.student.ExerciseType;
import siima.model.jaxb.checker.student.StudentType;
import siima.model.jaxb.checker.taskflow.CheckerTaskFlowType;
import siima.model.jaxb.checker.taskflow.FlowType;
import siima.model.jaxb.checker.taskflow.OperationType;
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
	
	// TREE:TaskFlows Hierarchy
	public ElementTree taskFlowsTree;
	public ElementModel taskFlowsTreeModel;
	
	private List<TaskFlowMetaData> taskFlowMetaDataList;
	private CheckerTaskFlowType selectedTaskflowObject;
	private int selectedTaskflowIndex = -1;
	
	public MainAppController() { //MainFrame viewFrame) {
		//this.viewFrame = viewFrame; // if needed
		graphbuilder = new TaskFlowJaxbContainer();	
		
	}

	public void openProjectExcel(File project_xlsx ){
		boolean ok = true;
		this.projectExcelFile = project_xlsx.getAbsolutePath();
		this.projectHome = project_xlsx.getParent();
		this.initApplication();
		System.out.println("===HOME=== " + this.projectHome);
		//return ok;
	}
	
	public void closeProjectExcel(){
		//Remember to close excel
		excel_mng.saveAndCloseResultsExcel(true);
	}
	
	public void initApplication(){
		
		excel_mng = new ExcelMng(this.projectExcelFile);
		this.studentContainer = new StudentJaxbContainer(excel_mng);
		this.studentContainer.readStudentBaseData();
		
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
				
		if(runConditions()){
			taskCycleProcessor.initProcessor(this.projectHome, taskFlowMetaDataList.get(selectedTaskflowIndex), excel_mng, selectedTaskflowObject, studentContainer);
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
	
	public String getSelectedElementInfo(){
		//String info=null;
		StringBuffer infobuff = new StringBuffer();
		this.selectedTaskflowObject = null;
		this.selectedTaskflowIndex = -1;
		
		ElementNode node = this.taskFlowsTreeModel.getLastSelectedNode();
		String nodetype = node.getNodetype();
		//int index = node.getParent().getIndexOfChild(node);
		
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
			//infobuff.append("\n\n(Checking disabled)");
		} else if("FlowType".equals(nodetype)){
			FlowType flow = (FlowType)node.getJaxbObject();
			infobuff.append("\nNODE TYPE: \t" + nodetype);
			infobuff.append("\nDESCRIPTION: " + flow.getDescription());
			infobuff.append("\nTYPE: \t" + flow.getType());
			infobuff.append("\nNAME: \t" + flow.getName());
			infobuff.append("\nIN CHANNEL: \t" + flow.getInChannel());
			infobuff.append("\nOUT CHANNEL: \t" + flow.getOutChannel());
			infobuff.append("\nOPERATIONS: #" + flow.getOperation().size());
		} else if("OperationType".equals(nodetype)){
			OperationType oper = (OperationType)node.getJaxbObject();
			infobuff.append("\nNODE TYPE: \t" + nodetype);
			infobuff.append("\nDESCRIPTION: " + oper.getDescription());
			infobuff.append("\nTYPE: \t" + oper.getType());
			infobuff.append("\nNAME: \t" + oper.getName());
			infobuff.append("\nPARAMETER 1: \t" + oper.getPar1());
			infobuff.append("\nPARAMETER 2: \t" + oper.getPar2());
			infobuff.append("\nRETURN: " + oper.getReturn());
		} else {			
			infobuff.append("\nNODE TYPE: \t" + nodetype);
			//infobuff.append("\n\n(Checking disabled)");
		}
	
		return infobuff.toString();
	}
	
	public void serializeResultsToXML(String filePath){
		
		studentContainer.serializeToXML(filePath);
		
	}
	
	public List<List<Object>> getStudentDataTableRows(){
		/*
		 * columnNames = {"Last Name","First Name","Student ID","P1","P2","P3","P4","P5","P6" }; 
		 * All String type
		 * Note: points of one Exercise is the sum(testcase points)
		 */
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
						exPoints =+ tcp;
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

	public static void main(String[] args) {
		String taskFlowXmlFile = "data/project_U1_sub1/taskflow/taskflow_U1E1_1_sub1.xml";
		
		MainAppController app = new MainAppController();
		app.openTaskFlowFile(taskFlowXmlFile);
		
	}
}
