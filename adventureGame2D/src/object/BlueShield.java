package object;

import adventureGame2D.GamePanel;
import enums.InventoryObjectType;

public class BlueShield extends GameObject implements DefenseObjectInterface{
	GamePanel gp;
	
	public BlueShield(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
		encumbrance = 2;
	}

	@Override
	public void setDefaultAttributes() {
		name = "Gooey Blue Shield";
		objectDescription = "An unstoppable monstrosity of defense.\nSadly, you can only barely able to move it with one hand.\nGives 3 defense but slows player speed by 2.";
		collisionOn = false;
		down1 = setupEntity("shield_blue", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
	}

	@Override
	public void setPickupState() {
		this.pickUpState = true;	
	}

	@Override
	public void setInventoryType() {
		this.inventoryType = InventoryObjectType.DEFENSE;
		
	}

	@Override
	public int getDefenseValue() {
		// TODO Auto-generated method stub
		return 3;
	}
}
