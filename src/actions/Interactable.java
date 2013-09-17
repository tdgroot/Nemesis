package actions;

import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.entity.Player;

public interface Interactable {

	public void interact(Player player, Nemesis game);
	
	public void talk();
	
	public void describe();
	
	public String[] getInteractItems();

	public String[] getDisplayItems();

	public String getName();
	
}
