package com.work1.now;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public interface Food {
	public void draw(Canvas canvas);
	public float getX();
	public float getY();
	public float getWidth();
	public float getHeight();
	public boolean isHit(float x, float y, float width, float height);
	public boolean validate(Canvas canvas);
	public Bitmap getBitmap();
	public void move(float dx, float dy);
	public void moveTo(float fx, float fy);
	public int getScore();
	public boolean isAbsurbed();
	public void absurb();
}
