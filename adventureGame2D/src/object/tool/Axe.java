package object.tool;

import adventureGame2D.GamePanel;
import enums_and_constants.ObjectType;
import enums_and_constants.ToolType;
import object.GameObject;
import object.interfaces.AttackObjectInterface;
import object.interfaces.ToolObjectInterface;

public class Axe extends GameObject implements AttackObjectInterface, ToolObjectInterface{
	GamePanel gp;
	
	public Axe(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		this.buyPrice = 40;
		this.sellPrice = 20;
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
		setTradeNameDescription();
	}

	@Override
	public void setDefaultAttributes() {
		name = "Crusty Greasy Axe";
		objectDescription = "Nearly unusable but full of potential to be greater.\nStrangely, it looks like your farmer uncle's old axe.\nLets you chop down trees and grants 1 physical attack.";
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

	@Override
	public void setTradeNameDescription() {
		this.tradeName = "Iron \nAxe";
		this.tradeDescription = "For chopping down dry trees";
		
	}
}
