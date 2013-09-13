package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class AboutMenu extends Menu {


	private int kt = 0;
	private String[] about = new String[]{
			"Nemesis",
			"",
			"A game by",
			"",
			"Maarten Wolfsen (Graphics)",
			"Timon de Groot (Programmer)",
	};

	public AboutMenu(Menu parent) {
		super(parent);
	}

	public void init(Nemesis game) {
		super.init(game);
		kt = 300;
	}

	public void update(int delta) {
		if (kt > 0) kt -= delta;
		if ((Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_X) || Keyboard.isKeyDown(Keyboard.KEY_C)) && kt <= 0) {
			Resources.interact.play();
			game.setMenu(parent);
		}
	}

	public void render(Screen screen) {
		Graphics graphics = screen.getGraphics();
		graphics.setColor(new Color(0, 0, 0));
		graphics.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		graphics.setColor(new Color(255, 255, 255));
		for (int i = 0; i < about.length; i++) {
			String msg = about[i];
			graphics.drawString(msg, (Display.getWidth() - graphics.getFont().getWidth(msg)) / 2, ((Display.getHeight() - about.length * 8) / 2) + i * 18);
		}
	}
}
