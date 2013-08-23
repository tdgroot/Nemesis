package nl.tdegroot.games.nemesis.entity;

import org.newdawn.slick.Image;

public class Roach extends Mob {

	public Roach(Image image, float x, float y, int width, int height, int mobID, int spawnerID) {
		super(image, x, y, width, height, mobID, spawnerID);
		movementSpeed = 1.5f;
		health = 45.0;

		collisionMulX = 78;
		collisionAddX = 16;

		collisionMulY = 30;
		collisionAddY = 29;
	}

}
