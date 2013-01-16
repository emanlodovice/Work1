package com.work1.now;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle {
	Bitmap pic;
	float width, height;
	float picWidth, picHeight;
	float x;
	float dy;
	float y;
	Rect dst, src;
	Paint paint;
	
	public Obstacle(Canvas canvas, float y, Bitmap bitmap, float dy) {
		this.x = canvas.getWidth();
		this.y = y;
		this.dy = dy;
		pic = bitmap;
		width = height = canvas.getHeight() / 6;
		picWidth = pic.getWidth();
		picHeight = pic.getHeight();
		paint = new Paint();
		paint.setFilterBitmap(true);
	}

	public void draw(Canvas canvas) {
		dst = new Rect((int)x, (int)y, (int)(x + width), (int) (y + height));
		src = new Rect(0,0,(int)picWidth, (int)picHeight);
		canvas.drawBitmap(pic, src, dst, paint);
	}

	public float getX() {
		return x;
	}

	public float getY() {		
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean isHit(float x, float y, float width, float height) {
		if ((y > (this.y + (getHeight() / 5))) && ((this.y + getHeight() - (getHeight() / 5)) > y)) {
			if ((x > (this.x + (getWidth() / 5))) && ((this.x + getWidth() - (getWidth() / 5)) > x)) {
				return true;
			}
			if (((this.x + (getWidth() / 5)) > x) && ((x + width) > this.x + (getWidth() / 5))) {
				return true;
			}
			
		}	else if (((this.y + (getWidth() / 5)) > y) && ((y + height) > (this.y + (getWidth() / 5)))) {
			if ((x > (this.x + (getWidth() / 5))) && (((this.x + getWidth() - (getWidth() / 5))) > x)) {
				return true;
			}
			if ((this.x + (getWidth() / 5) > x) && ((x + width) > this.x + (getWidth() / 5))) {
				return true;
			}
		}
		return false;
	}

	public boolean validate(Canvas canvas) {
		if (x + pic.getWidth() < 0) {
			pic = null;
			paint = null;
			dst = null;
			src = null;
			return false;
		}
		if ((y + getHeight()) > canvas.getHeight()) {
			pic = null;
			paint = null;
			dst = null;
			src = null;
			return false;
		}
		return true;
	}
	
	public Bitmap getBitmap() {
		return pic;
	}
	
	public void move(float dx, Canvas canvas) {
		this.x -= dx;
		if (this.dy != 0) {
			if ((canvas.getHeight() > (this.y + this.height + this.dy) && (this.dy > 0)) || ((this.dy < 0) && ((this.y + this.dy) > 0))) {
				this.y += this.dy;
			}	else {
				this.dy *= -1;
			}
		}
	}

}
