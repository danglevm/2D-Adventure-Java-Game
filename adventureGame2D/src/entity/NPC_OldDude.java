package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import adventureGame2D.UtilityTool;

public class NPC_OldDude extends Entity implements NPC_Interface{
	//Dialogues
	String oldDudeDialogues[] = new String [20];
	int oldDudeDialogueIndex = 0;

	public NPC_OldDude (GamePanel gp, int worldX, int worldY) {
		super(gp);
		//x, y, width, height
		solidArea = new Rectangle(8, 8, 24, 36);
		this.WorldX = gp.tileSize * worldX;
		this.WorldY = gp.tileSize * worldY;
		
		this.setDefaultValues();
		this.getImage();
		this.setDialogue();
	}
	

	//-------------------------------NPC RENDER METHODS------------------
	public void getImage() {
		
		up1 = setupCharacter("oldman_up_1", "/npc/");
		up2 = setupCharacter("oldman_up_2", "/npc/");
		down1 = setupCharacter("oldman_down_1", "/npc/");
		down2 = setupCharacter("oldman_down_2", "/npc/");
		left1 = setupCharacter("oldman_left_1", "/npc/");
		left2 = setupCharacter("oldman_left_2", "/npc/");
		right1 = setupCharacter("oldman_right_1", "/npc/");
		right2 = setupCharacter("oldman_right_2", "/npc/");
		

		
	}
	@Override
	public void setDialogue() {
		
		//Meeting the player for the first time
		oldDudeDialogues[0] = " '...' ";
		oldDudeDialogues[1] = " 'OH what the-' ";
		oldDudeDialogues[2] = " 'Who the heck are you, whippersnapper?\n Creeping on me like that?' ";
		
		//depends on what the player chooses
		oldDudeDialogues[3] = " 'You deaf, son?' ";
		oldDudeDialogues[4] = " 'I sure as hell haven't seen you around.\n What's your name?' ";
		oldDudeDialogues[5] = " 'You what? You woke up in my HOUSE?' ";
	}
	
	@Override
	public void setAction() {
		
		++actionLock;
		
		//After every a certain pseudo random amount of time
	if (actionLock == 240) {
		Random random = new Random();
		int i = random.nextInt(100) + 1;//1 to 100
		
		
		if (i < 25) {
			direction = "up";
		} else if (i < 50) {
			direction = "down";
		} else if (i < 75) {
			direction = "left";
		} else {
			direction = "right";
		}
		
		actionLock = 0;
		}
	}
	
	public void speak() {
		//Opening speak
		if (oldDudeDialogues[oldDudeDialogueIndex]!=null) {
			gp.ui.setCurrentDialogue( oldDudeDialogues[oldDudeDialogueIndex]);
			++oldDudeDialogueIndex;
		}
		talkingDirection(gp.player, this);
		
	}


	@Override
	public final void setDefaultValues() {
		name = "Old_Dude";
		direction = "down";
		speed = 2;
		maxLife = 6;
		life = maxLife;
		entityType = 1;
		
	}


	
}
