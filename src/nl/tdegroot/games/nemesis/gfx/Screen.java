package nl.tdegroot.games.nemesis.gfx;

import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import nl.tdegroot.games.nemesis.ui.Dialog;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

import java.util.List;

public class Screen {

	private float xOffset, yOffset;
	private Camera camera;
	private Graphics graphics;

	public Screen(Camera camera, Graphics g) {
		this.camera = camera;
		this.graphics = g;
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
		sheet.renderInUse((int) (mob.getX() - xOffset), (int) (mob.getY() - yOffset), mob.getAnimIndex(), mob.getDir());
		sheet.endUse();
	}

	public void renderProjectile(Projectile projectile) {
		projectile.getSprite().setRotation((float) Math.toDegrees(- projectile.getAngle()) - 180.0f);
		projectile.getSprite().drawCentered(projectile.getX() - xOffset, projectile.getY() - yOffset);
	}

	public void renderDialog() {
		List<String> history = Dialog.history;
		Image img = Dialog.getImage();

		float dialogOffset = (Display.getWidth() - img.getWidth()) / 2;
		img.draw(dialogOffset, Display.getHeight() - img.getHeight(), Display.getWidth() - dialogOffset * 2, img.getHeight());

		graphics.setFont(Resources.agency_fb);
		for (int i = 0; i < history.size(); i++) {
			graphics.drawString(history.get(i), dialogOffset + 70, (Display.getHeight() - img.getHeight() + 19) + i * 22);
		}
	}

	public void renderEntity(Entity entity) {
	}

	public void setOffset(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
