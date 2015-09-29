package com.example.truong.quytchat;

import com.example.truong.quytchat.api.BaseResultServer;
import com.example.truong.quytchat.api.WebService;
import com.example.truong.quytchat.model.Account;
import com.example.truong.quytchat.model.Debug;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOverlay;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by TRUONG on 6/15/2015.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class SigninActivity extends Activity {
	private EditText edt_username, edt_pass;
	private Button bt_login;
	private TextView tv_Signup;
	private CheckBox chksaveaccount;
	String username,passwd;
	Account account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();		
		if (isOnline() == false) {
			new AlertDialog.Builder(SigninActivity.this).setMessage(R.string.wifi).setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).create().show();
		}
		bt_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				username = edt_username.getText().toString();
				passwd = edt_pass.getText().toString();
				if (checkFields(username, passwd))
					return;
				account = new Account();
				account.setUsername(username);
				account.setPasswd(passwd);
				LoginAsync login = new LoginAsync();
				login.execute(account);
			}
		});

		tv_Signup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SigninActivity.this, SignupActivity.class);
				startActivity(i);
			}
		});
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();		
		savingPreferences();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		restoringPreferences();
	}

	private boolean checkFields(String username, String passwd) {
		boolean flag = false;
		if (username.equals("")) {
			edt_username.setError("Tên đăng nhập");
			flag = true;
		}
		if (passwd.equals("")) {
			edt_pass.setError("Mật khẩu đăng ký");
			flag = true;
		}
		return flag;
	}
	
	

	public class LoginAsync extends AsyncTask<Account, Void, BaseResultServer> {
		WebService ws = null;
		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			ws = new WebService();
			dialog = ProgressDialog.show(SigninActivity.this, null, "Đang xử lý...");
		}

		@Override
		protected BaseResultServer doInBackground(Account... params) {
			return ws.signin(params[0]);
		}

		@Override
		protected void onPostExecute(BaseResultServer result) {
			dialog.dismiss();
			if (result == null) {
				Debug.Toast(SigninActivity.this, "Lỗi đăng nhập hệ thống");
				return;
			}
			
			if (result.isStatus()) {
				Intent i = new Intent(SigninActivity.this, ListAccountActivity.class);
				i.putExtra(Global.NAME,username);
				i.putExtra(Global.PASS,passwd);
				startActivity(i);
				finish();
			} else {
				Debug.Toast(SigninActivity.this, result.getMsg());
			}
		}

	}

	private void init() {
		edt_username = (EditText) findViewById(R.id.etUserName);
		edt_pass = (EditText) findViewById(R.id.etPass);
		bt_login = (Button) findViewById(R.id.btLogIn);
		tv_Signup = (TextView) findViewById(R.id.tvSignUp);
		chksaveaccount=(CheckBox)findViewById(R.id.chksaveacount);
	}

	private Boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni != null && ni.isConnected()) {
			return true;
		}
		return false;
	}
	
	//luu trang thai dang nhap
	public void savingPreferences() {
		SharedPreferences pre=getSharedPreferences(Global.prefname, MODE_PRIVATE);
		SharedPreferences.Editor editor=pre.edit();
		boolean bchk=chksaveaccount.isChecked();
		if(!bchk){
			editor.clear();
		}else{
			editor.putString("user", username);
			editor.putString("pwd", passwd);
			editor.putBoolean("checked", bchk);
		}		
		editor.commit();
	}
	
	//doc trang thai dang nhap
	public void restoringPreferences() {
		SharedPreferences pre=getSharedPreferences(Global.prefname, MODE_PRIVATE);
		//lấy giá trị checked ra, nếu không thấy thì giá trị mặc định là false
		boolean bchk=pre.getBoolean("checked", false);
		if(bchk){
			String user=pre.getString("user", "");
			String pwd=pre.getString("pwd", "");
			edt_username.setText(user);
			edt_pass.setText(pwd);
		}
		chksaveaccount.setChecked(bchk);
	}
}
