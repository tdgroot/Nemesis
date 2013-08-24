package nl.tdegroot.games.nemesis.map.object;

import nl.tdegroot.games.nemesis.map.action.Action;

public class ContainerObject extends MapObject {

	String items;

	public ContainerObject(Action action, String items) {
		super(action);
		this.items = items;
	}

}
