package nl.tdegroot.games.nemesis.item;

import nl.tdegroot.games.nemesis.gfx.Resources;

public class Bow extends Item {

	public Bow(int buyCost, int sellCost) {
		super("Bow", Resources.bow, sellCost, buyCost);
	}

	public boolean equipable() {
		return true;
	}

}
