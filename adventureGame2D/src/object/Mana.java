package object;

import adventureGame2D.GamePanel;
import enums.ObjectType;

public class Mana extends GameObject {
	
	GamePanel gp;
	
	public Mana (GamePanel gp) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
	}

	public void setDefaultAttributes() {
		name = "Mana Crystal";
		image = setupEntity("manacrystal_full", "/hud/", gp.getTileSize(), gp.getTileSize());
		image2 = setupEntity("manacrystal_blank", "/hud/", gp.getTileSize(), gp.getTileSize());
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
