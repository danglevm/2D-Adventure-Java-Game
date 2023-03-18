package object;

import adventureGame2D.GamePanel;

public class Axe extends GameObject {
	GamePanel gp;
	
	public Axe(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
		
	}

	@Override
	public void setDefaultAttributes() {
		name = "Crusty Greasy Axe";
		objectDescription = "Nearly unusable but full of potential to be greater.\nStrangely, it looks like your farmer uncle's old axe.\nThis can be used to chop down trees.";
		collisionOn = false;
		down1 = setupEntity("axe", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
	}

	@Override
	public void setPickupState() {
		this.pickUpState = true;	
	}
}
