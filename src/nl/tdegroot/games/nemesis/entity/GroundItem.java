package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.item.Item;

/**
 * Created by timon on 12/15/13.
 */
public class GroundItem extends Entity {

	private Item item;
	private int life = 0;

	public GroundItem(float x, float y, Item item, int life) {
		super(item.getSprite(), x, y, 64, 64);
		this.item = item;
		this.life = life;
	}

	public void update(int delta) {
		if (life > 0) life -= delta;
		if (life <= 0) remove();
	}

	public void render(Screen screen) {
		screen.render(x, y, item.getSprite());
	}

}
