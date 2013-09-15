package nl.tdegroot.games.nemesis.item;

import org.newdawn.slick.Image;

public class Bow extends Item {

	public Bow(String name, Image image) {
		super("Bow", image, 25, 15);
	}

	public boolean equipable() {
		return true;
	}

}
