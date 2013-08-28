package nl.tdegroot.games.nemesis.map.object;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.ui.Dialog;

public class Fence extends MapObject{

	public Fence() {
		this.message = "Just a fence..";
	}

	public void interact(Player player) {
		Dialog.activate(message);
	}

}
