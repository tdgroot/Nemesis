package nl.tdegroot.games.nemesis.map;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.map.object.Chest;
import nl.tdegroot.games.nemesis.map.object.MapObject;
import nl.tdegroot.games.nemesis.map.object.Sign;
import org.newdawn.slick.tiled.TiledMap;

public class ObjectMap {

	private MapObject[] objects;

	private int width, height;
	private int objectLayer = MapLayer.MAP_LAYER_MAP_OBJECTS;

	public ObjectMap(TiledMap map, int tileSize) {
		width = map.getWidth() * tileSize;
		height = map.getHeight() * tileSize;

		objects = new MapObject[width * height];

		int size = 0;

		for (int i = 0; i <= map.getObjectCount(objectLayer); i++) {

			int x = (map.getObjectX(objectLayer, i) + map.getObjectWidth(objectLayer, i)) / tileSize;
			int y = (map.getObjectY(objectLayer, i) + map.getObjectHeight(objectLayer, i)) / tileSize;

			for (int xx = (map.getObjectX(objectLayer, i) / tileSize); xx < x; xx++) {
				for (int yy = (map.getObjectY(objectLayer, i) / tileSize); yy < y; yy++) {

					String type = map.getObjectProperty(objectLayer, i, "type", "");
					String arrows = map.getObjectProperty(objectLayer, i, "arrows", "");
					String message = map.getObjectProperty(objectLayer, i, "message", "");
					String itemList = map.getObjectProperty(objectLayer, i, "items", "");

					MapObject mapObject = null;

					Log.log("Type: " + type);

					if (type.equals("chest")) {
						mapObject = processChest(itemList);
					} else if (type.equals("sign")) {
						mapObject = new Sign();
					}

					if (mapObject != null) {
						if (arrows != "")
							mapObject.addArrows(Integer.parseInt(arrows));
						if (message != "")
							mapObject.setMessage(message);
					}

					objects[xx + yy * width] = mapObject;
					size++;

				}

			}
		}

		Log.log("ObjectMap initialized! Amount of objects: " + size);

	}

	private MapObject processChest(String itemList) {
		if (itemList == "") return null;
		MapObject mapObject = new Chest();

		int openBracket = itemList.indexOf("{");
		int closeBracket = itemList.lastIndexOf("}");

		String params = itemList.substring(openBracket + 1, closeBracket);
		String[] paramList = params.split(",");

		for (int i = 0; i < paramList.length; i++) {
			String[] pData = paramList[i].trim().split(":");
			Item item = Item.getItem(pData[0]);
			int count = Integer.parseInt(pData[1]);
			mapObject.addItem(item, count);
		}

		return mapObject;
	}

	private boolean isNumeric(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c)) return false;
		}
		return true;
	}

	public MapObject getMapObject(int x, int y) {
		return objects[x + y * width];
	}

}
