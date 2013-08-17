package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Entity {

	Image sprite;
	protected Level level;

	protected float x, y;
	
	protected int width = 0;
	protected int height = 0;
	

	public Entity(Image image, Level level, float x, float y, int width, int height) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		positionate();
	}
	
	public void positionate() {
		x *= level.tileSize;
		y *= level.tileSize;
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
		return width;
	}

	public int getHeight() {
		return height;
	}
}
