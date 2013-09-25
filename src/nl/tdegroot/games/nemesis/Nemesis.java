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
import org.codehaus.jackson.map.ObjectMapper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.openal.SoundStore;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Nemesis extends BasicGame {

	private Level level;
	private Player player;

	private Camera camera;
	private InputHandler input;
	public Screen screen;
	private UIHandler uiHandler;
	private Menu menu = null;
	private GameContainer gameContainer;

	private float soundVolume = 1.0f, musicVolume = 1.0f;

	public Graphics graphics;

	public int mt = 0;

	public Nemesis() {
		super("Nemesis");
	}

	public void init(GameContainer gameContainer) throws SlickException {
		this.gameContainer = gameContainer;
		new Resources();
		setMenu(new MainMenu(null, true));
	}

	public void setupGame() {

		try {
			init(gameContainer);
			player = new Player(Resources.player, 48, 47, 53, 64);
			level = new Level("resources/levels/starterisland.tmx", player);
			camera = new Camera(player, new Vector2f(Display.getWidth(), Display.getHeight()), new Rectangle(0, 0, level.getPixelWidth(), level.getPixelHeight()));
			screen = new Screen(camera, gameContainer.getGraphics());
			input = new InputHandler();
			uiHandler = new UIHandler(player);
			player.init(level, this);
			Dialog.init();
			SoundStore.get().setSoundVolume(soundVolume);
			SoundStore.get().setMusicVolume(musicVolume);
			gameContainer.getInput().addMouseListener(input);
			gameContainer.setAlwaysRender(true);
			gameContainer.setDefaultFont(Resources.DEFAULT);
			Dialog.activate("Welcome, to the world of Nemesis!");
			Dialog.activate("To start, try reading some of the signs!");
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
				if (player.isDead()) setMenu(new DeadMenu(null, player));
			}
		}
	}

	public void logic(GameContainer gameContainer) {
	}

	public void render(GameContainer gameContainer, Graphics g) throws SlickException {
		graphics = g;
		if (menu != null) {
			if (menu.renderBackground()) {
				float xOffset = player.getX() - Display.getWidth() / 2;
				float yOffset = player.getY() - Display.getHeight() / 2;
				screen.setOffset(xOffset, yOffset);
				level.render(g, screen);
				uiHandler.render(g);
				g.drawString("Cash: " + (int) Math.floor(player.getCash()), 500, 80);
				g.drawString("Arrows: " + player.getArrows(), 700, 80);
			}
			menu.render(g);
		} else {
			float xOffset = player.getX() - Display.getWidth() / 2;
			float yOffset = player.getY() - Display.getHeight() / 2;
			screen.setOffset(xOffset, yOffset);
			level.render(g, screen);
			uiHandler.render(g);
			g.drawString("Cash: " + (int) Math.floor(player.getCash()), 500, 80);
			g.drawString("Arrows: " + player.getArrows(), 700, 80);
		}
		g.drawString("FPS: " + gameContainer.getFPS(), 135, 10);
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
		if (menu != null) menu.init(this);
		mt = 250;
	}

	public synchronized void stop() {
		gameContainer.exit();
	}

	public Player getPlayer() {
		return player;
	}

	public void setVolume(float soundVolume, float musicVolume) {
		this.soundVolume = soundVolume;
		this.musicVolume = musicVolume;
	}

	public void save() {
		String filename = "save";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			FileOutputStream fos = new FileOutputStream(Score.location + "\\" + filename + ".dat");
			GZIPOutputStream gzos = new GZIPOutputStream(fos);
			ObjectOutputStream out = new ObjectOutputStream(gzos);

			Log.log("Player Save: " + objectMapper.writeValueAsString(player.getSave()));
			out.writeObject(objectMapper.writeValueAsString(player.getSave()));
			out.flush();
			Log.log("Saved Player!");

			out.writeObject(objectMapper.writeValueAsString(level.getSave()));
			out.flush();
			Log.log("Saved Level!");

			out.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void load() {
		setupGame();
		String filename = "save";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File(Score.location + "\\" + filename + ".dat");
			if (!file.exists()) {
				Log.log("File does not exist!");
				return;
			}
			FileInputStream fis = new FileInputStream(Score.location + "\\" + filename + ".dat");
			GZIPInputStream gzis = new GZIPInputStream(fis);
			ObjectInputStream in = new ObjectInputStream(gzis);

			player.setSave(objectMapper.readValue((String) in.readObject(), Object[].class));
			uiHandler = new UIHandler(player);

//			level = new Level("resources/levels/starterisland.tmx", player);
//			level.setSave(objectMapper.readValue((String) in.readObject(), Object[].class));

			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
