package siima.app.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import siima.app.model.tree.ElementNode;
import siima.model.jaxb.checker.taskflow.CheckerTaskFlowType;
import siima.model.jaxb.checker.taskflow.FlowType;
import siima.model.jaxb.checker.taskflow.OperationType;
import siima.model.jaxb.checker.taskflow.ParamValueListType;
import siima.model.jaxb.checker.taskflow.TestCaseType;

public class TaskFlowJaxbContainer {
	private static final Logger logger=Logger.getLogger(TaskFlowJaxbContainer.class.getName());
	private static String TASKFLOW_SCHEMA ="configure/schema/taskflow4.xsd";
	//private Object taskflowRootObject;
	private CheckerTaskFlowType taskflow;
	private Path taskFlowFilePath; //latest loaded
	private List<Path> loadedTaskFlowFilePaths;
	private int taskFlowfileNr = 0;
	
	public ElementNode taskFlowsRootElement = new ElementNode("TASKFLOWs:");
	
	
	public Path getTaskFlowFilePath() {
		return taskFlowFilePath;
	}

	public void setTaskFlowFilePath(Path taskFlowFilePath) {
		this.taskFlowFilePath = taskFlowFilePath;
	}

	public int getTaskFlowfileNr() {
		return taskFlowfileNr;
	}

	public void setTaskFlowfileNr(int taskFlowfileNr) {
		this.taskFlowfileNr = taskFlowfileNr;
	}

	public CheckerTaskFlowType getTaskflow() {
		return taskflow;
	}

	public  void buildElementGraphFromXML() {
		
		//Enabling the tree to contain several TaskFlows
		List<ElementNode> taskFlowElementList = new ArrayList<ElementNode>();
		
		//JAXBElement jaxbroot = (JAXBElement) taskflowRootObject;
		//CheckerTaskFlowType checkerTaskFlow = (CheckerTaskFlowType) jaxbroot.getValue();
		String round = this.taskflow.getRound();
		String exer = this.taskflow.getExercise();
		String desc = this.taskflow.getDescription();
		String stuzip = this.taskflow.getStuZip();
		String refzip = this.taskflow.getRefZip();
		String comment = this.taskflow.getComment();		
		List<TestCaseType> testcasetypes = this.taskflow.getTestCase();
		
		//TaskFlows hierarchy Tree
		setTaskFlowfileNr(getTaskFlowfileNr() + 1);
		ElementNode taskFlowElement = new ElementNode("TaskFlow_" + getTaskFlowfileNr() + ": " + getTaskFlowFilePath().getFileName());
		taskFlowElement.setNodetype("CheckerTaskFlowType");
		taskFlowElement.setJaxbObject(this.taskflow);
		taskFlowElementList.add(taskFlowElement);		
		ElementNode.linkChildren(taskFlowsRootElement, taskFlowElementList);
		
		//Taskflow element
		List<ElementNode> taskflow_children = new ArrayList<ElementNode>();
		
		if(round!=null){
		ElementNode roundkid = new ElementNode("Round:" + round);
		roundkid.setJaxbObject(round);
		roundkid.setNodetype("Round");
	    taskflow_children.add(roundkid);
		}
		if(exer!=null){
	    ElementNode exerkid = new ElementNode("Exercise:" + exer);
	    exerkid.setJaxbObject(exer);
	    exerkid.setNodetype("Exercise");
	    taskflow_children.add(exerkid);
		}
		if(desc!=null){
	    ElementNode desckid = new ElementNode("Description:");
	    desckid.setJaxbObject(desc);
	    desckid.setNodetype("Description");
	    taskflow_children.add(desckid);
		}
		
		for (TestCaseType tcto : testcasetypes) {
					
			ElementNode tckid = new ElementNode("TestCase:" + tcto.getNumber());
			tckid.setJaxbObject(tcto);
			tckid.setNodetype("TestCaseType");
		    taskflow_children.add(tckid);
		    
		    List<ElementNode> testcase_children = new ArrayList<ElementNode>();
			    
		    buildTestCaseElementNode(tckid,testcase_children,tcto);
		    ElementNode.linkChildren(tckid, testcase_children);
		    
		}
		ElementNode.linkChildren(taskFlowElement, taskflow_children);
	}
	
