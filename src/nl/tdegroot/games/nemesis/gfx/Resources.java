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
	public static Image entityArrow;

	// Items
	public static Image bow;
	public static Image itemArrow;

	// UI
	public static Image dialogWindow;

	// Fonts
	public static TrueTypeFont agency_fb;

	public Resources() {
		try {

			// Player
			player = new Image("resources/spritesheets/birk_anim.png");

			// Mobs
			roach = new Image("resources/spritesheets/roach_anim.png");

			// Entities
			entityArrow = new Image("resources/textures/entities/arrow.png");

			// Items
			bow = new Image("resources/textures/items/bow.png");
			itemArrow = new Image("resources/textures/items/arrow.png");

			// UI
			dialogWindow = new Image("resources/textures/hud/bar_text.png");

			// Fonts
			agency_fb = new TrueTypeFont(new Font("Tahoma", Font.BOLD, 16), true);

		} catch (SlickException e) {
			Log.exception(e.getMessage());
		}
	}

}
