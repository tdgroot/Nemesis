package nl.tdegroot.games.nemesis.level;

import org.newdawn.slick.tiled.TiledMap;

public class CollisionMap {

	private boolean[] solid;
	private int width, height;

	public CollisionMap(TiledMap map, int tileSize) {

		width = map.getWidth() * tileSize;
		height = map.getHeight() * tileSize;

		solid = new boolean[width * height];

		for (int i = 0; i <= map.getObjectCount(MapLayer.MAP_LAYER_COLLISION); i++) {

			int x = (map.getObjectX(MapLayer.MAP_LAYER_COLLISION, i) + map.getObjectWidth(MapLayer.MAP_LAYER_COLLISION, i)) / tileSize;
			int y = (map.getObjectY(MapLayer.MAP_LAYER_COLLISION, i) + map.getObjectHeight(MapLayer.MAP_LAYER_COLLISION, i)) / tileSize;

			for (int xx = (map.getObjectX(MapLayer.MAP_LAYER_COLLISION, i) / tileSize); xx < x; xx++) {
				for (int yy = (map.getObjectY(MapLayer.MAP_LAYER_COLLISION, i) / tileSize); yy < y; yy++) {

					solid[xx + yy * width] = true;

				}
			}
		}
	}

	public boolean isSolid(int x, int y) throws ArrayIndexOutOfBoundsException {
		return solid[x + y * width];
	}

}
