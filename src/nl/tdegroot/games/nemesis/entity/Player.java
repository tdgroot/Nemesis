package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.lwjgl.input.Keyboard;
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

		if (xa != 0 || ya != 0) {
			isMoving = true;
			move(xa, ya);
		} else {
			isMoving = false;
		}

		frame++;

		if (isMoving) {
			if (frame % (102 / delta) == 0) {
				animIndex = ((animIndex + 1) % animCount);
			}
		}
	}

	public void render(Graphics g, Screen screen) {
		screen.renderPlayer(this);
	}

}
