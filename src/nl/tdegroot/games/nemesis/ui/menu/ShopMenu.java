package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.entity.npc.ShopNPC;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.item.Arrow;
import nl.tdegroot.games.nemesis.item.Bow;
import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class ShopMenu extends Menu {

	private ShopNPC npc;
	private int currentBank = 0;
	private final int slotSize = 61;
	private final int slotsPerBank;
	private int banks = 1;
	private Item[][] items;

	private int bt = 0;

	public ShopMenu(Menu parent, ShopNPC npc) {
		super(parent);
		this.npc = npc;
		kt = 300;
		slotsPerBank = (Display.getHeight() - 350) / slotSize;
		initItems();
	}

	public void initItems() {
		if (npc.getItems().size() > slotsPerBank)
			banks = (int) Math.ceil((double) npc.getItems().size() / (double) slotsPerBank);
		items = new Item[banks][slotsPerBank];
		for (int b = 0; b < banks; b++) {
			for (int s = 0; s < slotsPerBank; s++) {
				int index = s;
				if (b > 0) index = slotsPerBank + s;
				if (index >= npc.getItems().size()) break;
				Log.log("Initializing Shop: " + b + "|" + s + "|" + index);
				items[b][s] = npc.getItems().get(index);
			}
		}
	}

	public void update(int delta) {
		if (kt > 0) kt -= delta;
		if (bt > 0) bt -= delta;
		if ((Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_C)) && kt <= 0) {
			game.setMenu(null);
		}

		int checkSel = Math.abs(selected - 1);
		if (Keyboard.isKeyDown(Keyboard.KEY_UP) && kt <= 0 && items[currentBank][checkSel] != null) {
			kt = 200;
			selected--;
		}

		checkSel = selected + 1 < slotsPerBank ? selected + 1 : selected;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && kt <= 0 && items[currentBank][checkSel] != null) {
			kt = 200;
			selected++;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && kt <= 0) {
			kt = 200;
			currentBank--;
			selected = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && kt <= 0) {
			kt = 200;
			currentBank++;
			selected = 0;
		}

		if (selected < 0) selected += slotsPerBank;
		if (selected >= slotsPerBank) selected -= slotsPerBank;

		if (currentBank < 0) currentBank += banks;
		if (currentBank >= banks) currentBank -= banks;

		if (Keyboard.isKeyDown(Keyboard.KEY_X) && bt <= 0 && kt <= 0) {
			Player player = game.getPlayer();
			Item item = items[currentBank][selected];
			if (item instanceof ItemStack) {
				ItemStack stack = (ItemStack) item;
				if (stack.item instanceof Arrow) {
					if (player.getScore() >= stack.item.buyCost) {
						stack.remove(1);
						player.addArrows(1);
						player.distractScore(stack.item.buyCost);
						bt = 60;
						Log.log("Arrows: " + player.getArrows());
					}
				} else {
					if (stack.item.isEquipable()) {
						stack.remove(1);
						player.equip((Bow) item);

					}
				}
			}
		}

	}

	public void render(Screen screen) {
		Graphics graphics = screen.getGraphics();
		graphics.setColor(new Color(0, 0, 0, 175));
		graphics.fillRect(0, 0, Display.getWidth(), Display.getHeight());
		graphics.setColor(new Color(255, 255, 255));
		for (int i = 0; i < slotsPerBank; i++) {
			Item item = items[currentBank][i];
			if (item == null) continue;
			if (item instanceof ItemStack) {
				ItemStack stack = (ItemStack) item;
				stack.getSprite().draw(256, 175 + i * slotSize);
				graphics.drawString("" + stack.itemList.size(), 256, 160 + i * slotSize);
			} else {
				item.getSprite().draw(256, 175 + i * slotSize);
			}
			if (i == selected) {
				graphics.drawRect(256, 159 + i * slotSize, 128, slotSize);
			}
		}
	}

}
