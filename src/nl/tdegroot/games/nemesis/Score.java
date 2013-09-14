package nl.tdegroot.games.nemesis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Score {

	private static final String location = System.getenv("APPDATA") + "\\.nemesis";

	public static void save(int score) {
		File file = new File(location);
		if (! file.exists()) {
			file.mkdir();
		}

		Properties prop = new Properties();

		try {
			prop.setProperty("score", "" + score);
			prop.store(new FileOutputStream(location + "\\" + "score.cfg"), null);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static int load() {
		int score = 0;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(location + "\\" + "score.cfg"));
			score = Integer.parseInt(prop.getProperty("score"));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return score;
	}

}
