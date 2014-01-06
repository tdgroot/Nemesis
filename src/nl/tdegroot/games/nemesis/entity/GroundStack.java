package nl.tdegroot.games.nemesis.entity;

import actions.Interactable;
import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.gfx.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timon on 26-12-13.
 */
public class GroundStack extends Entity implements Interactable {

    private List<GroundItem> items = new ArrayList<GroundItem>();

    public void add(GroundItem item) {
        items.add(0, item);
    }

    public void remove(int index) {
        items.remove(index);
    }

	public void update(int delta) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).update(delta);
		}
	}

	public void render(Screen screen) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).render(screen);
		}
	}

	public void interact(Player player, Nemesis game) {
    }

    public void talk() {
    }

    public void describe() {
    }

    public String[] getInteractItems() {
        return new String[0];
    }

    public String[] getDisplayItems() {
        return new String[0];
    }

    public String getName() {
        return "Items";
    }
}
