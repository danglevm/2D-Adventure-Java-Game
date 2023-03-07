package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import adventureGame2D.UtilityTool;

public class OldDudeNPC extends Entity implements NPCInterface{
	//Dialogues
	private ArrayList<String> oldDudeDialogues = new ArrayList <String>();
	private int dialogueIndex = 0;

	public OldDudeNPC (GamePanel gp, int worldX, int worldY) {
		super(gp);
		//x, y, width, height
		solidArea = new Rectangle(8, 8, 36, 36);
		this.WorldX = gp.tileSize * worldX;
		this.WorldY = gp.tileSize * worldY;
		
		this.setDefaultValues();
		this.getImage();
		this.setDialogue();
	}
	

	//-------------------------------NPC RENDER METHODS------------------
	public void getImage() {
		
		up1 = setupEntity("oldman_up_1", "/npc/", gp.tileSize, gp.tileSize);
		up2 = setupEntity("oldman_up_2", "/npc/", gp.tileSize, gp.tileSize);
		down1 = setupEntity("oldman_down_1", "/npc/", gp.tileSize, gp.tileSize);
		down2 = setupEntity("oldman_down_2", "/npc/", gp.tileSize, gp.tileSize);
		left1 = setupEntity("oldman_left_1", "/npc/", gp.tileSize, gp.tileSize);
		left2 = setupEntity("oldman_left_2", "/npc/", gp.tileSize, gp.tileSize);
		right1 = setupEntity("oldman_right_1", "/npc/", gp.tileSize, gp.tileSize);
		right2 = setupEntity("oldman_right_2", "/npc/", gp.tileSize, gp.tileSize);
		

		
	}
	@Override
	public void setDialogue() {
		
		//Meeting the player for the first time
		oldDudeDialogues.add(" '...' ");
		oldDudeDialogues.add(" 'OH what the-' ");
		oldDudeDialogues.add(" 'Who the heck are you, whippersnapper?\n Creeping on me like that?' ");
		
		//depends on what the player chooses
		oldDudeDialogues.add(" 'You deaf, son?' "); 
		oldDudeDialogues.add(" 'I sure as hell haven't seen you around.\n What's your name?' "); 
		oldDudeDialogues.add(" 'You what? You woke up in my HOUSE?' ");
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
	
		if (collisionOn) {
			switch (direction) {
			case "up" : direction = "down"; break;
			case "down" : direction = "up"; break;
			case "left" : direction = "right"; break;
			case "right" : direction = "left"; break;
			
			}
		}
	}
	
	public void speak() {
		//Opening speak
		try {
		if (oldDudeDialogues.get(dialogueIndex) != null) {
			gp.ui.setCurrentDialogue(oldDudeDialogues.get(dialogueIndex));
			++dialogueIndex;
			}
		} catch (IndexOutOfBoundsException e){}
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
