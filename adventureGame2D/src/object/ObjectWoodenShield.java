package object;

import adventureGame2D.GamePanel;
import entity.Entity;

public class ObjectWoodenShield extends Entity implements ObjectInterface, DefenseObjectInterface {
	
	private int defenseValue;

	GamePanel gp;
	
	public ObjectWoodenShield(GamePanel gp) {
		super(gp);
		this.gp = gp;
	}
	
	public void setDefaultAttributes() {
		name = "woodenshield";
		down1 = setupEntity("shield_wood", "/objects/equip/", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		
		
	}


	@Override
	public int getDefenseValue() {
		// TODO Auto-generated method stub
		return defenseValue;
	}

}
