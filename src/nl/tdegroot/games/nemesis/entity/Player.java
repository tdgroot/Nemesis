package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.InputHandler;
import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.projectile.Arrow;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.item.Bow;
import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.map.MapLayer;
import nl.tdegroot.games.nemesis.map.object.MapObject;
import nl.tdegroot.games.nemesis.ui.Dialog;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Player extends Mob {

	private Bow bow;
	private Inventory inventory;

	private double energy;
	private int arrows = 0;

	private int fireRate = 0;
	private int mobsKilled = 0;
	private int score = 0;
	private int it = 0;

	public Player(Image image, float x, float y, int width, int height) {
		super(image, x, y, width, height);

		inventory = new Inventory();

		movementSpeed = 2.5f;
		
		baseHealth = 100.0;
		health = baseHealth;

		energy = 100.0;

		collisionMulX = 38;
		collisionAddX = 17;
		collisionMulY = 20;
		collisionAddY = 10;

		Log.log("Player initialized. Player Width: " + getWidth() + ", Player Height: " + getHeight());
	}

	protected void positionate() {
		float x = (float) level.getMap().getObjectX(MapLayer.MAP_LAYER_PLAYER, 0) + level.getMap().getObjectWidth(MapLayer.MAP_LAYER_PLAYER, 0);
		float y = (float) level.getMap().getObjectY(MapLayer.MAP_LAYER_PLAYER, 0) + level.getMap().getObjectHeight(MapLayer.MAP_LAYER_PLAYER, 0);

		this.x = x;
		this.y = y;
	}

	public void update(int delta) {
		if (fireRate > 0)
			fireRate--;
		if (it > 0)
			it -= delta;

		float xa = 0;
		float ya = 0;

		if (! Dialog.isActive()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) xa -= movementSpeed;
			if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) xa += movementSpeed;
			if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) ya -= movementSpeed;
			if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) ya += movementSpeed;
		}

		wasWalking = isWalking;

		if (xa != 0 || ya != 0) {
			isWalking = true;
			move(xa, ya);
		} else {
			isWalking = false;
		}

		frame++;

		if (isWalking) {
			if (frame % (48 / delta) == 0) {
				animIndex = ((animIndex + 1) % animCount);
			}
		}

		if (! wasWalking) {
			animIndex = 0;
		}

		clear();
		pollInput();
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) {
				level.getProjectiles().remove(i);
			}
		}
	}

	private void pollInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
			if (Dialog.isActive()) {
				Dialog.deactivate();
			}
		}
		if (InputHandler.getMouseB() == 0 && fireRate <= 0 && bow != null && arrows > 0) {
			double dx = (InputHandler.getMouseX() - Display.getWidth() / 2);
			double dy = (InputHandler.getMouseY() - Display.getHeight() / 2);
			double dir = Math.atan2(dx, dy);
			shoot(x, y, dir, this);
			arrows--;
			fireRate = Arrow.FIRE_RATE;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_X) && it <= 0) {
			interact();
		}
	}

	protected boolean collision(float xa, float ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (int) ((x + xa) + c % 2 * 38 - 17) / level.tileSize;
			int yt = (int) ((y + ya) + c / 2 * 20 + 10) / level.tileSize;
			if (level.getCollisionMap().isCollision(xt, yt))
				solid = true;
		}
		return solid;
	}

	public void interact() {
		MapObject object = null;
		for (int c = 0; c < 4; c++) {
			int xt = 0;
			int yt = 0;
			if (dir == 0) {
				xt = (int) ((x) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y - level.tileSize / 2) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (dir == 1) {
				xt = (int) ((x + level.tileSize / 2) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (dir == 2) {
				xt = (int) ((x) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y + level.tileSize / 2) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (dir == 3) {
				xt = (int) ((x - level.tileSize / 2) + c % 2 * collisionMulX - collisionAddX) / level.tileSize;
				yt = (int) ((y) + c / 2 * collisionMulY + collisionAddY) / level.tileSize;
			}
			if (level.getMapObject(xt, yt) != null)
				object = level.getMapObject(xt, yt);
		}

		if (object != null) {
			object.interact(this);
			it = 250;
		}
	}

	public void giveItem(Item item) {
		Log.log("I retrieved item: " + item.getName());
		inventory.add(0, item);
		if (item instanceof Bow) {
			Bow bow = (Bow) item;
			equip(bow);
		}
	}

	public void equip(Bow bow) {
		this.bow = bow;
	}

	public void addArrows(int amount) {
		arrows += amount;
	}

	public void mobKilled(int s) {
		mobsKilled++;
		score += s;
	}

	public void render(Graphics g, Screen screen) {
		screen.renderPlayer(this);
	}

	public int getKills() {
		return mobsKilled;
	}

	public int getScore() {
		return score;
	}

	public int getArrows() {
		return arrows;
	}
	
	public double getHealth() {
		return health;
	}
	
	public double getPercentHealth() {
		return (baseHealth / 100 * health);
	}

	public double getBaseHealth() {
		return baseHealth;
	}

	public double getEnergy() {
		return energy;
	}
}
