package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.Log;
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
		slots = width * height;
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

	public void add(Item item, int count) {
		for (int i = 0; i < count; i++) {
			add(0, item);
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

	public void setSave(String itemList) {

		int openBracket = itemList.indexOf("{");
		int closeBracket = itemList.lastIndexOf("}");

		String params = itemList.substring(openBracket + 1, closeBracket);
		String[] paramList = params.split(",");
		for (int i = 0; i < paramList.length; i++) {
			String[] pData = paramList[i].trim().split(":");
			Item item = Item.getItem(pData[0].trim());
			int count = Integer.parseInt(pData[1].trim());
			if (count > 1) {
				add(0, new ItemStack(item, count));
			} else if (count == 1) {
				add(0, item);
			}
		}

	}

	public String getSave() {
		String itemString = "{";

		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof ItemStack) {
				ItemStack stack = (ItemStack) items.get(i);
				if (i == 0) {
					itemString += (stack.item.getName().toLowerCase() + ":" + stack.size());
				} else {
					itemString += (", " + stack.item.getName().toLowerCase() + ":" + stack.size());
				}
			} else {
				if (i == 0) {
					itemString += (items.get(i).getName().toLowerCase() + ":" + 1);
				} else {
					itemString += (", " + items.get(i).getName().toLowerCase() + ":" + 1);
				}
			}
		}

		itemString += "}";

		Log.log(itemString);

		return itemString;
	}

}
