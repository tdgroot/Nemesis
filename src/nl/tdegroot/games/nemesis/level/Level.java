package nl.tdegroot.games.nemesis.level;

import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class Level {

	private TiledMap map;
	public final int tileSize;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Mob> mobs = new ArrayList<Mob>();

	public Level(String path) throws SlickException {
		map = new TiledMap(path);
		tileSize = map.getTileWidth() & map.getTileHeight();
		if (tileSize == 0) {
			throw new SlickException("Tilewidth and Tileheight are not equal!");
		}
	}

	public void update(int delta) {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
	}

	public void render(Graphics g, float xOffset, float yOffset, Screen screen, Player player) {
		screen.setOffset(xOffset, yOffset);
		screen.renderMap(map, tileSize);

		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g, screen);
		}

	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void addMob(Mob mob) {
		mobs.add(mob);
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

}
