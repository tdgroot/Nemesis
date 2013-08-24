package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.InputHandler;
import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.map.action.Action;
import nl.tdegroot.games.nemesis.entity.projectile.Arrow;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.map.object.MapObject;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Player extends Mob {

	SpriteSheet spriteSheet;
	Animation animation;

	private int fireRate = 0;
	private int mobsKilled = 0;

	public Player(Image image, float x, float y, int width, int height) {
		super(image, x, y, width, height);

		movementSpeed = 2.5f;

		collisionMulX = 38;
		collisionAddX = 17;
		collisionMulY = 20;
		collisionAddY = 10;

		Log.log("Player initialized. Player Width: " + getWidth() + ", Player Height: " + getHeight());
	}

	public void update(int delta) {
		if (fireRate > 0)
			fireRate--;

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

		if (!wasWalking) {
			animIndex = 0;
		}

		clear();
		pollInput();
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) {
				level.getProjectiles().remove(i);
			}
		}
	}

	private void pollInput() {
		if (InputHandler.getMouseB() == 0 && fireRate <= 0) {
			double dx = (InputHandler.getMouseX() - Display.getWidth() / 2);
			double dy = (InputHandler.getMouseY() - Display.getHeight() / 2);
			double dir = Math.atan2(dx, dy);
			shoot(x, y, dir, this);
			fireRate = Arrow.FIRE_RATE;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			interact();
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

	public void interact() {
		MapObject object = null;
		for (int c = 0; c < 4; c++) {
			int xt = 0;
			int yt = 0;
			if (dir == 0) {
				xt = (int) ((x) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y - level.tileSize / 2) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (dir == 1) {
				xt = (int) ((x + level.tileSize / 2) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (dir == 2) {
				xt = (int) ((x) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y + level.tileSize / 2) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (dir == 3) {
				xt = (int) ((x - level.tileSize / 2) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (level.getMapObject(xt, yt) != null)
				object = level.getMapObject(xt, yt);
		}

		if (object != null)
			object.getAction().log();
	}

	public Action getAction() {
		Action action = null;
		for (int c = 0; c < 4; c++) {
			int xt = 0;
			int yt = 0;
			if (dir == 0) {
				xt = (int) ((x) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y - level.tileSize / 2) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (dir == 1) {
				xt = (int) ((x + level.tileSize / 2) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (dir == 2) {
				xt = (int) ((x) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y + level.tileSize / 2) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (dir == 3) {
				xt = (int) ((x - level.tileSize / 2) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}

//			action = level.getMapObject(xt, yt);
		}
		return action;
	}

	public void mobKilled() {
		mobsKilled++;
	}

	public void render(Graphics g, Screen screen) {
		screen.renderPlayer(this);
	}

	public int getKills() {
		return mobsKilled;
	}
}
