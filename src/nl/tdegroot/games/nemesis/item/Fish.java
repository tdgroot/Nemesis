package nl.tdegroot.games.nemesis.item;

import nl.tdegroot.games.nemesis.gfx.Resources;

public class Fish extends FoodItem {

	public Fish(int buyCost, int sellCost) {
		super("Fish", Resources.fish, buyCost, sellCost);
		healPoints = 22.5;
	}

	public boolean stackable() {
		return true;
	}

}
