package com.example.truong.quytchat.gcm;

import javax.net.ssl.SSLEngineResult.HandshakeStatus;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.truong.quytchat.BaseActivity;
import com.example.truong.quytchat.ChatActivity;
import com.example.truong.quytchat.Global;
import com.example.truong.quytchat.R;
import com.example.truong.quytchat.SplashScreenActivity;
import com.example.truong.quytchat.model.Chat;
import com.example.truong.quytchat.model.Debug;
import com.google.android.gms.gcm.GoogleCloudMessaging;

@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class GCMNotificationIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "GCMNotificationIntentService";

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);
		String username = getResources().getString(R.string.app_name);
		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification(username, "Send error: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification(username, "Deleted messages on server: " + extras.toString());
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

				for (int i = 0; i < 5; i++) {
					Log.i(TAG, "Working... " + (i + 1) + "/5 @ " + SystemClock.elapsedRealtime());
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}

				}

				try {
					// username = extras.getString(Config.USERNAME).toString();
					Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
					final String message = extras.getString(Global.MESSAGE_KEY).toString();
					if(BaseActivity.instance instanceof ChatActivity){
						((ChatActivity)BaseActivity.instance).update(new Chat(message, true));
					} else {
						sendNotification(username, extras.getString(Global.MESSAGE_KEY).toString());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				

			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	private void sendNotification(String username, String msg) {
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashScreenActivity.class), 0);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_launcher);
		mBuilder.setContentTitle(getResources().getString(R.string.app_name));
		mBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
		mBuilder.setContentText(username + " " + msg);
		mBuilder.setContentIntent(contentIntent);
		mBuilder.setSound(alarmSound);
		Notification notification = mBuilder.build();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotificationManager.notify(NOTIFICATION_ID, notification);
	}

	private void sendUpdateTalk(final String username, final String msg) {

		// BaseActivity.instance.runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		// ((ChatActivity) BaseActivity.context).adapter.addBaseChat(new
		// BaseChat(username, msg, true));
		// ((ChatActivity)
		// BaseActivity.context).listview_talk.setSelection(((ChatActivity)
		// BaseActivity.context).adapter.getCount());
		// }
		// });

	}
}
