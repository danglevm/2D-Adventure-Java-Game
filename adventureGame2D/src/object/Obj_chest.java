package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Obj_chest extends SuperObject {
	public Obj_chest() {
		name  = "Chest";
		collision=true;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
			
		} catch (IOException e) {
			e.printStackTrace(); //Trace back this error
		}
		}
}
