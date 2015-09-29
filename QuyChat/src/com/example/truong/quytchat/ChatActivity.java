package com.example.truong.quytchat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.truong.quytchat.api.BaseResultServer;
import com.example.truong.quytchat.api.WebService;
import com.example.truong.quytchat.model.Chat;
import com.example.truong.quytchat.model.Debug;
import com.example.truong.quytchat.model.adapter.ChatAdapter;
import com.google.android.gms.wearable.NodeApi.GetConnectedNodesResult;

public class ChatActivity extends BaseActivity implements OnClickListener {

	private ListView listview;
	private static ChatAdapter adapter;
	private Button btn_send;
	private EditText edt_message;
	private static String message, diachi = null, address, city, GPSaddress = null;
	private double latitude, longitude;
	private GPSTracker gpstracker;
	private String email;
	private Geocoder geocoder;
	private List<Address> addresses = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		listview = (ListView) findViewById(R.id.listview);
		getGPS();
		ArrayList<Chat> lst = new ArrayList<Chat>();
		email = getIntent().getExtras().getString(Global.EMAIL);
		adapter = new ChatAdapter(ChatActivity.this, lst);
		listview.setAdapter(adapter);
		edt_message = (EditText) findViewById(R.id.edt_message);
		btn_send = (Button) findViewById(R.id.btn_send);
		btn_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		SendAsync sendAsync = new SendAsync();
		message = edt_message.getText().toString() + diachi;
		if (!message.equals(""))
			sendAsync.execute(email, message);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_gps, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.gps:
			if (item.isChecked()) {
				diachi = GPSaddress;
				item.setChecked(false);
			} else {
				diachi = ".";
				item.setChecked(true);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class SendAsync extends AsyncTask<String, Void, BaseResultServer> {

		private WebService ws;
		private String message;

		@Override
		protected void onPreExecute() {
			ws = new WebService();
		}

		@Override
		protected BaseResultServer doInBackground(String... params) {
			message = params[1];
			return ws.sendMessage(params[0], message);
		}

		@Override
		protected void onPostExecute(BaseResultServer result) {
			edt_message.setText("");
			update(new Chat(message, false));
		}
	}

	public void update(final Chat chat) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				adapter.add(chat);
				listview.setSelection(adapter.getCount());
			}
		});
	}

	public void getGPS() {
		try {
			gpstracker = new GPSTracker(ChatActivity.this);
			if (gpstracker.canGetLocation) {
				longitude = gpstracker.getLongitude();
				latitude = gpstracker.getLatitude();

				geocoder = new Geocoder(ChatActivity.this, Locale.getDefault());
				addresses = geocoder.getFromLocation(latitude, longitude, 1);

				address = addresses.get(0).getAddressLine(0);
				city = addresses.get(0).getLocality();
				diachi = "\n" + address + " , " + city;
				GPSaddress=diachi;

			} else {
				gpstracker.showSettingsAlert();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
