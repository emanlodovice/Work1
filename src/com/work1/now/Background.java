package com.work1.now;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Background {
	int sx;
	int fx;
	int fy;
	Bitmap pic;
	Paint paint;
	Rect rec;

	public Background(int sx, int fx, int fy, Bitmap pic) {
		this.sx = sx;
		this.fx = fx;
		this.fy = fy;
		this.pic = pic;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
	}
	
	public void draw(Canvas canvas) {
		rec = new Rect(sx, 0, fx, fy);
		canvas.drawBitmap(pic, null, rec, paint);
	}
	
	public void move(float speed) {
		sx -= speed;
		fx -= speed;
	}
	
	public boolean validate() {
		if (fx < 0) {
			pic = null;
			rec = null;
			paint = null;
			return false;
		}
		return true;
	}
	
	public int getFx() {
		return fx;
	}
	
}
