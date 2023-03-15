package object;

import adventureGame2D.GamePanel;

public class ObjectHeart extends GameObject {
	
	GamePanel gp;
	
	public ObjectHeart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
		setPickupState();
	}

	public void setDefaultAttributes() {
		name = "Heart";
		image = setupEntity("heart_full", "/heart/", gp.getTileSize(), gp.getTileSize());
		image2 = setupEntity("heart_half", "/heart/", gp.getTileSize(), gp.getTileSize());
		image3 = setupEntity("heart_blank", "/heart/", gp.getTileSize(), gp.getTileSize());
		
	}

	@Override
	public void setPickupState() {
		this.pickUpState = false;
		
	}
}
