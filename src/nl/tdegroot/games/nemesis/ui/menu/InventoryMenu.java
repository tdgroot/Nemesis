package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.entity.Inventory;
import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class InventoryMenu extends Menu {

	Inventory inventory;
	private Item[] slots;
	private int width, height;
	private int x, y;
	private int slotSize;

	public InventoryMenu(Menu parent, Inventory inventory) {
		super(parent);
		this.inventory = inventory;
		width = inventory.width;
		height = inventory.height;
		slots = new Item[width * height];
		int ix = 0;
		int iy = 0;
		for (int i = 0; i < inventory.size(); i++) {
			slots[ix + iy * width] = inventory.get(i);
			ix++;
			if (ix > width) {
				iy++;
				ix = 0;
			}
		}
		slotSize = 48;
		kt = 400;
	}

	public void update(int delta) {
		if (kt > 0) kt -= delta;

		if ((Keyboard.isKeyDown(Keyboard.KEY_C) || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_I)) && kt <= 0)
			game.setMenu(null);

		if (Keyboard.isKeyDown(Keyboard.KEY_UP) && kt <= 0) {
			if (y - 1 >= 0)
				y--;
			kt = 200;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && kt <= 0) {
			if (y + 1 <= height)
				y++;
			kt = 200;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && kt <= 0) {
			if (x - 1 >= 0)
				x--;
			kt = 200;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && kt <= 0) {
			if (x + 1 <= width)
				x++;
			kt = 200;
		}


	}

	public void render(Graphics graphics) {
		int xOffset = ((Display.getWidth() / 2) - (width * slotSize) / 2);
		int yOffset = ((Display.getHeight() / 2) - (height * slotSize) / 2);
		int xa = x * slotSize;
		int ya = y * slotSize;
		graphics.setColor(new Color(0xBF232323));
		graphics.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		for (int xx = 0; xx <= width; xx++) {
			for (int yy = 0; yy <= height; yy++) {
				graphics.setColor(new Color(0x434343));
				graphics.fillRect(xOffset + xx * slotSize, yOffset + yy * slotSize, slotSize, slotSize);
				graphics.setColor(new Color(0x939393));
				graphics.drawRect(xOffset + xx * slotSize, yOffset + yy * slotSize, slotSize, slotSize);
			}
		}
		graphics.setColor(Color.white);
		int ix = 0;
		int iy = 0;
		for (int i = 0; i < slots.length; i++) {
			if (slots[i] == null) continue;
			Item item = slots[i];
			int tx = slotSize - item.getSprite().getWidth();
			int ty = slotSize - item.getSprite().getHeight();
			int renderX = xOffset + ix * slotSize + tx / 2;
			int renderY = yOffset + iy * slotSize + ty / 2;
			if (item instanceof ItemStack) {
				ItemStack stack = (ItemStack) item;
				stack.item.getSprite().draw(renderX, renderY);
				graphics.drawString("" + stack.itemList.size(), renderX - 5, renderY - 8);
			} else {
				item.getSprite().draw(renderX, renderY);
			}
			ix++;
			if (ix >= width) {
				iy++;
				ix = 0;
			}
		}
		graphics.drawRect(xOffset + xa, yOffset + ya, slotSize, slotSize);
	}

}
