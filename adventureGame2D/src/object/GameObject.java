package object;

import adventureGame2D.GamePanel;
import entity.Entity;

public abstract class GameObject extends Entity implements ObjectInterface{

	protected String objectDescription;
	
	protected boolean pickUpState;

	
	public GameObject(GamePanel gp) {
		super(gp);
		
	} 

	public String getObjectDescription () { return objectDescription; }
	
	public boolean getPickUpState () { return pickUpState; }
}
