package object;

import adventureGame2D.GamePanel;
import enums_and_constants.ObjectType;


public class Mana extends PowerUpObject {
	
	GamePanel gp;
	
	public Mana (GamePanel gp, int WorldX, int WorldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = WorldX * gp.getTileSize();
		this.WorldY = WorldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
		setTradeNameDescription();
		
	}

	public void setDefaultAttributes() {
		name = "a Mana Crystal";
		image = setupEntity("manacrystal_full", "/hud/", gp.getTileSize(), gp.getTileSize());
		image2 = setupEntity("manacrystal_blank", "/hud/", gp.getTileSize(), gp.getTileSize());
		down1 = setupEntity("manacrystal_full", "/hud/", gp.getTileSize(), gp.getTileSize());
	}

	@Override
	public void setPickupState() {
		this.pickUpState = true;
		
	}
	
	@Override
	public void setInventoryType() {
		this.inventoryType = ObjectType.POWERUP;
		
	}

	@Override
	public void grantPowerUpEffects() {
		player.setMana(player.getMana() + 1);
		if (player.getMana() >= player.getMaxMana()) player.setMana(player.getMaxMana());
	}

	@Override
	public void setTradeNameDescription() {
		// TODO Auto-generated method stub
		this.tradeName = "Mana";
		this.tradeDescription = "Not supposed to be here. You cheating?";
	}	
}
