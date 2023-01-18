package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	//Position of the player on the world map
	public int WorldX, WorldY;
	public int speed;
	public int spriteCounter=0, spriteNum=1;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	//specifies the solid area of the character entity for collision
	//Store data about this rectangle as x, y, width and height
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	
	public boolean collisionOn = false;
	
}
