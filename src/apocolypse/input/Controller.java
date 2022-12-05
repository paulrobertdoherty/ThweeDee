package apocolypse.input;

import apocolypse.Apocolypse;

public class Controller {
	public double x, y, z, rotation, xa, za, rotationa;
	public static boolean turnLeft = false, turnRight = false;
	public static boolean ableToRun = true;
	public static boolean walking = false;
	public static boolean runWalk = true;
	public static boolean isPaused = false;

	public void tick(boolean foward, boolean back, boolean left, boolean right, boolean jump, boolean crouch, boolean sprint, boolean noclip) {
		double rotationSpeed = 0.002 * Apocolypse.mouseSpeed;
		double walkSpeed = 0.4;
		double jumpHeight = 1;
		double crouchHeight = 0.8;
		double xMove = 0;
		double zMove = 0;

		if (foward) {
			zMove += 2.5;
			walking = true;
		}
		if (back) {
			zMove -= 2.5;
			walking = true;
		}
		if (left) {
			xMove -= 2.5;
			walking = true;
		}
		if (right) {
			xMove += 2.5;
			walking = true;
		}
		if (turnLeft) {
			if(InputHandler.MouseBotton == 3) {
				
			} else {
				rotationa -= rotationSpeed;
			}
		}
		if (turnRight) {
			if(InputHandler.MouseBotton == 3) {
				
			} else {
				rotationa += rotationSpeed;
			}
		}
		if (jump) {
			y += jumpHeight;
			ableToRun = false;
		} else {
			ableToRun = true;
		}
		if (crouch) {
			y -= crouchHeight;
			walkSpeed = 0.1;
			ableToRun = false;
			walking = false;
		} else {
			ableToRun = true;
		}
		if (sprint & ableToRun) {
			walkSpeed = 0.8;
		}
		if (!foward && !back && !left && !right) {
			walking = false;
		}
		if (!sprint) {
			runWalk = false;
		}
		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

		x += xa;
		z += za;

		xa *= 0.1;
		if (!noclip) {
			y *= 0.9;
		}
		za *= 0.1;

		rotation += rotationa;
		rotationa *= 0.5;
	}
}
