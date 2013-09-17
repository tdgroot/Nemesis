package nl.tdegroot.games.nemesis.calc;

import java.util.Random;

import nl.tdegroot.games.nemesis.level.Level;

public class GameUtil {

	private static boolean checkCollision(int x, int y, Level level) {
		boolean solid = false;
		if ((x < 0) || (x >= level.getPixelWidth()) || (y < 0) || (y >= level.getPixelHeight())) {
			return true;
		}
		int id = level.getMap().getTileId(x / level.tileSize, y / level.tileSize, 0);
		String solidString = level.getMap().getTileProperty(id, "solid", "false");
		solid = Boolean.parseBoolean(solidString);
		return solid;
	}

	public static boolean checkCollision(float x, float y, Level level) {
		return checkCollision((int) java.lang.Math.floor(x), (int) java.lang.Math.floor(y), level);
	}

	public static int random(int a, int b) {
		int dif = Math.abs(a - b);
		return Math.min(a, b) + (dif <= 0 ? 0 : new Random().nextInt(dif));
	}

	public static String firstCharToUpper(String userIdea) {
		char[] stringArray = userIdea.toCharArray();
		stringArray[0] = Character.toUpperCase(stringArray[0]);
		return new String(stringArray);
	}

}
