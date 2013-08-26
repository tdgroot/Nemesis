package nl.tdegroot.games.nemesis.entity.particles;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Particle extends Entity {

	protected Image sprite;

	protected List<Particle> particles = new ArrayList();

	protected int time = 0;

	protected double xa, ya, za;
	protected double xx, yy, zz;

	protected int life;
	protected Random random = new Random();

	public Particle(float x, float y, int life, Image sprite) {
		this.sprite = sprite;
		this.life = life;

		this.xx = x;
		this.yy = y;
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.za = random.nextFloat();

		this.particles.add(this);
	}

	public Particle(float x, float y, int amount, int life, Image sprite) {
		this.sprite = sprite;
		this.life = life;
		for (int i = 0; i < amount; i++) {
			this.particles.add(new Particle(x, y, life, sprite));
		}
	}

	public void update(int delta) {
		for (int i = 0; i < this.particles.size(); i++) {
			Particle p = (Particle) this.particles.get(i);
			if (p.isRemoved()) {
				particles.remove(i);
				continue;
			}
			p.time += 1;
			if (p.time >= p.life) p.remove();
			Log.log(p.time + "");
			p.za -= 0.1D;

			if (p.zz < 0.0D) {
				p.zz = 0.0D;
				p.za *= -0.5D;
				p.ya *= 0.4D;
				p.xa *= 0.4D;
			}

			p.xx += p.xa;
			p.zz += p.za;
			p.yy += p.ya;

			p.x = ((int) p.xx);
			p.y = ((int) p.yy);
		}
		if (particles.size() <= 0) {
			remove();
		}
	}

	private boolean collision(Particle p) {
		boolean solid = false;
		for (int i = 0; i < 4; i++) {
			int xt = (int) (p.x + p.xa + i % 2 * 2 - 1) / level.tileSize;
			int yt = (int) (p.y + p.ya + i / 2 * 1 + 4) / level.tileSize;
			if (level.isSolid(xt, yt)) {
				solid = true;
			}
		}
		return solid;
	}

	public void clear() {
		for (int i = 0; i < this.particles.size(); i++) {
			if (this.particles.get(i).isRemoved()) {
				this.particles.remove(i);
			}
		}
	}

	public void render(Screen screen) {
		for (int i = 0; i < this.particles.size(); i++) {
			Particle p = (Particle) this.particles.get(i);
			screen.renderParticle(p.x, p.y - (float) p.zz, sprite);
		}
	}

}
