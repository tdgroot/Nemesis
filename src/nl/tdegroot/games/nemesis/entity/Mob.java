package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;

public class Mob extends Entity {

	protected boolean isMoving = false;

	private boolean checkCollision = false;
	protected boolean doCollision = true;

	protected float movementSpeed = 0;
	protected Level level;
	protected int dir = 0;

	public Mob(float x, float y, Level level) {
		super(x, y);
		this.level = level;
	}

	public void update() {

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

		float dx = x + xa;
		float dy = y + ya;

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
			if (isSolid(xt, yt))
				solid = true;
		}
		return solid;
	}

	private boolean isSolid(int x, int y) {
		boolean solid = false;
		try {
			int tileID = level.getMap().getTileId(x, y, 0);
			String solidTile = level.getMap().getTileProperty(tileID, "solid", "false");
			solid = Boolean.parseBoolean(solidTile);
		} catch (Exception e) {
			solid = true;
			System.out.println("Trying to access: " + x + ", " + y + " on map: " + level.getMap().getWidth() + ", " + level.getMap().getHeight());
		}
		return solid;
	}

	public void render(Screen screen) {
		screen.renderMob(this);
	}

}
