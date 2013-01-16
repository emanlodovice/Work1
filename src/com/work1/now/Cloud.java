package com.work1.now;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Cloud {
	
	float x;
	float y;
	Bitmap pic;
	float picWidth;
	float picHeight;
	float width;
	float height;
	int state;
	int stateInc;
	
	public Cloud(Canvas canvas, float y, Bitmap bitmap) {
		this.x = canvas.getWidth();
		this.y = canvas.getHeight() / 10;
		pic = bitmap;
		height = canvas.getHeight() / 5;
		picWidth = pic.getWidth() / 3;
		picHeight = pic.getHeight();
		width = height * (picWidth / picHeight);
		state = 0;
		stateInc = 1;
	}
	
	public void move(float spead) {
		x -= spead;
	}
	
	public void draw(Canvas canvas) {
		Rect dst = new Rect((int)x, (int)y, (int)(x + width),(int)(y + height));
		Rect src = new Rect((int)(picWidth * (state / 20)), 0, (int)(picWidth * ((state / 20) + 1)), (int) picHeight);
		if ((state == 59) && (stateInc > 0)) {
			stateInc = -1;
		}	else if ((state == 0) && (stateInc < 0)) {
			stateInc = 1;
		}
		state = (state + stateInc) % 60;
		canvas.drawBitmap(pic, src, dst, null);
	}
	
	public boolean validate() {
		if (x + width < 0) {
			pic = null;
			return false;
		}
		return true;
	}

}
