package object;

import adventureGame2D.GamePanel;
import entity.Entity;

public class GameObject extends Entity implements ObjectInterface{

	public GameObject(GamePanel gp) {
		super(gp);

	}

	@Override
	public void setDefaultAttributes() {
		
		
	}

}
