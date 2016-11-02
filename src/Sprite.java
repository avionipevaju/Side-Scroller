import java.awt.Graphics;

public class Sprite {

	private SpriteSheet mSpriteSheet;
	private double mX, mY;
	private int animationID = 3;
	private int animFrame = 0;
	private boolean animPlaying = false;
	private int frameInterval = 2;
	private int frameCountdown = 0;
	
//	private boolean isJumping=false;
//	private int jumpCount=0;

	public Sprite(SpriteSheet spriteSheet, int x, int y) {

		mSpriteSheet = spriteSheet;
		mX = x;
		mY = y;

	}

	public void draw(Graphics g) {
		mSpriteSheet.drawTo(g, (int) mX, (int) mY, animFrame, animationID);
	}
	
	public void update()
	{
		if(animPlaying)
		{
			frameCountdown--;
			if(frameCountdown < 0)
			{
				animFrame = (animFrame + 1) % mSpriteSheet.getColumnCount();
				frameCountdown = frameInterval;
			}
		}
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

	public void move(int x, int y) {
		mX += x;
		mY += y;
	}
	
	public void play() { animPlaying = true; }
	public void pause() { animPlaying = false; }
	public void stop() { animPlaying = false; animFrame = 0; frameCountdown = frameInterval; }

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

//	public void setFrameCountdown(int frameCountdown) {
//		this.frameCountdown = frameCountdown;
//	}
//	
//	public Rectangle getRect(){
//		return new Rectangle(mX,mY,)
//	}

//	public boolean isJumping() {
//		return isJumping;
//	}
//
//	public void setJumping(boolean isJumping) {
//		this.isJumping = isJumping;
//	}
//	
	

}
