package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ControlsMenu extends Menu {

	private int kt = 0;

	private String[] controls = new String[]{
			"W / Arrow Up: Walk northwards",
			"S / Arrow Down: Walk southwards",
			"A / Arrow Left: Walk westwards",
			"D / Arrow Right: Walk eastwards",
			"",
			"Left Mouse Button: Shoot",
			"",
			"X: Interact / Action key",
			"C: Close Dialog",
			"",
			"Escape: Open / Close menu"
	};

	public ControlsMenu(Menu parent) {
		super(parent);
	}

	public void init(Nemesis game) {
		super.init(game);
		kt = 300;
	}

	public void update(int delta) {
		if (kt > 0) kt -= delta;
		if ((Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_X) || Keyboard.isKeyDown(Keyboard.KEY_C)) && kt <= 0) {
			game.setMenu(parent);
		}
	}

	public void render(Screen screen) {
		Graphics graphics = screen.getGraphics();
		graphics.setColor(new Color(0, 0, 0));
		graphics.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		graphics.setColor(new Color(255, 255, 255));
		for (int i = 0; i < controls.length; i++) {
			String msg = controls[i];
			graphics.drawString(msg, (Display.getWidth() - graphics.getFont().getWidth(msg)) / 2, ((Display.getHeight() - controls.length * 8) / 2) + i * 18);
		}
	}

}
