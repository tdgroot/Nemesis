package nl.tdegroot.games.nemesis.ui;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Resources;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class CharacterHUD {

	private Image overlay;
	private Player player;

	private float xHealth, yHealth;
	private float healthOriginWidth, healthOriginHeight;
	private double healthBarY = 0;
	private double healthBarHeight;

	private float xEnergy, yEnergy;
	private float energyOriginWidth, energyOriginHeight;
	private double energyBarY = 0;
	private double energyBarHeight = 173;

	public CharacterHUD(Image overlay, Player player) {
		this.overlay = overlay;
		this.player = player;

		xHealth = 20;
		yHealth = 17;
		healthOriginWidth = Resources.healthBar.getWidth();
		healthOriginHeight = Resources.healthBar.getHeight();

		xEnergy = 66;
		yEnergy = 17;
		energyOriginWidth = Resources.energyBar.getWidth();
		energyOriginHeight = Resources.energyBar.getHeight();
	}

	public void update(int delta) {
		double changePerHealthPoint = healthOriginHeight / player.getBaseHealth();
		double negativeHealth = player.getBaseHealth() - player.getHealth();
		double pixelsHealthDrained = negativeHealth * changePerHealthPoint;

		double changePerEnergyPoint = energyOriginHeight / 100;
		double negativeEnergy = 100 - player.getEnergy();
		double pixelsEnergyDrained = negativeEnergy * changePerEnergyPoint;

		healthBarY = yHealth + pixelsHealthDrained;
		healthBarHeight = healthOriginHeight - pixelsHealthDrained;

		energyBarY = yEnergy + pixelsEnergyDrained;
		energyBarHeight = energyOriginHeight - pixelsEnergyDrained;
	}

	public void render(Graphics g) {
		Resources.healthBar.draw(xHealth, (float) healthBarY, healthOriginWidth, (float) healthBarHeight);
		Resources.energyBar.draw(xEnergy, (float) energyBarY, energyOriginWidth, (float) energyBarHeight);
		overlay.draw(0, 0);
	}

}
