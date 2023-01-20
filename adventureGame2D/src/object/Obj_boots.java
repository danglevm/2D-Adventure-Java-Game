package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Obj_boots extends SuperObject{
	public Obj_boots() {
		name  = "Boots";
		collision=true;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
			
		} catch (IOException e) {
			e.printStackTrace(); //Trace back this error
		}
		}
}

