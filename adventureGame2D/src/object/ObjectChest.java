package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class ObjectChest extends Entity implements ObjectInterface {
	
	GamePanel gp;
	
	public ObjectChest(GamePanel gp, int worldX, int worldY) {
		super(gp);  
		this.gp = gp;
		this.WorldX = worldX * gp.tileSize;
		this.WorldY = worldY * gp.tileSize;
		setDefaultAttributes();
	}

	@Override
	public void setDefaultAttributes() {
		 name = "chest";
		 down1 = setupCharacter("chest","/objects/environment/", gp.tileSize, gp.tileSize);
	}		
}
