package apocolypse.input;

import java.awt.*;
import java.awt.event.*;

import apocolypse.*;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener, FocusListener {
	public boolean[] key = new boolean[68836];
	public static int MouseX, MouseY, MouseBotton;

	public void focusGained(FocusEvent e) {
	}

	public void focusLost(FocusEvent e) {
		for (int i = 0; i < key.length; i++) {
			key[i] = false;
		}
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e1) {
			MouseX = e1.getX();
			MouseY = e1.getY();
			try {
				Robot robot = new Robot();
				if (MouseX == 0) {
					robot.mouseMove(Apocolypse.width, Apocolypse.height / 2);
				}
				if (MouseX == Apocolypse.height) {
				}
			} catch (AWTException e) {}
	}

	public void mouseClicked(MouseEvent e) {
		MouseBotton = e.getButton();
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length) {
			key[keyCode] = false;
		}
	}

	public void keyTyped(KeyEvent e) {}
}