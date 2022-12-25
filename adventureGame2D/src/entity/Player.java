package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import adventureGame2D.KeyHandler;

public class Player extends Entity {
	
	//Variables
	GamePanel gp;
	KeyHandler keyH;
	
	//Where the player is drawn on the screen - camera 
	public final int screenX;
	public final int screenY;
	
	
	//-------------------------------CONSTRUCTORS------------------
	public Player (GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		//Places the character at the center of the screen
		//Subtract half of the tile length to be at the center of the player character
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		//x, y, width, length
		solidArea = new Rectangle(8,12,32,32);
		
		
		setDefaultValues();
		getPlayerImage();
	}
	
	//-------------------------------CLASS METHODS------------------
	public void getPlayerImage() {
		try {
			//Returns the buffered image from the link specified 
			//GetClass () - returns the runtime class of the object
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		}catch(IOException e){
			//prints and trace the error to its cause
			e.printStackTrace();
		}
	}
	
	public void setDefaultValues() {
		WorldX = gp.tileSize*23;
		WorldY= gp.tileSize*15;
		speed = 4;
		direction = "down";
	}
	
	public void update() {
		if (keyH.upPressed == true||keyH.downPressed==true||
				keyH.leftPressed==true||keyH.rightPressed==true)
		{//X and Y values increase as the player moves right and down
			
			//Check tile collision
			collisionOn = false;
			//Collision checker receive subclass
			gp.cChecker.CheckTile(this);
			
			if (keyH.upPressed == true) {
				direction ="up";
				if (collisionOn) {WorldY+=speed/5;} else {WorldY-=speed;}
			}
			if (keyH.downPressed== true) {
				direction = "down";
				if (collisionOn) {WorldY-=speed/5;} else {WorldY+=speed;}
			}
			if (keyH.leftPressed == true) {
				direction = "left";	
				if (collisionOn) {WorldX+=speed/5;} else {WorldX-=speed;}
			}
			if (keyH.rightPressed==true) {
				direction = "right";
				if (collisionOn) {WorldX-=speed/5;} else {WorldX+=speed;}
			}
			
			
			
			
			/*//player can move when collision on is false
			if (collisionOn ==false) {
			if (direction == "up") {
					WorldY-=speed;
			}
			if (direction == "down") {
					WorldY+=speed;
			}
			if (direction =="left") {
					WorldX-=speed;
			}
			if (direction == "right") {
					WorldX+=speed;
				}
			}*/
			
			
			
			
			spriteCounter++;
			//Player image changes every 12 frames
			if (spriteCounter > 12) {
				if (spriteNum==1) {
					spriteNum =2;
				}
				
				else if (spriteNum ==2) {
					spriteNum=1;
				}
				spriteCounter=0;
			}
			
		}//keypress loop
			
		
	}//update
		
	
	public void draw(Graphics2D g2) {
		/*
		g2.setColor(Color.yellow);
		g2.fillRect(x, y, gp.tileSize,gp.tileSize );
		*/
		
		BufferedImage image = null;
		
		switch (direction) {
		case "up":
			if (spriteNum==1) {
				image = up1;
			}
			if (spriteNum==2) {
				image = up2;
			}
			
			break;
		case "down":
			if (spriteNum==1) {
				image = down1;
			}
			if (spriteNum==2) {
				image = down2;
			}
			break;
		case "left":
			if (spriteNum==1) {
				image = left1;
			}
			if (spriteNum==2) {
				image = left2;
			}
			break;
		case "right":
			if (spriteNum==1) {
				image = right1;
			}
			if (spriteNum==2) {
				image = right2;
			}
			break;
		}
		
		//16 pixels
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
			
	}
}
