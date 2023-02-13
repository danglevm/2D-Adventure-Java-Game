package monster;

import java.util.Random;

import adventureGame2D.GamePanel;
import entity.Entity;
import monster.Monster_Interface.Monster;

public class Monster_Green_Slime extends Entity implements Monster{
	public Monster_Green_Slime (GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.WorldX = worldX * gp.tileSize;
		this.WorldY = worldY * gp.tileSize;
		this.setDefaultValues();
		this.getImage();
	}
	
	public final void setDefaultValues() {
		name = "";
		speed = 1;
		maxLife = 6;
		life = maxLife;
		
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	
	public final void getImage() {
		up1 = setupCharacter("greenslime_down_1","/monster/");
		up2 = setupCharacter("greenslime_down_2","/monster/");

		down1 = setupCharacter("greenslime_down_1","/monster/");
		down2 = setupCharacter("greenslime_down_2","/monster/");
		
		right1 = setupCharacter("greenslime_down_1","/monster/");
		right2 = setupCharacter("greenslime_down_2","/monster/");
		
		left1 = setupCharacter("greenslime_down_1","/monster/");
		left2 = setupCharacter("greenslime_down_2","/monster/");
	}
	
	@Override
	public final void setAction() {
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
}
