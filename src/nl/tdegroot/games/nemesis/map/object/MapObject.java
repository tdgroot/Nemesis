package nl.tdegroot.games.nemesis.map.object;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.item.Item;

public class MapObject {

	protected String message;

	public MapObject() {
	}

	public void interact(Player player) {
	}

	public void addItem(Item item, int count) {
	}

	public void addArrows(int amount) {
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
