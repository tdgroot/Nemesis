package nl.tdegroot.games.nemesis.gfx;

import nl.tdegroot.games.nemesis.Log;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class Resources {

	public static Image player;

	// Mobs
	public static Image roach;

	// Entities
	public static Image arrow;

	// UI
	public static Image dialogWindow;

	// Fonts
	public static TrueTypeFont agency_fb;

	public Resources() {
		try {
			player = new Image("resources/spritesheets/birk_anim.png");

			roach = new Image("resources/spritesheets/roach_anim.png");

			arrow = new Image("resources/textures/entities/arrow.png");

			dialogWindow = new Image("resources/textures/hud/bar_text.png");

			agency_fb = new TrueTypeFont(new Font("Agency FB", Font.BOLD, 16), true);
		} catch (SlickException e) {
			Log.exception(e.getMessage());
		}
	}

}
