package nl.tdegroot.games.nemesis.entity.particles;

import nl.tdegroot.games.nemesis.gfx.Resources;

public class BloodParticle extends Particle {

	public BloodParticle(float x, float y, int amount, int life) {
		super(x, y, amount, life, Resources.bloodParticle);
	}

}
