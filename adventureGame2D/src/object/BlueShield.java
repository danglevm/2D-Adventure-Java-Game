package object;

import adventureGame2D.GamePanel;

public class BlueShield extends GameObject{
	GamePanel gp;
	
	public BlueShield(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
		
	}

	@Override
	public void setDefaultAttributes() {
		name = "Gooey Blue Shield";
		objectDescription = "An unstoppable monstrosity of defense.\nSadly, you can only barely able to move it with one hand.\nGives 3 defense but slows you down by 2 movement speed.";
		collisionOn = false;
		down1 = setupEntity("shield_blue", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
	}

	@Override
	public void setPickupState() {
		this.pickUpState = true;	
	}
}
