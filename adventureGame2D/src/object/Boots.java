package object;


import adventureGame2D.GamePanel;
import enums_and_constants.ObjectType;


public class Boots extends GameObject {
	
	GamePanel gp;
	
	public Boots(GamePanel gp, int worldX, int worldY) {
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
		name = "Shaggy Soggy Boots";
		objectDescription = "A timeless generational relic from your late father.\nSo unkempt from years of neglect.\nGrants 1 Movement Speed.";
		collisionOn = false;
		down1 = setupEntity("boots", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
	}

	@Override
	public void setPickupState() {
		this.pickUpState = true;	
	}

	@Override
	public void setInventoryType() {
		this.inventoryType = ObjectType.ACCESSORY;
	}

	@Override
	public void setTradeNameDescription() {
		this.tradeName = "Boots";
		this.tradeDescription = "Grants 1 movement speed";
	}
}

