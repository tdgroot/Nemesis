package nl.tdegroot.games.nemesis;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Camera;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.level.Level;
import nl.tdegroot.games.nemesis.ui.Dialog;
import nl.tdegroot.games.nemesis.ui.UIHandler;
import nl.tdegroot.games.nemesis.ui.menu.DeadMenu;
import nl.tdegroot.games.nemesis.ui.menu.MainMenu;
import nl.tdegroot.games.nemesis.ui.menu.Menu;
import nl.tdegroot.games.nemesis.ui.menu.PauseMenu;

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
	private InputHandler input;
	private Screen screen;
	private UIHandler uiHandler;
	private Menu menu = null;
	private GameContainer gameContainer;

	public int mt = 0;

	public Nemesis() {
		super("Nemesis");
	}

	public void init(GameContainer gameContainer) throws SlickException {
		this.gameContainer = gameContainer;
		new Resources();
		player = new Player(Resources.player, 48, 47, 53, 64);
		level = new Level("resources/levels/starterisland.tmx", player);
		camera = new Camera(player, new Vector2f(Display.getWidth(), Display.getHeight()), new Rectangle(0, 0, level.getPixelWidth(), level.getPixelHeight()));
		screen = new Screen(camera, gameContainer.getGraphics());
		input = new InputHandler();
		uiHandler = new UIHandler(player);
		player.init(level, this);
		Dialog.init();
		gameContainer.getInput().addMouseListener(input);
		gameContainer.setAlwaysRender(true);
		Dialog.activate("Welcome, to the world of Nemesis!");
		Dialog.activate("To start, try reading some of the signs!");
		setMenu(new MainMenu(null));
	}

	public void resetGame() {
		try {
			init(gameContainer);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		mt = 0;
	}

	public void update(GameContainer gameContainer, int delta) throws SlickException {
		if (gameContainer.hasFocus()) {
			if (mt > 0) {
				mt -= delta;
			}
			if (menu != null) {
				menu.update(delta);
			} else {
				player.update(delta);
				level.update(delta);
				uiHandler.update(delta);
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && mt <= 0 && !Dialog.isActive()) {
					setMenu(new PauseMenu(null));
				}
				if (player.isDead())
					setMenu(new DeadMenu(null, player));
			}
		}
	}

	public void logic(GameContainer gameContainer) {
	}

	public void render(GameContainer gameContainer, Graphics g) throws SlickException {
		float xOffset = player.getX() - Display.getWidth() / 2;
		float yOffset = player.getY() - Display.getHeight() / 2;
		screen.setOffset(xOffset, yOffset);
		level.render(g, screen);
		uiHandler.render(g);
		if (menu != null) {
			menu.render(screen);
		}
		g.drawString("FPS: " + gameContainer.getFPS(), 135, 10);
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
		if (menu != null)
			menu.init(this);
		mt = 250;
	}

	public synchronized void stop() {
		System.exit(0);
	}

	public Player getPlayer() {
		return player;
	}

}
