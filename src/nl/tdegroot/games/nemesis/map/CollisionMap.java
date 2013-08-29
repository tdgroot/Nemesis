package nl.tdegroot.games.nemesis.map;

import nl.tdegroot.games.nemesis.Log;
import org.newdawn.slick.tiled.TiledMap;

public class CollisionMap {

	private boolean[] solid;
	private boolean[] water;
	private int width, height;

	public CollisionMap(TiledMap map, int tileSize, int width, int height) {

		this.width = width;
		this.height = height;

		solid = new boolean[width * height];
		water = new boolean[width * height];

		Log.log("Map size: " + width * height);

		for (int i = 0; i <= map.getObjectCount(MapLayer.MAP_LAYER_COLLISION); i++) {

			int x = (map.getObjectX(MapLayer.MAP_LAYER_COLLISION, i) + map.getObjectWidth(MapLayer.MAP_LAYER_COLLISION, i));
			int y = (map.getObjectY(MapLayer.MAP_LAYER_COLLISION, i) + map.getObjectHeight(MapLayer.MAP_LAYER_COLLISION, i));

			for (int xx = (map.getObjectX(MapLayer.MAP_LAYER_COLLISION, i)); xx < x; xx++) {
				for (int yy = (map.getObjectY(MapLayer.MAP_LAYER_COLLISION, i)); yy < y; yy++) {

					solid[xx + yy * width] = true;

				}
			}
		}

		initWater(map, tileSize);
	}

	private void initWater(TiledMap map, int tileSize) {
		int waterLayer = MapLayer.MAP_LAYER_WATER;

		for (int i = 0; i <= map.getObjectCount(waterLayer); i++) {

			int x = (map.getObjectX(waterLayer, i) + map.getObjectWidth(waterLayer, i));
			int y = (map.getObjectY(waterLayer, i) + map.getObjectHeight(waterLayer, i));

			for (int xx = (map.getObjectX(waterLayer, i)); xx < x; xx++) {
				for (int yy = (map.getObjectY(waterLayer, i)); yy < y; yy++) {

					water[xx + yy * width] = true;

				}
			}
		}
	}

	public boolean isCollision(int x, int y) {
		return isSolid(x, y) || isWater(x, y);
	}

	public boolean isSolid(int x, int y) {
		return x >= width || y >= height || solid[x + y * width];
	}

	public boolean isWater(int x, int y) {
		return water[x + y * width];
	}

}
