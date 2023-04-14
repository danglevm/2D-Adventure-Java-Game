package object.nonpickup;

import adventureGame2D.GamePanel;
import enums_and_constants.ObjectType;
import object.GameObject;


public class Door extends GameObject {

	GamePanel gp;
	
	public Door(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
		setTradeNameDescription();
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
	
	@Override
	public void setInventoryType() {
		this.inventoryType = ObjectType.NONPICKUP;
		
	}

	@Override
	public void setTradeNameDescription() {
		this.tradeName = "Door";
		this.tradeDescription = "Not supposed to be here. You cheating?";
		
	}	
	
	
} 


