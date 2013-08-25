package nl.tdegroot.games.nemesis.map.object;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.ui.Dialog;

import java.util.ArrayList;
import java.util.List;

public class Chest extends MapObject {

	private int arrows = 0;
	private List<Item> items;

	public Chest() {
		super();
		items = new ArrayList<Item>();
	}

	public void interact(Player player) {
		if (! empty()) {
			Dialog.activate(message);
			for (int i = 0; i < items.size(); i++) {
				Log.log("Gifting item: " + i + ", of: " + items.size());
				player.giveItem(items.get(i));
			}
			player.addArrows(arrows);
		} else {
			Dialog.activate("Aww, this chest is empty! ");
		}
		items.clear();
		arrows = 0;
	}

	public void addItem(Item item, int count) {
		for (int i = 0; i < count; i++) {
			items.add(item);
		}
	}

	public void addArrows(int amount) {
		arrows += amount;
	}

	public List<Item> getItems() {
		return items;
	}

	public boolean empty() {
		return (items.size() <= 0 && arrows <= 0);
	}
}
