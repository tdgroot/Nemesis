package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.Camera;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class Player extends Mob {

	SpriteSheet spriteSheet;
	Animation animation;

	public Player(int x, int y, Level level) throws Exception {
		super(x, y, level);
		this.x = x * level.tileSize;
		this.y = y * level.tileSize;
		movementSpeed = 2.5f;
		sprite = new Image("resources/textures/characters/birk_front.png");
		spriteSheet = new SpriteSheet(new Image("resources/spritesheets/spritesheet_birk_anim.png"), 53, 64);
//		animation = new Animation(spriteSheet, 0, 0, 477, 0, true, 50, true);
		System.out.println("Player initialized. Player Width: " + getWidth() + ", Player Height: " + getHeight());
	}

	public void update() {

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

	}

	public void render(Graphics g, Screen screen) {
		screen.renderPlayer(this);
	}

}
