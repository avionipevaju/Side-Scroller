

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import rafgfxlib.GameState;
import rafgfxlib.Util;

public class Leaderboard extends GameState {
	 
	private ArrayList<String> mList = new ArrayList<>();
	private Rectangle mBack, mMouse;
	private BufferedImage mBackground;

	public Leaderboard(GameHost host) {
		super(host);
		
		mMouse = new Rectangle();
		mBack = new Rectangle(660, 520, 100, 40);
		mBackground = Util.loadImage("leaderboard_bg.jpg");
		
		BufferedReader reader;
		String line = null;
		
		try {
			reader = new BufferedReader(new FileReader("./leaderboard.txt"));
			line = reader.readLine();
			while (line != null) {
				mList.add(line);
				line = reader.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}

	@Override
	public boolean handleWindowClose() {
		return true;
	}

	@Override
	public String getName() {
		return Strings.LEADERBOARD;
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
		g.clearRect(0, 0, sw, sh);
		g.drawImage(mBackground, 0, 0, Const.WIDTH, Const.HEIGHT, null);
		int centerX = host.getWidth()/2,  y = 110;
		g.setFont(Const.MENU_FONT);
		g.setColor(Color.CYAN);
		g.drawString(Strings.LEADERBOARD, centerX - 110, 60);
		
		for (String line: mList) {
			g.drawString(line, centerX - 100, y);
			y += 40;
		}
		drawButton(g, mBack, Strings.BACK, 15);
	}
	
	private void drawButton(Graphics2D g, Rectangle rect, String text, int offset) {
		if (mMouse.intersects(rect)) g.setColor(Color.RED);
		else g.setColor(Color.CYAN);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.setFont(Const.MENU_FONT);
		g.drawString(text, rect.x + offset, rect.y + 40);
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
		mMouse = new Rectangle(x, y, 1, 1);
		if (button == GFMouseButton.Left)
			if (mMouse.intersects(mBack)) host.setState(Strings.MENU);
		
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
		if (keyCode == KeyEvent.VK_ESCAPE) host.setState(Strings.MENU);
		
	}

}
