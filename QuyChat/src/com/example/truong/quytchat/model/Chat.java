package com.example.truong.quytchat.model;

public class Chat {

	private String message;
	private boolean flag;

	public Chat(String message, boolean flag) {
		super();
		this.message = message;
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
