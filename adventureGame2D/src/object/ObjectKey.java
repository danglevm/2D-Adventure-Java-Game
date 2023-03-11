package object;

import adventureGame2D.GamePanel;


public class ObjectKey extends GameObject {
	
	GamePanel gp;
	
	public ObjectKey(GamePanel gp, int worldX, int worldY) {
		super (gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		
		setDefaultAttributes();
		
	}
	
	
	public void setDefaultAttributes() {
		name = "Key";
		collisionOn = false;
		down1 = setupEntity("key", "/objects/environment/", gp.getTileSize(), gp.getTileSize());
		
	}
	
}

