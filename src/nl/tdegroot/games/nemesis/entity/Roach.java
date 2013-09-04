package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.Log;
import org.newdawn.slick.Image;

public class Roach extends Mob {

	private int randomWalkTime = 0;
	float xa = 0;
	float ya = 0;
	int followRange = 0;

	public Roach(Image image, float x, float y, int width, int height, int mobID, int spawnerID) {
		super(image, x, y, width, height, mobID, spawnerID);
		movementSpeed = 1.5f;
		health = 45.0;

		collisionMulX = 78;
		collisionAddX = 16;

		collisionMulY = 30;
		collisionAddY = 29;

		ldDefault = 1000;
		followRange = 415;
	}

	public void update(int delta) {
		super.update(delta);
		Player player = level.getPlayer();

		if (level.getPlayer() != null && randomWalkTime <= 0) {
			float xd = player.x - x;
			float yd = player.y - y;
			if (xd * xd + yd * yd < followRange * followRange) {
				if (!(xd * xd + yd * yd > 10 * 10)) {
					if (vulnerability.intersects(target.getVulnerability()) && ht <= 0) {
						player.hurt(this);
						ht = 750;
					}
				}
				xa = 0;
				ya = 0;
				if (xd < -2) xa -= movementSpeed * delta * deltaMul;
				if (xd > 2) xa += movementSpeed * delta * deltaMul;
				if (yd < -2) ya -= movementSpeed * delta * deltaMul;
				if (yd > 2) ya += movementSpeed * delta * deltaMul;
				Log.log("Roach in range!");

			} else if (random.nextInt(200) == 0) {
				randomWalkTime = 1250;
				xa = ((random.nextInt(3) - 1) * random.nextInt(2)) * delta * 0.12f;
				ya = ((random.nextInt(3) - 1) * random.nextInt(2)) * delta * 0.12f;
			}
		}

		if (xa != 0 || ya != 0) {
			move(xa, ya, delta);
		}

		if (randomWalkTime > 0) randomWalkTime -= delta;
		if (ht > 0) ht -= delta;

		vulnerability.setLocation(x + 10, y + 10);

	}

}
