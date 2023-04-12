package object.consummable;

import adventureGame2D.GamePanel;
import enums_and_constants.ObjectType;
import object.GameObject;
import object.interfaces.ConsummableInterface;

public class HealingPotion extends GameObject implements ConsummableInterface {

	private final int healValue = 4;
	
	GamePanel gp;
	
	public HealingPotion(GamePanel gp, int WorldX, int WorldY) {
		super(gp);
		this.gp = gp;
		this.WorldX = WorldX * gp.getTileSize();
		this.WorldY = WorldY * gp.getTileSize();
		setDefaultAttributes();
		setPickupState();
		setInventoryType();
	}

	@Override
	public void setDefaultAttributes() {
		name = "Sanguine Crimson Liquid Flask";
		down1 = setupEntity("potion_red", "/objects/potion/", 48, 48);
		objectDescription = "Contains a most undeniably enticing fragrant. \nTerribly akin to your dearest wife's plum lips. \nHeals you for two hearts.";
		
	}

	@Override
	protected void setPickupState() {
		this.pickUpState = true;
		}

	@Override
	protected void setInventoryType() {
		this.inventoryType = ObjectType.CONSUMMABLE;
		
	}

	@Override
	public boolean useConsummable() {
		
		
		//Heals for 4 hp
		if (gp.getPlayer().getLife() < gp.getPlayer().getMaxLife()) {
		gp.getPlayer().setLife(gp.getPlayer().getLife() + healValue);
		gp.playSE(14);
		if (gp.getPlayer().getLife() > gp.getPlayer().getMaxLife()) gp.getPlayer().setLife(gp.getPlayer().getMaxLife());
			
			return true;
		} else {
			//Do something if player drinks at full HP
		}
		return false;
	}

}
