package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;

public class Obj_key extends SuperObject {
	GamePanel gp;
	public Obj_key(GamePanel gp) {
		name = "Key";
		this.gp = gp;
	
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
		uTool.scaleImage(image, gp.tileSize, gp.tileSize);

	} catch (IOException e) {
		e.printStackTrace(); //Trace back this error
	}
	} 
}
