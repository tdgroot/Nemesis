package nl.tdegroot.games.nemesis;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Camera;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;
import nl.tdegroot.games.nemesis.ui.Dialog;
import nl.tdegroot.games.nemesis.ui.UIHandler;
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
	@SuppressWarnings("unused")
	private Resources resources;
	InputHandler input;
	private Screen screen;
	private UIHandler uiHandler;

	public Nemesis() {
		super("Nemesis");
	}

	public void init(GameContainer gameContainer) throws SlickException {
		resources = new Resources();
		player = new Player(Resources.player, 48, 47, 53, 64);
		level = new Level("resources/levels/starterisland.tmx");
		level.setPlayer(player);
		camera = new Camera(player, new Vector2f(Display.getWidth(), Display.getHeight()), new Rectangle(0, 0, level.getPixelWidth(), level.getPixelHeight()));
		screen = new Screen(camera, gameContainer.getGraphics());
		input = new InputHandler();
		uiHandler = new UIHandler(player);
		gameContainer.getInput().addMouseListener(input);
		player.init(level);
		gameContainer.setVSync(true);
		Dialog.activate("Welcome, to the world of world of Nemesis!");
	}

	public void update(GameContainer gameContainer, int delta) throws SlickException {
		player.update(delta);
		level.update(delta);
		uiHandler.update(delta);
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) stop();
	}

	public void logic(GameContainer gameContainer) {
	}

	public void render(GameContainer gameContainer, Graphics g) throws SlickException {
		float xOffset = player.getX() - Display.getWidth() / 2;
		float yOffset = player.getY() - Display.getHeight() / 2;
		level.render(g, xOffset, yOffset, screen, player);
//		player.render(g, screen);
		g.drawString("FPS: " + gameContainer.getFPS(), 135, 10);
		g.drawString("Mobs Killed: " + player.getKills(), Display.getWidth() - 500, 10);
		g.drawString("Score: " + player.getScore(), Display.getWidth() - 300, 10);
		g.drawString("Arrows: " + player.getArrows(), Display.getWidth() - 150, 10);
		g.setAntiAlias(true);
		uiHandler.render(g);
	}

	private synchronized void stop() {
		System.exit(0);
	}


}
