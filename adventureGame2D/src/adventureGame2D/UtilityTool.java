package adventureGame2D;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UtilityTool {
	
	/*
	 * Meant to store useful  functions
	 */
	
	/*
	 * Scale tile images
	 */
	public BufferedImage scaleImage (BufferedImage original, int width, int height) {
		//Scale the image before being drawn - pass width, height, image type
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		
		//Whatever g2 is going to draw it is going be stored into scaledImage
		Graphics2D g2 = scaledImage.createGraphics();
		
		//draw tile.image into scaledImage that the graphics 2d is linked to
		g2.drawImage(original, 0, 0, width, height, null);
		
		g2.dispose();
		//reset the tile into the scaled Image
		return scaledImage;
	}
	
}
