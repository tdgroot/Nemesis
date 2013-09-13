package nl.tdegroot.games.nemesis.entity;

import java.util.ArrayList;
import java.util.List;

import nl.tdegroot.games.nemesis.item.Item;

public class Inventory {

	private List<Item> items = new ArrayList<Item>();

	public void add(int slot, Item item) {
		items.add(slot, item);
	}

	public boolean hasItems(Item item, int count) {
		return (count(item) >= count);
	}

	public int count(Item item) {
		int count = 0;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).equals(item)) count++;
		}
		return count;
	}

	public int size() {
		return items.size();
	}
}
