package nl.tdegroot.games.nemesis.map;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.npc.NPC;
import nl.tdegroot.games.nemesis.entity.npc.ShopNPC;
import nl.tdegroot.games.nemesis.entity.npc.Steve;
import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.item.ItemStack;
import nl.tdegroot.games.nemesis.level.Level;

import org.newdawn.slick.tiled.TiledMap;

public class NPCMap {

	private Level level;
	private NPC[] npcs;

	private int width, height;
	private int objectLayer;

	public NPCMap(TiledMap map, Level level, int tileSize) {
		this.level = level;

		width = map.getWidth() * tileSize;
		height = map.getHeight() * tileSize;

		objectLayer = map.getObjectLayerIndex("NPC");

		npcs = new NPC[width * height];

		for (int i = 0; i <= map.getObjectCount(objectLayer); i++) {

			int x = (map.getObjectX(objectLayer, i) + map.getObjectWidth(objectLayer, i)) / tileSize;
			int y = (map.getObjectY(objectLayer, i) + map.getObjectHeight(objectLayer, i)) / tileSize;

			for (int xx = (map.getObjectX(objectLayer, i) / tileSize); xx < x; xx++) {
				for (int yy = (map.getObjectY(objectLayer, i) / tileSize); yy < y; yy++) {

					String type = map.getObjectProperty(objectLayer, i, "type", "");
					String name = map.getObjectProperty(objectLayer, i, "name", "");
					String arrows = map.getObjectProperty(objectLayer, i, "arrows", "");
					String message = map.getObjectProperty(objectLayer, i, "message", "");
					String itemList = map.getObjectProperty(objectLayer, i, "items", "");

					NPC npc = null;

					Log.log("Type: " + type);

					switch (type) {
						case "shop":
							npc = processShop(name, itemList, Integer.parseInt(arrows), message, xx, yy, i);
							break;
					}

					npcs[xx + yy * width] = npc;
				}
			}
		}
	}

	private ShopNPC processShop(String name, String itemList, int arrows, String message, float x, float y, int id) {
		ShopNPC npc = null;

		switch (name) {
			case "steve":
				npc = new Steve(x, y, id);
				break;
		}


		if (arrows > 0) {
			if (arrows > 1) {
				npc.addItem(new ItemStack(Item.arrow, arrows), 1);
			} else {
				npc.addItem(Item.arrow, 1);
			}
		}

		if (itemList != "") {
			int openBracket = itemList.indexOf("{");
			int closeBracket = itemList.lastIndexOf("}");

			String params = itemList.substring(openBracket + 1, closeBracket);
			String[] paramList = params.split(",");
			for (int i = 0; i < paramList.length; i++) {
				String[] pData = paramList[i].trim().split(":");
				Item item = Item.getItem(pData[0].trim());
				int count = Integer.parseInt(pData[1].trim());
				if (count > 1) {
					npc.addItem(new ItemStack(item, count), 1);
				} else if (count == 1) {
					npc.addItem(item, 1);
				}
			}
		}

		if (npc != null) {
			if (arrows > 0)
				npc.addArrows(arrows);
			if (message != "")
				npc.setMessage(message);
			level.addEntity(npc);
			Log.log("Added npc!");
		}

		return npc;
	}

	public NPC getNPC(int x, int y) {
		return npcs[x + y * width];
	}
}
