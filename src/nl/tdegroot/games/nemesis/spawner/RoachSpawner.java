package nl.tdegroot.games.nemesis.spawner;

import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.entity.Roach;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.level.Level;

public class RoachSpawner extends MobSpawner {

	public RoachSpawner(Level level, Player target, int x, int y, int spawnerID) {
		super(level, target, x, y, spawnerID);
		spawnTime = 10000 / 59;
		maxMobs = 25;
		minRange = 1;
		maxRange = 5;
	}

	public void update(int delta) {
		timer += delta;
		if (timer >= spawnTime && mobsAlive < maxMobs) {
			spawnMob();
			timer = 0;
		}
	}

	public void spawnMob() {
		mobsSpawned++;
		float newX = generateX();
		float newY = generateY();
		while (level.isCollision((int) newX * level.tileSize, (int) newY * level.tileSize)) {
			newX = generateX();
			newY = generateY();
		}
		Mob mob = new Roach(Resources.roach, newX, newY, 64, 59, mobsSpawned, spawnerID);
		mob.init(level, target);

		if (mobHealth > 0.0)
			mob.setHealth(mobHealth);

		if (movementSpeed > 0.0f)
			mob.setMovementSpeed(movementSpeed);

		if (mobScore > 0)
			mob.setScore(mobScore);

		if (mobDamage > 0.0) {
			mob.setDamage(mobDamage);
		}

		if (getMobCash() > 0){
			mob.setCash(mobCash);
		}

		addMob(mob);
	}

}
