package nl.tdegroot.games.nemesis.item;

import org.newdawn.slick.Image;

public class FoodItem extends Item {

	public double healPoints;

	public FoodItem(String name, Image image, int buyCost, int sellCost) {
		super(name, image, buyCost, sellCost);
	}

	public boolean eatable() {
		return true;
	}

}
