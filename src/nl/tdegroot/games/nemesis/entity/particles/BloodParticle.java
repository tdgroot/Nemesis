package nl.tdegroot.games.nemesis.entity.particles;

import nl.tdegroot.games.nemesis.gfx.Resources;
import org.newdawn.slick.Image;

public class BloodParticle extends Particle {


	public BloodParticle(float x, float y, int life) {
		super(x, y, life, Resources.bloodParticle);
	}

	public BloodParticle(float x, float y, int amount, int life) {
		super(x, y, amount, life, Resources.bloodParticle);
	}

}
