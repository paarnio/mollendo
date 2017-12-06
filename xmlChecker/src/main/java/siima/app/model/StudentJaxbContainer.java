/* StudentJaxbContainer.java
 * NOTE: Student data is read from excel-file (not from xml-file)
 * 
 */

package siima.app.model;

import java.util.ArrayList;
import java.util.List;

import siima.app.control.ExcelMng;
import siima.model.jaxb.checker.student.StudentType;

public class StudentJaxbContainer {
	
	private ExcelMng excelMng;
	private List<StudentType> students;
	
	public StudentJaxbContainer(ExcelMng exMng){
		this.excelMng = exMng;
	}
	
	
	public List<StudentType> readStudentBaseData(){
		/* NOTE: Student data is read from excel-file (not from xml-file)
		 * reading base data of all the students listed in project excel 
		 */
		
		this.students = this.excelMng.readStudentsBaseData();		
		return students;
	}

	/*
	 * Getters
	 */
	
	public List<StudentType> getStudents() {
		return students;
	}

	
	
}
