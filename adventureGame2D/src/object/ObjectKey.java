package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class ObjectKey extends Entity implements ObjectInterface {
	
	GamePanel gp;
	
	public ObjectKey(GamePanel gp, int worldX, int worldY) {
		super (gp);
		this.gp = gp;
		this.WorldX = worldX * gp.tileSize;
		this.WorldY = worldY * gp.tileSize;
		
		setDefaultAttributes();
		
	}
	
	
	public void setDefaultAttributes() {
		name = "Key";
		collisionOn = false;
		down1 = setupEntity("key", "/objects/environment/", gp.tileSize, gp.tileSize);
		
	}
	
}

