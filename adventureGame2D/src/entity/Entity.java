package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import adventureGame2D.UtilityTool;
import enums_and_constants.Direction;
import enums_and_constants.EntityType;
import monster.Monster;

public class Entity{
	
	GamePanel gp;
	protected BufferedImage image, image2, image3;
	protected BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	protected String name;
	protected Direction direction = Direction.DOWN;
	
	protected boolean findPath = false;
	
	
	//Entity position
	protected int WorldX;
	protected int WorldY;
	protected int speed;
	protected int spriteCounter = 0, actionLock = 0, invincibilityCounter = 0;
	protected boolean spriteNum = true; 
	
		
	//specifies the solid area of the character entity for collision
	//Store data about this rectangle as x, y, width and height
	protected Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	protected int solidAreaDefaultX;
	protected int solidAreaDefaultY;
	protected boolean collisionOn = false;
	protected boolean invincibility = false;
	//0 - player, 1 - npc, 2 - monster
	protected EntityType entityType;
	
	//Monster HP Bar
	private boolean hpBarEnabled = false;
	protected int attack, defense;
	private int hpBarCounter = 0;

	
	//attack area
	protected Rectangle attackArea = new Rectangle (0, 0, 0, 0);
	
	
	//ENTITY STATUS
	//Character HP - both players and monster
	protected int maxLife, life;
	//maxLife and Life get and set
	
	protected int deathCount = 0;
	
		
	//alive and in dying animation or not
	protected boolean alive = true, dying = false;
	
	/**
	 * Setting up particle characteristics
	 */
	protected Color pColor;
	
	protected int pSize; //6 pixels
	
	protected int pSpeed; //how fast particle flies
	
	protected int pDuration; //HP of particle
	
	protected int pOffset; //Changes where the particle effects take place

	
	
	
//	Constructor
	public Entity (GamePanel gp) {
		this.gp = gp;
	}
	
	
// 	Class methods
	protected void setBehaviour() {}
	
	protected void checkCollision() {
		this.collisionOn = false;
		gp.getCollisionCheck().CheckTile(this);
		gp.getCollisionCheck().checkObject(this);
		gp.getCollisionCheck().checkEntity(this, gp.getNPCS());
		gp.getCollisionCheck().checkEntity(this, gp.getMonsters());
		gp.getCollisionCheck().checkEntity(this, gp.getInteractiveTiles());
		
		this.checkMonsterCollision();
	}
	
	
	public void update() {
		setBehaviour();
		checkCollision();
		

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

		
		
		//What this is checking is that it's checking if the entity object that the it's referring to
		//is also an object of type Monster. if it is then execute the Monster-specific method
		if (this instanceof Monster) {
			((Monster) this).checkInvincibilityTime();
		}
	}
	
	
	
	public void draw (Graphics2D g2, GamePanel gp) {
		this.gp = gp;
		BufferedImage image = null;
		
		int entityScreenX = this.WorldX - gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX();
		int entityScreenY = this.WorldY - gp.getPlayer().WorldY + gp.getPlayer().getScreenY();
		
		//Render the monsters on screen - pretty fuzzy about this since just copy
		if (this.WorldX + gp.getTileSize() > gp.getPlayer().getWorldX() - gp.getPlayer().getScreenX() && 
			this.WorldX - gp.getTileSize() < gp.getPlayer().getWorldX() + gp.getPlayer().getScreenX() &&
			this.WorldY + gp.getTileSize() > gp.getPlayer().WorldY - gp.getPlayer().getScreenY() &&
			this.WorldY - gp.getTileSize() < gp.getPlayer().WorldY + gp.getPlayer().getScreenY()) {
		
			switch (direction) {
			case UP: if (spriteNum) {image = up1;} else {image = up2;} break;
			case DOWN: if (spriteNum) {image = down1;} else {image = down2;} break;
			case LEFT: if (spriteNum) {image = left1;} else {image = left2;} break;
			case RIGHT: if (spriteNum) {image = right1;} else {image = right2;} break;
			default:
				break;
			}
			//Draw monster HP Bar
			drawHealthBar(g2, entityScreenX, entityScreenY);
			
			
			
			if (this.entityType == EntityType.HOSTILE && this instanceof Monster) {
				if (invincibility) {
					hpBarEnabled = true;
					hpBarCounter = 0;
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));	
				}
				
				if (dying && !alive) {
					((Monster)this).drawDeathAnimation(g2);
				}
			}
			
