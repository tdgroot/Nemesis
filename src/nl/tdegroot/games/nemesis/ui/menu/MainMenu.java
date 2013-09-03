package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class MainMenu extends Menu {

	private int kt = 0;
	private int xt = 0;

	public MainMenu(Menu parent) {
		super(parent);
	}

	public void init(Nemesis game) {
		super.init(game);
		items = new String[]{"Play", "Controls", "About", "Quit Game"};
		xt = 200;
	}

	public void update(int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && game.mt <= 0) {
			game.setMenu(null);
			return;
		}
		if (kt > 0) kt -= delta;
		if (xt > 0) xt -= delta;

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

		if (Keyboard.isKeyDown(Keyboard.KEY_X) && xt <= 0) {
			if (selected == 0) game.setMenu(null);

			if (selected == 1) game.setMenu(new ControlsMenu(this));

			if (selected == 2) game.setMenu(new AboutMenu(this));

			if (selected == 3) game.stop();

			kt = 350;
		}
	}

	public void render(Screen screen) {
		Graphics graphics = screen.getGraphics();
		graphics.setColor(new Color(0, 0, 0, 255));
		graphics.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		graphics.setColor(Color.white);
		for (int i = 0; i < items.length; i++) {
			String msg = items[i];
			if (i == selected) {
				msg = "> " + msg + " <";
			}
			graphics.drawString(msg, (Display.getWidth() - graphics.getFont().getWidth(msg)) / 2, (44 + i * 3) * 8);
		}
	}

}
