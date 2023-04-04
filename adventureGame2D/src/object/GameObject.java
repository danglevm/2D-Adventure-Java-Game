package object;

import adventureGame2D.GamePanel;
import entity.Entity;
import entity.Player;
import enums.ObjectType;


public abstract class GameObject extends Entity{

	protected String objectDescription;
	
	protected boolean pickUpState;
	
	protected ObjectType inventoryType;
	
	protected int encumbrance;
	
	protected Player player;

	
	public GameObject(GamePanel gp) {
		super(gp);
		encumbrance = 0;
		player = gp.getPlayer();
		
	} 
	
	//Ensure inherited classes set pickup state and inventory type
	protected abstract void setPickupState();
	
	protected abstract void setInventoryType();
	
	protected abstract void setDefaultAttributes();

	public String getObjectDescription () { return objectDescription; }
	
	public boolean getPickUpState () { return pickUpState; }
	
	public int getEncumbrance () { return encumbrance; }
	
	public ObjectType getInventoryType () { return inventoryType; }
}
