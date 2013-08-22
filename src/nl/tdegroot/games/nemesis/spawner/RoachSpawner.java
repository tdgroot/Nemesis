package nl.tdegroot.games.nemesis.spawner;

import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Roach;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.level.Level;

public class RoachSpawner extends MobSpawner {

	public RoachSpawner(Level level, int x, int y, int spawnerID) {
		super(level, x, y, spawnerID);
		spawnTime = 10000 / 59;
		maxMobs = 25;
		minRange = 1;
		maxRange = 15;
	}

	public void update() {
		timer++;
		if (timer == spawnTime && mobsAlive < maxMobs) {
			spawnMob();
			timer = 0;
		}
	}

	public void spawnMob() {
		mobsSpawned++;
		float newX = generateX();
		float newY = generateY();
		while (level.isSolid((int) newX, (int) newY)) {
			newX = generateX();
			newY = generateY();
		}
		Mob mob = new Roach(Resources.roach, newX, newY, 64, 59, mobsSpawned, spawnerID);
		mob.init(level);
		addMob(mob);
//		Log.log("Spawned a new mob at: " + mob.getX() / level.getTileSize() + ", " + mob.getY() / level.getTileSize());
	}

}
