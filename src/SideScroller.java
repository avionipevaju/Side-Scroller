import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.Synthesizer;

import rafgfxlib.GameFrame;
import rafgfxlib.Util;

public class SideScroller extends GameFrame {

	private static final long serialVersionUID = 1L;
	private static final int PLAYER_SPEED = 5;
	private static final double GRAVITY = 7.0;
	private static final int JUMP_FACTOR = -20;
	private static final int JUMP_LIMIT=12;
//	private static final int ANIM_DOWN = 0;
	private static final int ANIM_LEFT = 1;
	private static final int ANIM_UP = 2;
	private static final int ANIM_RIGHT = 3;
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 600;
	
	private int mJumpDuration;
	
	private final Set<Integer> mPressedKeys;
	private SpriteSheet mSpriteSheet;
	private Sprite mSprite;
	private AffineTransform mTransform;
	private Background mBackground;
	private boolean mMiddle = false;
	
	private Rectangle rect=new Rectangle(0, getWidth()-40, 100, 40);


	public SideScroller(String title, int sizeX, int sizeY) {
		super(title, sizeX, sizeY);
		setUpdateRate(60);
		mBackground = new Background();
		mTransform = new AffineTransform();
		mSpriteSheet=new SpriteSheet("sheet.png", 10, 4);
		mSprite = new Sprite(mSpriteSheet,0,getHeight()-mSpriteSheet.getFrameHeight());
		mPressedKeys = new HashSet<Integer>();
		startThread();
	}

	@Override
	public void handleWindowInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleWindowDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		g.setBackground(Color.white);
		g.clearRect(0, 0, sw, sh);
		mBackground.draw(g);
		mSprite.draw(g);
		g.setColor(Color.orange);
		g.draw(rect);
		//g.fillRect(400, getHeight()-30, 100, 30);

	}

	@Override
	public void update() {
		
		//if(rect.intersects())

		if (mSprite.getY() < getHeight() - mSprite.getSpriteSheet().getFrameHeight())
			mSprite.setY(mSprite.getY() + GRAVITY);

		if (mPressedKeys.contains(KeyEvent.VK_RIGHT) && mPressedKeys.contains(KeyEvent.VK_SPACE)
				&& mJumpDuration<=JUMP_LIMIT){
			mSprite.move(PLAYER_SPEED, JUMP_FACTOR);
			mJumpDuration++;
		}
		
		else if (mPressedKeys.contains(KeyEvent.VK_LEFT) && mPressedKeys.contains(KeyEvent.VK_SPACE)
				&& mJumpDuration<=JUMP_LIMIT){
			mSprite.move(-PLAYER_SPEED, JUMP_FACTOR);
			mJumpDuration++;
		}
		
		else if (mPressedKeys.contains(KeyEvent.VK_SPACE) && mJumpDuration<=JUMP_LIMIT){
			mSprite.move(0, JUMP_FACTOR);
			mJumpDuration++;
		}
		
		else if (mPressedKeys.contains(KeyEvent.VK_RIGHT)){
			mSprite.move(PLAYER_SPEED, 0);
			mSprite.setAnimation(ANIM_RIGHT);
			mSprite.play();
			if (mSprite.getX() == SCREEN_WIDTH/2)
				mMiddle = true;
			else
				mMiddle = false;
			mBackground.update(1, mMiddle);
		}
		else if (mPressedKeys.contains(KeyEvent.VK_LEFT)){
			mSprite.move(-PLAYER_SPEED, 0);
			mSprite.setAnimation(ANIM_LEFT);
			mSprite.play();
			mBackground.update(0, mMiddle);
		}
		
		mSprite.update();
		

	}

	@Override
	public void keyPressed(KeyEvent e) {

		mPressedKeys.add(e.getKeyCode());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		mPressedKeys.remove(e.getKeyCode());
		
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
			mJumpDuration=0;
		
		mSprite.stop();
		mSprite.setFrame(5);
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
		
		System.out.println("key down");
		
		if(keyCode == KeyEvent.VK_LEFT)
		{
			mSprite.setAnimation(ANIM_LEFT);
			mSprite.play();
		}
		else if(keyCode == KeyEvent.VK_RIGHT)
		{
			mSprite.setAnimation(ANIM_RIGHT);
			mSprite.play();
		}

	}

	@Override
	public void handleKeyUp(int keyCode) {
		
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP ||
				keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT)
		{
			mSprite.stop();
			mSprite.setFrame(5);
		}

	}

}
