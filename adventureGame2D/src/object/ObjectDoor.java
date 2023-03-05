package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class ObjectDoor extends Entity implements ObjectInterface {
	
	GamePanel gp;
	
	public ObjectDoor(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
	}

	public void setDefaultAttributes() {
		name  = "door";
		down1 = setupEntity("door", "/objects/environment/", gp.tileSize, gp.tileSize);
		collisionOn = true;
		this.worldX = worldX * gp.tileSize;
		this.worldY = worldY * gp.tileSize;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
	}
	
	
} 


