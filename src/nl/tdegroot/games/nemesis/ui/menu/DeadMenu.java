package nl.tdegroot.games.nemesis.ui.menu;

import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class DeadMenu extends Menu {

	private int time;
	private boolean renderText = false;
	private Player player;

	public DeadMenu(Menu parent, Player player) {
		super(parent);
		this.player = player;
	}

	public void init(Nemesis game) {
		super.init(game);
		items = new String[] {"Restart", "Main Menu", "Quit Game"};
	}

	public void update(int delta) {
		if (kt > 0) kt -= delta;
		time++;
		renderText = time % (5 * delta) >= 40;


		if ((Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)) && kt <= 0) {
			selected--;
			Resources.select.play();
			kt = 200;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)) && kt <= 0) {
			selected++;
			Resources.select.play();
			kt = 200;
		}

		int length = items.length;

		if (selected < 0) selected += length;
		if (selected >= length) selected -= length;

		if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			
			if (selected == 0) {
				Resources.interact.play();
				game.resetGame();
				game.setMenu(null);
			}

			if (selected == 1) {
				Resources.interact.play();
				game.setMenu(new MainMenu(null));
			}

			if (selected == 2) game.stop();

			kt = 350;
		}
	}

	public void render(Screen screen) {
		Graphics graphics = screen.getGraphics();
		graphics.setColor(new Color(0, 0, 0, 175));
		graphics.fillRect(0, 0, Display.getWidth(), Display.getWidth());
		graphics.setColor(Color.white);
		if (renderText) {
			String dead_message = "YOU'RE DEAD";
			String score_message = "Your score: " + player.getScore();
			graphics.setFont(Resources.courier_new_bold);
			graphics.drawString(dead_message, (Display.getWidth() - graphics.getFont().getWidth(dead_message)) / 2, 50);
			graphics.drawString(score_message, (Display.getWidth() - graphics.getFont().getWidth(score_message)) / 2, 100);
			graphics.resetFont();
		}
		for (int i = 0; i < items.length; i++) {
			String msg = items[i];
			if (i == selected) {
				msg = "> " + msg + " <";
			} else {
				graphics.resetFont();
			}
			graphics.drawString(msg, (Display.getWidth() - graphics.getFont().getWidth(msg)) / 2, (44 + i * 3) * 8);
		}
	}

}
