package object;

import adventureGame2D.GamePanel;
import enums.ObjectType;

public class Heart extends GameObject {
	
	GamePanel gp;
	
	public Heart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
	}

	public void setDefaultAttributes() {
		name = "Heart";
		image = setupEntity("heart_full", "/heart/", gp.getTileSize(), gp.getTileSize());
		image2 = setupEntity("heart_half", "/heart/", gp.getTileSize(), gp.getTileSize());
		image3 = setupEntity("heart_blank", "/heart/", gp.getTileSize(), gp.getTileSize());
		
	}

	@Override
	public void setPickupState() {
		this.pickUpState = false;
		
	}
	
	@Override
	public void setInventoryType() {
		this.inventoryType = ObjectType.NONPICKUP;
		
	}	
}
