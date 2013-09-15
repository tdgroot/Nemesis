package nl.tdegroot.games.nemesis.gfx;

import java.awt.Font;

import nl.tdegroot.games.nemesis.Log;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;

public class Resources {

	public static Image player;

	// Mobs
	public static Image roach;

	// Entities
	public static Image entityArrow;
	public static Image steve;

	// Particles
	public static Image slimeParticle;
	public static Image bloodParticle;

	// Items
	public static Image bow;
	public static Image fish;
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
	// Sounds
	public static Sound select;
	public static Sound mob_death;
	public static Sound bow_shot;
	public static Sound interact;
	public static Sound interact_fail;
	public static Sound mob_hit;
	public static Sound player_hit;
	public static Sound steve_interact;
	public static Sound eat;

	public Resources() {
		try {

			// Player
			player = new Image("resources/spritesheets/birk_anim.png");

			// Mobs
			roach = new Image("resources/spritesheets/roach_anim.png");

			// Entities
			entityArrow = new Image("resources/textures/entities/arrow.png");
			steve = new Image("resources/textures/entities/npc/steve.png");

			// Particles
			slimeParticle = new Image("resources/textures/entities/particles/slimeParticle.png");
			bloodParticle = new Image("resources/textures/entities/particles/bloodParticle.png");

			// Items
			bow = new Image("resources/textures/items/bow.png");
			fish = new Image("resources/textures/items/fish.png");
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
			
			// Sounds
			select = new Sound("resources/sounds/select.wav");
			mob_death = new Sound("resources/sounds/mob_death.wav");
			bow_shot = new Sound("resources/sounds/bow_shot.wav");
			interact = new Sound("resources/sounds/interact.wav");
			interact_fail = new Sound("resources/sounds/interact_fail.wav");
			mob_hit = new Sound("resources/sounds/mob_hit.wav");
			player_hit = new Sound("resources/sounds/au.wav");
			steve_interact = new Sound("resources/sounds/steve.wav");
			eat = new Sound("resources/sounds/eat.wav");
			
		} catch (SlickException e) {
			Log.exception(e.getMessage());
		}
	}

}
