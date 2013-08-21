package nl.tdegroot.games.nemesis.spawner;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Roach;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.level.Level;

public class RoachSpawner extends MobSpawner {

	public RoachSpawner(Level level, int x, int y, int spawnerID) {
		super(level, x, y, spawnerID);
		spawnTime = 5000 / 59;
		maxMobs = 1;
		minRange = 1;
		maxRange = 15;
	}

	public void update() {
		timer++;
		if (timer == spawnTime && mobsAlive < maxMobs) {
			spawnMob();
			timer = 0;
		}
//		Log.log("Amount of roaches: " + mobs.size());
	}

	public void spawnMob() {
		mobsSpawned++;
		Mob mob = new Roach(Resources.roach, generateX(), generateY(), 64, 59, mobsSpawned);
		mob.init(level);
		addMob(mob);
		Log.log("Spawned a new mob at: " + mob.getX() / level.getTileSize() + ", " + mob.getY() / level.getTileSize());
	}

}
