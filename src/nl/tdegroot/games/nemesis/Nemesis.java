package nl.tdegroot.games.nemesis;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
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
	private Resources resources;
	private Screen screen;

	public Nemesis() {
		super("Nemesis");
	}

	public void init(GameContainer gameContainer) throws SlickException {
		resources = new Resources();
		level = new Level("resources/levels/level1test.tmx");
		player = new Player(Resources.player, 45, 45, 53, 64);
		camera = new Camera(player, new Vector2f(Display.getWidth(), Display.getHeight()), new Rectangle(0, 0, level.getPixelWidth(), level.getPixelHeight()));
		screen = new Screen(camera);
		player.init(level);
	}

	public void update(GameContainer gameContainer, int delta) throws SlickException {
		player.update(delta);
		level.update(delta);
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) stop();
	}

	public void logic(GameContainer gameContainer) {
	}

	public void render(GameContainer gameContainer, Graphics g) throws SlickException {
		float xOffset = player.getX() - Display.getWidth() / 2;
		float yOffset = player.getY() - Display.getHeight() / 2;
		level.render(g, xOffset, yOffset, screen, player);
		player.render(g, screen);
	}

	private synchronized void stop() {
		System.exit(0);
	}


}
