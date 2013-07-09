package nl.tdegroot.games.nemesis;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class RunGame {

	public static void main(String[] args) throws SlickException {
		AppGameContainer game = new AppGameContainer(new Nemesis());

		game.setDisplayMode(1280, 768, false);
		game.setShowFPS(true);
		game.setMaximumLogicUpdateInterval(17);
		game.setMinimumLogicUpdateInterval(17);
		game.start();
	}

}
