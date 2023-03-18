package object;

import adventureGame2D.GamePanel;

public class Sword extends GameObject implements AttackObjectInterface {

	GamePanel gp;
	
	private int attackValue;
	
	public Sword(GamePanel gp) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
		setPickupState();
	}


	@Override
	public void setDefaultAttributes() {
		
		name = "Old Shabby Metal Sword";
		down1 = setupEntity("sword_normal", "/objects/equip/", gp.getTileSize(), gp.getTileSize());
		attackValue = 2;
		objectDescription = "A most fitting companion for a true hero... \nExcept you are a bum. A piece of scrap metal now. \nGives 1 Attack.";
		attackArea.width = 36;
		attackArea.height = 36;
	}
	
	@Override
	public int getAttackValue() {
		return attackValue;
	}


	@Override
	public void setPickupState() {
		this.pickUpState = true;
		
	}



	
}
