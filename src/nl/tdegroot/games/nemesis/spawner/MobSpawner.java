package nl.tdegroot.games.nemesis.spawner;

import nl.tdegroot.games.nemesis.calc.GameUtil;
import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.level.Level;

import java.util.Random;

public class MobSpawner {

	protected Level level;
	protected Player target;
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

	protected double mobHealth = 0.0;
	protected float movementSpeed = 0.0f;
	protected int mobScore = 0;
	protected double mobDamage = 0.0;
	protected double mobCash = 0;

	public MobSpawner(Level level, Player target, int x, int y, int spawnerID) {
		this.level = level;
		this.target = target;
		this.x = x;
		this.y = y;
		this.spawnerID = spawnerID;
	}

	public void update(int delta) {
	}

	public int getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(int maxRange) {
		this.maxRange = maxRange;
	}

	public int getSpawnTime() {
		return spawnTime;
	}

	public void setSpawnTime(int spawnTime) {
		this.spawnTime = spawnTime;
	}

	public int getMaxMobs() {
		return maxMobs;
	}

	public void setMobScore(int mobScore) {
		this.mobScore = mobScore;
	}

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public void setMobHealth(double mobHealth) {
		this.mobHealth = mobHealth;
	}

	public void setMaxMobs(int maxMobs) {
		this.maxMobs = maxMobs;
	}

	public void addMob(Mob mob) {
		mobsAlive++;
		level.addMob(mob);
//		Log.log("Spawned a mob with ID: " + spawnerID + "||" + mob.getID());
	}

	public int generateX() {
		int randomX = 0;
		randomX = GameUtil.random(x - maxRange, x + (maxRange * 2));
		while (randomX < 0 || randomX > level.getWidthInTiles()) {
			randomX = GameUtil.random(x - maxRange, x + (maxRange * 2));
		}
		return randomX;
	}

	public int generateY() {
		int randomY = 0;
		randomY = GameUtil.random(y - maxRange, y + (maxRange * 2));
		while (randomY < 0 || randomY > level.getHeightInTiles()) {
			randomY = GameUtil.random(y - maxRange, y + (maxRange * 2));
		}
		return randomY;
	}

	public int getID() {
		return spawnerID;
	}

	public void removeMob() {
		mobsAlive--;
	}

	public void setMobDamage(double mobDamage) {
		this.mobDamage = mobDamage;
	}

	public double getMobDamage() {
		return mobDamage;
	}

	public void setMobCash(double mobCash) {
		this.mobCash = mobCash;
	}

	public double getMobCash() {
		return mobCash;
	}
}
