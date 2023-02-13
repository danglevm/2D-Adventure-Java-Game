package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class Obj_door extends Entity{
	public Obj_door(GamePanel gp, int worldX, int worldY) {
		super(gp);
		name  = "Door";
		down1 = setupCharacter("door", "/objects/");
		collisionOn = true;
		this.WorldX = worldX;
		this.WorldY = worldY;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	
	}
	
	
} 


