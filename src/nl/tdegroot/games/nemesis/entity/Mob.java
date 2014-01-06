package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.particles.BloodParticle;
import nl.tdegroot.games.nemesis.entity.particles.Particle;
import nl.tdegroot.games.nemesis.entity.particles.TextParticle;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.util.pathfinding.Mover;

public class Mob extends Entity implements Mover {

	protected SpriteSheet sheet;
	protected Rectangle vulnerability;
	protected Player target;

	protected boolean isWalking = false;
	protected boolean wasWalking = false;
	protected boolean triggered = false;

	protected int tt = 0;

	protected double baseHealth;
	protected double health;

	protected int mobID = 0;
	protected int spawnerID = 0;
	protected int score = 0;
	protected int ht = 0;

	public int dir = 0;
	private int lastDir = 0;
	protected int ld = 0;
	protected int ldDefault = 0;
	public int animIndex = 0;
	public int animCount = 0;
	public int animType = 0;

	protected float deltaMul = 0.006f;

	public int frame = 0;
	protected int collisionMulX = 0;
	protected int collisionMulY = 0;
	protected int collisionAddY = 0;
	protected int collisionAddX = 0;

	protected double cash;

	protected float movementSpeed = 0;
	protected double damage;
	protected double critChance = 9.0;

	public Mob(Image image, float x, float y, int width, int height) {
		super(image, x, y, width, height);
		animCount = image.getWidth() / width;
		sheet = new SpriteSheet(image, width, height);
	}

	public Mob(Image image, float x, float y, int width, int height, int mobID, int spawnerID) {
		super(image, x, y, width, height);
		animCount = image.getWidth() / width;
		sheet = new SpriteSheet(image, width, height);
		vulnerability = new Rectangle(x + 10, y + 10, width - 20, height - 20);
		this.mobID = mobID;
		this.spawnerID = spawnerID;
	}

	public void init(Level level, Player target) {
		super.init(level);
		if (level == null) Log.log("Level is null!");
		this.target = target;
	}

	public void update(int delta) {
		if (isWalking) {
			if (frame % (102 / delta) == 0) {
				animIndex = ((animIndex + 1) % animCount);
			}
		}
	}

	public void move(float xa, float ya, int delta) {
		if (xa != 0 && ya != 0) {
			move(xa, 0, delta);
			move(0, ya, delta);
			return;
		}

		if (ld > 0) ld -= delta / 2;

		if (ya < 0) dir = 0;
		if (xa > 0) dir = 1;
		if (ya > 0) dir = 2;
		if (xa < 0) dir = 3;

		if (lastDir != dir && dir != 1 && dir != 3 && ld <= 0) {
			lastDir = dir;
			ld = ldDefault;
		}

		float limit = movementSpeed * delta * deltaMul;
		if (xa > -limit && xa < limit && ya > -limit && ya < limit) dir = lastDir;

		if (x < 0) x = 0;
		if (y < 0) y = 0;

		if (collision(xa, ya)) return;

		x += xa;
		y += ya;
	}

	protected boolean collision(float xa, float ya) {
		boolean solid = false;

		for (int c = 0; c < 4; c++) {
			int xt = (int) ((x + xa) + c % 2 * collisionMulX - collisionAddX);
			int yt = (int) ((y + ya) + c / 2 * collisionMulY + collisionAddY);
			if (level.isCollision(xt, yt)) solid = true;
		}
		return solid;
	}

	public void hurt(Mob mob) {
		Color fontColor = new Color(255, 0, 0);
		double dmg = mob.getDamage() + (random.nextInt(8) - 4) / 1.5;
		TextParticle.Type fontType = TextParticle.Type.NORMAL;
		if (random.nextInt(100) < mob.getCritChance()) {
			fontColor = new Color(255, 106, 0);
			dmg *= 1.35;
			fontType = TextParticle.Type.BIG;
		}
		health -= dmg;
		if (!Resources.player_hit.playing()) Resources.player_hit.play(1.0f, 0.3f);
		if (health < 0) health = 0;
		Player player = level.getPlayer();
		Particle bloodParticle = new BloodParticle(player.getX(), player.getY(), 75, 600);
		bloodParticle.setLevel(level);
		level.addParticle(bloodParticle);
		Particle p = new TextParticle("" + Math.round(dmg * 100.0) / 100.0, player.getX(), player.getY() - 50, 500, fontType, fontColor);
		p.setLevel(level);
		level.addParticle(p);
	}

	public void hit(Projectile p) {
		Color fontColor = new Color(255, 0, 0);
		double dmg = p.getDamage() + (random.nextInt(8) - 4) / 1.5;
		TextParticle.Type fontType = TextParticle.Type.NORMAL;
		if (random.nextInt(100) < p.getCritChance()) {
			fontColor = new Color(255, 131, 0);
			dmg *= 1.5;
			fontType = TextParticle.Type.BIG;
		}
		health -= dmg;
		if (health <= 0.0) remove();
		Particle particle = new TextParticle("" + Math.round(dmg * 100.0) / 100.0, getX(), getY() - 50, 500, fontType, fontColor);
		particle.setLevel(level);
		level.addParticle(particle);
	}

	protected int getDirection(float xa, float ya) {
		boolean xPositive = true, yPositive = true;

		if (xa < 0)
			xPositive = false;

		if (ya < 0)
			yPositive = false;

		if (xPositive && yPositive) {
			if (xa > ya) {
				return 1;
			} else if (ya > xa) {
				return 2;
			}
		} else if (!xPositive && !yPositive) {
			if (xa < ya) {
				return 3;
			} else if (ya > xa) {
				return 0;
			}
		} else if (xPositive && !yPositive) {
			float yy = ya * -1;
			if (xa > yy) {
				return 1;
			} else if (yy > xa) {
				return 0;
			}
		} else if (!xPositive && yPositive) {
			float xx = xa * -1;
			if (xx > ya) {
				return 3;
			} else if (ya > xx) {
				return 2;
			}
		}

		return dir;
	}

	public void render(Screen screen) {
		screen.render(this);
	}

	public Rectangle getVulnerability() {
		return vulnerability;
	}

	public SpriteSheet getSheet() {
		return sheet;
	}

	public int getID() {
		return mobID;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void trigger() {
		triggered = true;
		tt = 650;
	}

	public void setHealth(double health) {
		this.health = health;
	}

	public void heal(double amount) {
		health += amount;
		if (health > baseHealth) health = baseHealth;
	}

	public void drop() {

	}

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public boolean isMoving() {
		return isWalking;
	}

	public int getDir() {
		return dir;
	}

	public int getLastDir() {
		return lastDir;
	}

	public int getAnimIndex() {
		return animIndex;
	}

	public int getAnimType() {
		return animType;
	}

	public double getHealth() {
		return health;
	}

	public int getSpawnerID() {
		return spawnerID;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getDamage() {
		return damage;
	}

	public double getCritChance() {
		return critChance;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public double getCash() {
		return cash;
	}

}
