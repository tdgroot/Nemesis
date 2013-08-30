package nl.tdegroot.games.nemesis.map;

import org.newdawn.slick.tiled.TiledMap;

public class MapLayer {

	public static int MAP_LAYER_COLLISION;
	public static int MAP_LAYER_SPAWNERS;
	public static int MAP_LAYER_PLAYER;
	public static int MAP_LAYER_MAP_OBJECTS;
	public static int MAP_LAYER_WATER;


	public MapLayer(TiledMap map) {
		MAP_LAYER_COLLISION = map.getObjectLayerIndex("Collision");
		MAP_LAYER_SPAWNERS = map.getObjectLayerIndex("Spawners");
		MAP_LAYER_PLAYER = map.getObjectLayerIndex("Player");
		MAP_LAYER_MAP_OBJECTS = map.getObjectLayerIndex("MapObjects");
		MAP_LAYER_WATER = map.getObjectLayerIndex("WaterLayer");
	}

}
