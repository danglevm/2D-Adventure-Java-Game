package projectile;

import adventureGame2D.GamePanel;
import entity.Entity;
import entity.Player;
import enums.Direction;

public abstract class Projectile extends Entity {
	
	protected Entity user;
	
	GamePanel gp;

	public Projectile(GamePanel gp) {
		super(gp);
		this.gp = gp;
	}
	
	public void set (int worldX, int worldY, Direction direction, Entity entity){
		{
			this.WorldX = worldX;
			this.WorldY = worldY;
			this.direction = direction;
			this.alive = true;
			this.user = entity;
			this.life = this.maxLife;
		}
	};
		
	@Override
	public void update () {
		
		if (user == gp.getPlayer()) {
			int monsterIndex = gp.getCollisionCheck().checkEntity(this, gp.getMonsters());
			if (monsterIndex != 9999) {
				this.alive = false;
				gp.getPlayer().damageMonster(monsterIndex);
				
				
			}
		}
		
		if (!collisionOn) {
			switch (direction) {
			case UP: WorldY -= speed; break;
			case DOWN: WorldY += speed; break;
			case LEFT: WorldX -= speed; break;
			case RIGHT: WorldX += speed; break;
			default:
				break;
			
			}
		}
		
		//Once the projectile is shot, the thing starts losing its life
		//Once its life is equal to 0, the projectile dies and stops getting rendered
		
		--life;
		if (life < 1) {
			this.alive = false;
			this.dying = false;
			
		}
		
		spriteCounter++;
		//Entity image changes every 12 frames
		if (!collisionOn) {
			if (spriteCounter > 12) {
				spriteNum = !spriteNum;
				spriteCounter = 0;
			}
		}

	}

}
