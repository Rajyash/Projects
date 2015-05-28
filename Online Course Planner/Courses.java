package cpd.model;

import java.util.List;

public class Courses {
	
	private int id;
	
	private String courseCode;
	
	private String courseName;
	
	private String preReqs;
	
	//private boolean taken;
	
	public Courses(int id, String courseCode, String courseName,
			String preReqs/*, boolean taken*/) {
		this.id = id;
		this.courseCode = courseCode;
		this.courseName = courseName;
		this.preReqs = preReqs;
		//this.taken = taken;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getPreReqs() {
		return preReqs;
	}

	public void setPreReqs(String preReqs) {
		this.preReqs = preReqs;
	}

	/*public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}*/

}
