package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.Camera;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.newdawn.slick.Graphics;
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

	public void render(Graphics g, Screen screen) {
		screen.renderEntity(this);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Image getSprite() {
		return sprite;
	}

	public int getWidth() {
		return sprite.getWidth();
	}

	public int getHeight() {
		return sprite.getHeight();
	}
}
