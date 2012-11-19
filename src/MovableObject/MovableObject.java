package MovableObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class MovableObject {
	public abstract void paint();

	/**
	 * macht eine Farbe in einem Bild durchsichtig
	 * 
	 * @param img
	 * @param color
	 * @return
	 */
	protected static BufferedImage setTranslucentColor(BufferedImage img,
			Color color) {
		for (int i = img.getWidth() - 1; i > -1; i--) {
			for (int j = img.getHeight() - 1; j > -1; j--) {
				if (img.getRGB(i, j) == color.getRGB()) {
					img.setRGB(i, j, new Color(0, 0, 0, 0).getRGB());
				}
			}
		}
		return img;
	}

	/**
	 * verkleinert, bzw vergrößert ein BufferedImage
	 * 
	 * @param rohImage
	 * @param scale
	 * @return
	 */
	protected static BufferedImage getScaledImage(BufferedImage rohImage,
			int scale) {

		int w = rohImage.getWidth() / scale;
		int h = rohImage.getHeight() / scale;

		BufferedImage scaledImage = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale((double) 1 / scale, (double) 1 / scale);
		AffineTransformOp scaleOp = new AffineTransformOp(at,
				AffineTransformOp.TYPE_BILINEAR);
		scaledImage = scaleOp.filter(rohImage, scaledImage);
		return scaledImage;
	}

	/**
	 * rotiert ein Bild und setzt die Grundlage für die CollisionDetection
	 * @param src
	 * @param degrees
	 * @return
	 */
	protected static BufferedImage getRotateImage(BufferedImage src, double degrees) {
		int WidthHeight = (int) Math.sqrt(src.getWidth() * src.getWidth()
				+ src.getHeight() * src.getHeight());
		AffineTransform affineTransform = AffineTransform.getRotateInstance(
				Math.toRadians(degrees), WidthHeight / 2, WidthHeight / 2);
		BufferedImage rotatedImage = new BufferedImage(WidthHeight,
				WidthHeight, src.getType());
		Graphics2D g = (Graphics2D) rotatedImage.getGraphics();
		g.setTransform(affineTransform);
		g.drawImage(src, (WidthHeight - src.getWidth()) / 2, (WidthHeight - src
				.getHeight()) / 2, null);
		return rotatedImage;
	}
}
