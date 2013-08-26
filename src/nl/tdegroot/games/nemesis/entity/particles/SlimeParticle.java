package nl.tdegroot.games.nemesis.entity.particles;

import nl.tdegroot.games.nemesis.gfx.Resources;

public class SlimeParticle extends Particle {


	public SlimeParticle(float x, float y, int life) {
		super(x, y, life, Resources.slimeParticle);
	}

	public SlimeParticle(float x, float y, int amount, int life) {
		super(x, y, amount, life, Resources.slimeParticle);
	}

}
