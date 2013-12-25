package nl.tdegroot.games.nemesis.entity.npc;

import actions.Interactable;
import nl.tdegroot.games.nemesis.Nemesis;
import nl.tdegroot.games.nemesis.entity.Entity;
import nl.tdegroot.games.nemesis.entity.Player;
import nl.tdegroot.games.nemesis.gfx.Screen;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

public class NPC extends Entity implements Interactable {

	protected String name;
	protected int id = 0;
	private String message;

    protected SpriteSheet sheet;
    public int animIndex = 0;
    public int animCount = 0;
    public int animType = 0;
    public int frame = 0;

	public NPC(Image image, float x, float y, int width, int height, String name, int id) {
		super(image, x, y, width, height);
		this.name = name;
		this.id = id;
        animCount = image.getWidth() / width;
        sheet = new SpriteSheet(image, width, height);
	}

	public void update(int delta) {
        if (frame % (175 / delta) == 0) {
            animIndex = ((animIndex + 1) % animCount);
        }
	}

    public SpriteSheet getSheet() {
        return sheet;
    }

    public int getAnimIndex() {
        return animIndex;
    }

	public void interact(Player player, Nemesis game) {
	}

	public void describe() {
	}

	public void talk() {
	}

	public void render(Screen screen) {
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String[] getInteractItems() {
		return new String[]{"Interact", "Talk", "Describe"};
	}

	public String[] getDisplayItems() {
		return new String[]{"Interact", "Talk", "Describe"};
	}

	public String getName() {
		return name;
	}

}
