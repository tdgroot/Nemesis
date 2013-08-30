package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.lwjgl.input.Keyboard;

public class PauseMenu extends Menu {

	public void init(Nemesis game) {
		super.init(game);
		items = new String[]{"Resume", "Quit Game"};
	}

	public void update(int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) game.setMenu(null);
	}

	public void render(Screen screen) {
		screen.renderMenu(this);
	}

}
