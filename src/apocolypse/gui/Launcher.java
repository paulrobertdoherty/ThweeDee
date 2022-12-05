package apocolypse.gui;

import java.awt.*;
import javax.swing.*;

public class Launcher extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel window = new JPanel();
	private JButton play, options, help, quit;
	private Rectangle rPlay, rOptions, rHelp, rQuit;
	private int width = 250;
	private int height = 320;

	public Launcher() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		setTitle("Apocolypse launcher 0.01");
		setSize(new Dimension(width, height));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(window);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		window.setLayout(null);
		drawButtons();
	}
	
	private void drawButtons() {
		play = new JButton("Play");
		rPlay = new Rectangle(10, 10, 80, 40);
		play.setBounds(rPlay);
		window.add(play);
		play.addActionListener(new PlayEvent());
		
		options = new JButton("Options");
		rOptions = new Rectangle(10, 50, 80, 40);
		options.setBounds(rOptions);
		window.add(options);
		options.addActionListener(new OptionEvent());
		
		help = new JButton("Help");
		rHelp = new Rectangle(10, 90, 80, 40);
		help.setBounds(rHelp);
		window.add(help);
		help.addActionListener(new HelpEvent());
		
		quit = new JButton("Quit");
		rQuit = new Rectangle(10, 130, 80, 40);
		quit.setBounds(rQuit);
		window.add(quit);
		quit.addActionListener(new QuitEvent());
	}
}
