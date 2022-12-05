package apocolypse;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class RunGame {
	
	public RunGame() {
		Apocolypse game = new Apocolypse();
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		JFrame frame = new JFrame(Apocolypse.title);
		frame.add(game);
		frame.pack();
		frame.getContentPane().setCursor(blank);
		frame.setSize(Apocolypse.width, Apocolypse.height);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		game.start();
	}
}
