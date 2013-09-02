package nl.tdegroot.games.nemesis.ui;

import java.util.ArrayList;
import java.util.List;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.gfx.Resources;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Dialog {

	public static List<String> history;
	private static Image dialogWindow = Resources.dialogWindow;

	private static boolean active = false;
	private static boolean renderKey = false;

	private static int time = 0;

	public static void update(int delta) {
		time++;

		if (active) {
			renderKey = time % (5 * delta) >= 40;
		}
	}

	public static void init() {
		history = new ArrayList<String>();
		active = false;
		renderKey = false;
	}

	public static void render(Graphics g) {
		float dialogOffset = (Display.getWidth() - dialogWindow.getWidth()) / 2;
		dialogWindow.draw(dialogOffset, Display.getHeight() - dialogWindow.getHeight(), Display.getWidth() - dialogOffset * 2, dialogWindow.getHeight());

		g.setFont(Resources.agency_fb);
		for (int i = 0; i < history.size(); i++) {
			g.drawString(history.get(i), dialogOffset + 70, (Display.getHeight() - dialogWindow.getHeight() + 23) + i * 22);
		}
		if (renderKey) {
			g.drawString("Press C to continue", dialogOffset + 70, Display.getHeight() - 37);
		}
	}

	public static void activate(String message) {
		if (history.size() == 6) {
			history.remove(0);
		}
		history.add(message);
		active = true;
	}

	public static void deactivate() {
		active = false;
	}

	public static Image getImage() {
		return dialogWindow;
	}

	public static boolean isActive() {
		return active;
	}
}
