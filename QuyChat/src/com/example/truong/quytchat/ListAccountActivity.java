package com.example.truong.quytchat;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.truong.quytchat.api.BaseResultServer;
import com.example.truong.quytchat.api.FriendsResultServer;
import com.example.truong.quytchat.api.WebService;
import com.example.truong.quytchat.model.Account;
import com.example.truong.quytchat.model.Debug;
import com.example.truong.quytchat.model.adapter.FriendsAdapter;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class ListAccountActivity extends Activity implements
		OnItemClickListener {

	private PullToRefreshListView listview;
	private FriendsAdapter adapter;
	String name, pass;
	Account account = new Account();
	ArrayList<Account> lstData;
	FriendsAsync friendsAsync;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_account);
		listview = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		adapter = new FriendsAdapter(getBaseContext(),
				R.layout.custom_item_friend, new ArrayList<Account>());
		listview.setAdapter(adapter);
		name = getIntent().getExtras().getString(Global.NAME);
		pass = getIntent().getExtras().getString(Global.PASS);
		account.setUsername(name);
		account.setPasswd(pass);

		/*
		 * new AlertDialog.Builder(ListAccountActivity.this)
		 * .setIcon(android.R.drawable.ic_dialog_alert) .setTitle("Question ?")
		 * .setMessage("you want to hide ?") .setPositiveButton("Yes", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) {
		 * OnOffAsync onOffAsync=new OnOffAsync(); onOffAsync.execute(account);
		 * 
		 * listview.setAdapter(adapter); FriendsAsync friendsAsync = new
		 * FriendsAsync(); friendsAsync.execute(); } }) .setNegativeButton("No",
		 * null) .show();
		 */		

		listview.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				adapter.clear();
				new FriendsAsync().execute();
				listview.onRefreshComplete();
			}

		});

		friendsAsync = new FriendsAsync();
		friendsAsync.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.online:
			new AlertDialog.Builder(ListAccountActivity.this)
			 .setIcon(android.R.drawable.ic_dialog_alert) .setTitle("Question ?")
			  .setMessage("You want Turn on Chatt ?") 
			  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {			 
				  @Override 
				  public void onClick(DialogInterface dialog, int which) {					 
					  new TurnOnAsync().execute(account);
					  adapter.clear();
					  new FriendsAsync().execute();			  
				  	}
				})
			   .setNegativeButton("No", null) .show();	
			break;

		case R.id.offline:			
			 new AlertDialog.Builder(ListAccountActivity.this)
			 .setIcon(android.R.drawable.ic_dialog_alert) .setTitle("Question ?")
			  .setMessage("You want Turn off Chatt ?") 
			  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {			 
				  @Override 
				  public void onClick(DialogInterface dialog, int which) {					 
					  new TurnOffAsync().execute(account);
					  adapter.clear();
					  new FriendsAsync().execute();			  
				  	}
				})
			   .setNegativeButton("No", null) .show();			 	
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(ListAccountActivity.this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Question ?")
				.setMessage("Are you sure you want to exit ?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								LogoutAsync logoutAsync = new LogoutAsync();
								logoutAsync.execute(account);

							}
						}).setNegativeButton("No", null).show();
	}

	class LogoutAsync extends AsyncTask<Account, Void, BaseResultServer> {
		WebService ws = null;

		@Override
		protected void onPreExecute() {
			ws = new WebService();
		}

		@Override
		protected BaseResultServer doInBackground(Account... params) {
			return ws.signoff(params[0]);
		}

		@Override
		protected void onPostExecute(BaseResultServer baseResultServer) {
			if (baseResultServer == null) {
				Debug.Toast(ListAccountActivity.this, "Lỗi đăng xuất hệ thống");
				return;
			}

			if (baseResultServer.isStatus()) {
				finish();
			} else {
				Debug.Toast(ListAccountActivity.this, baseResultServer.getMsg());
			}
		}
	}
	class TurnOnAsync extends AsyncTask<Account, Void, BaseResultServer> {
		WebService ws = null;

		@Override
		protected void onPreExecute() {
			ws = new WebService();
		}

		@Override
		protected BaseResultServer doInBackground(Account... params) {
			return ws.turnon(params[0]);
		}

		@Override
		protected void onPostExecute(BaseResultServer baseResultServer) {
			if (baseResultServer == null) {
				Debug.Toast(ListAccountActivity.this, "Lỗi trang thai online hệ thống");
				return;
			}

			if (baseResultServer.isStatus()) {
				// finish();
			} else {
				Debug.Toast(ListAccountActivity.this, baseResultServer.getMsg());
			}
		}
	}
	
	class TurnOffAsync extends AsyncTask<Account, Void, BaseResultServer> {
		WebService ws = null;

		@Override
		protected void onPreExecute() {
			ws = new WebService();
		}

		@Override
		protected BaseResultServer doInBackground(Account... params) {
			return ws.signoff(params[0]);
		}

		@Override
		protected void onPostExecute(BaseResultServer baseResultServer) {
			if (baseResultServer == null) {
				Debug.Toast(ListAccountActivity.this, "Lỗi đăng xuất hệ thống");
				return;
			}

			if (baseResultServer.isStatus()) {
				// finish();
			} else {
				Debug.Toast(ListAccountActivity.this, baseResultServer.getMsg());
			}
		}
	}

	class FriendsAsync extends AsyncTask<Void, Void, FriendsResultServer> {
		WebService ws = null;

		@Override
		protected void onPreExecute() {
			ws = new WebService();
		}

		@Override
		protected FriendsResultServer doInBackground(Void... arg0) {
			return ws.getAllFriends();
		}

		@Override
		protected void onPostExecute(FriendsResultServer result) {
			if (result == null) {
				Debug.Toast(ListAccountActivity.this, "Lỗi kết nối server");
				return;
			}
			adapter.addAll(result.getAccounts());			
			listview.setOnItemClickListener(ListAccountActivity.this);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> itemview, View view, int position,
			long delay) {
		try {
			Account acc = (Account) itemview.getItemAtPosition(position+1);
			Intent intent = new Intent(ListAccountActivity.this,
					ChatActivity.class);
			Log.e("tag", acc.getUsername());
			intent.putExtra(Global.EMAIL, acc.getEmail());
			startActivity(intent);
		} catch (Exception e) {
			Log.e("tag", e.toString());
		}
	}

}
