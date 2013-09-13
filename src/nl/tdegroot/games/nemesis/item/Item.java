package nl.tdegroot.games.nemesis.item;

import java.util.ArrayList;
import java.util.List;

import nl.tdegroot.games.nemesis.gfx.Resources;

import org.newdawn.slick.Image;

public class Item {

	private static List<Item> itemList = new ArrayList<Item>();

	public static Item bow = new Bow("Bow", Resources.bow);
	public static Item arrow = new Arrow(6, 3);

	private final String name;
	private final Image sprite;
	public final int buyCost;
	public final int sellCost;

	public Item(String name, Image image, int buyCost, int sellCost) {
		this.name = name;
		this.sprite = image;
		this.buyCost = buyCost;
		this.sellCost = sellCost;
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

	public Image getSprite() {
		return sprite;
	}

	public boolean isEquipable() {
		return false;
	}
}
