package object;

import adventureGame2D.GamePanel;


public class ObjectDoor extends GameObject {

	GamePanel gp;
	
	public ObjectDoor(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
	}

	public void setDefaultAttributes() {
		name  = "Door";
		 down1 = setupEntity("door","/objects/environment/", gp.getTileSize(), gp.getTileSize());
		collisionOn = true;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
	}

	@Override
	public void setPickupState() {
		this.pickUpState = false;
		
	}
	
	
} 


