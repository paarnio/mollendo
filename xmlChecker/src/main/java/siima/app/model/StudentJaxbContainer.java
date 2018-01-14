/* StudentJaxbContainer.java
 * NOTE: Student data is read from excel-file (not from xml-file)
 * 
 */

package siima.app.model;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import siima.app.control.ExcelMng;
import siima.model.jaxb.checker.student.ExerciseType;
import siima.model.jaxb.checker.student.StudentSubmits;
import siima.model.jaxb.checker.student.StudentType;
import siima.model.jaxb.checker.taskflow.CheckerTaskFlowType;

public class StudentJaxbContainer {
	private static final Logger logger=Logger.getLogger(StudentJaxbContainer.class.getName());
	private static String STUDENT_SCHEMA ="configure/schema/students3.xsd";
	private ExcelMng excelMng;
	private List<StudentType> students;
	private StudentSubmits studentSubmits;
	
	public StudentJaxbContainer(ExcelMng exMng){
		this.excelMng = exMng;
	}
	
	
	public List<StudentType> readStudentBaseData(){
		/* NOTE: Student data is read from excel-file (not from xml-file)
		 * reading base data of all the students listed in project excel 
		 */
		
		this.students = this.excelMng.readStudentsBaseData();
		this.studentSubmits = new StudentSubmits();
		this.studentSubmits.getStudent().addAll(students);
		/*TODO: */
		this.studentSubmits.setSubmitId("???");
		this.studentSubmits.setDescription("???");
		this.studentSubmits.setReferenceZip("???");
		
		return students;
	}

	public StudentType getStudent(int idx) {
		StudentType student = null;
		if((students!=null)&&(idx<students.size())){
		 student = students.get(idx);
		}
		return student;
	}
	
	public ExerciseType getStudentExercise(int studentIdx, int exerciseIdx) {
		StudentType student = null;
		ExerciseType exercise = null;
		if ((students != null) && (studentIdx < students.size())) {
			student = students.get(studentIdx);
			List<ExerciseType> exercises = student.getExercise();
			if ((!exercises.isEmpty()) && (exerciseIdx < exercises.size())) {
				exercise = exercises.get(exerciseIdx);
			}
		}
		return exercise;
	}
	
	public ExerciseType getStudentExercise(int studentIdx, String exerciseId) {
		StudentType student = null;
		ExerciseType exercise = null;
		if ((students != null) && (studentIdx < students.size())) {
			student = students.get(studentIdx);
			List<ExerciseType> exercises = student.getExercise();
			if ((!exercises.isEmpty())&&(exerciseId!=null)) {
				for(ExerciseType ext : exercises){
					if(exerciseId.equals(ext.getExerciseId()))
							exercise = ext;
				}
			}
		}
		return exercise;
	}
	
	
	public void addStudentExercise(int studentIdx, ExerciseType exercise){
		/*
		 * If exercise with the same ID exits it will be replaced
		 * 
		 */
		StudentType student = null;
		//ExerciseType exercise = null;
		if ((students != null) && (studentIdx < students.size())) {
			student = students.get(studentIdx);
			List<ExerciseType> exercises = student.getExercise();
			String exerciseId = exercise.getExerciseId();
			int matchIdx = -1;
			if ((!exercises.isEmpty())&&(exerciseId!=null)) {
				int idx = -1;
				for(ExerciseType ext : exercises){
					idx++;
					if(exerciseId.equals(ext.getExerciseId())){
						//exercise with the same id found so replace it
						ext = exercise;
						matchIdx = idx;
					}
					
				}
			}
			
			if(matchIdx == -1){
				//Adding exercise as a new one
				student.getExercise().add(exercise);
			}
		}
	}
	
	
	public boolean serializeToXML(String filepath){
		/*
		 * unable to marshal type "siima.model.jaxb.checker.student.StudentSubmitsType" 
		 * as an element because it is missing an @XmlRootElement annotation]
		 */
		/*StudentSubmits rootObject =  new StudentSubmits();
		rootObject.setSubmitId("U1_sub1");//TODO:???
		rootObject.getStudent().addAll(getStudents()); */
		
		StudentSubmits rootObject =  this.studentSubmits;
		JAXBContext context;

		try {
			context = JAXBContext.newInstance("siima.model.jaxb.checker.student");			
			Marshaller m = context.createMarshaller();
	
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(rootObject, new File(filepath)); 
			//logger.log(Level.INFO, "saveData() SAVING to file: " + filepath);
		} catch (JAXBException e) {
			 //logger.log(Level.ERROR, e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public StudentSubmits unmarshalStudentSubmitsType(Path path) {
		/*
		 * FROM: \git\valle-de-luna\ERAmlHandler JaxbContainer.java
		 */
		StudentSubmits unmarshalledSubmitsRootObject = null;
		//setTaskFlowFilePath(path); //latest loaded caex file
		JAXBContext context;
		Schema schema;
		
		//if(loadedTaskFlowFilePaths==null) loadedTaskFlowFilePaths = new ArrayList<Path>();
		//loadedTaskFlowFilePaths.add(path);
		
		try {
			context = JAXBContext.newInstance("siima.model.jaxb.checker.student");
			Unmarshaller u = context.createUnmarshaller();
			//Validate against taskflow Schema
			 SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
	            try {
	            	schema = sf.newSchema(new File(STUDENT_SCHEMA)); //
	            	
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
	                                logger.log(Level.ERROR, msgbuff.toString());
	                            } else {
	                            	logger.log(Level.WARN, msgbuff.toString());
	                            }
	                            return true;
	                        }
	                    }
	                );
	            } catch (org.xml.sax.SAXException se) {
	                //System.out.println("Unable to validate due to following error.");
	                logger.log(Level.ERROR, se.getMessage());
	                se.printStackTrace();
	            }
	            // Unmarshalling main taskflow file
	            //taskflowRootObject = 
	            JAXBElement tfelem = (JAXBElement) u.unmarshal(Paths.get(path.toUri()).toFile());
	            unmarshalledSubmitsRootObject = (StudentSubmits) tfelem.getValue();
	            logger.log(Level.INFO, "loadData(): StudentSubmits RootObject created by unmarshalling the main student submits xml file!");
	            
	            
		} catch (JAXBException e) {

			e.printStackTrace();
			return null;
		}

		return unmarshalledSubmitsRootObject;

	}

	
	/*
	 * Getters
	 */
	
	public List<StudentType> getStudents() {
		return students;
	}

	
	
}
