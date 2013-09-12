package nl.tdegroot.games.nemesis.gfx;

import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.entity.Mob;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.entity.projectile.Projectile;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.Path;

public class Screen {

	public float xOffset, yOffset;
	private Camera camera;
	private Graphics graphics;

	public Screen(Camera camera, Graphics g) {
		this.camera = camera;
		this.graphics = g;
	}

	public void renderMap(TiledMap map, int tileSize, String l, int amount) {
		String[] layerArray = new String[amount];
		if (amount > 1) {
			layerArray = l.split(":");
		} else {
			layerArray[0] = l;
		}

		int x = (int) - (xOffset % tileSize) - tileSize;
		int y = (int) - (yOffset % tileSize) - tileSize;
		int sx = (int) (xOffset / tileSize) - 1;
		int sy = (int) (yOffset / tileSize) - 1;
		int sectionWidth = (Display.getWidth() / tileSize) + 3;
		int sectionHeight = (Display.getHeight() / tileSize) + 4;

		for (int i = 0; i < amount; i++) {
			int layer = map.getLayerIndex(layerArray[i]);
			map.render(x, y, sx, sy, sectionWidth, sectionHeight, layer, false);
		}
	}

	public void renderPlayer(Player player) {
		SpriteSheet sheet = player.getSheet();
		sheet.startUse();
		sheet.renderInUse((int) camera.getXOffset(), (int) camera.getYOffset(), player.getAnimIndex(), player.getDir());
		sheet.endUse();
//		graphics.drawRect(camera.getXOffset(), camera.getYOffset(), 53, 64);
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

	public void renderParticle(float x, float y, Image sprite) {
		sprite.draw(x - xOffset, y - yOffset);
	}

	public void renderPath(Path destination) {
		for (int i = 0; i < destination.getLength(); i++) {
			graphics.fillRect(destination.getX(i), destination.getY(i), 64, 64);
		}
	}

	public void renderEntity(Entity entity) {
		entity.getSprite().draw(entity.getX() * 64 - xOffset, entity.getY() * 64 - yOffset);
	}

	public void renderText(String msg, float x, float y, boolean fixed, TrueTypeFont font, Color color) {
		graphics.setFont(font);
		Color old = graphics.getColor();
		graphics.setColor(color);
		if (fixed) graphics.drawString(msg, x, y);
		else graphics.drawString(msg, (x - graphics.getFont().getWidth(msg) / 2) - xOffset, y - yOffset);
		graphics.setColor(old);
		graphics.resetFont();
	}

	public void setOffset(float xOffset, float yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public Graphics getGraphics() {
		return graphics;
	}
}
