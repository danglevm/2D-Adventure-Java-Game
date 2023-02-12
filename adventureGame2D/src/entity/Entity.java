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
	protected int spriteCounter = 0, actionLock = 0;
	protected boolean spriteNum = true;
	
		
	//specifies the solid area of the character entity for collision
	//Store data about this rectangle as x, y, width and height
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	
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
		gp.cChecker.checkPlayer(this);
		
		if (!collisionOn) {
			switch (direction) {
			case "up": WorldY -= speed; break;
			case "down": WorldY += speed; break;
			case "left": WorldX -= speed; break;
			case "right": WorldX += speed; break;
			
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
				if (spriteNum) {
					image = up1;
				}
				if (!spriteNum) {
					image = up2;
				}
				
				break;
			case "down":
				if (spriteNum) {
					image = down1;
				}
				if (!spriteNum) {
					image = down2;
				}
				break;
			case "left":
				if (spriteNum) {
					image = left1;
				}
				if (!spriteNum) {
					image = left2;
				}
				break;
			case "right":
				if (spriteNum) {
					image = right1;
				}
				if (!spriteNum) {
					image = right2;
				}
				break;
			}
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}
	
	//Render and scale the entity
	public BufferedImage setupCharacter(String imageName, String pathName) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage scaledImage = null;
		
		try {
			scaledImage = ImageIO.read(getClass().getResourceAsStream(pathName +imageName+".png"));
			scaledImage = uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return scaledImage;
	}
	
	//Set direction when talking to player
	public void talkingDirection (Entity player, Entity NPC) {
		String direction = "";
		switch(player.direction) {
		case "up":
			direction ="down";
			break;
		case "down":
			direction = "up";
			break;
		case "right":
			direction = "left";
			break;
		case "left":
			direction = "right";
			break;
		}
		NPC.direction = direction;
		
	}
	
	
}
