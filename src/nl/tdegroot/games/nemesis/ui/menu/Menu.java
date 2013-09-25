package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Nemesis;
import org.newdawn.slick.Graphics;

public class Menu {

	protected Nemesis game;
	public int selected = 0;
	public String[] items;
	protected int kt = 0;
	protected Menu parent;

	public Menu(Menu parent) {
		this.parent = parent;
	}

	public void init(Nemesis game) {
		this.game = game;
	}

	public void update(int delta) {
	}

	public void render(Graphics g) {
	}

	public boolean renderBackground() {
		return true;
	}

}
