package nl.tdegroot.games.nemesis.level;

import java.util.ArrayList;
import java.util.List;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.Entity;
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
import nl.tdegroot.games.nemesis.map.NPCMap;
import nl.tdegroot.games.nemesis.map.ObjectMap;
import nl.tdegroot.games.nemesis.map.object.MapObject;
import nl.tdegroot.games.nemesis.spawner.MobSpawner;
import nl.tdegroot.games.nemesis.spawner.RoachSpawner;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

public class Level implements TileBasedMap {

	private Player player;
	private TiledMap map;
	public final int tileSize;
	private CollisionMap collisionMap;
	private ObjectMap objectMap;
	private NPCMap npcMap;

	private List<MobSpawner> spawners = new ArrayList<MobSpawner>();
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Mob> mobs = new ArrayList<Mob>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();

	public Level(String path, Player player) throws SlickException {
		this.player = player;
		map = new TiledMap(path);
		new MapLayer(map);
		tileSize = map.getTileWidth() & map.getTileHeight();
		if (tileSize == 0) { throw new SlickException("Tilewidth and Tileheight are not equal!"); }
		collisionMap = new CollisionMap(map, tileSize, getPixelWidth(), getPixelHeight());
		objectMap = new ObjectMap(map, tileSize);
		npcMap = new NPCMap(map, this, tileSize);
		Log.log("Object layers: " + map.getObjectLayerCount());
		initMobSpawners();
	}

	public void initMobSpawners() {
		for (int i = 0; i <= map.getObjectCount(MapLayer.MAP_LAYER_SPAWNERS); i++) {

			int x = (map.getObjectX(MapLayer.MAP_LAYER_SPAWNERS, i) + map.getObjectWidth(MapLayer.MAP_LAYER_SPAWNERS, i)) / tileSize;
			int y = (map.getObjectY(MapLayer.MAP_LAYER_SPAWNERS, i) + map.getObjectHeight(MapLayer.MAP_LAYER_SPAWNERS, i)) / tileSize;

			for (int xx = (map.getObjectX(MapLayer.MAP_LAYER_SPAWNERS, i) / tileSize); xx < x; xx++) {
				for (int yy = (map.getObjectY(MapLayer.MAP_LAYER_SPAWNERS, i) / tileSize); yy < y; yy++) {

					// Spawner properties
					String spawnerType = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "spawner", "");
					String spawnTime = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "spawnTime", "");
					String maxMobs = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "maxMobs", "");

					// Mob properties
					String mobScore = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "score", "");
					String mobHealth = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "health", "");
					String mobSpeed = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "speed", "");
					String mobDamage = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "damage", "");
					String mobCash = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "cash", "");

					if (spawnerType != "") {
						spawners.add(getSpawner(spawnerType, spawnTime, maxMobs, mobScore, mobHealth, mobSpeed, mobDamage, mobCash, xx, yy, i));
						Log.log("Spawner created with ID: " + i + ", mobHealth: " + mobHealth + ", mobSpeed: " + mobSpeed + ", mobScore: " + mobScore);
					}

				}
			}
		}
		Log.log("Mob spawners initialized! Amount of Mobspawners: " + spawners.size());
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

		checkProjectiles();
	}

	public void clear() {
		for (int i = 0; i < mobs.size(); i++) {
			Mob mob = mobs.get(i);
			if (mob.isRemoved()) {
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
				if (p.getAreaOfEffect().intersects(mob.getVulnerability()) && !p.isRemoved()) {
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
		screen.renderMap(map, tileSize, "tileLayer:objectLayer", 2);

		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).render(screen);
		}

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}

		if (player.getLastDir() == 2) {
			player.render(screen);
			screen.renderMap(map, tileSize, "objectLayerSpecial", 1);
		} else {
			screen.renderMap(map, tileSize, "objectLayerSpecial", 1);
			player.render(screen);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}

		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}

	}

	public MobSpawner getSpawner(String type, String spawnTime, String maxMobs, String mobScore, String mobHealth, String mobSpeed, String mobDamage, String mobCash, int x, int y, int i) {
		MobSpawner spawner = null;

		switch (type) {
		case "roach":
			spawner = new RoachSpawner(this, player, x, y, i);
			break;
		}

		if (spawner != null) {

			if (spawnTime != "") {
				spawner.setSpawnTime(Integer.parseInt(spawnTime));
			}

			if (maxMobs != "") {
				spawner.setMaxMobs(Integer.parseInt(maxMobs));
			}

			if (mobScore != "") {
				spawner.setMobScore(Integer.parseInt(mobScore));
			}

			if (mobHealth != "") {
				spawner.setMobHealth(Double.parseDouble(mobHealth));
			}

			if (mobSpeed != "") {
				spawner.setMovementSpeed(Float.parseFloat(mobSpeed));
			}

			if (mobDamage != "") {
				spawner.setMobDamage(Double.parseDouble(mobDamage));
			}

			if (mobCash != "") {
				spawner.setMobCash(Double.parseDouble(mobCash));
			}

		}

		return spawner;
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
}
