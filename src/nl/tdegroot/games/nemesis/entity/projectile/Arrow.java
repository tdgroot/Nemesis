package nl.tdegroot.games.nemesis.entity.projectile;

import nl.tdegroot.games.nemesis.gfx.Screen;

public class Arrow extends Projectile {

	public Arrow(float x, float y, double dir) {
		super(x, y, dir);
		range = 650;
		speed = 4.0f;
		damage = 20.0d;

		nx = speed * Math.sin(angle);
		ny = speed * Math.cos(angle);
	}

	public void update(int delta) {
		move();
	}

	public void move() {
		x += nx;
		y += ny;

		if (distance() > range) {
			remove();
		}
	}

	private double distance() {
		double dist = 0.0d;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile(this);
	}

}
