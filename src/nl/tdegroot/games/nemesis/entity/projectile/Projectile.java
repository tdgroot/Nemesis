package nl.tdegroot.games.nemesis.entity.projectile;

import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.newdawn.slick.geom.Rectangle;

public class Projectile extends Entity {

	protected final float xOrigin;
	protected final float yOrigin;
	protected double angle;
	protected float x, y;
	protected float distance;
	protected double nx, ny;
	protected float speed, range;
	protected double damage;

	protected Rectangle aoe;
	protected Player player;

	public Projectile(float x, float y, double dir, Player player) {
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.player = player;
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

	public Rectangle getAreaOfEffect() {
		return aoe;
	}

	public double getAngle() {
		return angle;
	}

	public double getDamage() {
		return damage;
	}

	public Player getPlayer() {
		return player;
	}
}
