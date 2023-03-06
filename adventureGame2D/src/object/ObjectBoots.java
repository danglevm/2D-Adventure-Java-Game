package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class ObjectBoots extends Entity implements ObjectInterface {
	
	GamePanel gp;
	
	public ObjectBoots(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.tileSize;
		this.WorldY = worldY * gp.tileSize;
		setDefaultAttributes();
		
	}

	@Override
	public void setDefaultAttributes() {
		name = "Boots";
		collisionOn = false;
		down1 = setupCharacter("boots", "/objects/equip/", gp.tileSize, gp.tileSize);
	}
}

