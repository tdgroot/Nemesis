package nl.tdegroot.games.nemesis.level;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.entity.GroundItem;
import nl.tdegroot.games.nemesis.entity.GroundStack;
import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.entity.npc.NPC;
import nl.tdegroot.games.nemesis.entity.particles.Particle;
import nl.tdegroot.games.nemesis.entity.particles.SlimeParticle;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.map.CollisionMap;
import nl.tdegroot.games.nemesis.map.MapLayer;
import nl.tdegroot.games.nemesis.map.MobSpawnerMap;
import nl.tdegroot.games.nemesis.map.NPCMap;
import nl.tdegroot.games.nemesis.map.ObjectMap;
import nl.tdegroot.games.nemesis.map.object.MapObject;
import nl.tdegroot.games.nemesis.spawner.MobSpawner;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements TileBasedMap, Serializable {

	private Player player;
	private TiledMap map;
	public final int tileSize;
	private CollisionMap collisionMap;
	private ObjectMap objectMap;
	private NPCMap npcMap;
	private MobSpawnerMap mobSpawnerMap;

	public List<MobSpawner> spawners = new ArrayList<MobSpawner>();
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Mob> mobs = new ArrayList<Mob>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();

	private GroundStack[] groundStacks;

	public Level(String path, Player player) throws SlickException {
		this.player = player;
		map = new TiledMap(path);
		new MapLayer(map);
		tileSize = map.getTileWidth() & map.getTileHeight();
		if (tileSize == 0) {
			throw new SlickException("Tilewidth and Tileheight are not equal!");
		}
		collisionMap = new CollisionMap(map, tileSize, getPixelWidth(), getPixelHeight());
		objectMap = new ObjectMap(map, tileSize);
		npcMap = new NPCMap(map, this, tileSize);
		Log.log("Object layers: " + map.getObjectLayerCount());
		initGroundStacks();
		mobSpawnerMap = new MobSpawnerMap(this, map);
	}

	private void initGroundStacks() {
		groundStacks = new GroundStack[map.getWidth() * map.getHeight()];
		for (int y = 0; y < map.getHeight(); y++) {
			for (int x = 0; x < map.getWidth(); x++) {
				groundStacks[x + y * map.getWidth()] = new GroundStack();
			}
		}
	}

	public void update(int delta) {
		clear();

		for (int i = 0; i < spawners.size(); i++) {
			spawners.get(i).update(delta);
		}

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update(delta);
		}

		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).update(delta);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update(delta);
		}

		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update(delta);
		}

		for (int i = 0; i < groundStacks.length; i++) {
			groundStacks[i].update(delta);
		}

		checkProjectiles();
	}

	public void clear() {
		for (int i = 0; i < mobs.size(); i++) {
			Mob mob = mobs.get(i);
			if (mob.isRemoved()) {
				mob.drop();
				int spawnerID = mob.getSpawnerID();
				mobs.remove(i);
				Resources.mob_death.play();
				for (int j = 0; j < spawners.size(); j++) {
					MobSpawner spawner = spawners.get(j);
					if (spawner.getID() == spawnerID) {
						spawner.removeMob();
					}
				}
			}
		}

		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			if (p.isRemoved()) {
				projectiles.remove(i);
			}
		}

		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			if (p.isRemoved()) {
				p.clear();
				particles.remove(i);
			}
		}
	}

	public void checkProjectiles() {
		for (Projectile p : projectiles) {
			for (Mob mob : mobs) {
				if (p.getAreaOfEffect().intersects(mob.getVulnerability()) && ! p.isRemoved()) {
					mob.hit(p);
					if (mob.isRemoved()) {
						p.getPlayer().mobKilled(mob.getScore(), mob.getCash());
						Particle particle = new SlimeParticle(mob.getX() + mob.getWidth() / 2, mob.getY() + mob.getHeight() / 2, 125, 1000);
						particle.setLevel(this);
						particles.add(particle);
					} else {
						Resources.mob_hit.play();
						Particle particle = new SlimeParticle(mob.getX() + mob.getWidth() / 2, mob.getY() + mob.getHeight() / 2, 75, 1000);
						particle.setLevel(this);
						particles.add(particle);
						mob.trigger();
					}
					p.remove();
					Log.log("Hit: Mob ID: " + mob.getID() + ", Damage: " + p.getDamage() + ", Mob's Health: " + mob.getHealth());
				}
			}
		}
	}

	public void render(Graphics g, Screen screen) {
		screen.render(map, tileSize, "tileLayer1:tileLayer2:tileLayer3:objectLayer", 2);

		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).render(screen);
		}

		for (int i = 0; i < groundStacks.length; i++) {
			groundStacks[i].render(screen);
		}

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}

		if (player.getLastDir() == 2) {
			player.render(screen);
			screen.render(map, tileSize, "objectLayerSpecial", 1);
		} else {
			screen.render(map, tileSize, "objectLayerSpecial", 1);
			player.render(screen);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}

		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}

	}

	public void addGroundItem(GroundItem e) {
		e.init(this);
	}

	public boolean isCollision(int x, int y) {
		return collisionMap.isCollision(x, y);
	}

	public MapObject getMapObject(int x, int y) {
		return objectMap.getMapObject(x, y);
	}

	public NPC getNPC(int x, int y) {
		return npcMap.getNPC(x, y);
	}

	public void addEntity(Entity entity) {
		entity.init(this);
		entities.add(entity);
	}

	public void addMob(Mob mob) {
		mobs.add(mob);
	}

	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}

	public void addParticle(Particle particle) {
		particles.add(particle);
	}

	public TiledMap getMap() {
		return map;
	}

	public CollisionMap getCollisionMap() {
		return collisionMap;
	}

	public int getPixelWidth() {
		return map.getWidth() * map.getTileWidth();
	}

	public int getPixelHeight() {
		return map.getHeight() * map.getTileHeight();
	}

	public int getWidthInTiles() {
		return map.getWidth();
	}

	public int getHeightInTiles() {
		return map.getHeight();
	}

	public void pathFinderVisited(int x, int y) {
	}

	public boolean blocked(PathFindingContext pathFindingContext, int x, int y) {
		return collisionMap.isCollision(x * tileSize, y * tileSize);
	}

	public float getCost(PathFindingContext pathFindingContext, int i, int i2) {
		return 1.0F;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public Player getPlayer() {
		return player;
	}

	public void setSave(Object object) {
//		Object[] objects = (Object[]) object;
	}

	public Object getSave() {
		Object[] object = new Object[2];
//		object[0] = entities.toArray();
//		object[1] = mobs;
//		object[2] = projectiles;
//		object[3] = particles;
		return object;
	}
}
