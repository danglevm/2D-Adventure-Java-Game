package npc;

import java.awt.Rectangle;
import java.util.Random;
import adventureGame2D.GamePanel;
import enums_and_constants.Direction;
import enums_and_constants.EntityType;

public class OldDude extends NPC {
	//Dialogues
	GamePanel gp;

	public OldDude (GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		//x, y, width, height
		solidArea = new Rectangle(8, 8, 36, 36);
		this.WorldX = gp.getTileSize() * worldX;
		this.WorldY = gp.getTileSize() * worldY;
		
		name = "Old_Dude";
		direction = Direction.DOWN;
		speed = 2;
		maxLife = 6;
		life = maxLife;
		entityType = EntityType.FRIENDLY;
		this.getImage();
		this.setDialogue();
	}
	

	//-------------------------------NPC RENDER METHODS------------------
	public void getImage() {
		
		up1 = setupEntity("oldman_up_1", "/npc/", gp.getTileSize(), gp.getTileSize());
		up2 = setupEntity("oldman_up_2", "/npc/", gp.getTileSize(), gp.getTileSize());
		down1 = setupEntity("oldman_down_1", "/npc/", gp.getTileSize(), gp.getTileSize());
		down2 = setupEntity("oldman_down_2", "/npc/", gp.getTileSize(), gp.getTileSize());
		left1 = setupEntity("oldman_left_1", "/npc/", gp.getTileSize(), gp.getTileSize());
		left2 = setupEntity("oldman_left_2", "/npc/", gp.getTileSize(), gp.getTileSize());
		right1 = setupEntity("oldman_right_1", "/npc/", gp.getTileSize(), gp.getTileSize());
		right2 = setupEntity("oldman_right_2", "/npc/", gp.getTileSize(), gp.getTileSize());
		

		
	}
	@Override
	public void setDialogue() {
		
		//Meeting the player for the first time
		dialogues.add(" '...' ");
		dialogues.add(" 'OH what the-' ");
		dialogues.add(" 'Who the heck are you, whippersnapper?\n Creeping on me like that?' ");
		
		//depends on what the player chooses
		dialogues.add(" 'You deaf, son?' "); 
		dialogues.add(" 'I sure as hell haven't seen you around.\n What's your name?' "); 
		dialogues.add(" 'You what? You woke up in my HOUSE?' ");
	}
	
	@Override
	public void setBehaviour() {
		
		++actionLock;
		
		//After every a certain pseudo random amount of time
	if (actionLock == 240) {
		Random random = new Random();
		int i = random.nextInt(100) + 1;//1 to 100
		
		
		if (i < 25) {
			this.direction = Direction.UP;
		} else if (i < 50) {
			this.direction = Direction.DOWN;
		} else if (i < 75) {
			this.direction = Direction.LEFT;
		} else {
			direction = Direction.RIGHT;
		}
		
		actionLock = 0;
		}
	
	}


	@Override
	void setInventory() {
		// TODO Auto-generated method stub
		
	}
	




	
}
