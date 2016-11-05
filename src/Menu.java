

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import rafgfxlib.GameState;
import rafgfxlib.Util;

public class Menu extends GameState {
	
	private Rectangle mPlay, mLeaderboard, mExit;
	private BufferedImage mLogo;

	public Menu(GameHost host) {
		super(host);
		
		int centerX = host.getWidth()/2 - 150;
		int centerY = host.getHeight()/2 - 100;
		mLogo = Util.loadImage("logo.png");
		mPlay = new Rectangle(centerX, centerY, Const.MENU_BUTTON_WIDTH, Const.MENU_BUTTON_HEIGHT);
		mLeaderboard = new Rectangle(centerX, centerY + 60, Const.MENU_BUTTON_WIDTH, Const.MENU_BUTTON_HEIGHT);
		mExit = new Rectangle(centerX, centerY + 120, Const.MENU_BUTTON_WIDTH, Const.MENU_BUTTON_HEIGHT);
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
		g.setColor(Color.cyan);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.setFont(Const.MENU_FONT);
		g.drawString(text, rect.x + offset, rect.y + 40);
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		g.setBackground(Color.DARK_GRAY);
		g.clearRect(0, 0, sw, sh);
		g.drawImage(mLogo, 220, 40, null);
		drawButton(g, mPlay, Strings.PLAY, 110);
		drawButton(g, mLeaderboard, Strings.LEADERBOARD, 60);
		drawButton(g, mExit, Strings.EXIT, 110);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseDown(int x, int y, GFMouseButton button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleMouseUp(int x, int y, GFMouseButton button) {
		Rectangle rect = new Rectangle(x, y, 1, 1);
		if (button == GFMouseButton.Left) {
			if (rect.intersects(mPlay)) host.setState(Strings.PLAY);
			if (rect.intersects(mLeaderboard)) System.out.println("Show leaderboard!");
			if (rect.intersects(mExit)) host.exit();
			
		}
		
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
