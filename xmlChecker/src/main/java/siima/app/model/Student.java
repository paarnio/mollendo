package siima.app.model;

import java.util.List;

public class Student {
	/* Student
	 * Data related to one submit (1) of one student (1).
	 * One submit zip (e.g. U1_sub1) is related to one round (e.g. U1) and
	 * it can contain solutions of several exercises 
	 */
	private String surname;
	private String firstname;
	private String studentId;
	private String round; //e.g. U1
	private String submit; //e.g. U1_sub1
	private String submitZip; // Round_U1_sub1_100000.zip containing student's solutions
	
	private List<StudentExercise> exercises;

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}

	public String getSubmit() {
		return submit;
	}

	public void setSubmit(String submit) {
		this.submit = submit;
	}

	public String getSubmitZip() {
		return submitZip;
	}

	public void setSubmitZip(String submitZip) {
		this.submitZip = submitZip;
	}

	public List<StudentExercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<StudentExercise> exercises) {
		this.exercises = exercises;
	}
	
	
}
