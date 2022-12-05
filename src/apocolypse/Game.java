package apocolypse;

import java.awt.event.*;
import apocolypse.input.*;
import apocolypse.level.Level;

public class Game {
	public static int time;
	public Controller controls;
	public Level level;
	
	public Game() {
		controls = new Controller();
		level = new Level(80, 80, 15);
	}
	
	public void tick(boolean[] key) {
		time++;
		boolean foward = key[KeyEvent.VK_W];
		boolean backward = key[KeyEvent.VK_S];
		boolean right = key[KeyEvent.VK_D];
		boolean left = key[KeyEvent.VK_A];
		boolean jump = key[KeyEvent.VK_Q];
		boolean crouch = key[KeyEvent.VK_SHIFT];
		boolean sprint = key[KeyEvent.VK_CONTROL];
		boolean noclip = key[KeyEvent.VK_F];
		controls.tick(foward, backward, left, right, jump, crouch, sprint, noclip);
	}
}
