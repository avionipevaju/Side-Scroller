import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import rafgfxlib.Util;

public class MovingMap {
	
	private static final int TILE_W = 64;
	private static final int TILE_H = 64;
	
	private int mapW = 100;
	private int mapH = 100;
	
	private int camX = 0;
	private int camY = 0;
	
	private int[][] tileMap = new int[mapW][mapH];
	
	private BufferedImage img = Util.loadImage("tile.png");
	private Tile[] tileset = new Tile[32];
	
	private class Tile
	{
		public BufferedImage image = null;
		public int offsetX = 0;
		public int offsetY = 0;
		@SuppressWarnings("unused")
		public int tileID = 0;
		
		public Tile(String fileName, int ID)
		{
			image = Util.loadImage(fileName);
			tileID = ID;
			if(image != null)
			{
				offsetX = 0;
				offsetY = -(image.getHeight() - TILE_H);
			}
			else
			{
				System.out.println("Fail at \"" + fileName + "\"");
			}
		}
	}


	
	public MovingMap() {
		
		for(int i = 0; i <= 16; ++i)
		{
			tileset[i] = new Tile("tile.png", i);
		}
		
		Random rnd = new Random();
		for(int y = 0; y < mapH; ++y)
		{
			for(int x = 0; x < mapW; ++x)
			{
				tileMap[x][y] = Math.abs(rnd.nextInt()) % 5;
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		int x0 = camX / TILE_W;
		int x1 = x0 + (800 / TILE_W) + 1;
		int y0 = camY / TILE_H;
		int y1 = y0 + (600 / TILE_H) + 1;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 < 0) x1 = 0;
		if(y1 < 0) y1 = 0;
		
		if(x0 >= mapW) x0 = mapW - 1;
		if(y0 >= mapH) y0 = mapH - 1;
		if(x1 >= mapW) x1 = mapW - 1;
		if(y1 >= mapH) y1 = mapH - 1;
		
		for(int y = y0; y <= y1; ++y)
		{
			for(int x = x0; x <= x1; ++x)
			{
				
				if (tileMap[x][y] == 1)
					g.drawImage(img, 
						x * TILE_W + tileset[tileMap[x][y]].offsetX - camX, 
						y * TILE_H + tileset[tileMap[x][y]].offsetY - camY, 
						null);
				

			}
		}
	}
	
	public void update(int direction) {
		if (direction == 1) camX += 5;
		if (direction == 0) camX -= 5;
	}


}
