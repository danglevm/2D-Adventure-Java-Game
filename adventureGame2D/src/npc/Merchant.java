package npc;

import java.awt.Rectangle;

import adventureGame2D.GamePanel;
import enums_and_constants.Direction;
import enums_and_constants.EntityType;
import enums_and_constants.GameState;
import object.consummable.HealingPotion;

public class Merchant extends NPC{
	
	GamePanel gp;
	
	public static final int MERCHANT_INDEX = 0;

	public Merchant(GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		//x, y, width, height
		solidArea = new Rectangle(8, 8, 36, 36);
		this.WorldX = gp.getTileSize() * worldX;
		this.WorldY = gp.getTileSize() * worldY;
		
		name = "Merchant";
		direction = Direction.DOWN;
		speed = 2;
		maxLife = 6;
		life = maxLife;
		entityType = EntityType.FRIENDLY;
		this.getImage();
		this.setDialogue();
		this.setInventory();
	}

	@Override
	void getImage() {
		up1 = setupEntity("merchant_down_1", "/npc/", gp.getTileSize(), gp.getTileSize());
		up2 = setupEntity("merchant_down_2", "/npc/", gp.getTileSize(), gp.getTileSize());
		down1 = setupEntity("merchant_down_1", "/npc/", gp.getTileSize(), gp.getTileSize());
		down2 = setupEntity("merchant_down_2", "/npc/", gp.getTileSize(), gp.getTileSize());
		left1 = setupEntity("merchant_down_1", "/npc/", gp.getTileSize(), gp.getTileSize());
		left2 = setupEntity("merchant_down_2", "/npc/", gp.getTileSize(), gp.getTileSize());
		right1 = setupEntity("merchant_down_1", "/npc/", gp.getTileSize(), gp.getTileSize());
		right2 = setupEntity("merchant_down_2", "/npc/", gp.getTileSize(), gp.getTileSize());
		
	}

	@Override
	void setDialogue() {
		dialogues.add("Hey there buddy!\nI got some good stuffs cooked up.\nWanna check it out?");
		
	}

	@Override
	void setInventory() {
		npcInventory.add(new HealingPotion(gp, 0, 0));
		npcInventory.add(new HealingPotion(gp, 0, 0));
		
	}
	
	@Override
	public void speak() {
		super.speak();
		gp.getGameUI().setNPC(this);
	}


}
