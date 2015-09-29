package com.example.truong.quytchat;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truong.quytchat.api.BaseResultServer;
import com.example.truong.quytchat.api.WebService;
import com.example.truong.quytchat.gcm.GCM;
import com.example.truong.quytchat.model.Account;
import com.example.truong.quytchat.model.Debug;

/**
 * Created by TRUONG on 6/18/2015.
 */
public class SignupActivity extends Activity {

	private EditText edt_username, edt_passwd, edt_email, edt_phone;
	private Button bt_Signup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		init();
		bt_Signup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String username = edt_username.getText().toString();
				String passwd = edt_passwd.getText().toString();
				String email = edt_email.getText().toString();
				String phone = edt_phone.getText().toString();
				if (checkFields(username, passwd, email, phone)) {
					return;
				}
				Account account = new Account(username, passwd, email, phone, "");
				SignupAsync signupAsync = new SignupAsync();
				signupAsync.execute(account);
			}
		});
	}

	private boolean checkFields(String username, String passwd, String email, String phone) {
		boolean flag = false;
		if (username.equals("")) {
			edt_username.setError("Xác nhận tên đăng nhập");
			flag = true;
		}
		if (passwd.equals("")) {
			edt_passwd.setError("Xác nhận mật khẩu");
			flag = true;
		}
		if (email.equals("")) {
			edt_email.setError("Xác nhận email");
			flag = true;
		}
		if (phone.equals("")) {
			edt_phone.setError("Xác nhận số điện thoại");
			flag = true;
		}
		return flag;
	}

	public class SignupAsync extends AsyncTask<Account, Void, BaseResultServer> {

		WebService ws = null;
		GCM gcm = null;
		
		@Override
		protected void onPreExecute() {
			gcm = new GCM();
			ws = new WebService();
			Toast.makeText(SignupActivity.this, getString(R.string.excute), Toast.LENGTH_LONG).show();
		}

		@Override
		protected BaseResultServer doInBackground(Account... params) {
			params[0].setRegID(gcm.registerGCM(SignupActivity.this));
			return ws.signup(params[0]);
		}

		@Override
		protected void onPostExecute(BaseResultServer baseresultserver) {
			if (baseresultserver == null) {
				Debug.Toast(SignupActivity.this, getString(R.string.error_signup));
				return;
			}
			Debug.Toast(SignupActivity.this, baseresultserver.getMsg());
			if (baseresultserver.isStatus()) {
				Intent i = new Intent(SignupActivity.this, SigninActivity.class);
				startActivity(i);
				finish();
			}
		}

	}

	private void init() {
		edt_username = (EditText) findViewById(R.id.etUserNameSignup);
		edt_passwd = (EditText) findViewById(R.id.etPassSignup);
		edt_email = (EditText) findViewById(R.id.etEmail);
		edt_phone = (EditText) findViewById(R.id.etSoDT);
		bt_Signup = (Button) findViewById(R.id.btDangKy);
	}
}
