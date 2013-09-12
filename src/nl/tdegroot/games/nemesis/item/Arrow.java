package nl.tdegroot.games.nemesis.item;

import nl.tdegroot.games.nemesis.gfx.Resources;

public class Arrow extends Item {

	public Arrow(int buyCost, int sellCost) {
		super("Arrow", Resources.itemArrow, buyCost, sellCost);
	}

}
