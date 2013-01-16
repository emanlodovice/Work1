package com.work1.now;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Settings extends Activity {

	LinearLayout changeName, changeSound;
	TextView summary;
	CheckBox sound;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		changeName = (LinearLayout) findViewById(R.id.llName);
		changeSound = (LinearLayout) findViewById(R.id.llSound);
		sound = (CheckBox) findViewById(R.id.cbSound);
		summary = (TextView) findViewById(R.id.tvSummary);
		pref = getSharedPreferences("prefs", 0);
		editor = pref.edit();
		
		summary.setText("Playing as " + pref.getString("name", "Player"));
		sound.setChecked(pref.getBoolean("sound", true));
		
		changeName.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showPopUp();
			}
		});
		
		changeSound.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sound.setChecked(!sound.isChecked());
				editor.putBoolean("sound", sound.isChecked());
				editor.commit();
			}
		});
	}
	
	private void showPopUp() {

		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle("Change Player's Name");
		 
		final EditText input = new EditText(this);
		input.setSingleLine();
		input.setText(pref.getString("name", "Player"));
		input.setHint("Type your name here...");
		helpBuilder.setView(input);
		 
		helpBuilder.setPositiveButton("OK", 
			new OnClickListener() {
			 	public void onClick(DialogInterface arg0, int arg1) {
			 		summary.setText("Playing as " + input.getText());
			 		String s = summary.getText().toString().substring(11);
			 		editor.putString("name", s);
			 		editor.commit();
			 	}
			}); 

		helpBuilder.setNegativeButton("CANCEL", 
			 new OnClickListener() {
			 	public void onClick(DialogInterface arg0, int arg1) {
					
				}
			});
		 
		 AlertDialog helpDialog = helpBuilder.create();
		 helpDialog.show();

		}

}