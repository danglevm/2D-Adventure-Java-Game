package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;

public class Object_heart extends SuperObject{
	GamePanel gp;
	public Object_heart(GamePanel gp) {
		this.gp = gp;
		name = "Heart";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/heart/heart_full.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/heart/heart_half.png"));
			image3 = ImageIO.read(getClass().getResourceAsStream("/heart/heart_blank.png"));
			image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
			image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
		} catch (IOException e) {
			e.printStackTrace(); //Trace back this error
		}
	}
}
