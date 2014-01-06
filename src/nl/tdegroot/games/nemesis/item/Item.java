package nl.tdegroot.games.nemesis.item;

import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

public class Item {

	private static List<Item> itemList = new ArrayList<Item>();

	public static Item bow = new Bow(15, 5);
	public static Item fish = new Fish(10, 4);
	public static Item arrow = new Arrow(5, 2);
	public static Item fishingRod = new FishingRod(25, 10);

	private final String name;
	private final Image sprite;
	public final int buyCost;
	public final int sellCost;

	public Item(String name, Image sprite, int buyCost, int sellCost) {
		this.name = name;
		this.sprite = sprite;
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

	public boolean equipable() {
		return false;
	}

	public boolean eatable() {
		return false;
	}

	public boolean stackable() {
		return false;
	}

}
