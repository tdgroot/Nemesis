package nl.tdegroot.games.nemesis.entity.npc;

import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Resources;
import nl.tdegroot.games.nemesis.gfx.Screen;
import nl.tdegroot.games.nemesis.item.Item;
import nl.tdegroot.games.nemesis.ui.Dialog;

public class FishNPC extends NPC {

	public FishNPC(float x, float y, int id) {
		super(Resources.fishSpot, x, y, 64, 64, "Fishing Spot", id);
	}

	public void render(Screen screen) {
		screen.renderEntity(this);
	}

	public void interact(Player player, Nemesis game) {
		if (player.hasItem(Item.fishingRod)) {
			Dialog.activate("You started fishing..");
			player.fish(this);
		} else {
			Dialog.activate("You don't have a rod.");
		}
	}

	public void describe() {
		Dialog.activate("I see some fish swimming here");
	}

	public String[] getInteractItems() {
		return new String[]{"Interact", "Describe"};
	}

	public String[] getDisplayItems() {
		return new String[]{"Fish", "Describe"};
	}

}
