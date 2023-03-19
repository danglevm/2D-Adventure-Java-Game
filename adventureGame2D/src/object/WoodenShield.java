package object;

import adventureGame2D.GamePanel;
import enums.InventoryObjectType;

public class WoodenShield extends GameObject implements DefenseObjectInterface{
	
	private int defenseValue;

	GamePanel gp;
	
	public WoodenShield(GamePanel gp) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
	}
	
	public void setDefaultAttributes() {
		name = "Dusty Old Wooden Shield";
		down1 = setupEntity("shield_wood", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
		defenseValue = 1;
		objectDescription = "Honed from the finest craftsman in the land. \nTruly unbreakable, until it breaks. \nGives 1 Defense.";
		
		
	}

	@Override
	public int getDefenseValue() {
		// TODO Auto-generated method stub
		return defenseValue;
	}

	@Override
	public void setPickupState() {
		this.pickUpState = true;
		
	}

	@Override
	protected void setInventoryType() {
		this.inventoryType = InventoryObjectType.DEFENSE;
		
	}

}
