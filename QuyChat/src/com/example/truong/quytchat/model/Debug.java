package com.example.truong.quytchat.model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Debug {

	public static void Toast(final Context context, final String message) {
		((Activity) context).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public static void v(String message) {
		String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
		Log.v("Tag " + className + "." + methodName + "():" + lineNumber, message);
	}

	public static void d(String message) {
		String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
		Log.d("Tag " + className + "." + methodName + "():" + lineNumber, message);
	}

	public static void i(String message) {
		String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
		Log.i("Tag " + className + "." + methodName + "():" + lineNumber, message);
	}

	public static void w(String message) {
		String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
		Log.w("Tag " + className + "." + methodName + "():" + lineNumber, message);
	}

	public static void e(String message) {
		String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
		Log.e("Tag " + className + "." + methodName + "():" + lineNumber, message);
	}

}