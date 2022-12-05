package apocolypse.graphics;

import apocolypse.*;
import apocolypse.input.*;
import apocolypse.level.*;

public class Render3d extends Render {
	public static double bobbing;
	public static boolean bobbingAllowed = false;
	private double foward, right, cosine, sine, up;

	public Render3d(int width, int height) {
		super(width, height);
		zBuffer = new double[width * height];
		zBufferWall = new double[width];
	}
	public double[] zBuffer;
	public double[] zBufferWall;
	public double renderDistance = 20000;
	
	public void floor(Game g, int floorPosition, int ceilingPosition) {
		for (int x = 0; x < width; x++) {
			zBufferWall[x] = 0;
		}
		double rotation = g.controls.rotation;
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);
		if (bobbingAllowed && Controller.walking) {
			bobbing = Math.sin(Game.time / 4.0) * 0.5;
		}
		if (Controller.runWalk) {
			bobbing = Math.sin(Game.time / 4.0) * 5;
		}
		
		right = g.controls.x;
		foward = g.controls.z;
		double up = g.controls.y;
		this.up = up;
		for (int y = 0; y < height; y++) {
			double ceiling = (y - height / 2.0) / height;
			double z = (floorPosition + up + bobbing) / ceiling;
			if (ceiling < 0) {
				z = (ceilingPosition - up - bobbing) / -ceiling;
			}
			for (int x = 0; x < width; x++) {
				double depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine + right;
				double yy = z * cosine - depth * sine + foward;
				int xPix = (int) (xx);
				int yPix = (int) (yy);
				zBuffer[x + y * width] = z;
				pixels[x + y * width] = Texture.floor.pixels[(xPix & 7) + (yPix & 7) * 16];
			}
		}
		Level level = g.level;
		int size = 50;
		for (int xBlock = -size; xBlock <= size; xBlock++) {
			for (int zBlock = -size; zBlock <= size; zBlock++) {
				Block block = level.create(xBlock, zBlock);
				Block east = level.create(xBlock + 1, zBlock);
				Block south = level.create(xBlock, zBlock + 1);
				
				if (block.solid) {
					if (!east.solid) {
						renderWall(xBlock + 2, xBlock + 2, zBlock, zBlock + 1, 0);
						renderWall(xBlock + 2, xBlock + 2, zBlock, zBlock + 1, 0.5);
					}
					if (!south.solid) {
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0);
						renderWall(xBlock + 1, xBlock, zBlock + 1, zBlock + 1, 0.5);
					}
				} else {
					if (east.solid) {
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0);
						renderWall(xBlock + 1, xBlock + 1, zBlock + 1, zBlock, 0.5);
					}
					if (south.solid) {
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0);
						renderWall(xBlock, xBlock + 1, zBlock + 1, zBlock + 1, 0.5);
					}
				}
			}
		}
	}
	
	public void renderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight) {
		RenderWall(xLeft, xRight, zDistanceLeft, zDistanceRight, yHeight);
		RenderWall(xRight, xLeft, zDistanceLeft, zDistanceRight, yHeight);
	}
	
	public void RenderWall(double xLeft, double xRight, double zDistanceLeft, double zDistanceRight, double yHeight) {
		double xcLeft = ((xLeft / 2) - (right / 17.5 + bobbing)) * 2;
		double zcLeft = ((zDistanceLeft / 2) - (foward / 17.5)) * 2;

		double rotLeftX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-yHeight) - -up / 17.5) * 2;
		double yCornerBL = ((+0.5 - yHeight) - -up / 17.5) * 2;
		double rotLeftZ = zcLeft * cosine + xcLeft * sine;

		double xcRight = ((xRight / 2) - right / 17.5) * 2;
		double zcRight = ((zDistanceRight / 2) - foward / 17.5) * 2;

		double rotRightX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight) - -up / 17.5) * 2;
		double yCornerBR = ((+0.5 - yHeight) - -up / 17.5) * 2;
		double rotRightZ = zcRight * cosine + xcRight * sine;

		
		double tex30 = 0;
		double tex40 = 8;
		double clip = 0.5;
		
		if (rotLeftZ < clip && rotRightZ < clip) {
			return;
		}
		
		if (rotLeftZ < clip) {
			double clip0 = (clip - rotLeftZ) / (rotRightZ - rotLeftZ);
			rotLeftZ = rotLeftZ + (rotRightZ - rotLeftZ) * clip0;
			rotLeftX = rotLeftX + (rotRightX - rotLeftX) * clip0;
			tex30 = tex30 + (tex40 - tex30) * clip0;
		}
		
		if (rotRightZ < clip) {
			double clip0 = (clip - rotRightZ) / (rotRightZ - rotLeftZ);
			rotRightZ = rotRightZ + (rotRightZ - rotLeftZ) * clip0;
			rotRightX = rotRightX + (rotRightX - rotLeftX) * clip0;
			tex40 = tex40 + (tex40 - tex30) * clip0;
		}
		
		double xPixelLeft = (rotLeftX / rotLeftZ * height + width / 2);
		double xPixelRight = (rotRightX / rotRightZ * height + width / 2);
		
		if (xPixelLeft >= xPixelRight) {
			return;
		}

		int xPixelLeftInt = (int) (xPixelLeft);
		int xPixelRightInt = (int) (xPixelRight);
		
		if (xPixelLeftInt < 0) {
			xPixelLeftInt = 0;
		}
		if (xPixelRightInt > width) {
			xPixelRightInt = width;
		}

		double yPixelLeftTop = (yCornerTL / rotLeftZ * height + height / 2.0);
		double yPixelLeftBottom = (yCornerBL / rotLeftZ * height + height / 2.0);
		double yPixelRightTop = (yCornerTR / rotRightZ * height + height / 2.0);
		double yPixelRightBottom = (yCornerBR / rotRightZ * height + height / 2.0);
		
		double tex1 = 1 / rotLeftZ;
		double tex2 = 1 / rotRightZ;
		double tex3 = tex30 / rotLeftZ;
		double tex4 = tex40 / rotRightZ - tex3;

		for (int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft) / (xPixelRight - xPixelLeft);
			double zWall = (tex1 + (tex2 - tex1) * pixelRotation);
			
			if (zBufferWall[x] > zWall) {
				continue;
			}
			zBufferWall[x] = zWall;
			
			int xTexture = (int) ((tex3 + tex4 * pixelRotation) / zWall);

			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop) * pixelRotation;
			double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom) * pixelRotation;

			int yPixelTopInt = (int) (yPixelTop);
			int yPixelBottomInt = (int) (yPixelBottom);

			if (yPixelTopInt < 0) {
				yPixelTopInt = 0;
			}
			if (yPixelBottomInt > height) {
				yPixelBottomInt = height;
			}

			for (int y = yPixelTopInt; y < yPixelBottomInt; y++) {
				double pixelRotationY = (y - yPixelTop) / (yPixelBottom - yPixelTop);
				int yTexture = (int) (8 * pixelRotationY);
				//pixels[x + y *width] = xTexture * 100 + yTexture * 100 * 256;
				pixels[x + y * width] = Texture.floor.pixels[8 + (xTexture & 7) + (yTexture & 7) * 16];
				zBuffer[x + y * width] = 1 / (tex1 + (tex2 + tex1) * pixelRotation) * 32;
			}
		}
	}
	
	public void renderDistance() {
		for (int i = 0; i < width * height; i++) {
			int color = pixels[i];
			int brightness = (int) (renderDistance / zBuffer[i]);
			
			if (brightness < 0) {
				brightness = 0;
			}
			
			if (brightness > 255) {
				brightness = 255;
			}
			
			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;
			
			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;
			
			pixels[i] = r << 16 | g << 8 | b;
		}
	}
}