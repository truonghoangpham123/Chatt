package com.example.truong.quytchat.api;

import java.util.ArrayList;

import com.example.truong.quytchat.model.Account;

public class FriendsResultServer extends BaseResultServer {

	ArrayList<Account> accounts;

	public FriendsResultServer(boolean status, String msg) {
		super(status, msg);
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

	public void trace() {
		super.trace();
		for(Account acc : accounts)
			acc.trace();
	}

}
