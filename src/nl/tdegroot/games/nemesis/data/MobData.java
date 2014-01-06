package nl.tdegroot.games.nemesis.data;

import nl.tdegroot.games.nemesis.Log;

import java.io.BufferedReader;
import java.io.FileReader;

public class MobData {

	private static String[][] data;

	public static void load() throws Exception {
		data = new String[100][100];
		BufferedReader br = new BufferedReader(new FileReader("resources/data/mobloot.txt"));
		StringBuilder sb = new StringBuilder();
		try {
			String line = br.readLine();
			boolean readingMob = false;
			String currentMob = "";
			while (line != null) {
				if (line.endsWith("{")) {
					readingMob = true;

				} else if (line.endsWith("}")) {
					readingMob = false;
				} else if (readingMob) {
					sb.append(line + ", ");
				}
				line = br.readLine();
			}
		} finally {
			br.close();
		}

		initData(sb.toString());

	}

	private static void initData(String loot) {
		Log.log("Loot: " + loot);

		String[] array = loot.split(", ");

		for (int i = 0; i < array.length; i++) {
			String sub = array[i];
		}

	}

}
