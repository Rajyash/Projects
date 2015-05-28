package cpd.model;

public class QuarterPlan {

	private int quarter_Id;
	
	private String quarterName;
	
	private int year;

	public QuarterPlan(int quarter_Id, String quarterName, int year) {
		super();
		this.quarter_Id = quarter_Id;
		this.quarterName = quarterName;
		this.year = year;
	}

	public int getQuarter_Id() {
		return quarter_Id;
	}

	public void setQuarter_Id(int quarter_Id) {
		this.quarter_Id = quarter_Id;
	}

	public String getQuarterName() {
		return quarterName;
	}

	public void setQuarterName(String quarterName) {
		this.quarterName = quarterName;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
}
