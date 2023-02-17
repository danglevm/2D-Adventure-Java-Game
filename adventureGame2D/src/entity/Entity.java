package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import adventureGame2D.UtilityTool;

public class Entity {
	
	GamePanel gp;
	public BufferedImage image, image2, image3;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String name, direction = "down";
	
	
	
	
	//Entity position
	public int WorldX, WorldY, speed;
	protected int spriteCounter = 0, actionLock = 0, invincibilityCounter = 0;
	protected boolean spriteNum = true; 
	

	
	
		
	//specifies the solid area of the character entity for collision
	//Store data about this rectangle as x, y, width and height
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false, invincibility = false;
	//0 - player, 1 - npc, 2 - monster
	protected int entityType;
	
	//attack area
	Rectangle attackArea = new Rectangle (0, 0, 0, 0);
	
	
	//Character HP - both players and monster
	protected int maxLife, life;
	//maxLife and Life get and set
	public int getMaxLife() {return maxLife;}
	public int getLife() {return life;}
	public void setMaxLife(int life) {maxLife = life;}
	public void setLife (int life) {this.life = life;}
	
	//Deal with objects
	
//	Constructor
	public Entity (GamePanel gp) {
		this.gp = gp;
	}
	
	
// 	Class methods
	public void setAction() {}
	
	public void speak() {}
	
	public void update() {
		setAction();
		collisionOn = false;
		gp.cChecker.CheckTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.NPCs);
		gp.cChecker.checkEntity(this, gp.monsters);
		
		
		if (gp.cChecker.checkPlayer(this) && this.entityType == 2) {
			if (!gp.player.invincibility) {
				gp.player.setLife(gp.player.getLife() - 1);
				gp.player.invincibility = true;
			}
		}
		
		if (!collisionOn) {
			switch (direction) {
			case "up": this.WorldY -= speed; break;
			case "down": this.WorldY += speed; break;
			case "left": this.WorldX -= speed; break;
			case "right": this.WorldX += speed; break;
			
			}
		}

		spriteCounter++;
		//Player image changes every 12 frames
		if (!collisionOn) {
		if (spriteCounter > 12) {
			if (spriteNum) {
				spriteNum = false;
			}
			
			else {
				spriteNum = true;
			}
			spriteCounter = 0;
		}
		}
	}
	
	protected void damageContact(Entity entity) {};
	
	protected void checkInvincibilityTime() {}
	
	public void draw (Graphics2D g2, GamePanel gp) {
		this.gp = gp;
		BufferedImage image = null;
		int screenX = WorldX - gp.player.WorldX + gp.player.screenX;
		int screenY = WorldY - gp.player.WorldY + gp.player.screenY;
		if (WorldX + gp.tileSize > gp.player.WorldX - gp.player.screenX && 
			WorldX - gp.tileSize < gp.player.WorldX + gp.player.screenX &&
			WorldY + gp.tileSize > gp.player.WorldY - gp.player.screenY &&
			WorldY - gp.tileSize < gp.player.WorldY + gp.player.screenY) {
			
			switch (direction) {
			case "up":
				if (spriteNum) {image = up1;} else {image = up2;}
				break;
			case "down":
				if (spriteNum) {image = down1;} else {image = down2;}
				break;
			case "left":
				if (spriteNum) {image = left1;} else {image = left2;}
				break;
			case "right":
				if (spriteNum) {image = right1;} else {image = right2;}
				break;
			}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
	
	//Render and scale the entity
	public BufferedImage setupCharacter(String imageName, String pathName, int width, int height) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage scaledImage = null;
		
		try {
			scaledImage = ImageIO.read(getClass().getResourceAsStream(pathName + imageName+".png"));
			scaledImage = uTool.scaleImage(scaledImage, width, height);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return scaledImage;
	}
	
	//Set direction when talking to player
	public void talkingDirection (Entity player, Entity NPC) {
		String direction = "";
		switch(player.direction) {
		case "up": direction ="down"; break;
		case "down": direction = "up"; break;
		case "right": direction = "left"; break;
		case "left": direction = "right"; break; 
		}
		NPC.direction = direction;
		
	}
	
	
	
}
