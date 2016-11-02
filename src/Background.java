import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import rafgfxlib.Util;

public class Background {
	
	private static final int IMAGE_WIDTH = 800;
	private static final int IMAGE_HEIGHT = 600;
	private static final String SUNSET_PATH = "city_sunset.png";
	private static final String NIGHT_PATH = "city_night.png";
	
	private BufferedImage mSunset, mNight;
	
	private int mSunsetX = 0, mSunsetY = 0;
	private int mNightX = 800, mNightY = 0;
	private long mCounter = 0;
	private boolean mEnd = true;
	
	public Background() {
		mSunset = Util.loadImage(SUNSET_PATH);
		mNight = Util.loadImage(NIGHT_PATH);
		
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(mSunset, mSunsetX, mSunsetY, IMAGE_WIDTH, IMAGE_HEIGHT, null);
		g.drawImage(mNight, mNightX, mNightY, IMAGE_WIDTH, IMAGE_HEIGHT, null);
		
	}
	
	public void update(int direction, boolean middle){
		if (direction == 1) {
			if (!middle) return ;
			mSunsetX -= 5;
			mNightX -= 5;
			mCounter++;
			mEnd = false;
			
			if (mSunsetX == -IMAGE_WIDTH) mSunsetX = IMAGE_WIDTH;
			if (mNightX == -IMAGE_WIDTH) mNightX = IMAGE_WIDTH;
		}
		if (direction == 0) {
			if (mEnd) return ;
			mSunsetX += 5;
			mNightX += 5;
			mCounter--;
			
			if (mSunsetX == IMAGE_WIDTH) mSunsetX = -IMAGE_WIDTH;
			if (mNightX == IMAGE_WIDTH) mNightX = -IMAGE_WIDTH;
			if (mCounter == 0) {
				mEnd = true;
				mNightX = IMAGE_WIDTH;
			}

		}
	}

	public long getCounter() {
		return mCounter;
	}

	

}
