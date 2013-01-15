package com.work1.now;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Pause extends Activity implements OnClickListener{
	
	private Button resume;
	private Button restart;
	private Button menu;
	MediaPlayer pause;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pause);
		init();
	}
	
	private void init() {
		resume = (Button) findViewById(R.id.bResume);
		resume.setOnClickListener(this);
		restart = (Button) findViewById(R.id.bRestart);
		restart.setOnClickListener(this);
		menu = (Button) findViewById(R.id.bPauseMenu);
		menu.setOnClickListener(this);
		pause = MediaPlayer.create(this, R.raw.pause);
		pause.start();
	}

	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bund = new Bundle();
		switch(v.getId()) {
		case R.id.bResume:
			bund.putString("action", "resume");
			break;
		case R.id.bRestart:
			bund.putString("action", "restart");
			break;
		default:
			bund.putString("action", "menu");
			break;
		}
		intent.putExtras(bund);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
