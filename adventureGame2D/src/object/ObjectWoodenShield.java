package object;

import adventureGame2D.GamePanel;

public class ObjectWoodenShield extends GameObject implements DefenseObjectInterface{
	
	private int defenseValue;

	GamePanel gp;
	
	public ObjectWoodenShield(GamePanel gp) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
	}
	
	public void setDefaultAttributes() {
		name = "woodenshield";
		down1 = setupEntity("shield_wood", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
		defenseValue = 1;
		
		
	}

	@Override
	public int getDefenseValue() {
		// TODO Auto-generated method stub
		return defenseValue;
	}

}
