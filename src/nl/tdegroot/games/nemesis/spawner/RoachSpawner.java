package nl.tdegroot.games.nemesis.spawner;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Roach;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.level.Level;

import java.util.Random;

public class RoachSpawner extends MobSpawner {

	public RoachSpawner(Level level, int x, int y) {
		super(level, x, y);
		spawnTime = 5000 / 59;
		maxMobs = 5;
		minRange = 1;
		maxRange = 15;
	}

	public void update() {
		timer++;
		if (timer == spawnTime && mobs.size() < maxMobs) {
			spawnMob();
			timer = 0;
		}
//		Log.log("Amount of roaches: " + mobs.size());
	}

	public void spawnMob() {
		Mob mob = new Roach(Resources.roach, 0, 0, 64, 59);
		mob.setX(generateX());
		mob.setY(generateY());
		mob.init(level);
		addMob(mob);
		Log.log("Spawned a new mob at: " + mob.getX() + ", " + mob.getY());
	}

}
