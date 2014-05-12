package org.bofur.bean;

public class User implements Bean {
	private long id;
	private String login;
	private String password;
	
	public User(String login, String password) {
		this(0, login, password);
	}
	
	public User(long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return login;
	}
	
	public void SetName(String name) {
		this.login = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