	public void buildTestCaseElementNode(ElementNode parent, List<ElementNode> children, TestCaseType parentObject ){
		
		 	Integer number = parentObject.getNumber();
		    String tcdesc = parentObject.getDescription();
		    String points = parentObject.getPoints();
		    String sdir1 = parentObject.getStuDir1();
		    String sdir2 = parentObject.getStuDir2();
		    String sfile1 = parentObject.getStuFile1();
		    String sfile2 = parentObject.getStuFile2();
		    String rdir1 = parentObject.getRefDir1();
		    String rdir2 = parentObject.getRefDir2();
		    String rfile1 = parentObject.getRefFile1();
		    String rfile2 = parentObject.getRefFile2();
		   
		    
		    if(number!=null){
		    	ElementNode kid = new ElementNode("Nr: " + number);
				kid.setJaxbObject(number);
				kid.setNodetype("Integer");
			    children.add(kid);	    	
		    }
		    if(points!=null){
		    	ElementNode kid = new ElementNode("Points: " + points);
				kid.setJaxbObject(points);
				kid.setNodetype("String");
			    children.add(kid);	    	
		    }
		    if(tcdesc!=null){
		    	ElementNode kid = new ElementNode("Description: " + tcdesc);
				kid.setJaxbObject(tcdesc);
				kid.setNodetype("String");
			    children.add(kid);	    	
		    }
		    //Student
		    if(sdir1!=null){
		    	ElementNode kid = new ElementNode("stuDir1: " + sdir1);
				kid.setJaxbObject(sdir1);
				kid.setNodetype("String");
			    children.add(kid);	    	
		    }		    
		    if(sdir2!=null){
		    	ElementNode kid = new ElementNode("stuDir2: " + sdir2);
				kid.setJaxbObject(sdir2);
				kid.setNodetype("String");
			    children.add(kid);	    	
		    }		 
		    if(sfile1!=null){
		    	ElementNode kid = new ElementNode("stuFile1: " + sfile1);
				kid.setJaxbObject(sfile1);
				kid.setNodetype("String");
			    children.add(kid);	    	
		    }		 
		    if(sfile2!=null){
		    	ElementNode kid = new ElementNode("stuFile2: " + sfile2);
				kid.setJaxbObject(sfile2);
				kid.setNodetype("String");
			    children.add(kid);	    	
		    }
		    
		    //Reference
		    
		    if(rdir1!=null){
		    	ElementNode kid = new ElementNode("refDir1: " + rdir1);
				kid.setJaxbObject(rdir1);
				kid.setNodetype("String");
			    children.add(kid);	    	
		    }		    
		    if(rdir2!=null){
		    	ElementNode kid = new ElementNode("refDir2: " + rdir2);
				kid.setJaxbObject(rdir2);
				kid.setNodetype("String");
			    children.add(kid);	    	
		    }		 
		    if(rfile1!=null){
		    	ElementNode kid = new ElementNode("refFile1: " + rfile1);
				kid.setJaxbObject(rfile1);
				kid.setNodetype("String");
			    children.add(kid);	    	
		    }		 
		    if(rfile2!=null){
		    	ElementNode kid = new ElementNode("refFile2: " + rfile2);
				kid.setJaxbObject(rfile2);
				kid.setNodetype("String");
			    children.add(kid);	    	
		    }
		    
		    //Flows:
		    List<FlowType> flows = parentObject.getFlow();
		
		    int fcnt =0;
		    for (FlowType flowto : flows) {
				++fcnt;			
				ElementNode fkid = new ElementNode("Flow: " + fcnt);
				fkid.setJaxbObject(flowto);
				fkid.setNodetype("FlowType");
			    children.add(fkid);
			    
			    List<ElementNode> flow_children = new ArrayList<ElementNode>();
			    buildFlowElementNode(fkid,flow_children,flowto);
			    ElementNode.linkChildren(fkid, flow_children);
		    }
		
	}
	
