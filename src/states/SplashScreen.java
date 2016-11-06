package states;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import utils.Const;
import utils.Strings;
import rafgfxlib.GameState;
import rafgfxlib.Util;

public class SplashScreen extends GameState {
	
	private BufferedImage mImg, mBack;
	private float alpha = 1f;
	private int counter = 2;

	public SplashScreen(GameHost host) {
		super(host);
		mImg = Util.loadImage("./resource/splash.jpg");
		mBack = Util.loadImage("./resource/menu.jpg");
	}

	@Override
	public boolean handleWindowClose() {
		return true;
	}

	@Override
	public String getName() {
		return Strings.SPLASH;
	}

	@Override
	public void resumeState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspendState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		g.setBackground(Color.DARK_GRAY);
		g.clearRect(0, 0, Const.WIDTH, Const.HEIGHT);
		g.drawImage(mBack, 0, 0, Const.WIDTH, Const.HEIGHT, null);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawImage(mImg, 0, 0, Const.WIDTH, Const.HEIGHT, null);
		
	}

	@Override
	public void update() {
		if (counter == 0) {
			 alpha += -0.03f;
			    if (alpha <= 0) {
			      alpha = 0;
			      host.setState(Strings.MENU);
			    }
			    counter = 3;
		}
		counter--;

		 
		
	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseMove(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleKeyDown(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleKeyUp(int keyCode) {
		// TODO Auto-generated method stub
		
	}

}
