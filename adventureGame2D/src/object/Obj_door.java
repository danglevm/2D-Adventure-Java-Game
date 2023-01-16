package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Obj_door extends SuperObject{
	
	public Obj_door() {
	name  = "Door";
	
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
		
	} catch (IOException e) {
		e.printStackTrace(); //Trace back this error
	}
	}
} 


