package com.example.truong.quytchat.gcm;

import java.io.IOException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Log;

import com.example.truong.quytchat.Global;
import com.example.truong.quytchat.model.Debug;
import com.example.truong.quytchat.model.PreferenceUtils;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCM {

	public static final String APP_VERSION = "APP_VERSION"; 
	private final String tag = "TAG GCM ";
	private GoogleCloudMessaging gcm;
	
	public GCM() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Đăng ký mới GCM cho user
	 * 
	 * @return
	 */
	public String registerGCM(Context context) {
		gcm = GoogleCloudMessaging.getInstance(context);
		String regId = getRegistrationId(context);
		if (TextUtils.isEmpty(regId)) {
			try {
				regId = gcm.register(Global.GOOGLE_PROJECT_ID);
				storeRegistrationId(context, regId);
				Debug.e(regId);
			} catch (IOException e){
				Debug.e(e.toString());
			}
		} else {
			Debug.e("RegId already available. RegId: " + regId);
		}
		return regId;
	}
	
	/**
	 * Lưu trữ RegistraionID trên client
	 * @param context
	 * @param regId
	 */
	private void storeRegistrationId(Context context, String regId) {
		int appVersion = getAppVersion(context);
		Debug.i("Saving regId on app version " + appVersion);
		PreferenceUtils.putString(context, Global.REGID, regId);
		PreferenceUtils.putInt(context, APP_VERSION, appVersion);
	}
	
	/**
	 * Đọc RegistraionID trên client
	 * @param context
	 * @return
	 */
	private String getRegistrationId(Context context) {
		String registrationId = PreferenceUtils.getString(context, Global.REGID);
		if (registrationId == null) {
			Debug.i("Registration not found.");
			return "";
		}
		int registeredVersion = PreferenceUtils.getInt(context, APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if (registeredVersion != currentVersion) {
			Debug.i("App version changed.");
			return "";
		}
		return registrationId;
	}
	
	/**
	 * Lấy phiên bản ứng dụng
	 * @param context
	 * @return
	 */
	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			Debug.i("I never expected this! Going down, going down!" + e);
			throw new RuntimeException(e);
		}
	}
	
}
