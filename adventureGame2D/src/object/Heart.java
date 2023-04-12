package object;

import adventureGame2D.GamePanel;
import enums_and_constants.ObjectType;
import object.interfaces.PowerUpObjectInterface;

public class Heart extends PowerUpObject {
	
	GamePanel gp;
	
	public Heart(GamePanel gp, int WorldX, int WorldY) {
		super(gp);
		this.WorldX = WorldX * gp.getTileSize();
		this.WorldY = WorldY * gp.getTileSize();
		this.gp = gp;
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
	}

	public void setDefaultAttributes() {
		name = "a Heart Crystal";
		image = setupEntity("heart_full", "/hud/", gp.getTileSize(), gp.getTileSize());
		image2 = setupEntity("heart_half", "/hud/", gp.getTileSize(), gp.getTileSize());
		image3 = setupEntity("heart_blank", "/hud/", gp.getTileSize(), gp.getTileSize());
		down1 = setupEntity("heart_full", "/hud/", gp.getTileSize(), gp.getTileSize());
		
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
		player.setLife(player.getLife() + 2);
		if (player.getLife() >= player.getMaxLife()) player.setLife(player.getMaxLife());
	}	
}