			//16 pixels
			g2.drawImage(image, entityScreenX, entityScreenY, null);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}
	
	//Render and scale the entity
	protected final BufferedImage setupEntity(String imageName, String pathName, int width, int height) {
		
		BufferedImage scaledImage = null;
		
		try {
			scaledImage = ImageIO.read(getClass().getResourceAsStream(pathName + imageName+".png"));
			scaledImage = UtilityTool.scaleImage(scaledImage, width, height);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return scaledImage;
	}
	

	//Checks to see if player is colliding with a monster
	private final void checkMonsterCollision() {
		if (gp.getCollisionCheck().checkPlayer(this) && this.entityType == EntityType.HOSTILE) {
			if (!gp.getPlayer().invincibility) {
				((Monster)this).damagePlayer();
				gp.getPlayer().invincibility = true;
			}
		}
	}
	
	
	//Draws monster's health bar and animation
	private final void drawHealthBar(Graphics2D g2, int entityScreenX, int entityScreenY) {
	
		//Monster HP bar
		if (entityType == EntityType.HOSTILE && hpBarEnabled) {
			double oneBarValue = (double) gp.getTileSize()/this.maxLife,
					hpBarValue = oneBarValue * this.life;
			
			//Draw damage above monster
			
			//Gray outline
			//x, y, width, height
			g2.setColor(new Color(35, 35, 35));
			g2.fillRect(entityScreenX - 1, entityScreenY - 1, gp.getTileSize() + 2, 12);
			
		
			
			//Red hp bar
			g2.setColor(new Color(255,0,30));
			g2.fillRect(entityScreenX, entityScreenY, (int) hpBarValue, 10);
			
			
			++hpBarCounter;
			
			if (hpBarCounter > 360) {
				hpBarCounter = 0;
				hpBarEnabled = false;
			}
		}
	}
	

	protected final void generateParticles(Entity generator, Entity target) {
		gp.getParticles().get(gp.currentMap).add(new Particle (gp, target, this.pColor, this.pSize, this.pSpeed, this.pDuration, -2, -1));
		gp.getParticles().get(gp.currentMap).add(new Particle (gp, target, this.pColor, this.pSize, this.pSpeed, this.pDuration, 2, -1));
		gp.getParticles().get(gp.currentMap).add(new Particle (gp, target, this.pColor, this.pSize, this.pSpeed, this.pDuration, -2, 1));
		gp.getParticles().get(gp.currentMap).add(new Particle (gp, target, this.pColor, this.pSize, this.pSpeed, this.pDuration, 2, 1));
	}
	
	protected void searchPath (int goalCol, int goalRow) {
		int tileSize = gp.getTileSize();
		int startCol = (this.WorldX + solidArea.x)/tileSize,
			startRow = (this.WorldY + solidArea.y)/tileSize;
		gp.getPathfinder().setNodes(startCol, startRow, goalCol, goalRow);
		
		if (gp.getPathfinder().searchPath()) {
			
			//Get next WorldX and WorldY where the entity is going to move to
			//complete the path
			int nextWorldX = gp.getPathfinder().getPathList().get(0).getCol() * tileSize;
			int nextWorldY = gp.getPathfinder().getPathList().get(0).getRow() * tileSize;
		
			//Get four sides of the entity's solid area
			int solidLeftX = this.WorldX + solidArea.x;
			int solidRightX = this.WorldX + solidArea.x + solidArea.width;
			int solidTopY = this.WorldY + solidArea.y;
			int solidBotY = this.WorldY + solidArea.y + solidArea.height;
			
			
			if (solidLeftX >= nextWorldX && solidRightX < nextWorldX + tileSize) {
				//if NPC is above the top block at the top
				if (solidTopY > nextWorldY) {
					this.direction = Direction.UP;
				}
				//if NPC is below the top block at the top
				if (solidTopY < nextWorldY) {
					this.direction = Direction.DOWN;
				}
			}
			
			//if NPC is further right than the block to move to
			//checks if the npc worldY can fit into that block
			//checks if the lower part of worldY fits in
			if (solidTopY >= nextWorldY && solidBotY < nextWorldY + tileSize) {
				if (solidLeftX < nextWorldX) {
					this.direction = Direction.RIGHT;
				}
				
				if (solidLeftX > nextWorldX) {
					this.direction = Direction.LEFT;
				}
			}
			
			//EXCEPTION CASES 
			//if the block to move to is at top left but 
			//in front of the NPC there is a solid block
			if (solidTopY > nextWorldY && solidLeftX > nextWorldX) {
				//GO UP OR LEFT
				this.direction = Direction.UP;
				checkCollision();
				//if he's colliding with the solid block up top
				if (collisionOn) direction = Direction.LEFT;	
			}
			
			//block to move is at top right but top block is solid
			if (solidTopY > nextWorldY && solidLeftX < nextWorldX) {
				this.direction = Direction.UP;
				checkCollision();
				//if colliding with block up top then go for right instead
				if (collisionOn) direction = Direction.RIGHT;
			}
			
			//block to move is at bottom left but below NPC is solid
			if (solidTopY < nextWorldY && solidLeftX > nextWorldX) {
				// go down or go left
				this.direction = Direction.DOWN;
				checkCollision();
				if (collisionOn) direction = Direction.LEFT;
			}
			
			//block to move is at bottom right but below NPC is solid
			if (solidTopY < nextWorldY && solidLeftX < nextWorldX) {
				// go down or go left
				this.direction = Direction.DOWN;
				checkCollision();
				if (collisionOn) direction = Direction.RIGHT;
			}
			
//			int nextCol = gp.getPathfinder().getPathList().get(0).getCol();
//			int nextRow = gp.getPathfinder().getPathList().get(0).getRow();
//			//if it reaches the goal stop searching
//			if (nextCol == goalCol && nextRow == goalRow) this.findPath = false;
//			
			
			
		}
		
	}
	/*
	 * SETTERS AND GETTERS
	 */
	
	public Direction getDirection () { return direction; }
	public void setDirection (Direction direction) { this.direction = direction; }
	
	public int getWorldX () { return WorldX; }
	public void setWorldX (int WorldX) { this.WorldX = WorldX; }
	
	public int getWorldY () { return WorldY; }
	public void setWorldY (int WorldY) { this.WorldY = WorldY; }
	
	public int getSpeed () { return speed; }
	public void setSpeed (int speed) { this.speed = speed; }
	
	public String getName () { return name; }
	public void setName (String name) { this.name = name; }
	
	public boolean getInvincibility () { return invincibility; }
	public void setInvincibility (boolean invincibility) { this.invincibility = invincibility; }
	
	public int getMaxLife() {return maxLife;}
	public int getLife() {return life;}
	
	public void setMaxLife(int life) {maxLife = life;}
	public void setLife (int life) {this.life = life;}
	
	public Rectangle getSolidArea() { return solidArea; }
	public void setSolidArea (Rectangle solidArea) { this.solidArea = solidArea; }
	
	public int getSolidAreaDefaultX() { return solidAreaDefaultX; }
	public void setSolidAreaDefaultX (int solidAreaDefaultX) { this.solidAreaDefaultX = solidAreaDefaultX; }
	
	public int getSolidAreaDefaultY() { return solidAreaDefaultY; }
	public void setSolidAreaDefaultY (int solidAreaDefaultY) { this.solidAreaDefaultY = solidAreaDefaultY; } 
	
	public boolean getCollisionOn() { return collisionOn; }
	public void setCollisionOn (boolean collisionOn) { this.collisionOn = collisionOn; } 
	
	public boolean getAlive() {return alive;}
	public boolean getDying() { return dying;}
	
	public int getAttack () { return attack; }
	
	public int getDefense () { return defense; }
	
	/*
	 * BUFFERED IMAGES SETTERS AND GETTERS
	 */
	
	public BufferedImage getDown1() { return down1;}
	public BufferedImage getImage1() { return image;}
	public BufferedImage getImage2() { return image2;}
	public BufferedImage getImage3() { return image3;}
	

	
	
}
