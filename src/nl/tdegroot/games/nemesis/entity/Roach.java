package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.Log;
import org.newdawn.slick.Image;

import java.util.Random;

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
		followRange = 400;
	}

	public void update(int delta) {
		super.update(delta);
		Random random = new Random();
		Player player = level.getPlayer();

		if (level.getPlayer() != null && randomWalkTime <= 0) {
			float xd = player.x - x;
			float yd = player.y - y;
			if (xd * xd + yd * yd < followRange * followRange) {
				if (xd * xd + yd * yd > 10 * 10) {
					xa = 0;
					ya = 0;
					if (xd < -1) xa -= movementSpeed * delta * deltaMul;
					if (xd > 1) xa += movementSpeed * delta * deltaMul;
					if (yd < -1) ya -= movementSpeed * delta * deltaMul;
					if (yd > 1) ya += movementSpeed * delta * deltaMul;
					Log.log("Roach in range!");
				} else {
					return;
				}
			} else if (random.nextInt(200) == 0) {
				randomWalkTime = 1250;
				xa = ((random.nextInt(3) - 1) * random.nextInt(2)) * delta * 0.12f;
				ya = ((random.nextInt(3) - 1) * random.nextInt(2)) * delta * 0.12f;
			}
		}

		move(xa, ya, delta);

		if (randomWalkTime > 0) randomWalkTime -= delta;

		vulnerability.setLocation(x + 10, y + 10);
	}

}
