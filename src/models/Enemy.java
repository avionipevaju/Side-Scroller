package models;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class Enemy extends Entity {
	
	private double mX, mY, mLimitF, mLimitB;
	private SpriteSheet mSpriteSheet;
	private int mAnimFrame = 0, mAnimationID = 2;
	private int mFrameInterval = 4, mFrameCountdown = 0;
	private int mSpeed = 2;
	private boolean mForward = false, mAnimPlaying = false;
	
	public Enemy(SpriteSheet spriteSheet, double x, double y, double limitF, double limitB) {
		mSpriteSheet = spriteSheet;
		mX = x;
		mY = y;
		mLimitF = limitF;
		if (limitB == 0) {
			limitB = -spriteSheet.getFrameWidth()-20;
			mForward = true;
		}
		mLimitB = limitB;
		
	}

	@Override
	public void draw(Graphics g) {
		mSpriteSheet.drawTo(g, (int) mX, (int) mY, mAnimFrame, mAnimationID);
		
	}

	@Override
	public void update() {
		if (mAnimPlaying) {
			mFrameCountdown--;
			if (mFrameCountdown < 0) {
				mAnimFrame = (mAnimFrame + 1) % mSpriteSheet.getColumnCount();
				mFrameCountdown = mFrameInterval;
			}
		}

		
	}

	public double getmLimitF() {
		return mLimitF;
	}

	public void setmLimitF(double mLimitF) {
		this.mLimitF = mLimitF;
	}

	public double getmLimitB() {
		return mLimitB;
	}

	public void setmLimitB(double mLimitB) {
		this.mLimitB = mLimitB;
	}

	@Override
	public void move(int x, int y) {
	}
	
	public void move() {
		if (mForward) {
			mX += mSpeed;
			if (mX >= mLimitF) {
				mForward = false;
				mAnimationID = 1;
			}
		}
		else {
			mX -= mSpeed;
			if (mX <= mLimitB) {
				mForward = true;
				mAnimationID = 2;
			}
		}
	}

	@Override
	public void play() {
		mAnimPlaying = true;
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getX() {
		return mX;
	}

	@Override
	public void setX(double mX) {
		this.mX = mX;
		
	}

	@Override
	public double getY() {
		return mY;
	}

	@Override
	public void setY(double mY) {
		this.mY = mY;
	}

	@Override
	public SpriteSheet getSpriteSheet() {
		return mSpriteSheet;
	}

	@Override
	public int getAnimationID() {
		return mAnimationID;
	}

	@Override
	public void setAnimation(int animationID) {
		mAnimationID = animationID;
	}

	@Override
	public int getAnimFrame() {
		return mAnimFrame;
	}

	@Override
	public void setFrame(int animFrame) {
		mAnimFrame = animFrame;
	}

	@Override
	public boolean isAnimPlaying() {
		return false;
	}

	@Override
	public void setAnimPlaying(boolean animPlaying) {
		
	}

	@Override
	public int getFrameInterval() {
		return mFrameInterval;
	}

	@Override
	public void setFrameInterval(int frameInterval) {
		mFrameInterval = frameInterval;	
	}

	@Override
	public int getFrameCountdown() {
		return mFrameCountdown;
	}

	@Override
	public void setFrameCountdown(int frameCountdown) {
		mFrameCountdown = frameCountdown;
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle((int) mX, (int) mY, mSpriteSheet.getFrameWidth(), mSpriteSheet.getFrameHeight());
	}

	@Override
	public Line2D getBottomLine() {
		return new Line2D.Float((float) (mX + 20), (float) (mY + mSpriteSheet.getFrameHeight()),
				(float) (mX + mSpriteSheet.getFrameWidth() - 20), (float) (mY + mSpriteSheet.getFrameHeight()));
	}

	@Override
	public void setSpriteSheet(SpriteSheet sheet) {
		mSpriteSheet=sheet;
	}

}
