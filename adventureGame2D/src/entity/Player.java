package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import adventureGame2D.KeyHandler;
import adventureGame2D.UI;
import adventureGame2D.UtilityTool;

public class Player extends Entity {
	
	//Variables
	GamePanel gp;
	KeyHandler keyH;
	public int hasKey = 0;

	
	
	
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
		solidArea = new Rectangle();
		solidArea.x =8;
		solidArea.y= 8;
		//Default values so x and y values of the rectangle can be changed later
		solidAreaDefaultX= solidArea.x;
		solidAreaDefaultY= solidArea.y;
		solidArea.width=32;
		solidArea.height=32;
		
		
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
		up1 = setupPlayer("boy_up_1");
		up2 = setupPlayer("boy_up_2");
		down1 = setupPlayer ("boy_down_1");
		down2 = setupPlayer ("boy_down_2");
		left1 = setupPlayer("boy_left_1");
		left2 = setupPlayer("boy_left_2");
		right1 = setupPlayer("boy_right_1");
		right2 = setupPlayer ("boy_right_2");
		
	}
	
	public void setDefaultValues() {
		WorldX = gp.tileSize*23;
		WorldY= gp.tileSize*15;
		
		speed = 2;
		
		direction = "down";
	}
	public BufferedImage setupPlayer(String imageName) {
		
		UtilityTool uTool = new UtilityTool();
		BufferedImage scaledImage = null;
		
		try {
			scaledImage = ImageIO.read(getClass().getResourceAsStream("/player/"+imageName+".png"));
			scaledImage = uTool.scaleImage(scaledImage, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
		return scaledImage;
	}
	public void update() {
		if (keyH.upPressed == true||keyH.downPressed==true||
				keyH.leftPressed==true||keyH.rightPressed==true)
		{//X and Y values increase as the player moves right and down
			
			//Check tile collision
			collisionOn = false;
			//Collision checker receive subclass
			gp.cChecker.CheckTile(this);
			
			
			//Check object collision
			int objIndex = gp.cChecker.checkObject(this, true);
			ObjectPickUp(objIndex);
			
			
			if (keyH.upPressed == true) {
				direction ="up";
				if (collisionOn==false) {WorldY-=speed;}
			}
			if (keyH.downPressed== true) {
				direction = "down";
				if (collisionOn==false) {WorldY+=speed;} 
			}
			if (keyH.leftPressed == true) {
				direction = "left";	
				if (collisionOn==false){WorldX-=speed;}
			}
			if (keyH.rightPressed==true) {
				direction = "right";
				if (collisionOn==false){WorldX+=speed;}
			}
	
			
			
			
			
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
	
	public void ObjectPickUp(int index) {
		
		if (index!=9999) {
			
			String objectName = gp.obj[index].name;
			
			switch (objectName) {
			case "Key":
				gp.playSE(1);
				++hasKey;
				gp.obj[index]=null;
				gp.ui.displayMessage("Picked up a key");
				break;
			case "Door":
				
				if (hasKey>0) {
					gp.obj[index]=null;
					--hasKey;
					gp.playSE(4);
					gp.ui.displayMessage("Opened a door");
				} else {
					gp.ui.displayMessage("No key in inventory");
				}
				System.out.println("Key:"+hasKey);
				break;
			case "Boots":
				speed+=1;
				gp.obj[index] = null;
				gp.playSE(2);
				gp.ui.displayMessage("Picked up Boots of Speed");
				break;
			
			case "Chest":
				gp.ui.gameCompleted = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
			
			}
			}	
	}
		
	
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
		g2.drawImage(image, screenX, screenY, null);
		
			
	}
}
