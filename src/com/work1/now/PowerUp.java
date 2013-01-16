package com.work1.now;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class PowerUp {
	int type;
	float x,y;
	Bitmap pic;
	float width, height;
	
	public PowerUp(float x, float y, int type, Bitmap pic, Canvas canvas) {
		this.x = x;
		this.y = y;
		this.pic = pic;
		this.type = type;
		width = canvas.getWidth() / 25;
		height = width;
	}
	
	public void move(float dis) {
		this.x -= dis;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void draw(Canvas canvas) {
		Rect rec = new Rect((int)x, (int)y, (int)(x + width), (int)(y + getHeight()));
		canvas.drawBitmap(pic, null, rec, null);
	}
	
	public boolean isHit(float x, float y, float width, float height) {
		if ((y > this.y) && ((this.y + getHeight()) > y)) {
			if ((x > this.x) && ((this.x + getWidth()) > x)) {
				return true;
			}
			if ((this.x > x) && ((x + width) > this.x)) {
				return true;
			}
			
		}	else if ((this.y > y) && ((y + height) > this.y)) {
			if ((x > this.x) && ((this.x + getWidth()) > x)) {
				return true;
			}
			if ((this.x > x) && ((x + width) > this.x)) {
				return true;
			}
		}
		return false;
	}

	public int getType() {
		return type;
	}
	
	public boolean validate() {
		if (this.x + getWidth() < 0) {
			pic = null;
			return false;
		}
		return true;
	}
}
