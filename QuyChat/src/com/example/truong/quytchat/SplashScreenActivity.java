package com.example.truong.quytchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by TRUONG on 6/15/2015.
 */
public class SplashScreenActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		Thread timerThread = new Thread() {
			public void run() {
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent i = new Intent(SplashScreenActivity.this, SigninActivity.class);
					startActivity(i);
				}
			}
		};
		timerThread.start();

	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}
