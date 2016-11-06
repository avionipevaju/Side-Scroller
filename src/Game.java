
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import rafgfxlib.GameHost;
import rafgfxlib.GameHost.GFMouseButton;
import rafgfxlib.GameState;
import rafgfxlib.Util;

public class Game extends GameState {

	private static final long serialVersionUID = 1L;

	private static final int PLAYER_SPEED = 5;
	private static final double GRAVITY = 7.0;
	private static final int JUMP_FACTOR = -20;
	private static final int JUMP_LIMIT = 12;
	private static final int ANIM_IDLE = 0;
	private static final int ANIM_LEFT = 1;
	private static final int ANIM_RIGHT = 2;
	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 600;

	private int mDefaultBound;
	private int mJumpDuration;
	private int mLowerBound;
	private int mCoinCount;
	private int mHealthCount;

	private final Set<Integer> mPressedKeys;
	private SpriteSheet mSpriteSheet, mAltSheet, mCoinSheet,mHealthSheet;
	private Sprite mMainCharacter;
	private Background mBackground;
	private boolean mMiddle = false, mBack = false;

	private ArrayList<Obstacle> mObastcles;
	private ArrayList<Entity> mItems;

	public Game(GameHost host) {
		super(host);
		generateMainCharacter();
		generateItems();
		generateObstacles();

		mBackground = new Background();
		mPressedKeys = new HashSet<Integer>();
		mDefaultBound = SCREEN_HEIGHT - mMainCharacter.getSpriteSheet().getFrameHeight();
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
		BufferedImage image = Util.loadImage("space_block.png");

		BufferedReader reader;
		String line = null;
		
		try {
			reader = new BufferedReader(new FileReader("./level1.txt"));
			while ((line = reader.readLine()) != null) {
				x = Integer.valueOf(line);
				line = reader.readLine();
				if(line==null)
					break;
				y = Integer.valueOf(line);
				temp = new Obstacle(image, x, y, 100, 30);
				mObastcles.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		

//		for (int i = 0; i < 50; i++) {
//			x = r.nextInt(4000);
//			y = r.nextInt(600);
//			temp = new Obstacle(image, x, y, 100, 30);
//			mObastcles.add(temp);
//
//		}

	}

	public void generateMainCharacter() {

		mCoinCount=0;
		mHealthCount=100;
		mSpriteSheet = new SpriteSheet("jerry_sheet2.png", 4, 3);
		mAltSheet = new SpriteSheet("badass_sheet.png", 4, 3);

		mMainCharacter = new Sprite(mSpriteSheet, 0, SCREEN_HEIGHT - mSpriteSheet.getFrameHeight());

		mMainCharacter.setAnimation(ANIM_IDLE);

		mMainCharacter.play();
	}

	public void generateItems() {

		Coin temp;
		Powerup temp1;
		Random r = new Random();
		int x, y;
		mItems = new ArrayList<>();
		mCoinSheet = new SpriteSheet("coins.png", 8, 1);
		mHealthSheet=new SpriteSheet("health.png", 12, 1);
		
		BufferedReader reader;
		String line = null;
		
		try {
			reader = new BufferedReader(new FileReader("./level1coins.txt"));
			while ((line = reader.readLine()) != null) {
				x = Integer.valueOf(line);
				line = reader.readLine();
				if(line==null)
					break;
				y = Integer.valueOf(line);
				temp = new Coin(mCoinSheet, x, y);
				temp.setAnimation(ANIM_IDLE);
				temp.play();
				mItems.add(temp);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		

		for(int i=0;i<10;i++){
			x = r.nextInt(4000);
			y = r.nextInt(600);
			temp1 = new Powerup(mHealthSheet, x, y);
			temp1.setAnimation(ANIM_IDLE);
			temp1.play();
			mItems.add(temp1);
		}

//		for (int i = 0; i < 50; i++) {
//			x = r.nextInt(4000);
//			y = r.nextInt(600);
//			temp = new Coin(mCoinSheet, x, y);
//			temp.setAnimation(ANIM_IDLE);
//			temp.play();
//			mItems.add(temp);
//
//		}

		// mCoin = new Powerup(mCoinSheet, 500, SCREEN_HEIGHT -
		// mCoinSheet.getFrameHeight());
		// mCoin.setAnimation(ANIM_IDLE);
		// mCoin.play();

		// mItems.add(mCoin);
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		g.setBackground(Color.white);
		g.clearRect(0, 0, sw, sh);

		mBackground.draw(g);
		
		g.setFont(Const.IN_GAME_FONT);
		g.setColor(Color.white);
		g.drawString("Score: "+mCoinCount, 0, 30);

		g.setColor(Color.orange);

		for (Obstacle obstacle : mObastcles) {
			g.fill(obstacle);
			obstacle.drawTo(g, obstacle.x, obstacle.y);
		}

		mMainCharacter.draw(g);

		for (Entity powerup : mItems) {
			powerup.draw(g);
		}

	}

	@Override
	public void update() {

		Entity temp = null;

		for (Entity item : mItems) {

			if (mMainCharacter.getRect().intersects(item.getRect())) {
				if (item instanceof Coin) {
					temp = item;
					mCoinCount+=100;
					break;
				}
				if (item instanceof Powerup) {
					mMainCharacter.setSpriteSheet(mAltSheet);
					temp = item;
					break;
				}
			}
		}
		if (temp != null)
			mItems.remove(temp);

		for (Obstacle x : mObastcles) {

			if (mMainCharacter.getBottomLine().intersects(x)) {
				mLowerBound -= x.height + SCREEN_HEIGHT - x.y;
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
				for (Entity powerup : mItems) {
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
				for (Entity powerup : mItems) {
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
				for (Entity powerup : mItems) {
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
				for (Entity powerup : mItems) {
					powerup.move(PLAYER_SPEED, 0);
				}
			}

			mBackground.update(0, mBack);
		}

		mMainCharacter.update();

		for (Entity powerup : mItems) {
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
