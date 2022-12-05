package apocolypse.graphics;

import java.util.*;

import apocolypse.*;

public class Screen extends Render {
	private Render test;
	Random random = new Random();
	private Render3d render;

	public Screen(int width, int height) {
		super(width, height);
		test = new Render(256, 256);
		render = new Render3d(width, height);
		for (int i = 0; i < 256 * 256; i++) {
			test.pixels[i] = random.nextInt() * (random.nextInt(5) / 4);
		}
	}

	public void render(Game game) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}
		render.floor(game, 10, 1000);
		//render.renderWall(0, 0.5, 1.5, 1.5, 0);
		//render.renderWall(0, 0, 1, 1.5, 0);
		//render.renderWall(0.5, 0.5, 1, 1.5, 0);
		//render.renderWall(0, 0.5, 1, 1, 0);
		render.renderDistance();
		draw(render, 0, 0);
	}
}
