package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Obj_key extends SuperObject {
	public Obj_key() {
		name = "Key";
	
	
	try {
		image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
		
	} catch (IOException e) {
		e.printStackTrace(); //Trace back this error
	}
	} 
}
