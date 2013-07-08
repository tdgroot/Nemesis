package nl.tdegroot.games.nemesis.entity;

import org.newdawn.slick.Image;

public class Entity {

	protected float x, y;
	Image sprite;

	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void update() {
	}

	public void render() {
		sprite.draw(x, y);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

}
