package nl.tdegroot.games.nemesis.entity;

import actions.Interactable;
import nl.tdegroot.games.nemesis.Nemesis;

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
