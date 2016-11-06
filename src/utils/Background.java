package utils;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import rafgfxlib.Util;

public class Background {
	
	private BufferedImage mBackground;
	private int mCounter = 0;
	private boolean mEnd = true;
	
	public Background() {
		mBackground = Util.loadImage(Strings.SKY_PATH);
		
	}
	
	public void draw(Graphics2D g) {
		g.setBackground(Color.GRAY);
		g.clearRect(0, 0, Const.WIDTH, Const.HEIGHT);
		
		g.drawImage(mBackground, 0, 0, 800, 600, null);

		
	}
	
	public void update(int direction, boolean middle){
		
		if (direction == 1) {
			if (!middle) return ;
			mCounter++;
			mEnd = false;
		}
		if (direction == 0) {
			if (mEnd || !middle) return ;
			mCounter--;
			
			if (mCounter == 0) {
				mEnd = true;
			}

		}
		
	}
	
	public long getCounter() {
		return mCounter;
	}
	

}
