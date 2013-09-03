package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.entity.npc.ShopNPC;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.List;

public class ShopMenu extends Menu {

	private ShopNPC npc;

	public ShopMenu(Menu parent, ShopNPC npc) {
		super(parent);
		this.npc = npc;
	}

	public void update(int delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_C)) {
			game.setMenu(parent);
		}
	}

	public void render(Screen screen) {
		Graphics graphics = screen.getGraphics();
		graphics.setColor(new Color(0, 0, 0, 175));
		graphics.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		graphics.setColor(new Color(255, 255, 255));
		for (int i = 0; i < npc.getItems().size(); i++) {
			Item item = npc.getItems().get(i);
			if (item instanceof ItemStack) {
				ItemStack itemStack = (ItemStack) item;
				graphics.drawString("" + itemStack.itemList.size(), 256, 235 + i * 64);
				itemStack.item.getSprite().draw(256, 256 + i * 64);
			} else {
				item.getSprite().draw((256) + i * 64, 256);
			}
		}
	}

	public void drawGrid(Graphics graphics) {
		int cellWidth = Display.getWidth() / 20;
		int cellHeight = Display.getHeight() / 12;

		for (int i = 4; i < 20; i++) {
			graphics.drawLine(i * cellWidth, 0, i * cellWidth, Display.getHeight());
		}
		for (int i = 4; i < 12; i++) {
			graphics.drawLine(0, i * cellHeight, Display.getWidth(), i * cellHeight);
		}
	}

}
