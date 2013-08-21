package nl.tdegroot.games.nemesis.entity;

import org.newdawn.slick.Image;

public class Roach extends Mob {

	public Roach(Image image, float x, float y, int width, int height, int mobID) {
		super(image, x, y, width, height, mobID);
		movementSpeed = 1.5f;
	}

}
