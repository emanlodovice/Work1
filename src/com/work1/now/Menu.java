package com.work1.now;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Menu extends Activity implements OnClickListener{
	
	Button play, scores, setting, shop;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.menu);
		initialize();
	}
	
	private void initialize() {
		play = (Button) findViewById(R.id.bPlay);
		scores = (Button) findViewById(R.id.bScores);
		setting = (Button) findViewById(R.id.bSettings);
//		shop = (Button) findViewById(R.id.bShop);
		play.setOnClickListener(this);
		scores.setOnClickListener(this);
		setting.setOnClickListener(this);
//		shop.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bPlay:
			Intent play = new Intent("com.work.now.GAME");
			startActivity(play);
			break;
		case R.id.bSettings:
			Intent set = new Intent("com.work.now.SETTINGS");
			startActivity(set);
			break;
		case R.id.bScores:
			Intent s = new Intent("com.work.now.HIGHSCORE");
			startActivity(s);
			break;
		}
	}
}
