package projectile;

import adventureGame2D.GamePanel;
import entity.Entity;
import entity.Player;
import enums.Direction;
import monster.Monster;

public abstract class Projectile extends Entity {
	
	protected Entity user;
	
	protected Integer spellCost;
	
	Player player;
	
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
			this.player = gp.getPlayer();
			this.life = this.maxLife;
		}
	};
		
	@Override
	public void update () {
		
		if (user == player) {
			int monsterIndex = gp.getCollisionCheck().checkEntity(this, gp.getMonsters());
			if (monsterIndex != 9999) {
				this.generateParticles(this, gp.getMonsters().get(monsterIndex));
				this.alive = false;
				player.damageMonster(monsterIndex);

			}
		} else if (user != player && user instanceof Monster) {
			if (!gp.getPlayer().getInvincibility() && gp.getCollisionCheck().checkPlayer(this)) {
				int postDmgLife = player.getLife() - this.attack + player.getTotalDefense();
				if (postDmgLife > player.getLife()) postDmgLife = player.getLife();
				player.setLife(postDmgLife);
				player.setInvincibility(true);
				this.generateParticles(((Monster)user).getMonsterProjectile(), player);
				player.checkInvincibilityTime();
				gp.playSE(6);
				this.alive = false;
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
	
	public Integer getSpellCost() {
		return spellCost;
	}

}
