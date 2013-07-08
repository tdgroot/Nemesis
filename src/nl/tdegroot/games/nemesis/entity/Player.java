package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.level.Level;
import org.lwjgl.input.Keyboard;

public class Player extends Mob{

	public Player(float x, float y, Level level) {
		super(x, y, level);
		movementSpeed = 4.5f;
	}

	public void update() {
		float xa = 0;
		float ya = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) xa -= movementSpeed;
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) xa += movementSpeed;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) ya -= movementSpeed;
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) ya += movementSpeed;

		if (xa != 0 || ya != 0) {
			move(xa, ya);
		}
	}

}
