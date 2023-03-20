package object.defense;

import adventureGame2D.GamePanel;
import enums.ObjectType;
import object.GameObject;
import object.interfaces.DefenseObjectInterface;

public class WoodenShield extends GameObject implements DefenseObjectInterface{
	
	private int defenseValue;

	GamePanel gp;
	
	public WoodenShield(GamePanel gp, int WorldX, int WorldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = WorldX * gp.getTileSize();
		this.WorldY = WorldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
	}
	
	public void setDefaultAttributes() {
		name = "Dusty Old Wooden Shield";
		down1 = setupEntity("shield_wood", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
		defenseValue = 1;
		objectDescription = "Honed from the finest craftsman in the land. \nTruly unbreakable, until it breaks. \nGrants 1 Defense.";
		
		
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
		this.inventoryType = ObjectType.DEFENSE;
		
	}

}
