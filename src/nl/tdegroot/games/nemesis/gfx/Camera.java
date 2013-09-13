package nl.tdegroot.games.nemesis.gfx;

import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.entity.Player;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Camera {

	private Player player;
	private Rectangle bounds;
	private Vector2f size;

	public Camera(Player player, Vector2f size, Rectangle bounds) {
		this.player = player;
		this.bounds = bounds;
		this.size = size;
	}

	public float getX() {
		float x = player.getX() - size.getX() / 2;
		float maxWidth = (bounds.getWidth() + (size.getX() / 2)) / 2;

		if (x < bounds.getX())
			x = bounds.getX();
		if (x > maxWidth)
			x = maxWidth;

		return x;
	}

	public float getY() {
		float y = player.getY() - (size.getY() * 2) / 2;
		float maxHeight = (bounds.getHeight() + (size.getY() / 2)) / 2;

		if (y < bounds.getY())
			y = bounds.getY();
		if (y > maxHeight)
			y = maxHeight;

		return y;
	}

	public float getXOffset() {
		return (size.getX() / 2) - (player.getWidth() / 2);
	}

	public float getYOffset() {
		return (size.getY() / 2) - (player.getHeight() / 2);
	}

	public boolean outOfRange(float xOffset, float yOffset, Entity entity) {
		float x = entity.getX();
		float y = entity.getY();
		float width = entity.getWidth();
		float height = entity.getHeight();

		return !((x + width > xOffset && x < Display.getWidth()) && (y + height > yOffset && y < Display.getWidth()));
	}

}
