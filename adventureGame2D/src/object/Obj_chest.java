package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class Obj_chest extends Entity {
	
	public Obj_chest(GamePanel gp) {
	 super(gp);  
	 
	 name = "chest";
	 down1 = setupCharacter("chest","/objects/");
	}		
}
