package nl.tdegroot.games.nemesis.gfx;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Resources {

	public static Image player;
	public static Image roach;

	public Resources() {
		try {
			player = new Image("resources/spritesheets/birk_anim.png");
			roach = new Image("resources/textures/mobs/roachy_north.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
