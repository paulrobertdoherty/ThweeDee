package apocolypse.graphics;

public class Render {
	public final int width, height;
	public final int[] pixels;

	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void draw(Render render, int XOffset, int YOffset) {
		for (int x = 0; x < render.width; x++) {
			int xPix = x + XOffset;
			if (xPix < 0 || xPix >= width) {
				continue;
			}
			for (int y = 0; y < render.height; y++) {
				int yPix = y + YOffset;
				if (yPix < 0 || yPix >= height) {
					continue;
				}
				int alpha = render.pixels[x + y * render.width];
				if (alpha > 0) {
					pixels[xPix + yPix * width] = alpha;
				}
			}
		}
	}
}
