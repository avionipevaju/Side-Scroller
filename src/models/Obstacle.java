package models;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class Obstacle extends Rectangle {
	
	private BufferedImage mImage;
	
	public Obstacle(BufferedImage image,int x, int y, int width, int height) {
		super(x, y, width, height);
		mImage=image;
		
	}

	public Obstacle(int x, int y, int width, int height) {
		super(x, y, width, height);

	}
	
	public void drawTo(Graphics g, int posX, int posY) {
		if (mImage == null)
			return;

		g.drawImage(mImage, super.x, super.y, (int)(super.x + getWidth()), (int)(super.y+ getHeight()),
				0, 0, mImage.getWidth(), mImage.getHeight(), null);
	}

	public Line2D getTopLine() {
		return new Line2D.Float((float) getX(), (float) getY(), (float) (x + getWidth()), (float) (getY()));
	}

}