	public void buildFlowElementNode(ElementNode parent, List<ElementNode> children, FlowType parentObject ){
		String ftype = parentObject.getType();
		String fname = parentObject.getName();		
		String fdesc = parentObject.getDescription();
		String finchan = parentObject.getInChannel();
		String foutchan = parentObject.getOutChannel();
		
		 if(ftype!=null){
		    	ElementNode kid = new ElementNode("Type: " + ftype);
				kid.setJaxbObject(ftype);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }		    
		
		 if(fname!=null){
		    	ElementNode kid = new ElementNode("Name: " + fname);
				kid.setJaxbObject(fname);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }
		
		 if(fdesc!=null){
		    	ElementNode kid = new ElementNode("Description: " + fdesc);
				kid.setJaxbObject(fdesc);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }
		 
		 if(finchan!=null){
		    	ElementNode kid = new ElementNode("InChannel: " + finchan);
				kid.setJaxbObject(finchan);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }
		 if(foutchan!=null){
		    	ElementNode kid = new ElementNode("OutChannel: " + foutchan);
				kid.setJaxbObject(foutchan);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }
	
		List<OperationType> fopers = parentObject.getOperation();
		
		 int ocnt =0;
		    for (OperationType operto : fopers) {
				++ocnt;			
				ElementNode okid = new ElementNode("Operation: " + ocnt);
				okid.setJaxbObject(operto);
				okid.setNodetype("OperationType");
			    children.add(okid);
			    
			    List<ElementNode> oper_children = new ArrayList<ElementNode>();
			    buildOperationElementNode(okid,oper_children,operto);
			    ElementNode.linkChildren(okid, oper_children);
		    }


	}
	
	public void buildOperationElementNode(ElementNode parent, List<ElementNode> children, OperationType parentObject ){
		String type = parentObject.getType();
		String name = parentObject.getName();
		String desc = parentObject.getDescription();
		String par1 = parentObject.getPar1();
		String par2 = parentObject.getPar2();		
		String ret = parentObject.getReturn();
		ParamValueListType parvaluelist = parentObject.getParamValueList();
		
		 if(type!=null){
		    	ElementNode kid = new ElementNode("Type: " + type);
				kid.setJaxbObject(type);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }		    
		
		 if(name!=null){
		    	ElementNode kid = new ElementNode("Name: " + name);
				kid.setJaxbObject(name);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }
		
		 if(desc!=null){
		    	ElementNode kid = new ElementNode("Description: " + desc);
				kid.setJaxbObject(desc);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }
		 
		 if(par1!=null){
		    	ElementNode kid = new ElementNode("Parameter1: " + par1);
				kid.setJaxbObject(par1);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }
		 
		 if(par2!=null){
		    	ElementNode kid = new ElementNode("Parameter2: " + par2);
				kid.setJaxbObject(par2);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }
		 
		 if(ret!=null){
		    	ElementNode kid = new ElementNode("Return: " + ret);
				kid.setJaxbObject(ret);
				kid.setNodetype("String");
			    children.add(kid);	    	
		 }
		 
		 if(parvaluelist!=null){
		    	ElementNode kid = new ElementNode("ParamValueListType: ");
				kid.setJaxbObject(parvaluelist);
				kid.setNodetype("ParamValueListType");
			    children.add(kid);
			    /* Lists for xsl param-value pairs
			    List<String> paramlist = parvaluelist.getParamList();
			    List<String> valuelist = parvaluelist.getValueList();
			    */
		 }
		 
		 
	}
	
