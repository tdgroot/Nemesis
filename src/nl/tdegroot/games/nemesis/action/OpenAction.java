package nl.tdegroot.games.nemesis.action;

import nl.tdegroot.games.nemesis.Log;
import nl.tdegroot.games.nemesis.ui.Dialog;

public class OpenAction extends Action {

	public OpenAction() {
	}

	public void activate() {
		Dialog.activate("Open the box!");
	}

}
