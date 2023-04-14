package object.nonpickup;
import adventureGame2D.GamePanel;
import enums_and_constants.ObjectType;
import object.GameObject;


public class Chest extends GameObject {
	
	GamePanel gp;
	
	public Chest(GamePanel gp, int worldX, int worldY) {  
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
		setTradeNameDescription();
	}

	@Override
	public void setDefaultAttributes() {
		 name = "chest";
		 down1 = setupEntity("chest","/objects/environment/", gp.getTileSize(), gp.getTileSize());
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
		this.tradeName = "Chest";
		this.tradeDescription = "Not supposed to be here. You cheating?";
		
	}		
}
