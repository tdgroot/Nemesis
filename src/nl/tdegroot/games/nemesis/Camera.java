package nl.tdegroot.games.nemesis;

import nl.tdegroot.games.nemesis.entity.Player;
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
		if (x < bounds.getX())
			x = bounds.getX();
		if (x > bounds.getWidth() / 2)
			x = bounds.getWidth() / 2;
		return x;
	}

	public float getY() {
		float y = player.getY() - size.getY() / 2;
		if (y < bounds.getY())
			y = bounds.getY();
		if (y > bounds.getHeight() / 2)
			y = bounds.getHeight() / 2;
		return y;
	}

}
