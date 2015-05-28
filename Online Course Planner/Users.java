package cpd.model;

public class Users {
	
	private String userName;
	
	private String password;
	
	private String reTypePassword;
	
	private String firstName;
	
	private String lastName;

	public Users(String userName, String password, String reTypePassword,
			String firstName, String lastName) {
		super();
		this.userName = userName;
		this.password = password;
		this.reTypePassword = reTypePassword;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReTypePassword() {
		return reTypePassword;
	}

	public void setReTypePassword(String reTypePassword) {
		this.reTypePassword = reTypePassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
