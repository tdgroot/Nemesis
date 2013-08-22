package nl.tdegroot.games.nemesis.level;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.spawner.MobSpawner;
import nl.tdegroot.games.nemesis.spawner.RoachSpawner;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import java.util.ArrayList;
import java.util.List;

public class Level implements TileBasedMap {

	private CollisionMap collisionMap;
	private TiledMap map;
	public final int tileSize;

	private List<MobSpawner> spawners = new ArrayList<MobSpawner>();
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Mob> mobs = new ArrayList<Mob>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();

	public Level(String path) throws SlickException {
		map = new TiledMap(path);
		tileSize = map.getTileWidth() & map.getTileHeight();
		if (tileSize == 0) {
			throw new SlickException("Tilewidth and Tileheight are not equal!");
		}
		collisionMap = new CollisionMap(map, tileSize);
		initMobSpawners();
	}

	public void initMobSpawners() {

		for (int i = 0; i <= map.getObjectCount(MapLayer.MAP_LAYER_SPAWNERS); i++) {

			int x = (map.getObjectX(MapLayer.MAP_LAYER_SPAWNERS, i) + map.getObjectWidth(MapLayer.MAP_LAYER_SPAWNERS, i)) / tileSize;
			int y = (map.getObjectY(MapLayer.MAP_LAYER_SPAWNERS, i) + map.getObjectHeight(MapLayer.MAP_LAYER_SPAWNERS, i)) / tileSize;

			for (int xx = (map.getObjectX(MapLayer.MAP_LAYER_SPAWNERS, i) / tileSize); xx < x; xx++) {
				for (int yy = (map.getObjectY(MapLayer.MAP_LAYER_SPAWNERS, i) / tileSize); yy < y; yy++) {

					String spawner = map.getObjectProperty(MapLayer.MAP_LAYER_SPAWNERS, i, "spawner", "");

					if (spawner != "") {
						spawners.add(getSpawner(spawner, xx, yy, i));
						Log.log("Spawner created with ID: " + i);
					}

				}
			}
		}
		Log.log("Mob spawners initialized! Amount of Mobspawners: " + spawners.size());
	}

	public void update(int delta) {
		for (int i = 0; i < spawners.size(); i++) {
			spawners.get(i).update();
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
	}

	public void render(Graphics g, float xOffset, float yOffset, Screen screen, Player player) {
		screen.setOffset(xOffset, yOffset);
		screen.renderMap(map, tileSize);

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g, screen);
		}

		for (int i = 0; i < mobs.size(); i++) {
			mobs.get(i).render(screen);
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}

		Log.log(projectiles.size() + " Projectiles");

	}

	public MobSpawner getSpawner(String type, int x, int y, int i) {
		MobSpawner spawner = null;
		switch (type) {
			case "roach":
				spawner = new RoachSpawner(this, x, y, i);
				break;
		}
		return spawner;
	}

	public boolean isSolid(int x, int y) throws ArrayIndexOutOfBoundsException {
		return collisionMap.isSolid(x, y);
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

	public void removeProjectile(Projectile projectile) {

	}

	public TiledMap getMap() {
		return map;
	}

	public int getPixelWidth() {
		return map.getWidth() * map.getTileWidth();
	}

	public int getPixelHeight() {
		return map.getHeight() * map.getTileHeight();
	}

	public int getTileSize() {
		return tileSize;
	}

	@Override
	public int getWidthInTiles() {
		return map.getWidth();
	}

	@Override
	public int getHeightInTiles() {
		return map.getHeight();
	}

	@Override
	public void pathFinderVisited(int i, int i2) {
	}

	@Override
	public boolean blocked(PathFindingContext pathFindingContext, int x, int y) {
		return collisionMap.isSolid(x, y);
	}

	@Override
	public float getCost(PathFindingContext pathFindingContext, int i, int i2) {
		return 1.0F;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}
}
