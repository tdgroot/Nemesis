package nl.tdegroot.games.nemesis.level;

import org.newdawn.slick.tiled.TiledMap;

public class CollisionMap {

	private boolean[][] solid;

	public CollisionMap(TiledMap map, int tileSize) {
		solid = new boolean[map.getWidth()][map.getHeight()];

		for (int i = 0; i <= map.getObjectCount(MapLayer.MAP_LAYER_COLLISION); i++) {

			int x = (map.getObjectX(MapLayer.MAP_LAYER_COLLISION, i) + map.getObjectWidth(MapLayer.MAP_LAYER_COLLISION, i)) / tileSize;
			int y = (map.getObjectY(MapLayer.MAP_LAYER_COLLISION, i) + map.getObjectHeight(MapLayer.MAP_LAYER_COLLISION, i)) / tileSize;

			for (int xx = (map.getObjectX(MapLayer.MAP_LAYER_COLLISION, i) / tileSize); xx < x; xx++) {
				for (int yy = (map.getObjectY(MapLayer.MAP_LAYER_COLLISION, i) / tileSize); yy < y; yy++) {

					solid[xx][yy] = true;

				}
			}
		}
	}

	public boolean isSolid(int x, int y) {
		return solid[x][y];
	}

}
