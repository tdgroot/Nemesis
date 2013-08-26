package nl.tdegroot.games.nemesis.map.object;

import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.ui.Dialog;

public class Sign extends MapObject {

	public void interact(Player player) {
		Dialog.activate("The sign says:");
		Dialog.activate(message);
	}

}
