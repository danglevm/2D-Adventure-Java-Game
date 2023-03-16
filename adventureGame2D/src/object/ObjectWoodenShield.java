package object;

import adventureGame2D.GamePanel;

public class ObjectWoodenShield extends GameObject implements DefenseObjectInterface{
	
	private int defenseValue;

	GamePanel gp;
	
	public ObjectWoodenShield(GamePanel gp) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
		setPickupState();
	}
	
	public void setDefaultAttributes() {
		name = "Dusty Old Wooden Shield";
		down1 = setupEntity("shield_wood", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
		defenseValue = 1;
		objectDescription = "Honed from the finest craftsman in the land. \nTruly unbreakable, until it breaks. \nGives 1 Defense.";
		
		
	}

	@Override
	public int getDefenseValue() {
		// TODO Auto-generated method stub
		return defenseValue;
	}

	@Override
	public void setPickupState() {
		this.pickUpState = true;
		
	}

}
