package nl.tdegroot.games.nemesis.ui;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Resources;
import org.lwjgl.opengl.Display;
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
		Resources.scoreBar.draw(Display.getWidth() - Resources.scoreBar.getWidth(), 0);
		g.setAntiAlias(true);
		int scoreHeight = 18;
		g.drawString("" + player.getKills(), Display.getWidth() - 460, scoreHeight);
		g.drawString("" + player.getScore(), Display.getWidth() - 210, scoreHeight);
	}
	
	
}
