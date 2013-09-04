package nl.tdegroot.games.nemesis.entity.npc;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.ui.menu.ShopMenu;

public class Steve extends ShopNPC {

	public Steve(float x, float y, int id) {
		super(Resources.steve, x, y, 53, 64, "Steve", id);
	}

	public void update(int delta) {
	}

	public void interact(Player player, Nemesis game) {
		Log.log("STEVE INTERACTION");
		game.setMenu(new ShopMenu(null, this));
	}

	public void render(Screen screen) {
		screen.renderEntity(this);
	}

	public void addArrows(int count) {
		arrows += count;
	}

	public void addItem(Item item, int count) {
		for (int i = 0; i < count; i++) {
			items.add(item);
		}
	}

}