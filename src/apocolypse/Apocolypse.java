package apocolypse;

import java.awt.*;
import java.awt.image.*;
import apocolypse.graphics.*;
import apocolypse.gui.*;
import apocolypse.input.*;

public class Apocolypse extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public static int width = 700;
	public static int height = 500;
	public static String title = "Apocolypse Pre-alpha 0.02";
	private Thread thread;
	private boolean running = false;
	Screen screen;
	private BufferedImage img;
	private int[] pixels;
	public static Game game = new Game();
	private InputHandler input = new InputHandler();
	private int newX, oldX, fps;
	public static int mouseSpeed;

	public synchronized void start() {
		if (running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new Launcher();
	}

	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		requestFocus();
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					fps = frames;
					previousTime += 1000;
					frames = 0;
				}
			}
			if (ticked) {
				render();
				frames++;
			}
			render();
			frames++;
			newX = InputHandler.MouseX;
			if (newX > oldX) {
				Controller.turnRight = true;
			}
			if (newX < oldX) {
				Controller.turnLeft = true;
			}
			if(newX == oldX) {
				Controller.turnRight = false;
				Controller.turnLeft = false;
			}
			mouseSpeed = Math.abs(newX - oldX);
			oldX = newX;
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.render(game);
		for (int i = 0; i < width * height; i++) {
			pixels[i] = screen.pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		g.setFont(new Font("Arial Black", 1, 20));
		g.setColor(Color.YELLOW);
		g.drawString(fps + " FPS", 20, 50);
		g.dispose();
		bs.show();
	}

	private void tick() {
		game.tick(input.key);
	}

	public Apocolypse() {
		screen = new Screen(width, height);
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		input = new InputHandler();
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		addFocusListener(input);
	}
}
