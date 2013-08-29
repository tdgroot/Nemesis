package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.projectile.Arrow;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

import java.util.Random;

public class Mob extends Entity {

	protected SpriteSheet sheet;
	private AStarPathFinder pathFinder;
	private Path destination;
	protected Rectangle vulnerability;

	protected boolean isWalking = false;
	protected boolean wasWalking = false;
	protected boolean hasDestination = false;

	protected double baseHealth;
	protected double health;

	protected int mobID = 0;
	protected int spawnerID = 0;
	protected int score = 0;

	public int dir = 0;
	private int lastDir = 0;
	protected int ld = 0;
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
		animCount = image.getWidth() / width;
		sheet = new SpriteSheet(image, width, height);
	}

	public Mob(Image image, float x, float y, int width, int height, int mobID, int spawnerID) {
		super(image, x, y, width, height);
		animCount = image.getWidth() / width;
		sheet = new SpriteSheet(image, width, height);
		vulnerability = new Rectangle(x + 10, y + 10, width - 20, height - 20);
		this.mobID = mobID;
		this.spawnerID = spawnerID;
	}

	public void init(Level level) {
		super.init(level);
		pathFinder = new AStarPathFinder(level, 50, true);
	}

	public void update(int delta) {
		Random random = new Random();
		if (! hasDestination)
			destination = getDestination(random.nextInt(10), random.nextInt(10));

		float xa = 0;
		float ya = 0;

//		move(xa, ya);

		if (isWalking) {
			if (frame % (102 / delta) == 0) {
				animIndex = ((animIndex + 1) % animCount);
			}
		}
		vulnerability.setLocation(x + 10, y + 10);
	}

	public void move(float xa, float ya, int delta) {
		if (xa != 0 && ya != 0) {
			move(xa, 0, delta);
			move(0, ya, delta);
			return;
		}

		if (ld > 0) {
			ld -= delta / 2;
		}

		dir = getDirection(xa, ya);

		Log.log("Last dir: " + lastDir);
		Log.log("LD: " + ld);

		if (lastDir != dir && dir != 1 && dir != 3 && ld <= 0) {
			lastDir = dir;
			ld = 500;
		}

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

	protected void shoot(float x, float y, double dir, Player player) {
		Projectile projectile = new Arrow(x, y, dir, player);
		level.addProjectile(projectile);
	}

	protected boolean collision(float xa, float ya) {
		boolean solid = false;

		for (int c = 0; c < 4; c++) {
			int xt = (int) ((x + xa) + c % 2 * collisionMulX - collisionAddX);
			int yt = (int) ((y + ya) + c / 2 * collisionMulY + collisionAddY);

			try {
				if (level.isCollision(xt, yt))
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

	public void hit(Projectile p) {
		health -= p.getDamage();
		if (health <= 0.0) {
			remove();
		}
	}

	protected int getDirection(float xa, float ya) {
		boolean xPositive = true, yPositive = true;

		if (xa < 0)
			xPositive = false;

		if (ya < 0)
			yPositive = false;

		if (xPositive && yPositive) {
			if (xa > ya) {
				return 1;
			} else if (ya > xa) {
				return 2;
			}
		} else if (! xPositive && ! yPositive) {
			if (xa < ya) {
				return 3;
			} else if (ya > xa) {
				return 0;
			}
		} else if (xPositive && ! yPositive) {
			float yy = ya * - 1;
			if (xa > yy) {
				return 1;
			} else if (yy > xa) {
				return 0;
			}
		} else if (! xPositive && yPositive) {
			float xx = xa * - 1;
			if (xx > ya) {
				return 3;
			} else if (ya > xx) {
				return 2;
			}
		}

		return - 1;
	}

	public void render(Screen screen) {
		screen.renderMob(this);
	}

	public Rectangle getVulnerability() {
		return vulnerability;
	}

	public SpriteSheet getSheet() {
		return sheet;
	}

	public int getID() {
		return mobID;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setHealth(double health) {
		this.health = health;
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

	public int getLastDir() {
		return lastDir;
	}

	public int getAnimIndex() {
		return animIndex;
	}

	public int getAnimType() {
		return animType;
	}

	public double getHealth() {
		return health;
	}

	public int getSpawnerID() {
		return spawnerID;
	}
}
