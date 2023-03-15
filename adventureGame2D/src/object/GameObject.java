package object;

import adventureGame2D.GamePanel;
import entity.Entity;

public abstract class GameObject extends Entity implements ObjectInterface{

	protected String objectDescription;

	
	public GameObject(GamePanel gp) {
		super(gp);
		
	}
	

	public String getObjectDescription () { return objectDescription; }

	

}
