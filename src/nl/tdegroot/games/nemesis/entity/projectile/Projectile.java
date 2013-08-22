package nl.tdegroot.games.nemesis.entity.projectile;

import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.gfx.Screen;

public class Projectile extends Entity {

	protected final float xOrigin;
	protected final float yOrigin;
	protected double angle;
	protected float x, y;
	protected float distance;
	protected double nx, ny;
	protected float speed, range;
	protected double damage;

	public Projectile(float x, float y, double dir) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}

	public void update(int delta) {
	}

	public void move() {
	}

	public void render(Screen screen) {
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
