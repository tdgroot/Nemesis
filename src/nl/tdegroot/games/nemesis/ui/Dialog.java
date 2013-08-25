package nl.tdegroot.games.nemesis.ui;

import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

public class Dialog {

	public static List<String> history = new ArrayList<String>();
	private static Image dialogWindow = Resources.dialogWindow;

	private static boolean active = false;

	public static void update(int delta) {
	}

	public static void render(Screen screen) {
		screen.renderDialog();
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
