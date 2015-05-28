package cpd.model;

import java.util.List;

public class Quarter {

	private Integer c_Id;
	
	private String c_Code;
	
	private String c_Name;
	
	private String c_PreStr;
	
	private boolean completed;

	public Quarter(Integer c_Id, String c_Code, String c_Name, String c_PreStr,
			boolean completed) {
		super();
		this.c_Id = c_Id;
		this.c_Code = c_Code;
		this.c_Name = c_Name;
		this.c_PreStr = c_PreStr;
		this.completed = completed;
	}

	public Integer getC_Id() {
		return c_Id;
	}

	public void setC_Id(Integer c_Id) {
		this.c_Id = c_Id;
	}

	public String getC_Code() {
		return c_Code;
	}

	public void setC_Code(String c_Code) {
		this.c_Code = c_Code;
	}

	public String getC_Name() {
		return c_Name;
	}

	public void setC_Name(String c_Name) {
		this.c_Name = c_Name;
	}

	public String getC_PreStr() {
		return c_PreStr;
	}

	public void setC_PreStr(String c_PreStr) {
		this.c_PreStr = c_PreStr;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}