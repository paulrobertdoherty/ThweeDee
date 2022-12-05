package apocolypse;

import java.applet.*;
import java.awt.*;

public class ApocolypseApplet extends Applet {
	private static final long serialVersionUID = 1l;
	private Apocolypse display = new Apocolypse();
	
	public void init() {
		setLayout(new BorderLayout());
		add(display);
	}

	public void start() {
		super.resize(Apocolypse.width, Apocolypse.height);
		display.start();
	}

	public void stop() {
		display.stop();
	}
}