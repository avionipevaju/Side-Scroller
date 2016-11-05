

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import rafgfxlib.GameState;
import rafgfxlib.Util;

public class Game extends GameState  {
	
	
	private static final long serialVersionUID = 1L;

	private static final int PLAYER_SPEED = 5;
	private static final double GRAVITY = 7.0;
	private static final int JUMP_FACTOR = -20;
	private static final int JUMP_LIMIT = 12;
	// private static final int ANIM_LEFT = 1;
	private static final int ANIM_IDLE = 0;
	private static final int ANIM_LEFT = 1;
	private static final int ANIM_RIGHT = 2;
	// private static final int ANIM_RIGHT = 3;
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 600;

	private int mDefaultBound;
	private int mJumpDuration;
	private int mLowerBound;

	private final Set<Integer> mPressedKeys;
	private SpriteSheet mSpriteSheet, mAltSheet, mCoinSheet;
	private Sprite mMainCharacter, mCoin;
	private Background mBackground;
	private boolean mMiddle = false, mBack = false;

	private ArrayList<Obstacle> mObastcles;
	private ArrayList<Sprite> mPowerups;
	

	public Game(GameHost host) {
		super(host);
		generateMainCharacter();
		generatePowerups();
		generateObstacles();
		
		mBackground = new Background();
		mPressedKeys = new HashSet<Integer>();
		mDefaultBound = host.getHeight() - mMainCharacter.getSpriteSheet().getFrameHeight();
		mLowerBound = mDefaultBound;
	}

	@Override
	public boolean handleWindowClose() {
		return true;
	}

	@Override
	public String getName() {
		return Strings.PLAY;
	}

	@Override
	public void resumeState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspendState() {
		// TODO Auto-generated method stub
		
	}

	

	public void generateObstacles() {

		mObastcles = new ArrayList<>();
		Obstacle temp;
		Random r = new Random();
		int x, y;
		BufferedImage image = Util.loadImage("block.png");

		for (int i = 0; i < 50; i++) {
			x = r.nextInt(4000);
			y = r.nextInt(600);
			temp = new Obstacle(image, x, y, 100, 30);
			mObastcles.add(temp);

		}

	}

	public void generateMainCharacter() {

		mSpriteSheet = new SpriteSheet("jerry_sheet2.png", 4, 3);
		mAltSheet = new SpriteSheet("badass_sheet.png", 4, 3);

		mMainCharacter = new Sprite(mSpriteSheet, 0, host.getHeight() - mSpriteSheet.getFrameHeight());

		mMainCharacter.setAnimation(ANIM_IDLE);

		mMainCharacter.play();
	}

	public void generatePowerups() {
		
		mPowerups=new ArrayList<>();

		mCoinSheet = new SpriteSheet("coin.png", 8, 2);
		mCoin = new Sprite(mCoinSheet, 500, host.getHeight() - mCoinSheet.getFrameHeight());
		mCoin.setAnimation(ANIM_IDLE);
		mCoin.play();
		
		mPowerups.add(mCoin);
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		g.setBackground(Color.white);
		g.clearRect(0, 0, sw, sh);

		mBackground.draw(g);

		g.setColor(Color.orange);

		for (Obstacle obstacle : mObastcles) {
			g.fill(obstacle);
			obstacle.drawTo(g, obstacle.x, obstacle.y);
		}


		mMainCharacter.draw(g);
		
		for (Sprite powerup : mPowerups) {
			powerup.draw(g);
		}

	}

