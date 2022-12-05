package apocolypse.graphics;

import java.awt.image.*;
import javax.imageio.*;

public class Texture {
	public static Render floor = loadBitmap("/textures/texture.png");
	
	public static Render loadBitmap(String filename) {
		try {
			BufferedImage image = ImageIO.read(Texture.class.getResource(filename));
			int width  = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width, height);
			image.getRGB(0, 0, width, height, result.pixels, 0, width);
			return result;
		} catch (Exception e) {
			System.out.println("The image could not be found, or something else happened.");
			throw new RuntimeException(e);
		}
	}
}
