package nl.tdegroot.games.nemesis.map.object;

import nl.tdegroot.games.nemesis.action.Action;

public class MapObject {

	protected Action action;

	public MapObject(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	public void interact() {
		if (action != null) {
			action.activate();
		}
	}

}
