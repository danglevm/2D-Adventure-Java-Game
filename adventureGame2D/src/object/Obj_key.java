package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class Obj_key extends Entity{
	
	public Obj_key(GamePanel gp, int worldX, int worldY) {
		super (gp);
		this.WorldX = worldX * gp.tileSize;
		this.WorldY = worldY * gp.tileSize;
		name = "Key";
		collisionOn = false;
		down1 = setupCharacter("key", "/objects/", gp.tileSize, gp.tileSize);
		
	}
	
}

