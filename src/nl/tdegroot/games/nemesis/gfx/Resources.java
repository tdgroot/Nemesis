package nl.tdegroot.games.nemesis.gfx;

import nl.tdegroot.games.nemesis.Log;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Resources {

	public static Image player;

	// Mobs
	public static Image roach;

	// Entities
	public static Image arrow;

	public Resources() {
		try {
			player = new Image("resources/spritesheets/birk_anim.png");

			roach = new Image("resources/spritesheets/roach_anim.png");

			arrow = new Image("resources/textures/entities/arrow.png");
		} catch (SlickException e) {
			Log.exception(e.getMessage());
		}
	}

}
