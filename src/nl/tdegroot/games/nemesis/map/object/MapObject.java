package nl.tdegroot.games.nemesis.map.object;

import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.item.Item;
import actions.Interactable;

public class MapObject implements Interactable {

	private float x, y;
	protected String message;
	protected String name;

	public void addItem(Item item, int count) {
	}

	public void addArrows(int amount) {
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void interact(Player player, Nemesis game) {
	}

	public void talk() {
	}

	public void describe() {
	}

	public String[] getInteractItems() {
		return new String[]{"Interact", "Describe"};
	}

	public String[] getDisplayItems() {
		return new String[]{"Interact", "Describe"};
	}

	public String getName() {
		return name;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}

	public void setName(String name) {
		this.name = name;
	}

}
