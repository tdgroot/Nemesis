package nl.tdegroot.games.nemesis.level;

import org.newdawn.slick.tiled.TiledMap;

public class MapLayer {

	private final TiledMap map;

	public static int MAP_LAYER_ACTIONS;
	public static int MAP_LAYER_PLAYER;
	public static int MAP_LAYER_SPAWNERS;
	public static int MAP_LAYER_COLLISION;

	public MapLayer(TiledMap map) {
		this.map = map;
		MAP_LAYER_COLLISION = map.getLayerCount() - 3;
		MAP_LAYER_PLAYER = map.getLayerCount() - 2;
		MAP_LAYER_SPAWNERS = map.getLayerCount() - 1;
		MAP_LAYER_COLLISION = map.getLayerCount();
	}

}
