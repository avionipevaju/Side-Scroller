package states;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import utils.Const;
import utils.Strings;
import rafgfxlib.GameState;
import rafgfxlib.Util;

public class Menu extends GameState {
	
	private Rectangle mPlay, mLeaderboard, mOptions, mExit, mMouse;
	private BufferedImage mLogo, mBackground, mUfo;
	private BufferedImage mMortyR, mMortyL, mMorty;
	private BufferedImage mRickR, mRickL, mRick;
	private int mMortyX = 805, mMortyY = 450;
	private int mRickX = 850, mRickY = 450;
	private int mSpeed = 15, mCounter = 5;
	private int mUfoX = 880, mUfoY = 200;
	private int mUfoSpeed = 10;
	private boolean mLeft = true;


	public Menu(GameHost host) {
		super(host);
		
		int centerX = host.getWidth()/2 - 150;
		int centerY = host.getHeight()/2 - 100;
		mMouse = new Rectangle();
		mLogo = Util.loadImage("./resource/logo.png");
		mBackground = Util.loadImage("./resource/menu.jpg");
		mMortyR = Util.loadImage("./resource/morty_right.png");
		mMortyL = Util.loadImage("./resource/morty_left.png");
		mMorty = mMortyR;
		mRickR = Util.loadImage("./resource/rick_right.png");
		mRickL = Util.loadImage("./resource/rick_left.png");
		mRick = mRickR;
		mUfo = Util.loadImage("./resource/ufo.png");
		mPlay = new Rectangle(centerX, centerY, Const.MENU_BUTTON_WIDTH, Const.MENU_BUTTON_HEIGHT);
		mLeaderboard = new Rectangle(centerX, centerY + 60, Const.MENU_BUTTON_WIDTH, Const.MENU_BUTTON_HEIGHT);
		mOptions = new Rectangle(centerX, centerY + 120, Const.MENU_BUTTON_WIDTH, Const.MENU_BUTTON_HEIGHT);
		mExit = new Rectangle(centerX, centerY + 180, Const.MENU_BUTTON_WIDTH, Const.MENU_BUTTON_HEIGHT);
	}

	@Override
	public boolean handleWindowClose() {
		return true;
	}

	@Override
	public String getName() {
		return Strings.MENU;
	}

	@Override
	public void resumeState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspendState() {
		// TODO Auto-generated method stub
		
	}
	
	private void drawButton(Graphics2D g, Rectangle rect, String text, int offset) {
		if (mMouse.intersects(rect)) g.setColor(Color.RED);
		else g.setColor(Color.CYAN);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.setFont(Const.MENU_FONT);
		g.drawString(text, rect.x + offset, rect.y + 40);
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		g.setBackground(Color.DARK_GRAY);
		g.clearRect(0, 0, sw, sh);
		g.drawImage(mBackground, 0, 0, Const.WIDTH, Const.HEIGHT, null);
		g.drawImage(mLogo, 220, 40, null);
		g.drawImage(mMorty, mMortyX, mMortyY, null);
		g.drawImage(mRick, mRickX, mRickY, null);
		g.drawImage(mUfo, mUfoX, mUfoY, null);
		drawButton(g, mPlay, Strings.PLAY, 110);
		drawButton(g, mLeaderboard, Strings.LEADERBOARD, 60);
		drawButton(g, mOptions, Strings.OPTIONS, 80);
		drawButton(g, mExit, Strings.EXIT, 110);
		
	}

	@Override
	public void update() {
		if (mCounter == 0){
			if (mLeft){
				mMorty = mMortyL;
				mRick = mRickL;
				mLeft = false;
			}
			else {
				mMorty = mMortyR;
				mRick = mRickR;
				mLeft = true;
			}
			mMortyX -= mSpeed;
			mRickX -= mSpeed;
			mUfoX -= mUfoSpeed;
			mCounter = 10;
			
		}
		mCounter--;
		
		if (mUfoX < -mUfo.getWidth()) {
			mMortyX = 805;
			mRickX = 850;
			mUfoX = 880;
		}

		
	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button) {
		mMouse = new Rectangle(x, y, 1, 1);
		if (button == GFMouseButton.Left) {
			if (mMouse.intersects(mPlay)) host.setState(Strings.PLAY);
			if (mMouse.intersects(mLeaderboard)) host.setState(Strings.LEADERBOARD);
			if (mMouse.intersects(mOptions)) host.setState(Strings.OPTIONS);
			if (mMouse.intersects(mExit)) host.exit();
			
		}
		
	}

	@Override
	public void handleMouseMove(int x, int y) {
		mMouse = new Rectangle(x, y, 1, 1);
		
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
