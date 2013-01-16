package com.work1.now;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Donut implements Food{
	
	Bitmap pic;
	
	float x, y, width, height;
	int score;
	private boolean isAbsurbed;
	Rect dst;
	Paint paint;
	
	public Donut(Canvas canvas, float y, Bitmap pic, int score) {
		this.x = canvas.getWidth();
		this.y = y;
		this.pic = pic;
		this.width = height = (canvas.getHeight() / 10);
		this.score = score;
		this.isAbsurbed = false;
		paint = new Paint();
		paint.setFilterBitmap(true);
	}

	public void draw(Canvas canvas) {
		dst = new Rect((int)x, (int)y, (int)(x + width), (int)(y + height));
		canvas.drawBitmap(pic, null, dst, paint);
	}

	public float getX() {
		return x;
	}

	public float getY() {		
		return y;
	}

	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
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

	public boolean validate(Canvas canvas) {
		if (x + getWidth() < 0) {
			pic = null;
			paint = null;
			dst = null;
			return false;
		}
		if ((y + getHeight()) > canvas.getHeight()) {
			pic = null;
			paint = null;
			dst = null;
			return false;
		}
		return true;
	}
	
	public Bitmap getBitmap() {
		return pic;
	}
	
	public void move(float dx, float dy) {
		this.x -= dx;
		this.y -= dy;
	}
	
	public void moveTo(float fx, float fy) {
		if (fx > this.x) {
			this.x += 20;
		}	else if (fx == this.x) {}
		else {
			this.x -= 20;
		}
		if (fy > this.y) {
			float dif = fy - this.y;
			if (dif >= 20) {
				this.y += 20;
			}	else {
				this.y += dif;
			}
		}	else if (fy == this.y) {}
		else {
			float dif = this.y - fy;
			if (dif >= 20) {
				this.y -= 20;
			}	else {
				this.y -= dif;
			}
		}
	}
	
	public int getScore() {
		return score;
	}

	public boolean isAbsurbed() {
		return isAbsurbed;
	}

	public void absurb() {
		this.isAbsurbed = true;
	}

}
