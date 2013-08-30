package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.lwjgl.input.Keyboard;

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
			Log.log("Selected: " + selected);
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

			if (selected == 2) ;

			if (selected == 3) game.stop();

			kt = 350;
		}
	}

	public void render(Screen screen) {
		screen.renderMenu(this);
	}

}
