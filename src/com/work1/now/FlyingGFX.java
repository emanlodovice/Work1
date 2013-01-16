package com.work1.now;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class FlyingGFX extends Activity implements OnTouchListener{
	
	Thread myThread;
	FlySurface surface;
	Pig charac;
	Canvas canvas;
	List<Food> food;
	List<Background> background;
	List<Cloud> clouds;
	Random rand;
	float foodSpeed, prevFoodSpeed;
	Resources res;
	Bitmap bg, wolf, burger, cake, pie, drumstick, hotdog, pizza, invu, pow;
	List<Obstacle> obstacles;
	long cycles, powEnd;
	boolean isGameOver, isSoundOn;
	int foodType;
	int burgerCount, cakeCount, pieCount, drumstickCount, hotdogCount, pizzaCount;
	int score, hits, level, foodProductionRate, prevFoodProductionRate, wolfProductionRate;
	float xLoc, yLoc, xPrevLoc, yPrevLoc;
	PowerUp powUp;
	MediaPlayer eatSound,bgSound;
	WakeLock wl;
	SharedPreferences prefs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "stayalive");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		surface = new FlySurface(this);
		surface.setOnTouchListener(this);
		prefs = getSharedPreferences("prefs", 0);
		res = getResources();
		loadResources();
		init();
		setContentView(surface);
	}
	
	public void init() {
		charac = new Pig(0, 20, getResources());
		myThread = null;
		canvas = null;
		rand = new Random();
		food = new ArrayList<Food>();
		obstacles = new ArrayList<Obstacle>();
		foodSpeed = 0;
		prevFoodSpeed = 0;
		isGameOver = false;
		background = new ArrayList<Background>();
		clouds = new ArrayList<Cloud>();
		foodType = 1;
		foodProductionRate = 60;
		prevFoodProductionRate = 0;
		wolfProductionRate = 100;
		score = 0;
		burgerCount = cakeCount = hotdogCount = pieCount = pizzaCount = drumstickCount = 0;
		powUp = null;
		cycles = 0;
		hits = 0;	
		isSoundOn = prefs.getBoolean("sound", true);
		eatSound = MediaPlayer.create(this, R.raw.oink);
		bgSound = MediaPlayer.create(this, R.raw.bgm);
		level = 1;
		if(isSoundOn) {
			bgSound.start();
			bgSound.setLooping(true);
		}
	}
	
	public void loadResources() {
		bg = BitmapFactory.decodeResource(res, R.drawable.bgplay);
		wolf = BitmapFactory.decodeResource(res, R.drawable.ware_wolf);
		burger = BitmapFactory.decodeResource(res, R.drawable.burger);
		cake = BitmapFactory.decodeResource(res, R.drawable.cake);
		hotdog = BitmapFactory.decodeResource(res, R.drawable.hotdog);
		pie = BitmapFactory.decodeResource(res, R.drawable.pie);
		pizza = BitmapFactory.decodeResource(res, R.drawable.pizza);
		drumstick = BitmapFactory.decodeResource(res, R.drawable.drumstick);
		invu = BitmapFactory.decodeResource(res, R.drawable.invul);
	}

	public boolean onTouch(View arg0, MotionEvent event) {
		if (event.getPointerCount() < 2) {
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				xPrevLoc = event.getX();
				yPrevLoc = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
			xLoc = event.getX();
			yLoc = event.getY();
			if (canvas != null) {
				float difX = xLoc - xPrevLoc;
				float difY = yLoc - yPrevLoc;
				if (difX < (canvas.getWidth() / 20) && difY < (canvas.getHeight() / 20)) {
					charac.move(difX, difY, canvas);
				}
			}
			xPrevLoc = xLoc;
			yPrevLoc = yLoc;
		return true;
		}
		return false;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		wl.release();
		if (isGameOver) {
			releaseSound();
			finish();
		}
		surface.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		wl.acquire();
		if (isGameOver) {
			releaseSound();
			finish();
		}	else {
			surface.onResume();
		}
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Bundle b = new Bundle();
			b.putInt("score", score);
			isGameOver = false;
			Intent i = new Intent("com.work.now.PAUSE");
			i.putExtras(b);
			startActivityForResult(i, 0);
		}
		return false;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle response = data.getExtras();
			String act = response.getString("action").toString();
			if (act.contentEquals("restart")) {
				bgSound.release();
				System.gc();
				init();
			}	else if (act.contentEquals("menu")) {
				releaseSound();
				finish();
			}	else {
				onResume();
			}
		}
	}
	
	
	public void gameOver() {
		Intent i = new Intent("com.work.now.GAMEOVER");
		Bundle bu = new Bundle();
		isGameOver = true;
		releaseSound();
		bu.putInt("burgerCount", burgerCount);
		bu.putInt("cakeCount", cakeCount);
		bu.putInt("hotdogCount", hotdogCount);
		bu.putInt("pieCount", pieCount);
		bu.putInt("pizzaCount", pizzaCount);
		bu.putInt("drumstickCount", drumstickCount);
		bu.putInt("totalScore", (int) (score + (cycles / 1000)));
		i.putExtras(bu);
		startActivity(i);
	}
	
	public void releaseSound() {
		eatSound.release();
		bgSound.release();
	}
	
	public class FlySurface extends SurfaceView implements Runnable{

		private boolean isRunning;
		private SurfaceHolder holder;
		
		public FlySurface(Context context) {
			super(context);
			isRunning = false;
		}
		
		public void onPause() {
			isRunning = false;
//			while(true) {
//				try {
//					myThread.join();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				break;
//			}
			if(isSoundOn) {
				bgSound.pause();
			}
			myThread = null;
		}
		
		public void onResume() {
			isRunning = true;
			myThread = new Thread(this);
			myThread.start();
		}

		public void run() {
			int ctr = 0;
			holder = getHolder();
			while(isRunning) {
				if (!holder.getSurface().isValid()) {
					continue;
				}
				canvas = holder.lockCanvas();
				if (foodSpeed == 0) {
					foodSpeed = canvas.getWidth() / 100;
				}
				ctr++;
				cycles++;
				if(ctr >= 1000) {
					System.gc();
					ctr = 0;
					if (level < 30) {
						level++;
					}
					if (cycles < 20000) {
						foodSpeed += canvas.getWidth() / 500;
					}
					if (foodProductionRate < 120) {
						foodProductionRate++;
					}
					if (wolfProductionRate > 60) {
						wolfProductionRate--;
					}
					
					foodType++;
					addPowerUp(canvas);
				}
				
				onDraw(canvas);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				holder.unlockCanvasAndPost(canvas);
			}
		}
		
		public void onDraw(Canvas canvas) {
			List<Food> removableFood = new ArrayList<Food>();
			List<Obstacle> removableObstacles = new ArrayList<Obstacle>();
			drawBackground(canvas);
			Paint paint = new Paint();
			paint.setTextAlign(Align.CENTER);
			paint.setTextSize(canvas.getHeight() / 10);
			paint.setTypeface(Typeface.SERIF);
			drawClouds(canvas);
			canvas.drawText(score + "", canvas.getWidth() / 2, 40, paint);
			
			charac.draw(canvas);
			
			if (powUp != null) {
				powUp.move(foodSpeed + 4);
				powUp.draw(canvas);
				if (powUp.isHit(charac.getX(), charac.getY(), charac.getWidth(), charac.getHeight())) {
					switch(powUp.getType()) {
					case 0:
						charac.setInvul(true);
						hits = 2;
						break;
					case 1:
						charac.setAbsurb(true);	
						powEnd = cycles + 300;
						break;
					case 2:
						prevFoodProductionRate = foodProductionRate;
						foodProductionRate = 10;
						powEnd = cycles + 400;
						break;
					}
					powUp = null;
				} else if (!powUp.validate()) {
					powUp = null;
				}
			}
			
			if (prevFoodProductionRate != 0) {
				if (powEnd <= cycles) {
					foodProductionRate = prevFoodProductionRate;
					prevFoodProductionRate = 0;
				}
			}
			
			if (rand.nextInt(foodProductionRate) == 5) {
				addFood(canvas);
			}
			
			if (rand.nextInt(300) == 5) {
				addCloud(canvas);
			}
			
			if (rand.nextInt(wolfProductionRate) == 5) {
				addObstacle(canvas);
			}
			
			for (int i = 0; i < food.size(); i++) {
				Food fo = food.get(i);
				fo.draw(canvas);
				if (fo.isHit(charac.getX(), charac.getY(), charac.getWidth(), charac.getHeight())) {
					if(isSoundOn) {
						eatSound.start();
					}
					switch(fo.getScore()) {
					case 6:
						score+= 6;
						drumstickCount++;
					case 5:
						score+=5;
						pizzaCount++;
					case 4:
						score+=4;
						pieCount++;
					case 3:
						score+=3;
						hotdogCount++;
					case 2:
						score+=2;
						cakeCount++;
						break;
					default:
						score += 1;
						burgerCount++;
						break;
					}
					charac.increaseSize();
					removableFood.add(fo);
				}	else if (!fo.validate(canvas)) {
					removableFood.add(fo);
				}
				if (!charac.canAbsurb()) {
					fo.move(foodSpeed, 0);
				}	else {
					if ((fo.getX() - (canvas.getWidth() / 4) < charac.getX()) && (fo.getX() + (canvas.getWidth() / 4) > charac.getX())) {
						fo.absurb();
					}
					if (powEnd > cycles) {
						if (fo.isAbsurbed()) {
							fo.moveTo(charac.getX() + (charac.getWidth() / 2), charac.getY() + (charac.getHeight() / 2));
						}	else {
							fo.move(foodSpeed, 0);
						}
					}	else {
						charac.setAbsurb(false);
					}
					
				}
			}
			
			for (int i = 0; i < obstacles.size(); i++) {
				Obstacle obj = obstacles.get(i);
				obj.draw(canvas);
				if (obj.isHit(charac.getX(), charac.getY(), charac.getWidth(), charac.getHeight())) {
					if (!charac.isInvul()) {
						gameOver();
					}	else {
						hits--;
						if (hits == 0) {
							charac.setInvul(false);
						}
						removableObstacles.add(obj);
					}
				}	else if (!obj.validate(canvas)) {
					removableObstacles.add(obj);
				}
				obj.move(foodSpeed + 3, canvas);
			}
			
			removeFoods(removableFood);
			removeObstacles(removableObstacles);
		}
		
		public void drawClouds(Canvas canvas) {
			List<Cloud> toRemoveCloud = new ArrayList<Cloud>();
			for(int i = 0; i < clouds.size(); i++) {
				Cloud c = clouds.get(i);
				c.draw(canvas);
				c.move(foodSpeed / 2);
				if (!c.validate()) {
					toRemoveCloud.add(c);
				}
			}
			for(int i = 0; i < toRemoveCloud.size(); i++) {
				clouds.remove(toRemoveCloud.get(i));
			}
		}
		
		public void addCloud(Canvas canvas) {
			Bitmap pic;
			switch(rand.nextInt(2)) {
			case 1:
				pic = BitmapFactory.decodeResource(res, R.drawable.cloud1);
				break;
			default:
				pic = BitmapFactory.decodeResource(res, R.drawable.cloud2);
				break;
			}
			clouds.add(new Cloud(canvas, 10, pic));
		}
		
		public void removeFoods(List<Food> fo) {
			for(int i = 0; i < fo.size(); i++) {
				food.remove(fo.get(i));
			}
		}
		
		public void removeObstacles(List<Obstacle> obs) {
			for(int i = 0; i < obs.size(); i++) {
				obstacles.remove(obs.get(i));
			}
		}
		
		public void addFood(Canvas canvas) {
			Bitmap pic;
			int score;
			switch(rand.nextInt(foodType)) {
			case 2:
				pic = cake;
				score = 2;
				break;
			case 3:
				pic = hotdog;
				score= 3;
				break;
			case 4:
				pic = pie;
				score = 4;
				break;
			case 5:
				pic = pizza;
				score = 5;
				break;
			case 6:
				pic = drumstick; 
				score = 6;
				break;
			default:
				pic = burger;
				score = 1;
				break;
			}
			Food f= new Donut(canvas, generateYLocation(), pic, score);
			food.add(f);
		}
		
		public void addPowerUp(Canvas canvas) {
			switch (rand.nextInt(3)) {
			case 1:
				pow = BitmapFactory.decodeResource(res, R.drawable.icon2);
				powUp = new PowerUp(canvas.getWidth(), generateYLocation(), 1, pow, canvas);
				break;
			case 2:
				pow = BitmapFactory.decodeResource(res, R.drawable.icon1);
				powUp = new PowerUp(canvas.getWidth(), generateYLocation(), 2, pow, canvas);
				break;
			default:
				pow = BitmapFactory.decodeResource(res, R.drawable.invul);
				powUp = new PowerUp(canvas.getWidth(), generateYLocation(), 0, pow, canvas);
				break;
			}
		}
		
		public float generateYLocation() {
			float yLocation;
			int grid = canvas.getHeight() / burger.getHeight();
			while(true) {
				yLocation = (rand.nextInt(grid) * burger.getHeight());
				if (isFree(yLocation)) {
					break;
				}
			}
			return yLocation;
		}
		
		public void addObstacle(Canvas canvas) {
			float dy = 0;
			if (cycles > 1000) {
				dy = rand.nextInt((int)foodSpeed / 2);
				switch(rand.nextInt(2)) {
				case 1:
					dy *= -1;
				}
			}
			Obstacle obs= new Obstacle(canvas, generateYLocation(), wolf, dy);
			obstacles.add(obs);
		}
		
		public boolean isFree(float yLocation) {
			Food f;
			boolean toReturn = true;
			for(int i = 0; i < food.size(); i++) {
				 f = food.get(i);
				if(f.isHit(canvas.getWidth() + f.getWidth(), yLocation, f.getWidth(), f.getHeight())) {
					toReturn = false;
					break;
				}
			}
			return toReturn;
		}
		
		public void drawBackground(Canvas canvas) {
			if (background.isEmpty()) {
				background.add(new Background(0, canvas.getWidth() + 400, canvas.getHeight(), bg));
			}
			List<Background> removables = new ArrayList<Background>();
			Background b;
			for(int i = 0; i < background.size(); i++) {
				b = background.get(i);
				if(background.size() < 3) {
					background.add(new Background(b.getFx(), b.getFx() + canvas.getWidth() + 400, canvas.getHeight(), bg));
				}
				b.draw(canvas);
				b.move(foodSpeed);
				if(!b.validate()) {
					removables.add(b);
				}
			}
			for (int i = 0; i < removables.size(); i++) {
				background.remove(removables.get(i));
			}
		}
	}

}
