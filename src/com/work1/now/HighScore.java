package com.work1.now;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class HighScore extends Activity implements View.OnTouchListener {

	TextView scorer;
	TextView score;
	TextView scorer2;
	TextView score2;
	TextView scorer3;
	TextView score3;
	SurfaceView surface;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		surface = new SurfaceView(this);
		surface.setZOrderOnTop(true);
		surface.setOnTouchListener(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.scorelayout);
		
		scorer = (TextView) findViewById(R.id.tvScorer);
		score = (TextView) findViewById(R.id.tvScore);
		scorer2 = (TextView) findViewById(R.id.tvScorer2);
		score2 = (TextView) findViewById(R.id.tvScore2);
		scorer3 = (TextView) findViewById(R.id.tvScorer3);
		score3 = (TextView) findViewById(R.id.tvScore3);
		
		SharedPreferences prefs = getSharedPreferences("score", 0);
		String highest1 = prefs.getString("highscorer1", "Someone");
		String highest2 = prefs.getString("highscorer2", "Someone");
		String highest3 = prefs.getString("highscorer3", "Someone");
		int s1 = prefs.getInt("highest1", 0);
		int s2 = prefs.getInt("highest2", 0);
		int s3 = prefs.getInt("highest3", 0);
		
		scorer.setText(highest1);
		score.setText(s1 + "");
		scorer2.setText(highest2);
		score2.setText(s2 + "");
		scorer3.setText(highest3);
		score3.setText(s3 + "");
	}

	public boolean onTouch(View arg0, MotionEvent arg1) {
		this.finish();
		return true;
	}
}
