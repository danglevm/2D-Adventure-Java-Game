package object;

import adventureGame2D.GamePanel;
import entity.Entity;

public class ObjectWoodenShield extends Entity implements ObjectInterface {

	GamePanel gp;
	
	private int defenseValue;
	
	public ObjectWoodenShield(GamePanel gp) {
		super (gp);
		this.gp = gp;
	}
	
	public void setDefaultAttributes() {
		name = "woodshield";
		down1 = setupEntity("shield_wood", "/objects/equip/", gp.tileSize, gp.tileSize);
		defenseValue = 1;
	}
	
	public int getDefenseValue() {
		return this.defenseValue;
	}

}
