package nl.tdegroot.games.nemesis.spawner;

import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MobSpawner {

	final protected List<Mob> mobs = new ArrayList<Mob>();

	protected Level level;
	final Random random = new Random();

	protected final int x, y;

	protected int minRange = 0;
	protected int maxRange = 0;

	protected int spawnTime = 0;
	protected int maxMobs = 0;

	protected long timer = 0;

	public MobSpawner(Level level, int x, int y) {
		this.level = level;
		this.x = x;
		this.y = y;
	}

	public void update() {
	}

	public void addMob(Mob mob) {
		mobs.add(mob);
		level.addMob(mob);
	}

	public int generateX() {
		int randomX = 0;
		randomX = (x - maxRange) + random.nextInt(maxRange);
		return randomX;
	}

	public int generateY() {
		int randomY = 0;
		randomY = (y - maxRange) + random.nextInt(maxRange - minRange + 1) + minRange;
		return randomY;
	}

}
