package apocolypse.gui;

import java.awt.event.*;
import apocolypse.*;

public class PlayEvent implements ActionListener{

	public void actionPerformed(ActionEvent arg0) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {}
		new RunGame();
	}
}
