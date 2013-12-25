package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.entity.npc.ShopNPC;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.item.Arrow;
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

    private int amount = 1;
    private Item currentItem;
    private boolean selection = false;

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
            Resources.interact.play();
            game.setMenu(null);
        }

        int checkSel = selected - 1;
        if (Keyboard.isKeyDown(Keyboard.KEY_UP) && kt <= 0) {
            if (checkSel < 0) {
                Resources.select.play();
                kt = 200;
                return;
            }
            if (items[currentBank][checkSel] != null) {
                selected--;
                selection = false;
                amount = 1;
                Resources.select.play();
                kt = 200;
            } else {
                Resources.select.play();
                kt = 200;
            }
        }

        checkSel = selected + 1 < slotsPerBank ? selected + 1 : selected;
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && kt <= 0) {
            if (items[currentBank][checkSel] != null) {
                selected++;
                selection = false;
                amount = 1;
                Resources.select.play();
                kt = 200;
            } else {
                Resources.select.play();
                kt = 200;
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT) && kt <= 0) {
            if (selection) {
                amount--;
                if (amount < 0) amount = 0;
            } else {
                currentBank--;
                selection = false;
                amount = 1;
                Resources.select.play();
                kt = 200;
                selected = 0;
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT) && kt <= 0) {
            if (selection) {
                amount++;
                int max = 1;
                if (currentItem instanceof ItemStack) max = ((ItemStack) currentItem).size();
                if (amount > max) amount = max;
            } else {
                currentBank++;
                selection = false;
                amount = 1;
                Resources.select.play();
                kt = 200;
                selected = 0;
            }
        }

        if (selected < 0) selected += slotsPerBank;
        if (selected >= slotsPerBank) selected -= slotsPerBank;

        if (currentBank < 0) currentBank += banks;
        if (currentBank >= banks) currentBank -= banks;
        currentItem = items[currentBank][selected];

        if (Keyboard.isKeyDown(Keyboard.KEY_X) && kt <= 0) {
            if (selection) {
                buy();
            } else {
                select();
            }
        }

    }

    private void select() {
        selection = true;
        Resources.select.play();
        kt = 450;
    }

    private void buy() {
        Player player = game.getPlayer();
        Item item = items[currentBank][selected];
        kt = 500;
        if (item instanceof ItemStack) {
            ItemStack stack = (ItemStack) item;
            if (player.getCash() < stack.item.buyCost || amount == 0) {
                Resources.interact_fail.play();
                return;
            }
            for (int i = 0; i < amount; i++) {
                if (stack.item instanceof Arrow) {
                    if (!Resources.interact.playing()) Resources.interact.play();
                    stack.remove(1);
                    player.addArrows(1);
                    player.chargeCash(stack.item.buyCost);
                    Log.log("Arrows: " + player.getArrows());
                } else {
                    if (stack.item.equipable()) {
                        if (!Resources.interact.playing()) Resources.interact.play();
                        stack.remove(1);
                        player.equip(stack.item);
                        player.chargeCash(stack.item.buyCost);
                    } else {
                        if (!Resources.interact.playing()) Resources.interact.play();
                        stack.remove(1);
                        player.giveItem(stack.item);
                        player.chargeCash(stack.item.buyCost);
                    }
                }
            }
        }
    }

    public void render(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0, 175));
        graphics.fillRect(0, 0, Display.getWidth(), Display.getHeight());
        graphics.setColor(new Color(0x242424));
        graphics.fillRect(250, 150, 720, 400);
        graphics.setColor(new Color(0xFFFFFF));
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
        renderInfoPane(graphics);
    }

    public void renderInfoPane(Graphics graphics) {
        int offsetX = 720, offsetY = 175;
        graphics.setColor(new Color(0xAA343434));
        graphics.fillRect(offsetX, offsetY, 200, 350);
        graphics.setColor(new Color(0xFFFFFF));
        if (currentItem != null) {
            String name = currentItem.getName();
            int price = currentItem.buyCost;
            graphics.drawString(name, offsetX + 30, offsetY + 30);
            graphics.drawString("Price: " + price, offsetX + 30, offsetY + 60);
            if (selection) {
                graphics.drawString(amount + " x " + price + " = " + amount * price, offsetX + 30, offsetY + 90);
            }
        }
    }

}
