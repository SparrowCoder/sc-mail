package com.sparrowcode.mail;

import javax.mail.*;

public class MailAuthenticator extends Authenticator {

	private String userName = null;
	private String password = null;

	public MailAuthenticator() {
		super();
	}

	public MailAuthenticator(String username, String password) {
		super();
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
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

}