import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class Coin extends Entity{
	
	private SpriteSheet mSpriteSheet;
	private double mX, mY;
	private int animationID = 0;
	private int animFrame = 0;
	private boolean animPlaying = false;
	private int frameInterval = 4;
	private int frameCountdown = 0;

	// private boolean isJumping=false;
	// private int jumpCount=0;

	public Coin(SpriteSheet spriteSheet, int x, int y) {

		mSpriteSheet = spriteSheet;
		mX = x;
		mY = y;

	}

	public void draw(Graphics g) {
		mSpriteSheet.drawTo(g, (int) mX, (int) mY, animFrame, animationID);
	}

	public void update() {
		if (animPlaying) {
			frameCountdown--;
			if (frameCountdown < 0) {
				animFrame = (animFrame + 1) % mSpriteSheet.getColumnCount();
				frameCountdown = frameInterval;
			}
		}
	}

	public void move(int x, int y) {
		mX += x;
		mY += y;
	}

	public void play() {
		animPlaying = true;
	}

	public void pause() {
		animPlaying = false;
	}

	public void stop() {
		animPlaying = false;
		animFrame = 0;
		frameCountdown = frameInterval;
	}

	public double getX() {
		return mX;
	}

	public void setX(double mX) {
		this.mX = mX;
	}

	public double getY() {
		return mY;
	}

	public void setY(double mY) {
		this.mY = mY;
	}

	public SpriteSheet getSpriteSheet() {
		return mSpriteSheet;
	}

	public int getAnimationID() {
		return animationID;
	}

	public void setAnimation(int animationID) {
		this.animationID = animationID;
	}

	public int getAnimFrame() {
		return animFrame;
	}

	public void setFrame(int animFrame) {
		this.animFrame = animFrame;
	}

	public boolean isAnimPlaying() {
		return animPlaying;
	}

	public void setAnimPlaying(boolean animPlaying) {
		this.animPlaying = animPlaying;
	}

	public int getFrameInterval() {
		return frameInterval;
	}

	public void setFrameInterval(int frameInterval) {
		this.frameInterval = frameInterval;
	}

	public int getFrameCountdown() {
		return frameCountdown;
	}

	public void setFrameCountdown(int frameCountdown) {
		this.frameCountdown = frameCountdown;
	}

	public Rectangle getRect() {
		return new Rectangle((int) mX, (int) mY, mSpriteSheet.getFrameWidth(), mSpriteSheet.getFrameHeight());
	}

	public Line2D getBottomLine() {
		return new Line2D.Float((float) (mX + 20), (float) (mY + mSpriteSheet.getFrameHeight()),
				(float) (mX + mSpriteSheet.getFrameWidth() - 20), (float) (mY + mSpriteSheet.getFrameHeight()));
	}

	public void setSpriteSheet(SpriteSheet sheet){
		mSpriteSheet=sheet;
		
	}

}
