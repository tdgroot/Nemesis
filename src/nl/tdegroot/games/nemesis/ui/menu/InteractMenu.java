package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import actions.Interactable;

public class InteractMenu extends Menu {

	private Player player;
	private Interactable interactable;

	private float x, y;
	private int width;
	private int height;

	private String[] displayItems;

	public InteractMenu(Player player, Interactable interactable, float x, float y) {
		super(null);
		this.player = player;
		this.interactable = interactable;
		this.x = x;
		this.y = y;
		displayItems = interactable.getDisplayItems();
		items = interactable.getInteractItems();
		for (int i = 0; i < items.length; i++) {
			int temp = ("> " + items[i] + " <").length() * 14;
			if (temp > width) {
				width = temp;
			}
		}
		if (interactable.getName().length() * 16 > width) width = interactable.getName().length() * 16;
		height = items.length * 16;
		kt = 400;
	}

	public void update(int delta) {
		if (kt > 0) kt -= delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			game.setMenu(new PauseMenu(null));
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			game.setMenu(parent);
		}

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

		if (Keyboard.isKeyDown(Keyboard.KEY_X) && kt <= 0) {

			if (items[selected] == "Interact") {
				game.setMenu(null);
				interactable.interact(player, game);
			}

			if (items[selected] == "Talk") {
				interactable.talk();
			}

			if (items[selected] == "Describe") {
				interactable.describe();
			}

			kt = 400;
		}

	}

	public void render(Screen screen) {
		Graphics graphics = screen.getGraphics();
		float xx = (x * 64) + 50 - screen.xOffset;
		float yy = (y * 64) - 15 - screen.yOffset;
		float nameHeight = graphics.getFont().getHeight(interactable.getName());
		graphics.setColor(new Color(0xa3a3a3));
		graphics.fillRect(xx, yy, width, nameHeight);
		graphics.setColor(Color.white);
		graphics.fillRect(xx, nameHeight + yy, width, height);
		graphics.setColor(Color.black);
		graphics.drawString(interactable.getName(), xx, yy);
		for (int i = 0; i < displayItems.length; i++) {
			String msg = displayItems[i];
			if (selected == i) msg = "> " + msg + " <";
			int mw = graphics.getFont().getWidth(msg);
			graphics.drawString(msg, (xx + width / 2) - mw / 2, yy + nameHeight + i * 14);
		}
		graphics.setColor(Color.white);
	}

}
