package nl.tdegroot.games.nemesis.item;

import java.util.ArrayList;
import java.util.List;

public class ItemStack extends Item {

	public List<Item> itemList = new ArrayList<Item>();
	public Item item;

	public ItemStack(Item item, int count) {
		super(item.getName(), item.getSprite(), item.buyCost, item.sellCost);
		this.item = item;
		for (int i = 0; i < count; i++) {
			itemList.add(item);
		}
	}

	public void add(Item item) {
		itemList.add(item);
	}

	public void remove(int amount) {
		if (itemList.size() >= amount) {
			for (int i = 0; i < amount; i++) {
				itemList.remove(0);
			}
		} else {
			for (int i = 0; i < itemList.size(); i++) {
				itemList.remove(0);
			}
		}
	}
}
