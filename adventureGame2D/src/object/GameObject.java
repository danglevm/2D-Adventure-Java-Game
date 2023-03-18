package object;

import adventureGame2D.GamePanel;
import entity.Entity;
import enums.InventoryObjectType;

public abstract class GameObject extends Entity implements ObjectInterface{

	protected String objectDescription;
	
	protected boolean pickUpState;
	
	protected InventoryObjectType inventoryType;

	
	public GameObject(GamePanel gp) {
		super(gp);
		
	} 

	public String getObjectDescription () { return objectDescription; }
	
	public boolean getPickUpState () { return pickUpState; }
	
	public InventoryObjectType getInventoryType () { return inventoryType; }
}
