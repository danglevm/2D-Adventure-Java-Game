package monster;

import java.util.Random;

import adventureGame2D.GamePanel;
import entity.Entity;
import entity.Player;

public class MonsterGreenSlime extends Entity implements MonsterInterface{
	public MonsterGreenSlime (GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.WorldX = worldX * gp.tileSize;
		this.WorldY = worldY * gp.tileSize;
		this.setDefaultValues();
		this.getImage(gp.tileSize);
		
	}
	
	public final void setDefaultValues() {
		name = "";
		speed = 1;
		maxLife = 6;
		life = maxLife;
		entityType = 2;
		
		
		solidArea.x = 8;
		solidArea.y = 8;
		solidArea.width = 40;
		solidArea.height = 40;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	@Override
	public final void getImage(int size) {
		up1 = setupCharacter("greenslime_down_1","/monster/", size, size);
		up2 = setupCharacter("greenslime_down_2","/monster/", size, size);

		down1 = setupCharacter("greenslime_down_1","/monster/", size, size);
		down2 = setupCharacter("greenslime_down_2","/monster/", size, size);
		
		right1 = setupCharacter("greenslime_down_1","/monster/", size, size);
		right2 = setupCharacter("greenslime_down_2","/monster/", size, size);
		
		left1 = setupCharacter("greenslime_down_1","/monster/", size, size);
		left2 = setupCharacter("greenslime_down_2","/monster/", size, size);
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
	
	@Override
	public final void damageContact(Entity entity) {
		if (!entity.invincibility) {
			entity.setLife(entity.getLife() - 1);
			entity.invincibility = true;
		}
	}
	
	@Override
	public void monsterDamageReaction(Player player) {
		this.actionLock = 0;
		this.direction = player.direction;
	}
	
	@Override
	public final int returnDeathSound() {
		return 7;
	};
	
	
	
	
}
