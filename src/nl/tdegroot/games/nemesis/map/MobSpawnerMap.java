package nl.tdegroot.games.nemesis.map;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.level.Level;
import nl.tdegroot.games.nemesis.spawner.MobSpawner;
import nl.tdegroot.games.nemesis.spawner.RoachSpawner;
import org.newdawn.slick.tiled.TiledMap;

public class MobSpawnerMap {

	private Level level;

	public MobSpawnerMap(Level level, TiledMap map) {
		this.level = level;
		for (int i = 0; i <= map.getObjectCount(MapLayer.MAP_LAYER_SPAWNERS); i++) {

			int x = (map.getObjectX(MapLayer.MAP_LAYER_SPAWNERS, i) + map.getObjectWidth(MapLayer.MAP_LAYER_SPAWNERS, i)) / level.tileSize;
			int y = (map.getObjectY(MapLayer.MAP_LAYER_SPAWNERS, i) + map.getObjectHeight(MapLayer.MAP_LAYER_SPAWNERS, i)) / level.tileSize;

			for (int xx = (map.getObjectX(MapLayer.MAP_LAYER_SPAWNERS, i) / level.tileSize); xx < x; xx++) {
				for (int yy = (map.getObjectY(MapLayer.MAP_LAYER_SPAWNERS, i) / level.tileSize); yy < y; yy++) {

					// Spawner properties
					String spawnerType = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "spawner", "");
					String spawnTime = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "spawnTime", "");
					String maxMobs = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "maxMobs", "");

					// Mob properties
					String mobScore = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "score", "");
					String mobHealth = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "health", "");
					String mobSpeed = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "speed", "");
					String mobDamage = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "damage", "");
					String mobCash = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "cash", "");

					if (spawnerType != "") {
						level.spawners.add(getSpawner(spawnerType, spawnTime, maxMobs, mobScore, mobHealth, mobSpeed, mobDamage, mobCash, xx, yy, i));
						Log.log("Spawner created with ID: " + i + ", mobHealth: " + mobHealth + ", mobSpeed: " + mobSpeed + ", mobScore: " + mobScore);
					}

				}
			}
		}
		Log.log("Mob spawners initialized! Amount of Mobspawners: " + level.spawners.size());
	}

	private MobSpawner getSpawner(String type, String spawnTime, String maxMobs, String mobScore, String mobHealth, String mobSpeed, String mobDamage, String mobCash, int x, int y, int i) {
		MobSpawner spawner = null;

		switch (type) {
			case "roach":
				spawner = new RoachSpawner(level, level.getPlayer(), x, y, i);
				break;
		}

		if (spawner != null) {

			if (spawnTime != "") {
				spawner.setSpawnTime(Integer.parseInt(spawnTime));
			}

			if (maxMobs != "") {
				spawner.setMaxMobs(Integer.parseInt(maxMobs));
			}

			if (mobScore != "") {
				spawner.setMobScore(Integer.parseInt(mobScore));
			}

			if (mobHealth != "") {
				spawner.setMobHealth(Double.parseDouble(mobHealth));
			}

			if (mobSpeed != "") {
				spawner.setMovementSpeed(Float.parseFloat(mobSpeed));
			}

			if (mobDamage != "") {
				spawner.setMobDamage(Double.parseDouble(mobDamage));
			}

			if (mobCash != "") {
				spawner.setMobCash(Double.parseDouble(mobCash));
			}

		}

		return spawner;
	}

}
