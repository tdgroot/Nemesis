package nl.tdegroot.games.nemesis.entity.particles;

import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class TextParticle extends Particle {

	public enum Type {
		BIG, NORMAL, TINY
	}

	Color color;
	String msg;
	TrueTypeFont font;
	Type type;

	public TextParticle(String msg, float x, float y, int life, Type type,  Color color) {
		super(x, y, life, null);
		this.color = color;
		this.msg = msg;
		this.type = type;

		if (type == Type.BIG) {
			font = Resources.CRIT_HIT;
		} else if (type == Type.NORMAL) {
			font = Resources.NORMAL_HIT;
		}

		ya = random.nextGaussian();
		xa = random.nextGaussian();
	}

	public void update(int delta) {
		x += xa;
		y += ya;
		if (life > 0) life -= delta;
		if (life <= 0) remove();
	}

	public void render(Screen screen) {
		screen.renderText(msg, x, y, false, font, color);
	}

}
