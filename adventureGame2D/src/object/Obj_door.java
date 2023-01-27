package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;

public class Obj_door extends SuperObject{
	GamePanel gp;
	public Obj_door(GamePanel gp) {
		name  = "Door";
		collision=true;
		this.gp = gp;
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
		uTool.scaleImage(image, gp.tileSize, gp.tileSize);

		
	} catch (IOException e) {
		e.printStackTrace(); //Trace back this error
	}
	
	}
	
	
} 


