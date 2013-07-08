package nl.tdegroot.games.nemesis;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.level.Level;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Nemesis extends BasicGame {

	private Level level;
	private Player player;

	private Camera camera;

	public Nemesis() {
		super("");

	}

	public void init(GameContainer gameContainer) throws SlickException {
		level = new Level("resources/levels/level1.tmx");
		player = new Player(5, 5, level);
		camera = new Camera(player, new Vector2f(1280, 720), new Rectangle(0, 0, level.getPixelWidth(), level.getPixelHeight()));
		level.addEntity(player);
		System.out.println("Map Width: " + level.getMap().getWidth() * level.getMap().getTileWidth());
	}

	public void update(GameContainer gameContainer, int delta) throws SlickException {
		System.out.println("Player X: " + player.getX() + ", Player Y: " + player.getY() + ", Camera X: " + camera.getX());
		level.update();
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) stop();
	}

	public void logic(GameContainer gameContainer) {
	}

	public void render(GameContainer gameContainer, Graphics g) throws SlickException {
		level.render(camera);
	}

	private synchronized void stop() {
		System.exit(0);
	}


}
