package nl.tdegroot.games.nemesis;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class RunGame {

	public static void main(String[] args) throws SlickException {
		Log.log("Amount of arguments: " + args.length);
		AppGameContainer game = new AppGameContainer(new Nemesis());
		game.setDisplayMode(1280, 720, true);
		game.setShowFPS(false);
		game.setTargetFrameRate(60);
		game.setMaximumLogicUpdateInterval(1000 / 60);
		game.setMinimumLogicUpdateInterval(1000 / 60);
		game.start();
	}

}
