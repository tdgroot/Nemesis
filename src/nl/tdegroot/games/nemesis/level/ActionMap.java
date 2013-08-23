package nl.tdegroot.games.nemesis.level;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.action.Action;
import nl.tdegroot.games.nemesis.action.OpenAction;
import org.newdawn.slick.tiled.TiledMap;

public class ActionMap {

	private Action[] actions;

	private int width, height;

	public ActionMap(TiledMap map, int tileSize) {
		width = map.getWidth() * tileSize;
		height = map.getHeight() * tileSize;

		actions = new Action[width * height];

		int size = 0;

		for (int i = 0; i <= map.getObjectCount(MapLayer.MAP_LAYER_ACTIONS); i++) {

			int x = (map.getObjectX(MapLayer.MAP_LAYER_ACTIONS, i) + map.getObjectWidth(MapLayer.MAP_LAYER_ACTIONS, i)) / tileSize;
			int y = (map.getObjectY(MapLayer.MAP_LAYER_ACTIONS, i) + map.getObjectHeight(MapLayer.MAP_LAYER_ACTIONS, i)) / tileSize;

			for (int xx = (map.getObjectX(MapLayer.MAP_LAYER_ACTIONS, i) / tileSize); xx < x; xx++) {
				for (int yy = (map.getObjectY(MapLayer.MAP_LAYER_ACTIONS, i) / tileSize); yy < y; yy++) {

					String action = map.getObjectProperty(MapLayer.MAP_LAYER_ACTIONS, i, "action", "");

					if (action != "") {
						actions[xx + yy * width] = getAction(action);
					}

				}
			}
		}

		Log.log("Actionmap initialized! Amount of actions: " + size);

	}

	public Action getAction(int x, int y) {
		return actions[x + y * width];
	}

	private Action getAction(String type) {
		Log.log(type);
		Action action = null;

		switch (type) {
			case "open":
				action = new OpenAction();
				break;
		}

		return action;
	}
}
