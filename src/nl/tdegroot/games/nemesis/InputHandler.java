package nl.tdegroot.games.nemesis;

import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

public class InputHandler implements MouseListener {

	private static int mouseX = - 1;
	private static int mouseY = - 1;
	private static int mouseB = - 1;

	public InputHandler() {

	}

	public static int getMouseX() {
		return mouseX;
	}

	public static int getMouseY() {
		return mouseY;
	}

	public static int getMouseB() {
		return mouseB;
	}

	public void mouseWheelMoved(int change) {
	}

	public void mouseClicked(int button, int x, int y, int clickCount) {
	}

	public void mousePressed(int button, int x, int y) {
		mouseB = button;
	}

	public void mouseReleased(int button, int y, int x) {
		mouseB = - 1;
	}

	public void mouseMoved(int oldX, int oldY, int newX, int newY) {
		mouseX = newX;
		mouseY = newY;
	}

	public void mouseDragged(int oldX, int oldY, int newX, int newY) {
		mouseX = newX;
		mouseY = newY;
	}

	public void setInput(Input input) {
	}

	public boolean isAcceptingInput() {
		return true;
	}

	public void inputEnded() {
	}

	public void inputStarted() {
	}
}
