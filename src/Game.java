
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
	private int mPowerupDuration = 600;

	private final Set<Integer> mPressedKeys;
	private SpriteSheet mSpriteSheet, mAltSheet, mCoinSheet, mHealthSheet, mHurtSheet, mBuffSheet, mEndSheet;
	private Sprite mMainCharacter;
	private Background mBackground;
	private boolean mMiddle = false, mBack = false, mSuspend = false;
	private BufferedImage mHealthMeter;

	private ArrayList<Obstacle> mObastcles;
	private ArrayList<Entity> mItems;
	private ArrayList<Enemy> mEnemies;

	public Game(GameHost host) {
		super(host);
		generateMainCharacter();
		generateItems();
		generateObstacles();
		generateEnemies();

		mBackground = new Background();
		mPressedKeys = new HashSet<Integer>();
		mDefaultBound = (int) mMainCharacter.getY();
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
		mSuspend = false;

	}

	@Override
	public void suspendState() {
		mSuspend = true;

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
				if (line == null)
					break;
				y = Integer.valueOf(line);
				temp = new Obstacle(image, x, y, 100, 30);
				mObastcles.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generateMainCharacter() {

		mCoinCount = 0;
		mHealthCount = 100;
		mSpriteSheet = new SpriteSheet("jerry_sheet2.png", 4, 3);
		mAltSheet = new SpriteSheet("big_badass.png", 4, 3);
		mHurtSheet = new SpriteSheet("hurt_jerry.png", 4, 3);

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
		// mHealthSheet = new SpriteSheet("strong.png", 15, 1);
		mHealthSheet = new SpriteSheet("health.png", 12, 1);
		mBuffSheet = new SpriteSheet("buff.png", 8, 1);
		mEndSheet = new SpriteSheet("door.png", 3, 1);

		BufferedReader reader;
		String line = null;

		try {
			reader = new BufferedReader(new FileReader("./level1coins.txt"));
			while ((line = reader.readLine()) != null) {
				x = Integer.valueOf(line);
				line = reader.readLine();
				if (line == null)
					break;
				y = Integer.valueOf(line);
				temp = new Coin(mCoinSheet, x, y);
				temp.setAnimation(ANIM_IDLE);
				temp.play();
				mItems.add(temp);

			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		line = null;
		try {
			reader = new BufferedReader(new FileReader("./level1buff.txt"));
			while ((line = reader.readLine()) != null) {
				x = Integer.valueOf(line);
				line = reader.readLine();
				if (line == null)
					break;
				y = Integer.valueOf(line);
				// temp1 = new Powerup(mHealthSheet, x, y,PowerupStyle.HEALTH);
				temp1 = new Powerup(mBuffSheet, x, y, PowerupStyle.BUFF);
				temp1.setAnimation(ANIM_IDLE);
				temp1.play();
				mItems.add(temp1);
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		line = null;
		try {
			reader = new BufferedReader(new FileReader("./level1helath.txt"));
			while ((line = reader.readLine()) != null) {
				x = Integer.valueOf(line);
				line = reader.readLine();
				if (line == null)
					break;
				y = Integer.valueOf(line);
				temp1 = new Powerup(mHealthSheet, x, y, PowerupStyle.HEALTH);
				temp1.setAnimation(ANIM_IDLE);
				temp1.play();
				mItems.add(temp1);
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		Powerup end = new Powerup(mEndSheet, 4370, 85, PowerupStyle.END);
		end.setAnimation(ANIM_IDLE);
		end.play();
		mItems.add(end);

	}

	public void generateEnemies() {
		mEnemies = new ArrayList<>();
		String[] temp;
		BufferedReader reader;
		String line = null;
		String img = null;

		try {
			reader = new BufferedReader(new FileReader("./enemy_positions.txt"));
			while ((line = reader.readLine()) != null) {
				temp = line.split("-");
				int x = Integer.valueOf(temp[0]);
				int y = Integer.valueOf(temp[1]);
				int limitF = Integer.valueOf(temp[2]);
				int limitB = Integer.valueOf(temp[3]);
				img = choseEnemy();
				SpriteSheet enemySheet = new SpriteSheet(img, 4, 3);
				Enemy enemy = new Enemy(enemySheet, x, y - enemySheet.getFrameHeight(),
						limitF - enemySheet.getFrameWidth(), limitB);
				enemy.play();
				mEnemies.add(enemy);
				mItems.add(enemy);
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String choseEnemy() {
		Random rnd = new Random();
		String temp = null;
		switch (rnd.nextInt(3)) {
		case 0:
			temp = "enemy.png";
			break;
		case 1:
			temp = "flargo.png";
			break;
		case 2:
			temp = "bluu.png";
			break;
		default:
			break;
		}
		return temp;
	}

	@Override
	public void render(Graphics2D g, int sw, int sh) {
		g.setBackground(Color.white);
		g.clearRect(0, 0, sw, sh);

		mBackground.draw(g);

		g.drawImage(mHealthMeter, 650, -20, 650 + mHealthMeter.getWidth(), -20 + mHealthMeter.getHeight(), 0, 0,
				mHealthMeter.getWidth(), mHealthMeter.getHeight(), null);
		g.setFont(Const.IN_GAME_FONT);
		g.setColor(Color.white);
		g.drawString("Score: " + mCoinCount, 0, 30);

		g.setColor(Color.orange);

		for (Obstacle obstacle : mObastcles) {
			g.fill(obstacle);
			obstacle.drawTo(g, obstacle.x, obstacle.y);
		}

		mMainCharacter.draw(g);
		for (Enemy enemy : mEnemies) {
			enemy.draw(g);
		}

		for (Entity powerup : mItems) {
			powerup.draw(g);
		}

	}

	@Override
	public void update() {

		if (mSuspend)
			return;

		if (mMainCharacter.getY() == mDefaultBound) {
			mMainCharacter.setJumpCount(0);
			mMainCharacter.setJumping(false);
		}

		Entity temp = null;

		switch (mHealthCount) {
		case 100: {
			mHealthMeter = Util.loadImage("health_meter_full.png");
			if (mMainCharacter.isPoweredUp())
				mMainCharacter.setSpriteSheet(mAltSheet);
			else
				mMainCharacter.setSpriteSheet(mSpriteSheet);
			break;
		}
		case 50: {
			mHealthMeter = Util.loadImage("health_meter_half.png");
			if (mMainCharacter.isPoweredUp())
				mMainCharacter.setSpriteSheet(mAltSheet);
			else
				mMainCharacter.setSpriteSheet(mHurtSheet);
			break;
		}
		case 0: {
			mHealthMeter = Util.loadImage("health_meter.png");
			// System.out.println("died");
			mMainCharacter.setSpriteSheet(mHurtSheet);
			gameEnd();
			break;
		}
		}

		for (Entity item : mItems) {

			if (mMainCharacter.getRect().intersects(item.getRect())) {
				if (item instanceof Coin) {
					temp = item;
					mCoinCount += 100;
					break;
				}
				if (item instanceof Powerup) {
					if (((Powerup) item).getStyle() == PowerupStyle.HEALTH) {
						temp = item;
						mHealthCount = 100;
						break;
					}
					if (((Powerup) item).getStyle() == PowerupStyle.BUFF) {
						temp = item;
						mMainCharacter.setSpriteSheet(mAltSheet);
						mMainCharacter.setPoweredUp(true);
						mPowerupDuration = 600;
						break;
					}
					if (((Powerup) item).getStyle() == PowerupStyle.END) {
						gameEnd();
						break;
					}
				}
				if (item instanceof Enemy) {
					if (mMainCharacter.isPoweredUp()) {
						mEnemies.remove(item);
						temp = item;
						break;
					}
					mEnemies.remove(item);
					mHealthCount -= 50;
					temp = item;
					break;
				}
			}
		}
		if (temp != null)
			mItems.remove(temp);

		if (mMainCharacter.isPoweredUp()) {
			mPowerupDuration--;
		}
		if (mPowerupDuration == 0) {
			mMainCharacter.setPoweredUp(false);
			mMainCharacter.setSpriteSheet(mSpriteSheet);
			mPowerupDuration = 1;
		}

		for (Obstacle x : mObastcles) {

			if (mMainCharacter.getBottomLine().intersects(x)) {
				mLowerBound -= x.height + SCREEN_HEIGHT - x.y;
				mMainCharacter.setJumping(false);
				mMainCharacter.setJumpCount(0);
				break;
			} else {
				mLowerBound = mDefaultBound;
			}

		}

		if (mMainCharacter.getY() < mLowerBound) {
			mMainCharacter.setY(mMainCharacter.getY() + GRAVITY);
			if (mMainCharacter.getY() + GRAVITY > mDefaultBound) {
				mMainCharacter.setY(mDefaultBound);
			}
		}

		if (mPressedKeys.contains(KeyEvent.VK_RIGHT) && mPressedKeys.contains(KeyEvent.VK_SPACE)
				&& mJumpDuration <= JUMP_LIMIT) {

			if (mMainCharacter.isJumping() && mMainCharacter.getJumpCount() >= 2) {
				updateAllEntites();
				return;
			}

			if (mBackground.getCounter() > 800) {
				updateAllEntites();
				return;
			}

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
				for (Enemy enemy : mEnemies) {
					double x = enemy.getX() - PLAYER_SPEED;
					enemy.setX(x);
					double bx = enemy.getmLimitB() - PLAYER_SPEED;
					enemy.setmLimitB(bx);
					double fx = enemy.getmLimitF() - PLAYER_SPEED;
					enemy.setmLimitF(fx);
				}

			} else {
				mMainCharacter.move(PLAYER_SPEED, JUMP_FACTOR);
				mJumpDuration++;
			}
		}

		else if (mPressedKeys.contains(KeyEvent.VK_LEFT) && mPressedKeys.contains(KeyEvent.VK_SPACE)
				&& mJumpDuration <= JUMP_LIMIT) {

			if (mMainCharacter.isJumping() && mMainCharacter.getJumpCount() >= 2) {
				updateAllEntites();
				return;
			}

			if (mMainCharacter.getX() == 0) {
				updateAllEntites();
				return;
			}

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
				for (Enemy enemy : mEnemies) {
					double x = enemy.getX() + PLAYER_SPEED;
					enemy.setX(x);
					double bx = enemy.getmLimitB() + PLAYER_SPEED;
					enemy.setmLimitB(bx);
					double fx = enemy.getmLimitF() + PLAYER_SPEED;
					enemy.setmLimitF(fx);
				}

			} else {
				mMainCharacter.move(-PLAYER_SPEED, JUMP_FACTOR);
				mJumpDuration++;
			}
		}

		else if (mPressedKeys.contains(KeyEvent.VK_SPACE) && mJumpDuration <= JUMP_LIMIT) {
			if (mMainCharacter.isJumping() && mMainCharacter.getJumpCount() >= 2) {
				updateAllEntites();
				return;
			}

			mMainCharacter.move(0, JUMP_FACTOR);
			mJumpDuration++;
		}

		else if (mPressedKeys.contains(KeyEvent.VK_RIGHT)) {

			if (mBackground.getCounter() > 800) {
				updateAllEntites();
				return;
			}
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
				for (Enemy enemy : mEnemies) {
					double x = enemy.getX() - PLAYER_SPEED;
					enemy.setX(x);
					double bx = enemy.getmLimitB() - PLAYER_SPEED;
					enemy.setmLimitB(bx);
					double fx = enemy.getmLimitF() - PLAYER_SPEED;
					enemy.setmLimitF(fx);
				}
			}
			mBackground.update(1, mMiddle);

		} else if (mPressedKeys.contains(KeyEvent.VK_LEFT)) {
			if (mMainCharacter.getX() == 0) {
				updateAllEntites();
				return;
			}

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
				for (Enemy enemy : mEnemies) {
					double x = enemy.getX() + PLAYER_SPEED;
					enemy.setX(x);
					double bx = enemy.getmLimitB() + PLAYER_SPEED;
					enemy.setmLimitB(bx);
					double fx = enemy.getmLimitF() + PLAYER_SPEED;
					enemy.setmLimitF(fx);
				}
			}

			mBackground.update(0, mBack);
		}

		updateAllEntites();

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

		if (keyCode == KeyEvent.VK_SPACE) {
			mMainCharacter.setJumping(true);
			mMainCharacter.setJumpCount(mMainCharacter.getJumpCount() + 1);
		}

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

	public void updateAllEntites() {
		mMainCharacter.update();

		for (Enemy enemy : mEnemies) {
			enemy.move();
			enemy.update();
		}

		for (Entity powerup : mItems) {
			powerup.update();
		}
	}

	public void gameEnd() {
		this.suspendState();
		new Dialog(host, mCoinCount);
		this.resumeState();
		resetGame();
		host.setState(Strings.MENU);
	}

	private void resetGame() {
		mItems.clear();
		mObastcles.clear();
		mEnemies.clear();

		generateMainCharacter();
		generateItems();
		generateObstacles();
		generateEnemies();

	}

}
