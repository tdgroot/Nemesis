package nl.tdegroot.games.nemesis.entity;

import nl.tdegroot.games.nemesis.InputHandler;
import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.entity.npc.FishNPC;
import nl.tdegroot.games.nemesis.entity.npc.NPC;
import nl.tdegroot.games.nemesis.entity.projectile.Arrow;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.item.Bow;
import nl.tdegroot.games.nemesis.item.FoodItem;
import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.item.ItemStack;
import nl.tdegroot.games.nemesis.level.Level;
import nl.tdegroot.games.nemesis.map.MapLayer;
import nl.tdegroot.games.nemesis.map.object.MapObject;
import nl.tdegroot.games.nemesis.ui.Dialog;
import nl.tdegroot.games.nemesis.ui.menu.InteractMenu;
import nl.tdegroot.games.nemesis.ui.menu.InventoryMenu;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import java.io.Serializable;

public class Player extends Mob implements Serializable {

	private Bow bow = (Bow) Item.bow;
	private Inventory inventory;
	private Nemesis game;

	private int animSpeedDefault;
	private int animSpeedSprint;

	private boolean sprinting = false;
	private boolean fishing = false;

	private double sprintMultiplier;

	private double energy;
	private double baseEnergy;
	private double energyRegen;

	private int arrows = 250;
	private int fireRate = 0;
	private int mobsKilled = 0;
	private int score = 0;

	public int it, et, ft, kt;

	public Player(Image image, float x, float y, int width, int height) {
		super(image, x, y, width, height);

		inventory = new Inventory();
		vulnerability = new Rectangle(x, y, 53, 64);

		animSpeedDefault = 48;
		animSpeedSprint = 35;

		movementSpeed = 2.5f;
		sprintMultiplier = 1.8;
		damage = 35;
		critChance = 15;

		health = baseHealth = 250.0;

		energy = baseEnergy = 100.0;
		energyRegen = 0.20;

		collisionMulX = 38;
		collisionAddX = 17;
		collisionMulY = 20;
		collisionAddY = 10;

		ldDefault = 500;

		Log.log("Player initialized. Player Width: " + getWidth() + ", Player Height: " + getHeight());
	}

	public void init(Level level, Nemesis game) {
		this.game = game;
		setLevel(level);
		positionate();
	}

	protected void positionate() {
		float x = (float) level.getMap().getObjectX(MapLayer.MAP_LAYER_PLAYER, 0) + level.getMap().getObjectWidth(MapLayer.MAP_LAYER_PLAYER, 0);
		float y = (float) level.getMap().getObjectY(MapLayer.MAP_LAYER_PLAYER, 0) + level.getMap().getObjectHeight(MapLayer.MAP_LAYER_PLAYER, 0);

		this.x = x;
		this.y = y;
	}

	public void update(int delta) {
		frame++;
		if (fireRate > 0) fireRate--;
		if (it > 0) it -= delta;
		if (et > 0) et -= delta;
		if (ft > 0) ft -= delta;
		if (kt > 0) kt -= delta;

		inventory.update(delta);

		float xa = 0;
		float ya = 0;

		float deltaMul = 0.065f;

		if (!Dialog.isActive()) {
			float speed = movementSpeed * delta * deltaMul;
			if (sprinting) speed *= sprintMultiplier;

			if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				xa -= speed;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				xa += speed;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				ya -= speed;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				ya += speed;
			}

		}

		sprinting = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && energy > energyRegen;

		if (sprinting && isWalking) {
			energy -= 0.35;
		}
		if (energy < 0) energy = 0;
		if (!(sprinting && isWalking) && energy < baseEnergy) energy += energyRegen;

		wasWalking = isWalking;

		if (xa != 0 || ya != 0) {
			isWalking = true;
			move(xa, ya, delta);
			vulnerability.setLocation(x, y);
		} else {
			isWalking = false;
		}

		if (isWalking) {
			int rate = sprinting ? animSpeedSprint : animSpeedDefault;
			if (frame % (rate / delta) == 0) {
				animIndex = ((animIndex + 1) % animCount);
			}
			fishing = false;
		} else {
			if (fishing) {
				if (random.nextInt(100) < 1 && ft <= 0) {
					Dialog.activate("Something starts to pull on your fishing rod...");
					if (random.nextInt(100) < 60) {
						Dialog.activate("You caught a fish!");
						inventory.add(0, Item.fish);
					} else {
						Dialog.activate("But it got away!");
					}
					ft = 3000;
				}
			}
		}

