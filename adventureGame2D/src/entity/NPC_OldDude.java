package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import adventureGame2D.UtilityTool;

public class NPC_OldDude extends Entity {

	public NPC_OldDude (GamePanel gp) {
		super(gp);
		direction = "down";
		speed = 2;
		solidArea = new Rectangle(8,8, 24, 24);
		
		getOldDudeImage();
	}
	

	//-------------------------------NPC RENDER METHODS------------------
	public void getOldDudeImage() {
		
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
	public void setAction() {
		
		++actionLock;
		
	if (actionLock ==240) {
		Random random = new Random();
		int i = random.nextInt(100) + 1;//1 to 100
		
		
		if (i <= 25) {
			direction = "up";
		} else if (i<=50) {
			direction = "down";
		} else if (i <= 75) {
			direction = "left";
		} else {
			direction = "right";
		}
		
		actionLock = 0;
	}
	}
	
	

	
}
