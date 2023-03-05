package object;

import adventureGame2D.GamePanel;
import entity.Entity;

public class ObjectSword extends Entity implements ObjectInterface {

	GamePanel gp;
	
	public int attackValue;
	
	public ObjectSword(GamePanel gp) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
	}


	@Override
	public void setDefaultAttributes() {
		
		name = "sword";
		
		attackValue = 1;
		
	}
	
}
