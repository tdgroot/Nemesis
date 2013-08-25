package nl.tdegroot.games.nemesis.item;

import nl.tdegroot.games.nemesis.gfx.Resources;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

public class Item {

	private static List<Item> itemList = new ArrayList<Item>();

	public static Item bow = new Bow("Bow", Resources.bow);

	private final String name;
	private final Image image;

	public Item(String name, Image image) {
		this.name = name;
		this.image = image;
		itemList.add(this);
	}

	public static Item getItem(String name) {
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).name.equalsIgnoreCase(name))
				return itemList.get(i);
		}
		return null;
	}

	public String getName() {
		return name;
	}

}
