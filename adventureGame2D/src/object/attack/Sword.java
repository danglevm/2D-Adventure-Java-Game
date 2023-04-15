package object.attack;

import adventureGame2D.GamePanel;
import enums_and_constants.ObjectType;
import object.GameObject;
import object.interfaces.AttackObjectInterface;

public class Sword extends GameObject implements AttackObjectInterface {

	GamePanel gp;
	
	private int attackValue;
	
	public Sword(GamePanel gp, int WorldX, int WorldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = WorldX * gp.getTileSize();
		this.WorldY = WorldY * gp.getTileSize();
		this.buyPrice = 30;
		this.sellPrice = 15;
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
		setTradeNameDescription();
	}


	@Override
	public void setDefaultAttributes() {
		
		name = "Old Shabby Metal Sword";
		down1 = setupEntity("sword_normal", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
		attackValue = 2;
		objectDescription = "A most fitting companion for a true hero... \nExcept you are a bum. A piece of scrap metal now. \nGrants 1 power and sword swinging.";
		attackArea.width = 36;
		attackArea.height = 36;
	}
	
	@Override
	public int getAttackValue() {
		return attackValue;
	}


	@Override
	public void setPickupState() {
		this.pickUpState = true;
		
	}


	@Override
	public void setInventoryType() {
		this.inventoryType = ObjectType.ATTACK;
		
	}


	@Override
	public void setTradeNameDescription() {
		this.tradeName = "Iron \nSword";
		this.tradeDescription = "Grants 2 attack power";
		
	}



	
}
