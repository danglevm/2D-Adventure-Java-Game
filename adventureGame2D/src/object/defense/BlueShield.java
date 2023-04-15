package object.defense;

import adventureGame2D.GamePanel;
import enums_and_constants.ObjectType;
import object.GameObject;
import object.interfaces.DefenseObjectInterface;

public class BlueShield extends GameObject implements DefenseObjectInterface{
	GamePanel gp;
	
	public BlueShield(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		this.buyPrice = 80;
		this.sellPrice = 40;
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
		encumbrance = 2;
		setTradeNameDescription();
	}

	@Override
	public void setDefaultAttributes() {
		name = "Gooey Blue Shield";
		objectDescription = "An unstoppable monstrosity of defense.\nSadly, you can barely move it with one hand.\nGrants 3 defense but slows you down by 2.";
		collisionOn = false;
		down1 = setupEntity("shield_blue", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
	}

	@Override
	public void setPickupState() {
		this.pickUpState = true;	
	}

	@Override
	public void setInventoryType() {
		this.inventoryType = ObjectType.DEFENSE;
		
	}

	@Override
	public int getDefenseValue() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void setTradeNameDescription() {
		this.tradeName = "Blue \nShield";
		this.tradeDescription = "Grants 3 defense but slows by 2";
		
	}
}
