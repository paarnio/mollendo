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
import java.util.List;
import java.util.logging.Logger;

import siima.app.gui.MainFrame;
import siima.app.model.StudentJaxbContainer;
import siima.app.model.TaskFlowJaxbContainer;
import siima.app.model.TaskFlowMetaData;
import siima.app.model.tree.ElementModel;
import siima.app.model.tree.ElementNode;
import siima.app.model.tree.ElementTree;
import siima.model.jaxb.checker.taskflow.CheckerTaskFlowType;

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
		String info=null;
		
		ElementNode node = this.taskFlowsTreeModel.getLastSelectedNode();
		String nodetype = node.getNodetype();
		if("CheckerTaskFlowType".equals(nodetype)){			
			this.selectedTaskflowObject = (CheckerTaskFlowType)node.getJaxbObject();
			this.selectedTaskflowIndex = node.getParent().getIndexOfChild(node);
			info="(Checking enabled)";
		} else {			
			this.selectedTaskflowObject = null;
			this.selectedTaskflowIndex = -1; 
			info="(Checking disabled)";
		}
		int index = node.getParent().getIndexOfChild(node);
		return "(" + index + ")" + nodetype + ":" + info;
	}
	
	public void serializeResultsToXML(String filePath){
		
		studentContainer.serializeToXML(filePath);
		
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
