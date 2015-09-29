package com.example.truong.quytchat.api;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.CountDownTimer;

import com.example.truong.quytchat.ServiceHandler;
import com.example.truong.quytchat.model.Account;
import com.example.truong.quytchat.model.Debug;

/**
 * Created by TRUONG on 6/18/2015.
 */
public class WebService {
// 172.17.5.88
//	192.168.1.83
	private final String HOME = "http://172.17.5.88:8080/gcm_server_php";
	private ServiceHandler handler = null;
	private ArrayList<NameValuePair> param;

	public WebService() {
		handler = new ServiceHandler();
		param = new ArrayList<NameValuePair>();
	}
	
	public BaseResultServer sendMessage(String email, String message){
		String url = getLink("sendmsg.php");
		param.add(new BasicNameValuePair("email", email));
		param.add(new BasicNameValuePair("message", message));
		String str = handler.makeServiceCall(url, ServiceHandler.GET, param);
		if (str != null) {
			try {
				Debug.e(str);
				JSONObject object = new JSONObject(str);
				BaseResultServer result = new BaseResultServer(object.getBoolean("status"), object.getString("alert"));
				return result;
			} catch (JSONException e) {
				Debug.e(e.toString());
			}
		} 
		return null;
		
	}
	
	public FriendsResultServer getAllFriends() {
		String url = getLink("allfriend.php");
		String str = handler.makeServiceCall(url, ServiceHandler.GET);
		if (str != null) {
			try {
				Debug.e(str);
				JSONObject object = new JSONObject(str);
				FriendsResultServer result = new FriendsResultServer(object.getBoolean("status"), object.getString("alert"));
				JSONArray array = object.getJSONArray("data");
				int n = array.length();
				ArrayList<Account> accounts = new ArrayList<Account>();
				for(int i=0; i<n; ++i){
					JSONObject obj = array.getJSONObject(i);
					Account acc = new Account(obj.getString("name"), null, obj.getString("email"), obj.getString("phone"), obj.getString("gcm_regid"));
					acc.setOnline(Boolean.parseBoolean(obj.getString("online")));
					accounts.add(acc);
				}
				result.setAccounts(accounts);
				return result;
			} catch (JSONException e) {
				Debug.e(e.toString());
			}
		} 
		return null;
	}
	
	public BaseResultServer signin(Account account){
		String url = getLink("signin.php");
		param.add(new BasicNameValuePair("name", account.getUsername()));
		param.add(new BasicNameValuePair("pass", account.getPasswd()));
		String str = handler.makeServiceCall(url, ServiceHandler.POST, param);
		if (str != null) {
			try {
				Debug.e(str);
				JSONObject object = new JSONObject(str);
				BaseResultServer result = new BaseResultServer(object.getBoolean("status"), object.getString("alert"));
				return result;
			} catch (JSONException e) {
				Debug.e(e.toString());
			}
		} 
		return null;
	}
	
	public BaseResultServer turnon(Account account){
		String url = getLink("turnon.php");
		param.add(new BasicNameValuePair("name", account.getUsername()));
		param.add(new BasicNameValuePair("pass", account.getPasswd()));
		String str = handler.makeServiceCall(url, ServiceHandler.POST, param);
		if (str != null) {
			try {
				Debug.e(str);
				JSONObject object = new JSONObject(str);
				BaseResultServer result = new BaseResultServer(object.getBoolean("status"), object.getString("alert"));
				return result;
			} catch (JSONException e) {
				Debug.e(e.toString());
			}
		}
		return null;
	}
	
	public BaseResultServer signoff(Account account){
		String url = getLink("signoff.php");
		param.add(new BasicNameValuePair("name", account.getUsername()));
		param.add(new BasicNameValuePair("pass", account.getPasswd()));
		String str = handler.makeServiceCall(url, ServiceHandler.POST, param);
		if (str != null) {
			try {
				Debug.e(str);
				JSONObject object = new JSONObject(str);
				BaseResultServer result = new BaseResultServer(object.getBoolean("status"), object.getString("alert"));
				return result;
			} catch (JSONException e) {
				Debug.e(e.toString());
			}
		}
		return null;
	}
	
	/**
	 * Đăng ký tài khoản mới
	 * @param account
	 * @return
	 */
	public BaseResultServer signup(Account account) {
		String url = getLink("signup.php");
		param.add(new BasicNameValuePair("name", account.getUsername()));
		param.add(new BasicNameValuePair("pass", account.getPasswd()));
		param.add(new BasicNameValuePair("email", account.getEmail()));
		param.add(new BasicNameValuePair("phone", account.getPhone()));
		param.add(new BasicNameValuePair("regid", account.getRegID()));
		String str = handler.makeServiceCall(url, ServiceHandler.POST, param);
		if (str != null) {
			try {
				JSONObject object = new JSONObject(str);
				BaseResultServer result = new BaseResultServer(object.getBoolean("status"), object.getString("alert"));
				result.trace();
				return result;
			} catch (JSONException e) {
				Debug.e(e.toString());
			}
		}
		return null;
	}

	private String getLink(String method) {
		return String.format("%s/%s", HOME, method);
	}

	
}
