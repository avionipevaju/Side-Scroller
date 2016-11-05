import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public abstract class Entity {
	
//	private SpriteSheet mSpriteSheet;
//	private double mX, mY;
//	private int animationID = 0;
//	private int animFrame = 0;
//	private boolean animPlaying = false;
//	private int frameInterval = 4;
//	private int frameCountdown = 0;

	public abstract void draw(Graphics g);

	public abstract void update();

	public abstract void move(int x, int y);

	public abstract void play();

	public abstract void pause();

	public abstract void stop();

	public abstract double getX();

	public abstract void setX(double mX);

	public abstract double getY();

	public abstract void setY(double mY);

	public abstract SpriteSheet getSpriteSheet();

	public abstract int getAnimationID();

	public abstract void setAnimation(int animationID);

	public abstract int getAnimFrame();

	public abstract void setFrame(int animFrame);

	public abstract boolean isAnimPlaying();

	public abstract void setAnimPlaying(boolean animPlaying);

	public abstract int getFrameInterval();

	public abstract void setFrameInterval(int frameInterval);

	public abstract int getFrameCountdown();

	public abstract void setFrameCountdown(int frameCountdown);

	public abstract Rectangle getRect();

	public abstract Line2D getBottomLine();

	public abstract void setSpriteSheet(SpriteSheet sheet);

}
