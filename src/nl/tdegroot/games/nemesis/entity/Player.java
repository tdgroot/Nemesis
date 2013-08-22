package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.InputHandler;
import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Player extends Mob {

	SpriteSheet spriteSheet;
	Animation animation;

	public Player(Image image, float x, float y, int width, int height) {
		super(image, x, y, width, height);
		movementSpeed = 2.5f;
		Log.log("Player initialized. Player Width: " + getWidth() + ", Player Height: " + getHeight());
	}

	public void update(int delta) {

		float xa = 0;
		float ya = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) xa -= movementSpeed;
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) xa += movementSpeed;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) ya -= movementSpeed;
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) ya += movementSpeed;

		wasWalking = isWalking;

		if (xa != 0 || ya != 0) {
			isWalking = true;
			move(xa, ya);
		} else {
			isWalking = false;
		}

		frame++;

		if (isWalking) {
			if (frame % (50 / delta) == 0) {
				animIndex = ((animIndex + 1) % animCount);
			}
		}

		if (! wasWalking) {
			animIndex = 0;
		}

		clear();
		pollShoot();
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) {
				level.getProjectiles().remove(i);
			}
		}
	}

	private void pollShoot() {
		if (InputHandler.getMouseB() == 0) {
			double dx = (InputHandler.getMouseX() - Display.getWidth() / 2);
			double dy = (InputHandler.getMouseY() - Display.getHeight() / 2);
			double dir = Math.atan2(dx, dy);
			shoot(x, y, dir);
			Log.log("Shot!");
		}
	}

	protected boolean collision(float xa, float ya) {
		boolean solid = false;
		if (x + getWidth() / 2 - 2 > level.getPixelWidth()) {
			x = level.getPixelWidth() - getWidth() / 2;
			solid = true;
		}
		if (y + getHeight() / 2 - 2 > level.getPixelHeight()) {
			y = level.getPixelHeight() - getHeight() / 2;
			solid = true;
		}
		for (int c = 0; c < 4; c++) {
			int xt = (int) ((x + xa) + c % 2 * 38 - 17) / level.tileSize;
			int yt = (int) ((y + ya) + c / 2 * 20 + 10) / level.tileSize;
			if (level.isSolid(xt, yt))
				solid = true;
		}
		return solid;
	}

	public void render(Graphics g, Screen screen) {
		screen.renderPlayer(this);
	}
}
