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
		
		name = "Old Shabby Metal Sword";
		down1 = setupEntity("sword_normal", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
		attackValue = 1;
		objectDescription = "A most fitting companion for a true hero... \nExcept you are a bum. A piece of scrap metal now. \nGives 1 Attack.";
		
	}
	
	@Override
	public int getAttackValue() {
		return attackValue;
	}



	
}
