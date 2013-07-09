package nl.tdegroot.games.nemesis.level;

import nl.tdegroot.games.nemesis.Camera;
import nl.tdegroot.games.nemesis.entity.Entity;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class Level {

	private TiledMap map;

	private List<Entity> entities = new ArrayList<Entity>();

	public Level(String path) throws SlickException {
		map = new TiledMap(path);
	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
	}

	public void render(Graphics g, Camera camera) {
		map.render((int) - camera.getX(), (int) - camera.getY());
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(g, camera);
		}
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public TiledMap getMap() {
		return map;
	}

	public float getPixelWidth() {
		return map.getWidth() * map.getTileWidth();
	}

	public float getPixelHeight() {
		return map.getHeight() * map.getTileHeight();
	}

}
