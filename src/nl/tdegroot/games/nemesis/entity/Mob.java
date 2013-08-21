package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

public class Mob extends Entity {

	protected SpriteSheet sheet;
	private AStarPathFinder pathFinder;
	private Path destination;

	protected boolean isMoving = false;
	protected boolean hasDestination = false;

	protected int mobID = 0;

	public int dir = 0;
	public int animIndex = 0;
	public int animCount = 0;
	public int animType = 0;
	public int frame = 0;

	protected float movementSpeed = 0;

	public Mob(Image image, float x, float y, int width, int height, int mobID) {
		super(image, x, y, width, height);
		animCount = (int) (image.getWidth() / width);
		sheet = new SpriteSheet(image, width, height);
		this.mobID = mobID;
	}

	public void init(Level level) {
		super.init(level);
		pathFinder = new AStarPathFinder(level, 100, true);
	}

	public void update(int delta) {
		destination = getDestination(7, 7);

		Log.log("Move to: " + destination.getX(1) + "," + destination.getY(1) + ".");

		float xa = x / 64 - destination.getX(1);
		float ya = y / 64 - destination.getY(1);

		move(xa, ya);

		if (isMoving) {
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

		if (x < 0) {
			x = 0;
		}

		if (y < 0) {
			y = 0;
		}

		if (!collision(xa, ya)) {
			x += xa;
			y += ya;
		}

	}

	protected boolean collision(float xa, float ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (int) ((x + xa) + c % 2 * 38 - 30) / level.tileSize;
			int yt = (int) ((y + ya) + c / 2 * 20 + 9) / level.tileSize;
			solid = level.isSolid(xt, yt);
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
		return isMoving;
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
