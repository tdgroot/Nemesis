package nl.tdegroot.games.nemesis.spawner;

import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.level.Level;

import java.util.Random;

public class MobSpawner {

	protected Level level;
	Random random = new Random();

	private final int x, y;

	protected int spawnerID = 0;

	protected int minRange = 0;
	protected int maxRange = 0;

	protected int spawnTime = 0;
	protected int maxMobs = 0;
	protected int mobsSpawned = 0;
	protected int mobsAlive = 0;

	protected long timer = 0;

	public MobSpawner(Level level, int x, int y, int spawnerID) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.spawnerID = spawnerID;
	}

	public void update() {
	}

	public void addMob(Mob mob) {
		mobsAlive++;
		level.addMob(mob);
//		Log.log("Spawned a mob with ID: " + spawnerID + "||" + mob.getID());
	}

	public int generateX() {
		int randomX = 0;
		randomX = random.nextInt(30);
		while (randomX < 0 || randomX > level.getWidthInTiles()) {
			randomX = random.nextInt(30);
		}
		return randomX;
	}

	public int generateY() {
		int randomY = 0;
		randomY = random.nextInt(30);
		while (randomY < 0 || randomY > level.getHeightInTiles()) {
			randomY = random.nextInt(30);
		}
		return randomY;
	}

	public int getID() {
		return spawnerID;
	}

	public void removeMob() {
		mobsAlive--;
	}
}
