package nl.tdegroot.games.nemesis.spawner;

import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.level.Level;

import java.util.Random;

public class RoachSpawner extends MobSpawner {

	public RoachSpawner(Level level, int x, int y) {
		super(level, x, y);
		spawnTime = 20000;
		maxMobs = 4;
		minRange = 1;
		maxRange = 15;
	}

	public void update() {
		timer++;
		if (timer == spawnTime && mobs.size() < maxMobs) {
			spawnMob();
			timer = 0;
		}
	}

	public void spawnMob() {
		Random random = new Random();
		Mob mob = Mob.roach;
		mob.setX((float) generateX());
		mob.setY((float) generateY());
		addMob(mob);
	}

}
