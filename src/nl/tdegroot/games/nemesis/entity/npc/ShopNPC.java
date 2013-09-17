package nl.tdegroot.games.nemesis.entity.npc;

import java.util.ArrayList;
import java.util.List;

import nl.tdegroot.games.nemesis.item.Item;

import org.newdawn.slick.Image;

public class ShopNPC extends NPC {

	protected int arrows = 0;
	protected List<Item> items = new ArrayList<Item>();

	public ShopNPC(Image image, float x, float y, int width, int height, String name, int id) {
		super(image, x, y, width, height, name, id);
	}

	public void addArrows(int i) {
	}

	public void addItem(Item item, int count) {
	}

	public int getArrows() {
		return arrows;
	}

	public String[] getDisplayItems() {
		return new String[] {"Shop", "Talk", "Describe"};
	}


	public List<Item> getItems() {
		return items;
	}

}
