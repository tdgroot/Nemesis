package nl.tdegroot.games.nemesis.entity.particles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.gfx.Screen;

import org.newdawn.slick.Image;

public class Particle extends Entity {

	protected Image sprite;

	protected List<Particle> particles = new ArrayList<Particle>();

	protected int time = 0;

	protected double xa, ya, za;
	protected double xx, yy, zz;

	protected int life;
	protected Random random = new Random();

	public Particle(float x, float y, int life, Image sprite) {
		this.sprite = sprite;
		this.life = life + (random.nextInt(200) - 100);
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.xa = random.nextGaussian() * 1.1f;
		this.ya = random.nextGaussian() * 1.5f;
		this.za = random.nextFloat();

		this.particles.add(this);
	}

	public Particle(float x, float y, int amount, int life, Image sprite) {
		this.sprite = sprite;
		this.life = life + (random.nextInt(200) - 100);
		for (int i = 0; i < amount; i++) {
			this.particles.add(new Particle(x, y, life, sprite));
		}
	}

	public void update(int delta) {
		for (int i = 0; i < this.particles.size(); i++) {
			Particle p = this.particles.get(i);
			if (p.isRemoved()) {
				particles.remove(i);
				continue;
			}
			p.time += delta;
			if (p.time >= p.life) p.remove();
			p.za -= 0.1D;

			p.xx += xa;
			p.yy += ya;
			p.zz += za;
			if (zz < 0) {
				zz = 0;
				p.za *= 0.1;
				p.xa *= 0.1;
				p.ya *= 1.2;
			}

			if (p.x < 0)
				p.x = 0;
			if (p.y < 0)
				p.y = 0;

			if (!collision(p)) {
				p.xx += p.xa;
				p.zz += p.za;
			}

			if (!collision(p)) {
				p.yy += p.ya;
			}
			p.za *= 0.15;
			p.x = (float) p.xx;
			p.y = (float) p.yy;
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
			if (level.getCollisionMap().isSolid(xt, yt)) {
				if (p.xa != 0.0) {
					p.xa *= -0.6;
				}

				if (p.ya != 0.0) {
					p.ya *= -0.6;
				}

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
			Particle p = this.particles.get(i);
			screen.renderParticle(p.x, p.y - (float) p.zz, sprite);
		}
	}

}