		if (!wasWalking) {
			animIndex = 0;
		}

		if (health <= 0) {
			Log.log("YOU'RE DEAD!");
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
			Resources.bow_shot.play();
			fishing = false;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_X) && it <= 0) {
			interact();
		} else if (Keyboard.isKeyDown(Keyboard.KEY_I) && kt <= 0) {
			game.setMenu(new InventoryMenu(null, inventory));
			kt = 200;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_H) && et <= 0) {
			if (eat()) {
				Resources.eat.play();
				fishing = false;
			}
		}
	}

	protected void shoot(float x, float y, double dir, Player player) {
		Projectile projectile = new Arrow(x, y, dir, player);
		projectile.setDamage(damage);
		projectile.setCritChance(critChance);
		level.addProjectile(projectile);
	}

	protected boolean collision(float xa, float ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (int) ((x + xa) + c % 2 * 38 - 17);
			int yt = (int) ((y + ya) + c / 2 * 20 + 10);
			if (level.getCollisionMap().isCollision(xt, yt)) solid = true;
		}
		return solid;
	}

	public void interact() {
		MapObject object = null;
		NPC npc = null;
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
			if (level.getMapObject(xt, yt) != null) object = level.getMapObject(xt, yt);
			if (level.getNPC(xt, yt) != null) npc = level.getNPC(xt, yt);
		}

		if (object != null) {
			Resources.interact.play();
			it = 250;
			game.setMenu(new InteractMenu(this, object, object.getX(), object.getY()));
		} else if (npc != null) {
			Resources.interact.play();
			it = 250;
			game.setMenu(new InteractMenu(this, npc, npc.getX(), npc.getY()));
		}
	}

	public boolean eat() {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof ItemStack) {
				ItemStack stack = (ItemStack) inventory.get(i);
				if (stack.item.eatable()) {
					FoodItem food = (FoodItem) stack.item;
					heal(food.healPoints);
					((ItemStack) inventory.get(i)).itemList.remove(food);
					et = 450;
					return true;
				}
			}
		}
		return false;
	}

	public void fish(FishNPC spot) {
		fishing = true;
		ft = 1500;
	}

	public void render(Screen screen) {
		screen.renderPlayer(this);
	}

	public void giveItem(Item item) {
		Log.log("I retrieved item: " + item.getName());
		inventory.add(0, item);
		if (item instanceof Bow) {
			Bow bow = (Bow) item;
			equip(bow);
		}
	}

	public boolean hasItem(Item item) {
		return inventory.hasItems(item, 1);
	}

	public void equip(Item item) {
		if (item instanceof Bow) this.bow = (Bow) item;
	}

	public void addArrows(int amount) {
		arrows += amount;
	}

	public void mobKilled(int s, double cash) {
		mobsKilled++;
		score += s;
		this.cash += cash;
	}

	public int getKills() {
		return mobsKilled;
	}

	public int getScore() {
		return score;
	}

	public void chargeCash(int amount) {
		cash -= amount;
	}

	public int getArrows() {
		return arrows;
	}

	public double getHealth() {
		return health;
	}

	public int getDir() {
		return dir;
	}

	public int getAnimIndex() {
		return animIndex;
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

	public boolean isDead() {
		return health <= 0.0;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setSave(Object object) {
		Object[] objects = (Object[]) object;

		x = Float.parseFloat("" + (double) objects[0]);
		y = Float.parseFloat("" + (double) objects[1]);

		Log.log("Loading: x: " + x + ", y: " + y);

		baseHealth = (double) objects[2];
		health = (double) objects[3];

		baseEnergy = (double) objects[4];
		energy = (double) objects[5];

		inventory.setSave((String) objects[6]);

		cash = (double) objects[7];
		score = (int) objects[8];
		mobsKilled = (int) objects[9];
	}

	public Object getSave() {
		Object[] objects = new Object[50];

		objects[0] = x;
		objects[1] = y;

		objects[2] = baseHealth;
		objects[3] = health;

		objects[4] = baseEnergy;
		objects[5] = energy;

		objects[6] = inventory.getSave();

		objects[7] = cash;
		objects[8] = score;
		objects[9] = mobsKilled;

		return objects;
	}

}
