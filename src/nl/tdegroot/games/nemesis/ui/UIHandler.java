package nl.tdegroot.games.nemesis.ui;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Resources;

import org.newdawn.slick.Graphics;


public class UIHandler {
	
	private Player player;
	private CharacterHUD characterHUD;

	public UIHandler(Player player) {
		this.player = player;
		characterHUD = new CharacterHUD(Resources.characterBar, player);
	}

	public void update(int delta) {
		Dialog.update(delta);
		characterHUD.update(delta);
	}
	
	public void render(Graphics g) {
		if (Dialog.isActive()) {
			Dialog.render(g);
		}
		characterHUD.render(g);
	}
	
	
}
