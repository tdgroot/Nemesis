package nl.tdegroot.games.nemesis.map;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.map.action.Action;
import nl.tdegroot.games.nemesis.map.action.OpenAction;
import nl.tdegroot.games.nemesis.map.object.ContainerObject;
import nl.tdegroot.games.nemesis.map.object.MapObject;
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

					String actionType = map.getObjectProperty(objectLayer, i, "action", "");
					String itemList = map.getObjectProperty(objectLayer, i, "items", "");

					Log.log("Action: " + actionType + ", Items: " + itemList);

					if (actionType != "" && itemList != "") {
						MapObject object;
						Action action = getAction(actionType);

						object = new ContainerObject(action, itemList);
						objects[xx + yy * width] = object;
						size++;
					} else if (actionType != "") {
						MapObject object;
						Action action = getAction(actionType);

						object = new MapObject(action);
						objects[xx + yy * width] = object;
						size++;
					}

				}

			}
		}

		Log.log("ObjectMap initialized! Amount of objects: " + size);

	}

	public MapObject getMapObject(int x, int y) {
		return objects[x + y * width];
	}

	private Action getAction(String type) {
		Action action = null;

		switch (type) {
			case "open":
				action = new OpenAction();
				break;
		}

		return action;
	}
}
