package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.projectile.Arrow;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

import java.util.Random;

public class Mob extends Entity {

	protected SpriteSheet sheet;
	private AStarPathFinder pathFinder;
	private Path destination;

	protected boolean isWalking = false;
	protected boolean wasWalking = false;
	protected boolean hasDestination = false;

	protected int mobID = 0;

	public int dir = 0;
	public int animIndex = 0;
	public int animCount = 0;
	public int animType = 0;
	public int frame = 0;

	protected int collisionMulX = 0;
	protected int collisionMulY = 0;
	protected int collisionAddX = 0;
	protected int collisionAddY = 0;

	protected float movementSpeed = 0;

	public Mob(Image image, float x, float y, int width, int height) {
		super(image, x, y, width, height);
		animCount = (int) (image.getWidth() / width);
		sheet = new SpriteSheet(image, width, height);
	}

	public Mob(Image image, float x, float y, int width, int height, int mobID, int spawnerID) {
		super(image, x, y, width, height);
		animCount = (int) (image.getWidth() / width);
		sheet = new SpriteSheet(image, width, height);
		this.mobID = mobID;
	}

	public void init(Level level) {
		super.init(level);
		pathFinder = new AStarPathFinder(level, 50, true);
	}

	public void update(int delta) {
		Random random = new Random();
		if (! hasDestination)
			destination = getDestination(random.nextInt(10), random.nextInt(10));

		float xa = (x / level.getTileSize() - destination.getX(1)) * (movementSpeed / 50);
		float ya = (y / level.getTileSize() - destination.getY(1)) * (movementSpeed / 50);

//		move(xa * level.getTileSize(), ya * level.getTileSize());

		if (isWalking) {
			if (frame % (102 / delta) == 0) {
				animIndex = ((animIndex + 1) % animCount);
			}
		}
	}

	public void move(float xa, float ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}

		if (ya < 0)
			dir = 0;
		if (xa > 0)
			dir = 1;
		if (ya > 0)
			dir = 2;
		if (xa < 0)
			dir = 3;

		Log.log("Direction = " + getDir());

		if (x < 0) {
			x = 0;
		}

		if (y < 0) {
			y = 0;
		}

		if (! collision(xa, ya)) {
			x += xa;
			y += ya;
		}

	}

	protected void shoot(float x, float y, double dir) {
		Projectile projectile = new Arrow(x, y, dir);
		level.addProjectile(projectile);
	}

	protected boolean collision(float xa, float ya) {
		boolean solid = false;
		if (x + getWidth() / 2 - 2 > level.getPixelWidth()) {
			x = level.getPixelWidth() - getWidth() / 2;
			solid = true;
		}
		if (y + getHeight() / 2 - 2 > level.getPixelHeight()) {
			y = level.getPixelHeight() - getHeight() / 2;
			solid = true;
		}
		for (int c = 0; c < 4; c++) {
			int xt = (int) ((x + xa) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
			int yt = (int) ((y + ya) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			try {
				if (level.isSolid(xt, yt))
					solid = true;
			} catch (ArrayIndexOutOfBoundsException e) {
				Log.exception(e.getMessage());
			}
		}
		return solid;
	}

	private Path getDestination(int destX, int destY) {
		int sx = (int) x / level.getTileSize();
		int sy = (int) y / level.getTileSize();
		Path destination = pathFinder.findPath(null, sx, sy, destX, destY);
		hasDestination = true;
		return destination;
	}

	public void render(Screen screen) {
		screen.renderMob(this);
	}

	public SpriteSheet getSheet() {
		return sheet;
	}

	public int getID() {
		return mobID;
	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public boolean isMoving() {
		return isWalking;
	}

	public int getDir() {
		return dir;
	}

	public int getAnimIndex() {
		return animIndex;
	}

	public int getAnimType() {
		return animType;
	}

}
