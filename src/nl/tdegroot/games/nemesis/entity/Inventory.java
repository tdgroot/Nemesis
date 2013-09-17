package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

	public int width, height;
	private int slots;

	private List<Item> items = new ArrayList<Item>();

	public Inventory() {
		width = 10;
		height = 6;
		slots = 10 * 6;
	}

	public void update(int delta) {
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof ItemStack) {
				ItemStack stack = (ItemStack) items.get(i);
				if (stack.itemList.size() <= 0) {
					items.remove(i);
				}
			}
		}
	}

	public void add(int slot, Item item) {
		boolean foundStack = false;
		if (item.stackable()) {
			for (int i = 0; i < items.size(); i++) {
				if (items.get(i) instanceof ItemStack) {
					ItemStack stack = (ItemStack) items.get(i);
					if (stack.item.equals(item)) {
						stack.add(item);
						foundStack = true;
					}
				}

			}
			if (!foundStack) {
				ItemStack stack = new ItemStack(item, 1);
				items.add(stack);
				foundStack = true;
			}
		} else {
			items.add(item);
		}
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

	public Item get(int index) {
		return items.get(index);
	}

	public void remove(Item item) {
		items.remove(item);
	}
}
