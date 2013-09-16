package nl.tdegroot.games.nemesis.map.object;

import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.ui.Dialog;

public class Sign extends MapObject {

	public void interact(Player player, Nemesis game) {
		if (message != "") {
			Dialog.activate("The sign says: " + message);
		} else {
			Dialog.activate("The sign is empty..");
		}
	}

	public void describe() {
		if (message != "") {
			Dialog.activate("A sign with something written on it");
		} else {
			Dialog.activate("An empty sign");
		}
	}
	
	public String[] getInteractItems(){
		return new String[] {"Read", "Describe"};
	}

}