	public boolean loadData(Path path) {
		/*
		 * FROM: \git\valle-de-luna\ERAmlHandler JaxbContainer.java
		 */
		setTaskFlowFilePath(path); //latest loaded caex file
		JAXBContext context;
		Schema schema;
		
		if(loadedTaskFlowFilePaths==null) loadedTaskFlowFilePaths = new ArrayList<Path>();
		loadedTaskFlowFilePaths.add(path);
		
		try {
			context = JAXBContext.newInstance("siima.model.jaxb.checker.taskflow");
			Unmarshaller u = context.createUnmarshaller();
			//Validate against taskflow Schema
			 SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
	            try {
	            	schema = sf.newSchema(new File(TASKFLOW_SCHEMA)); //
	            	
	                u.setSchema(schema);
	                u.setEventHandler(
	                    new ValidationEventHandler() {
	                        // allow unmarshalling to continue even if there are errors
	                        public boolean handleEvent(ValidationEvent ve) {
	                        	StringBuffer msgbuff = new StringBuffer();
	                        	msgbuff.append("loadData():ValidationEventHandler()\n");
	                        	ValidationEventLocator vel = ve.getLocator();
	                        	msgbuff.append("Line:Col[" + vel.getLineNumber() +
	                                    ":" + vel.getColumnNumber() +
	                                    "]:" + ve.getMessage());
	                            // ignore warnings
	                            if (ve.getSeverity() != ValidationEvent.WARNING) {
	                                logger.log(Level.SEVERE, msgbuff.toString());
	                            } else {
	                            	logger.log(Level.WARNING, msgbuff.toString());
	                            }
	                            return true;
	                        }
	                    }
	                );
	            } catch (org.xml.sax.SAXException se) {
	                //System.out.println("Unable to validate due to following error.");
	                logger.log(Level.SEVERE, se.getMessage());
	                se.printStackTrace();
	            }
	            // Unmarshalling main taskflow file
	            //taskflowRootObject = 
	            JAXBElement tfelem = (JAXBElement) u.unmarshal(Paths.get(getTaskFlowFilePath().toUri()).toFile());
	            this.taskflow = (CheckerTaskFlowType) tfelem.getValue();
	            logger.log(Level.INFO, "loadData(): taskflowRootObject created by unmarshalling the main taskflow xml file!");
	            
	            
		} catch (JAXBException e) {

			e.printStackTrace();
			return false;
		}

		return true;

	}

	
	
	
	public CheckerTaskFlowType wwwloadCheckerTaskFlowModel(String taskFlowXmlFile) {
		/* NOT USED
		 * From: git\simple_xml_projects\SerialXmlZipProcessor 
		 */
		CheckerTaskFlowType tflow=null;
		try {
			// create a JAXBContext capable of handling classes generated into:
			JAXBContext jc = JAXBContext.newInstance("siima.model.jaxb.checker.taskflow");
			// create an Unmarshaller
			Unmarshaller u = jc.createUnmarshaller();
			// unmarshal a checkerTaskFlow instance document into a tree of Java content
			// objects composed of classes from the package.
			JAXBElement poe = (JAXBElement) u.unmarshal(new FileInputStream(taskFlowXmlFile)); //"data/taskflow/taskflow1.xml"));
			this.taskflow = (CheckerTaskFlowType) poe.getValue();
			tflow = this.taskflow;
		} catch (JAXBException je) {
			je.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return tflow;
	}

	/*
	 * GETTERS AND SETTERS
	 * 
	 */
	

	public ElementNode getTaskFlowsRootElement() {
		return taskFlowsRootElement;
	}

	public static void main(String[] args) {
		String taskFlowXmlFile = "data/project_U1_sub1/taskflow/taskflow_U1E1_1_sub1.xml";
		/* Reading from src/main/resources 		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("file/test.xml").getFile());
		InputStream inputStream = new FileInputStream(file);
		*/
		/*
		TaskFlowJaxbContainer tcc = new TaskFlowJaxbContainer();
		CheckerTaskFlowType taskflow = tcc.loadCheckerTaskFlowModel(taskFlowXmlFile);
		
		String szip = taskflow.getStuZip();
		System.out.println("TestCaseContainer StuZip: " + szip);
		
		List<TestCaseType> cases = taskflow.getTestCase();
		System.out.println("POINTS: " + cases.get(0).getPoints());
		 */
	}

}
