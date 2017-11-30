package siima.app.model;

import java.util.ArrayList;
import java.util.List;

public class StudentExercise {
	/* StudentExercise
	 * Data related to one exercise (1) of one student (1)
	 * Student's main data specified in Student class
	 */
	private String surname;
	private String firstname;
	private String studentId;
	private String round; //e.g. U1
	private String exercise; //e.g. U1_E1_1
	private String submitZip; //containing student's solution
	private List<Integer> pointsOfTestCases;
	private List<String> resultsOfTestCases;
	private List<String> errorsOfTestCases;
	
	/*
	 * Constructors
	 * 
	 */
	
	public StudentExercise(String surname, String firstname, String studentId, String round, String exercise, String submitZip){
		this.surname = surname;
		this.firstname = firstname;
		this.studentId = studentId;
		this.round = round;
		this.exercise = exercise;
		this.submitZip = submitZip;
		pointsOfTestCases = new ArrayList<Integer>();
		resultsOfTestCases = new ArrayList<String>();
		resultsOfTestCases = new ArrayList<String>();
	}
	
	public StudentExercise(String surname, String firstname, String studentId){
		this.surname = surname;
		this.firstname = firstname;
		this.studentId = studentId;
		pointsOfTestCases = new ArrayList<Integer>();
		resultsOfTestCases = new ArrayList<String>();
		resultsOfTestCases = new ArrayList<String>();
	}
	
	public StudentExercise(String studentId){		
		this.studentId = studentId;
		pointsOfTestCases = new ArrayList<Integer>();
		resultsOfTestCases = new ArrayList<String>();
		resultsOfTestCases = new ArrayList<String>();
	}
	
	
	public StudentExercise(){
		pointsOfTestCases = new ArrayList<Integer>();
		resultsOfTestCases = new ArrayList<String>();
		resultsOfTestCases = new ArrayList<String>();
	}
	
	/*
	 *  Total points of this exercise
	 */
	
	public Integer getExercisePoints(){
		Integer total =0;
		
		return total;
	}
	/*
	 * Getters and setters
	 */
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
	public String getExercise() {
		return exercise;
	}
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	public String getSubmitZip() {
		return submitZip;
	}
	public void setSubmitZip(String submitZip) {
		this.submitZip = submitZip;
	}
	public List<Integer> getPointsOfTestCases() {
		return pointsOfTestCases;
	}
	public void setPointsOfTestCases(List<Integer> pointsOfTestCases) {
		this.pointsOfTestCases = pointsOfTestCases;
	}
	public List<String> getResultsOfTestCases() {
		return resultsOfTestCases;
	}
	public void setResultsOfTestCases(List<String> resultsOfTestCases) {
		this.resultsOfTestCases = resultsOfTestCases;
	}
	public List<String> getErrorsOfTestCases() {
		return errorsOfTestCases;
	}
	public void setErrorsOfTestCases(List<String> errorsOfTestCases) {
		this.errorsOfTestCases = errorsOfTestCases;
	}
	
	
	
	
}