	@Override
	public void update() {

		Sprite temp=null;
		
		for(Sprite powerup:mPowerups){
			if (mMainCharacter.getRect().intersects(powerup.getRect())) {
				mMainCharacter.setSpriteSheet(mAltSheet);
				temp=powerup;
				break;
			}
		}
		if(temp!=null)
			mPowerups.remove(temp);
			
		

		for (Obstacle x : mObastcles) {

			if (mMainCharacter.getBottomLine().intersects(x)) {
				mLowerBound -= x.height + host.getHeight() - x.y;
				break;
			} else {
				mLowerBound = mDefaultBound;
			}

		}

		if (mMainCharacter.getY() < mLowerBound)
			mMainCharacter.setY(mMainCharacter.getY() + GRAVITY);

		if (mPressedKeys.contains(KeyEvent.VK_RIGHT) && mPressedKeys.contains(KeyEvent.VK_SPACE)
				&& mJumpDuration <= JUMP_LIMIT) {

			if (mMiddle) {
				mMainCharacter.move(0, JUMP_FACTOR);
				mJumpDuration++;
				mBackground.update(1, mMiddle);
				for (Obstacle obstacle : mObastcles) {
					obstacle.x -= PLAYER_SPEED;
				}
				for(Sprite powerup:mPowerups){
					powerup.move(-PLAYER_SPEED, 0);
				}
			} else {
				mMainCharacter.move(PLAYER_SPEED, JUMP_FACTOR);
				mJumpDuration++;
			}
		}

		else if (mPressedKeys.contains(KeyEvent.VK_LEFT) && mPressedKeys.contains(KeyEvent.VK_SPACE)
				&& mJumpDuration <= JUMP_LIMIT) {

			if (mBack) {
				mMainCharacter.move(0, JUMP_FACTOR);
				mJumpDuration++;
				mBackground.update(0, mMiddle);
				for (Obstacle obstacle : mObastcles) {
					obstacle.x += PLAYER_SPEED;
				}
				for(Sprite powerup:mPowerups){
					powerup.move(PLAYER_SPEED, 0);
				}

			} else {
				mMainCharacter.move(-PLAYER_SPEED, JUMP_FACTOR);
				mJumpDuration++;
			}
		}

		else if (mPressedKeys.contains(KeyEvent.VK_SPACE) && mJumpDuration <= JUMP_LIMIT) {
			mMainCharacter.move(0, JUMP_FACTOR);
			mJumpDuration++;
		}

		else if (mPressedKeys.contains(KeyEvent.VK_RIGHT)) {
			if (!mMiddle) {
				mMainCharacter.move(PLAYER_SPEED, 0);
			}
			mMainCharacter.setAnimation(ANIM_RIGHT);
			mMainCharacter.play();
			if (mMainCharacter.getX() >= SCREEN_WIDTH / 2)
				mMiddle = true;
			else
				mMiddle = false;
			if (mMiddle) {
				for (Obstacle obstacle : mObastcles) {
					obstacle.x -= PLAYER_SPEED;
				}
				for(Sprite powerup:mPowerups){
					powerup.move(-PLAYER_SPEED, 0);
				}
			}
			mBackground.update(1, mMiddle);

		} else if (mPressedKeys.contains(KeyEvent.VK_LEFT)) {
			if (mMainCharacter.getX() == 0)
				return;

			if (!mBack || mBackground.getCounter() < 20) {
				mMainCharacter.move(-PLAYER_SPEED, 0);
			}
			mMainCharacter.setAnimation(ANIM_LEFT);
			mMainCharacter.play();

			if (mMainCharacter.getX() == SCREEN_WIDTH / 4)
				mBack = true;
			else
				mBack = false;

			if (mBack) {
				for (Obstacle obstacle : mObastcles) {
					obstacle.x += PLAYER_SPEED;
				}
				for(Sprite powerup:mPowerups){
					powerup.move(PLAYER_SPEED, 0);
				}
			}

			mBackground.update(0, mBack);
		}

		mMainCharacter.update();
		
		for(Sprite powerup:mPowerups){
			powerup.update();
		}

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
		
		mPressedKeys.add(keyCode);

	}

	@Override
	public void handleKeyUp(int keyCode) {

		
		mPressedKeys.remove(keyCode);

		if (keyCode == KeyEvent.VK_SPACE)
			mJumpDuration = 0;

		mMainCharacter.setAnimation(ANIM_IDLE);
		mMainCharacter.play();

	}

}
