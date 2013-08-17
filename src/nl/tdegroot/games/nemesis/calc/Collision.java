package nl.tdegroot.games.nemesis.calc;

import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.level.Level;
import org.newdawn.slick.tiled.TiledMap;


public class Collision {

	public static boolean check(Mob mob, float xa, float ya, Level level, int dir) {
		boolean solid = false;
		int x, y;
		TiledMap map = level.getMap();

		if (dir == 0) {
			x = (int) ((mob.getX() + xa) / level.tileSize);
			y = (int) ((mob.getY() + ya) / level.tileSize);

			solid = isSolid(x, y, map);
			solid = isSolid(x, y - 1, map);
		}
		if (dir == 1) {
			x = (int) ((mob.getX() + xa) / level.tileSize);
			y = (int) ((mob.getY() + ya) / level.tileSize);

			solid = isSolid(x, y, map);
			solid = isSolid(x + 1, y, map);
		}
		if (dir == 2) {
			x = (int) ((mob.getX() + xa) / level.tileSize);
			y = (int) ((mob.getY() + ya) / level.tileSize);

			solid = isSolid(x, y, map);
			solid = isSolid(x, y + 1, map);
		}
		if (dir == 3) {
			x = (int) ((mob.getX() + xa) / level.tileSize);
			y = (int) ((mob.getY() + ya) / level.tileSize);

			solid = isSolid(x, y, map);
			solid = isSolid(x - 1, y, map);
		}

		return solid;
	}

	private static boolean isSolid(int x, int y, TiledMap map) {
		boolean solid = false;
		int tileID = map.getTileId(x, y, 0);

		solid = Boolean.parseBoolean(map.getTileProperty(tileID, "solid", "false"));

		return solid;
	}

}
