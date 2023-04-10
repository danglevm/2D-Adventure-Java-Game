package monster;

import java.awt.Color;
import java.util.Random;

import adventureGame2D.GamePanel;
import entity.Player;
import enums.Direction;
import enums.EntityType;
import object.BronzeCoin;
import object.Heart;
import object.Mana;
import projectile.Rock;

public class MonsterGreenSlime extends Monster {
	
	GamePanel gp;
	
	Player player;
	
	public MonsterGreenSlime (GamePanel gp, int worldX, int worldY) {
		super(gp);
		this.gp = gp;
		player = gp.getPlayer();
		this.WorldX = worldX * gp.getTileSize();
		this.WorldY = worldY * gp.getTileSize();
		this.setDefaultValues();
		this.getImage(gp.getTileSize());
		projectile = new Rock(gp);
		this.pColor = new Color (240, 50, 0);
		this.pSize = 6;
		this.pSpeed = 1;
		this.pDuration = 18;
	}
	/**
	 * Override from MonsterInterface
	 */
	public final void setDefaultValues() {
		name = "Green Slime";
		attack = 2;
		defense = 0;
		speed = 1;
		maxLife = 6;
		experience = 2;
		life = maxLife;
		entityType = EntityType.HOSTILE;
		
		solidArea.x = 8;
		solidArea.y = 8;
		solidArea.width = 40;
		solidArea.height = 40;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	@Override
	/**
	 * Override from MonsterInterface
	 */
	public final void getImage(int size) {
		up1 = setupEntity("greenslime_down_1","/monster/", size, size);
		up2 = setupEntity("greenslime_down_2","/monster/", size, size);

		down1 = setupEntity("greenslime_down_1","/monster/", size, size);
		down2 = setupEntity("greenslime_down_2","/monster/", size, size);
		
		right1 = setupEntity("greenslime_down_1","/monster/", size, size);
		right2 = setupEntity("greenslime_down_2","/monster/", size, size);
		
		left1 = setupEntity("greenslime_down_1","/monster/", size, size);
		left2 = setupEntity("greenslime_down_2","/monster/", size, size);
	}
	
	@Override
	/**
	 * Override from Entity
	 */
	public final void setBehaviour() {
	++actionLock;
		//After every a certain pseudo random amount of time
	if (actionLock == 240) {
		Random random = new Random();
		int i = random.nextInt(100) + 1;//1 to 100
		if (i < 25) direction = Direction.UP;
		else if (i < 50) direction = Direction.DOWN;
		else if (i < 75) direction = Direction.LEFT;
		else direction = Direction.RIGHT;
		actionLock = 0;
	}
	
	switch (direction) {
	
	case UP:
		if (spriteNum) {image = up1;} else {image = up2;} break;
		
	case DOWN:
		if (spriteNum) {image = down1;} else {image = down2;} break;
		
	case LEFT:
		if (spriteNum) {image = left1;} else {image = left2;} break;
		
	case RIGHT:
		if (spriteNum) {image = right1;} else {image = right2;} break;
	default: break;
		}
	
	if (!collisionOn) ++spriteCounter;
	//Player image changes every 12 frames
	if (spriteCounter > 12) {
		spriteNum = !spriteNum;
		spriteCounter = 0;
	}
	
	int i = new Random().nextInt(100) + 1;
		if (i > 99 && !projectile.getAlive()) {
			projectile.set(this.WorldX, this.WorldY, direction, this);
			gp.getProjectiles().get(gp.currentMap).add(projectile);
			this.projectile.setCollisionOn(false);
		}
	}
	
	/**
	 * Override from MonsterInterface
	 */
	
	@Override
	public void monsterDamageReaction() {
		this.actionLock = 0;
		this.direction = player.getDirection();
	}
	
	/**
	 * Override from MonsterInterface
	 */
	
	@Override
	public void damagePlayer () {
	
		int postDmgLife = player.getLife() - this.attack + player.getTotalDefense();
		
		this.generateParticles(this, player);
		
		if (postDmgLife > player.getLife()) postDmgLife = player.getLife();
		
		if (postDmgLife != player.getLife() && !this.dying && this.alive) {
			gp.getGameUI().addSubtitleMsg(gp.getPlayer().getName() + " hurt by " + this.name);
			gp.playSE(6);
		}
		
		if (!this.dying)
		{
			player.setLife(postDmgLife);
			player.setInvincibility(true);
			player.checkInvincibilityTime();
		}
		
	}
	
	@Override
	public final int returnDeathSound() {
		return 7;
	};
	
	@Override
	public void getMonsterDrop() {
		int i = new Random().nextInt(300) + 1;
		int entityWorldX = this.WorldX/gp.getTileSize();
		int entityWorldY = this.WorldY/gp.getTileSize();
		if (i < 100) {
			gp.getObjects().get(gp.currentMap).add(new BronzeCoin(gp, entityWorldX, entityWorldY));
		}
		
		if (i > 60 && i < 80) {
			gp.getObjects().get(gp.currentMap).add(new BronzeCoin (gp, entityWorldX, entityWorldY));
			gp.getObjects().get(gp.currentMap).add(new Heart (gp, entityWorldX, entityWorldY));
		}
		
		if (i > 100 && i < 130) {
			gp.getObjects().get(gp.currentMap).add(new Mana (gp, entityWorldX, entityWorldY));
			gp.getObjects().get(gp.currentMap).add(new Heart (gp, entityWorldX, entityWorldY));
		}
		
		if (i > 200 && i < 230) {
			gp.getObjects().get(gp.currentMap).add(new Mana (gp, entityWorldX, entityWorldY));
			gp.getObjects().get(gp.currentMap).add(new BronzeCoin (gp, entityWorldX, entityWorldY));
		}
		
		
		if (i > 180 && i < 200) {
			gp.getObjects().get(gp.currentMap).add(new Mana (gp, entityWorldX, entityWorldY));
		}
		
		if (i > 250 && i < 270) {
			gp.getObjects().get(gp.currentMap).add(new Heart(gp, entityWorldX, entityWorldY));
		}
	}
	
	
	
}
