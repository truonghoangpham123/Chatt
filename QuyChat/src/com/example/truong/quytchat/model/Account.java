package com.example.truong.quytchat.model;

import com.example.truong.quytchat.MD5;

/**
 * Created by TRUONG on 6/18/2015.
 */
public class Account {

	private String username;
	private String passwd;
	private String email;
	private String phone;
	private String regID;
	private boolean online;

	public Account() {
	}

	public Account(String username, String passwd, String email, String phone, String regID) {
		super();
		this.username = username;
		this.passwd = passwd;
		this.email = email;
		this.phone = phone;
		this.regID = regID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRegID(String regID) {
		this.regID = regID;
	}

	public String getPasswd() {
		return MD5.encryptMD5(passwd);
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getRegID() {
		return regID;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void trace() {
		Debug.e(email + "\n" + username);
	}

}
