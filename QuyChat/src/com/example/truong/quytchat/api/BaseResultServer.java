package com.example.truong.quytchat.api;

import com.example.truong.quytchat.model.Debug;

public class BaseResultServer {

	private boolean status;
	private String msg;

	public BaseResultServer(boolean status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public boolean isStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}
	
	public void trace(){
		Debug.e("status: " + status +"\n" + "msg: " + msg);
	}

}
