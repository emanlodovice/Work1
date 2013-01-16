package com.work1.now;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends Activity implements OnClickListener{

	Button restart;
	Button menu;
	Bundle score;
	TextView burgerCount;
	TextView cakeCount;
	TextView hotdogCount;
	TextView pieCount;
	TextView pizzaCount;
	TextView drumstickCount;
	TextView totalScore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.gameover);
		init();
		Intent intent = getIntent();
		score = intent.getExtras();
		burgerCount.setText(" = " + score.getInt("burgerCount"));
		cakeCount.setText(" = " + score.getInt("cakeCount"));
		hotdogCount.setText(" = " + score.getInt("hotdogCount"));
		pieCount.setText(" = " + score.getInt("pieCount"));
		pizzaCount.setText(" = " + score.getInt("pizzaCount"));
		drumstickCount.setText(" = " + score.getInt("drumstickCount"));
		totalScore.setText(score.getInt("totalScore") + "");
		int tScore = score.getInt("totalScore");
		
		SharedPreferences sPrefs = getSharedPreferences("score", 0);
		int s1 = sPrefs.getInt("highest1", 0);
		int s2 = sPrefs.getInt("highest2", 0);
		int s3 = sPrefs.getInt("highest3", 0);
		String scorer1 = sPrefs.getString("highscorer1", "Someone");
		String scorer2 = sPrefs.getString("highscorer2", "Someone");
		SharedPreferences.Editor editor =  sPrefs.edit();
		
		SharedPreferences sp = getSharedPreferences("prefs", 0);
		String name = sp.getString("name", "Player");
		if (tScore > s1) {
			editor.putInt("highest1", tScore);
			editor.putString("highscorer1", name);
			editor.putInt("highest2", s1);
			editor.putString("highscorer2", scorer1);
			editor.putInt("highest3", s2);
			editor.putString("highscorer3", scorer2);
			editor.commit();
		} else if (tScore > s2) {
			editor.putInt("highest2", tScore);
			editor.putString("highscorer2", name);
			editor.putInt("highest3", s2);
			editor.putString("highscorer3", scorer2);
			editor.commit();
		} else if (tScore > s3) {
			editor.putInt("highest3", tScore);
			editor.putString("highscorer3", name);
			editor.commit();
		}
	}
	
	public void onClick(View event) {
		switch(event.getId()) {
		case R.id.bRestart :
			Intent i = new Intent("com.work.now.GAME");
			startActivity(i);
			break;
		case R.id.bMenu:
			finish();
			break;
		}
	}
	
	public void init() {
		restart = (Button) findViewById(R.id.bRestart);
		menu = (Button) findViewById(R.id.bMenu);
		burgerCount = (TextView) findViewById(R.id.tvBurgerCount);
		cakeCount = (TextView) findViewById(R.id.tvCakeCount);
		hotdogCount = (TextView) findViewById(R.id.tvHotdogCount);
		pieCount = (TextView) findViewById(R.id.tvPieCount);
		pizzaCount = (TextView) findViewById(R.id.tvPizzaCount);
		drumstickCount = (TextView) findViewById(R.id.tvDrumstickCount);
		totalScore = (TextView) findViewById(R.id.tvScore);
		restart.setOnClickListener(this);
		menu.setOnClickListener(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	

}
