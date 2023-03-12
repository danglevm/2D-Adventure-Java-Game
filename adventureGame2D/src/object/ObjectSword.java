package object;

import adventureGame2D.GamePanel;

public class ObjectSword extends GameObject implements AttackObjectInterface {

	GamePanel gp;
	
	private int attackValue;
	
	public ObjectSword(GamePanel gp) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
	}


	@Override
	public void setDefaultAttributes() {
		
		name = "sword";
		down1 = setupEntity("sword_normal", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
		attackValue = 1;
		
	}
	
	@Override
	public int getAttackValue() {
		return attackValue;
	}



	
}
