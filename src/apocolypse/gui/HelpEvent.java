package apocolypse.gui;

import java.awt.event.*;
import javax.swing.*;

public class HelpEvent implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(null, "WASD to move, mouse to look around, and Q to jump.");
	}
}
