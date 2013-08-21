package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.PathFinder;

public class Mob extends Entity {


	protected SpriteSheet sheet;

	protected boolean isMoving = false;

	public int dir = 0;
	public int animIndex = 0;
	public int animCount = 0;
	public int animType = 0;
	public int frame = 0;

	protected float movementSpeed = 0;

	public Mob() {

	}

	public Mob(Image image, float x, float y, int width, int height) {
		super(image, x, y, width, height);
		animCount = (int) (image.getWidth() / width);
		sheet = new SpriteSheet(image, width, height);
	}

	public void update(int delta) {
		AStarPathFinder pathFinder = new AStarPathFinder(level, 100, true);

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

		if (! collision(xa, ya)) {
			x += xa;
			y += ya;
		}

	}

	protected boolean collision(float xa, float ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (int) ((x + xa) + c % 2 * 38 - 17) / level.tileSize;
			int yt = (int) ((y + ya) + c / 2 * 20 + 10) / level.tileSize;
			if (level.isSolid(xt, yt))
				solid = true;
		}
		return solid;
	}

	private boolean isSolid(int x, int y) {
		return level.isSolid(x, y);
	}

	public void render(Screen screen) {
		screen.renderMob(this);
	}

	public SpriteSheet getSheet() {
		return sheet;
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
