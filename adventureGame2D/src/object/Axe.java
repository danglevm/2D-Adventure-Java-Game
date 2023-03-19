package object;

import adventureGame2D.GamePanel;
import enums.ObjectType;
import enums.ToolType;

public class Axe extends GameObject implements AttackObjectInterface, ToolObjectInterface{
	GamePanel gp;
	
	public Axe(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
		
	}

	@Override
	public void setDefaultAttributes() {
		name = "Crusty Greasy Axe";
		objectDescription = "Nearly unusable but full of potential to be greater.\nStrangely, it looks like your farmer uncle's old axe.\nCan be used to chop down trees and gives 1 physical attack.";
		collisionOn = false;
		down1 = setupEntity("axe", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
		attackArea.width = 36;
		attackArea.height = 36;
	}

	@Override
	public void setPickupState() {
		this.pickUpState = true;	
	}
	
	@Override
	public void setInventoryType() {
		this.inventoryType = ObjectType.TOOL;	
	}

	@Override
	public int getAttackValue() {
		return 1;
	}

	@Override
	public ToolType getToolType() {
		return ToolType.AXE;
	}
}
