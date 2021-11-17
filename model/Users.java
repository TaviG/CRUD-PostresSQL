package model;

public class Users {
	private int userid;
	private String username;
	private String password;
	private boolean admin;
	
	
	public Users(int userid, String username, String password, boolean admin) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.admin = admin;
	}


	public Users(String username, String password, boolean admin) {
		super();
		this.username = username;
		this.password = password;
		this.admin = admin;
	}


	public int getUserid() {
		return userid;
	}


	public void setUserid(int userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isAdmin() {
		return admin;
	}


	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	
}
