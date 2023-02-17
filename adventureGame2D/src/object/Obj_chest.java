package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class Obj_chest extends Entity {
	
	public Obj_chest(GamePanel gp, int worldX, int worldY) {
	 super(gp);  
	 name = "chest";
	 this.WorldX = worldX * gp.tileSize;
	 this.WorldY = worldY * gp.tileSize;
	 down1 = setupCharacter("chest","/objects/", gp.tileSize, gp.tileSize);
	}		
}
