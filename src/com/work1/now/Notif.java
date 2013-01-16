package com.work1.now;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Notif extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.notif);
		Intent i = getIntent();
		Bundle b = i.getExtras();
		TextView prompt = (TextView) findViewById(R.id.tvPropt);
		prompt.setText(b.getString("prompt"));
		Thread myThread = new Thread() {
			public void run() {
				try {
				sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					onPause();
				}
				
			};
		};
		myThread.start();
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
