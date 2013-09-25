package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.gfx.Resources;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class MainMenu extends Menu {

	private int kt = 0;
	private int xt = 0;
	private boolean reset;

	public MainMenu(Menu parent, boolean reset) {
		super(parent);
		this.reset = reset;
	}

	public void init(Nemesis game) {
		super.init(game);
		items = new String[]{"Play", "Load Game", "Controls", "About", "Quit Game"};
		xt = 400;
	}

	public void update(int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && game.mt <= 0) {
			game.setMenu(parent);
			return;
		}
		if (kt > 0) kt -= delta;
		if (xt > 0) xt -= delta;

		if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) && kt <= 0) {
			selected--;
			Resources.select.play();
			kt = 200;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) && kt <= 0) {
			selected++;
			Resources.select.play();
			kt = 200;
		}

		int length = items.length;

		if (selected < 0) selected += length;
		if (selected >= length) selected -= length;

		if (Keyboard.isKeyDown(Keyboard.KEY_X) && xt <= 0) {
			if (selected == 0) {
				Resources.interact.play();
				if (reset) game.setupGame();
				game.setMenu(parent);
			}

			if (selected == 1) {
				Resources.interact.play();
				game.load();
				game.setMenu(null);
			}

			if (selected == 2) {
				Resources.interact.play();
				game.setMenu(new ControlsMenu(this));
			}

			if (selected == 3) {
				Resources.interact.play();
				game.setMenu(new AboutMenu(this));
			}

			if (selected == 4) game.stop();

			kt = 350;
		}
	}

	public void render(Graphics graphics) {
		graphics.setColor(new Color(0, 0, 0, 255));
		graphics.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		graphics.setColor(Color.white);
		for (int i = 0; i < items.length; i++) {
			String msg = items[i];
			if (i == selected) {
				msg = "> " + msg + " <";
			}
			graphics.drawString(msg, (Display.getWidth() - graphics.getFont().getWidth(msg)) / 2, ((Display.getHeight() - items.length * graphics.getFont().getHeight(msg)) / 2) + (i * 3) * 8);
		}
	}

	public boolean renderBackground() {
		return false;
	}

}
