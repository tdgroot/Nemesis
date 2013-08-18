package nl.tdegroot.games.nemesis.gfx;

import nl.tdegroot.games.nemesis.Camera;
import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Player;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class Screen {

	private float xOffset, yOffset;
	private Camera camera;

	public Screen(Camera camera) {
		this.camera = camera;
	}

	public void renderMap(TiledMap map, int tileSize) {
		int x = (int) - (xOffset % tileSize) - tileSize;
		int y = (int) - (yOffset % tileSize) - tileSize;
		int sx = (int) (xOffset / tileSize) - 1;
		int sy = (int) (yOffset / tileSize) - 1;
		int sectionWidth = (Display.getWidth() / tileSize) + 3;
		int sectionHeight = (Display.getHeight() / tileSize) + 4;

		map.render(x, y, sx, sy, sectionWidth, sectionHeight);
	}

	public void renderPlayer(Player player) {
		SpriteSheet sheet = player.getSheet();
		sheet.startUse();
		sheet.renderInUse((int) camera.getXOffset(), (int) camera.getYOffset(), player.getAnimIndex(), player.getDir());
		sheet.endUse();
	}

	public void renderMob(Mob mob) {
		SpriteSheet sheet = mob.getSheet();
		sheet.startUse();
		sheet.renderInUse((int) (mob.getX() - xOffset), (int) (camera.getYOffset() - yOffset), mob.getAnimIndex(), mob.getDir());
		sheet.endUse();
	}

	public void renderEntity(Entity entity) {
	}

	public void setOffset(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}


}
