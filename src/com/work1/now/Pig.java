package com.work1.now;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Pig {
	private float x;
	private float y;
	private Bitmap pig;
	private float width;
	private float height;
	private float picWidth, picHeight;
	private int state;
	private boolean isInvul;
	private boolean canAbsurb;
	private float minLeftLoc;
	
	
	public Pig(float x, float y, Resources res) {
		this.x = x;
		this.y = y;
		pig = BitmapFactory.decodeResource(res, R.drawable.pigrinning);
		width = picWidth = pig.getWidth() / 3;
		height = picHeight = pig.getHeight();
		state = 0;
		isInvul = canAbsurb =  false;
		minLeftLoc = 0;
	}
	
	public void setInvul(boolean isInvul) {
		this.isInvul = isInvul;
	}
	
	
	public boolean isInvul() {
		return isInvul;
	}
	
	public void setAbsurb(boolean abs) {
		this.canAbsurb = abs;
	}
	
	public boolean canAbsurb() {
		return canAbsurb;
	}
	
	
	public void move(float x, float y, Canvas canvas) {
		if (((this.x + x) >= minLeftLoc) && ((this.x + x + width) <= canvas.getWidth())) {
			this.x += x;
		}
		if (((this.y + y) > 0) && (this.y + y + height) <= canvas.getHeight()) {
			this.y += y;
		}
	}
	
	public void increaseMinLeftLoc(Canvas canvas) {
		if (minLeftLoc + (canvas.getWidth() / 20) < (canvas.getWidth() / 2)) {
			minLeftLoc += canvas.getWidth() / 20;
		}
	}
	
	public boolean isTouched(float x, float y) {
		if ((x > this.x) && (this.x + width) > x) {
			if ((y > this.y) && (this.y + height) > y) {
				return true;
			}
		}
		return false;
	}
	
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void increaseSize() {
		if ((picWidth * 5) > width) {
			width += .5;
			height += .5;
		}
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	
	public void draw(Canvas canvas) {
		Rect dst = new Rect((int) x, (int) y, (int) (x + width), (int) (y + height));
		Rect src = new Rect((int) ((state /2) * picWidth), 0, (int) ((state/2) * picWidth + picWidth), (int) picHeight);
		canvas.drawBitmap(pig,src, dst, null);
		state = (state + 1) % 6;
	}

}
