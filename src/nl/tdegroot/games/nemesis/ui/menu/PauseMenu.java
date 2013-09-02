package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.awt.*;
import java.awt.Font;

public class PauseMenu extends Menu {

	private int kt = 0;

	public void init(Nemesis game) {
		super.init(game);
		items = new String[] {"Resume", "Restart", "Main Menu", "Quit Game"};
	}

	public void update(int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && game.mt <= 0) {
			game.setMenu(null);
			return;
		}
		if (kt > 0) kt -= delta;

		if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) && kt <= 0) {
			selected--;
			kt = 200;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) && kt <= 0) {
			selected++;
			kt = 200;
		}

		int length = items.length;

		if (selected < 0) selected += length;
		if (selected >= length) selected -= length;

		if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			if (selected == 0) game.setMenu(null);

			if (selected == 1) {
				game.resetGame();
				game.setMenu(null);
			}

			if (selected == 2) game.setMenu(new MainMenu());

			if (selected == 3) game.stop();

			kt = 350;
		}
	}

	public void render(Screen screen) {
		Graphics graphics = screen.getGraphics();
		graphics.setColor(new Color(0, 0, 0, 150));
		graphics.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		graphics.setColor(Color.white);
		for (int i = 0; i < items.length; i++) {
			String msg = items[i];
			int xx = msg.length() / 8;
			if (i == selected) {
				graphics.setAntiAlias(true);
				graphics.setFont(new TrueTypeFont(new Font("Courier New", Font.BOLD, 16), true));
				msg = "> " + msg + " <";
			} else {
				graphics.resetFont();
			}
			graphics.drawString(msg, (Display.getWidth() - msg.length() * 8) / 2, (44 + i * 3) * 8);
		}
	}

}
