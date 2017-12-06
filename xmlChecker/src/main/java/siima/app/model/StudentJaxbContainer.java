/* StudentJaxbContainer.java
 * NOTE: Student data is read from excel-file (not from xml-file)
 * 
 */

package siima.app.model;

import java.util.ArrayList;
import java.util.List;

import siima.app.control.ExcelMng;
import siima.model.jaxb.checker.student.ExerciseType;
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
	
	
	/*
	 * Getters
	 */
	
	public List<StudentType> getStudents() {
		return students;
	}

	
	
}
