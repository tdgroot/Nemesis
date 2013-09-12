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
	public static Image steve;

	public static Image slimeParticle;
	public static Image bloodParticle;

	// Items
	public static Image bow;

	public static Image itemArrow;
	// UI
	public static Image healthBar;
	public static Image energyBar;
	public static Image characterBar;
	public static Image dialogWindow;

	public static Image scoreBar;
	// Fonts
	public static TrueTypeFont agency_fb;
	public static TrueTypeFont courier_new_bold;
	public static final TrueTypeFont NORMAL_HIT = new TrueTypeFont(new Font("Courier New", 0, 16), true);
	public static final TrueTypeFont CRIT_HIT = new TrueTypeFont(new Font("Courier New", Font.BOLD, 25), true);

	public Resources() {
		try {

			// Player
			player = new Image("resources/spritesheets/birk_anim.png");

			// Mobs
			roach = new Image("resources/spritesheets/roach_anim.png");

			// Entities
			entityArrow = new Image("resources/textures/entities/arrow.png");
			slimeParticle = new Image("resources/textures/entities/particles/slimeParticle.png");
			bloodParticle = new Image("resources/textures/entities/particles/bloodParticle.png");
			steve = new Image("resources/textures/entities/npc/steve.png");

			// Items
			bow = new Image("resources/textures/items/bow.png");
			itemArrow = new Image("resources/textures/items/arrow.png");

			// UI
			healthBar = new Image("resources/textures/ui/healthbar.png");
			energyBar = new Image("resources/textures/ui/energybar.png");
			characterBar = new Image("resources/textures/ui/bar_healthenergy.png");
			dialogWindow = new Image("resources/textures/ui/bar_text.png");
			scoreBar = new Image("resources/textures/ui/scorebar.png");

			// Fonts
			agency_fb = new TrueTypeFont(new Font("Californian FB", Font.BOLD, 16), true);
			courier_new_bold = new TrueTypeFont(new Font("Courier New", Font.BOLD, 32), true);

		} catch (SlickException e) {
			Log.exception(e.getMessage());
		}
	}

}
