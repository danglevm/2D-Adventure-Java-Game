package object;
import adventureGame2D.GamePanel;


public class ObjectChest extends GameObject {
	
	GamePanel gp;
	
	public ObjectChest(GamePanel gp, int worldX, int worldY) {  
		super(gp);
		this.gp = gp;
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
	}

	@Override
	public void setDefaultAttributes() {
		 name = "chest";
		 down1 = setupEntity("chest","/objects/environment/", gp.getTileSize(), gp.getTileSize());
	}

	@Override
	public void setPickupState() {
		this.pickUpState = false;
		
	}		
}
