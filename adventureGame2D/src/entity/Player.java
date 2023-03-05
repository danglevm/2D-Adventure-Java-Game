package entity;

import java.awt.AlphaComposite;
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
import object.ObjectInterface;
import object.ObjectSword;
import object.ObjectWoodenShield;

public class Player extends Entity {
	
	/*
	 * Display variables
	 */
	public final int screenX;
	public final int screenY;
	KeyHandler keyH;
	BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	
	/*
	 * Variables for applying opacity to player
	 */
	private boolean switchOpacity;
	private int switchOpacityCounter;

	/*
	 * Player attack and stamina
	 */
	private int attackCost, 
				attackStamina, 	
				staminaRechargeCounter;

	public boolean playerAttack;
	
	private boolean staminaEnabled;
	
	/*
	 * Player attributes
	 */
	private	String name;
	private int maxStamina,
				speed,
				level,
				strength,
				dexterity,
				attack,
				defense,
				coin,
				experience,
				nextLevelExperience;
	
	/*
	 * Equipped weapon and shield
	 */
	private ObjectInterface currentWeapon,
				   			currentShield;
				
	/*
	 * Item attributes
	 */
	private int attackVal,
				defenseVal;
				

	
	
	/*
	 * 
	 * CONSTRUCTORS
	 * 
	 */
	public Player (GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		
		//Places the character at the center of the screen
		//Subtract half of the tile length to be at the center of the player character
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
		this.setDefaultPlayerValues();
		this.getPlayerImage();
		this.getPlayerAttackImage();
	}
	
	//-------------------------------CLASS METHODS------------------
	private final void getPlayerImage() {
		
		attackUp1 = setupEntity("boy_attack_up_1", "/player_attack/", gp.tileSize, gp.tileSize*2);
		attackUp2 = setupEntity("boy_attack_up_2", "/player_attack/", gp.tileSize, gp.tileSize * 2);
		attackDown1 = setupEntity("boy_attack_down_1", "/player_attack/", gp.tileSize, gp.tileSize * 2);
		attackDown2 = setupEntity("boy_attack_down_2", "/player_attack/", gp.tileSize, gp.tileSize * 2);
		attackLeft1 = setupEntity("boy_attack_left_1", "/player_attack/", gp.tileSize * 2, gp.tileSize);
		attackLeft2 = setupEntity("boy_attack_left_2", "/player_attack/", gp.tileSize * 2, gp.tileSize);
		attackRight1 = setupEntity("boy_attack_right_1", "/player_attack/", gp.tileSize * 2, gp.tileSize);
		attackRight2 = setupEntity("boy_attack_right_2", "/player_attack/", gp.tileSize * 2, gp.tileSize);
		
		
	}
	
	private final void getPlayerAttackImage() {
		
		up1 = setupEntity("boy_up_1", "/player/", gp.tileSize, gp.tileSize);
		up2 = setupEntity("boy_up_2", "/player/", gp.tileSize, gp.tileSize);
		down1 = setupEntity("boy_down_1", "/player/", gp.tileSize, gp.tileSize);
		down2 = setupEntity("boy_down_2", "/player/", gp.tileSize, gp.tileSize);
		left1 = setupEntity("boy_left_1", "/player/", gp.tileSize, gp.tileSize);
		left2 = setupEntity("boy_left_2", "/player/", gp.tileSize, gp.tileSize);
		right1 = setupEntity("boy_right_1", "/player/", gp.tileSize, gp.tileSize);
		right2 = setupEntity("boy_right_2", "/player/", gp.tileSize, gp.tileSize);
		
	}
	
	
	private final void setDefaultPlayerValues() {

		/*
		 * Starting location
		 */
		worldX = gp.tileSize * 122;
		worldY = gp.tileSize * 132;
		direction = "down";
		
		
		/*
		 * Player collision area
		 */
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 8;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 24;
		solidArea.height = 32;
		
		
		/*
		 * Attack area - to be increased for potions
		 */
		attackArea.width = 32;
		attackArea.height = 32;
		playerAttack = false;
		
		
		/*
		 * Stamina
		 */
		attackCost = 60;
		attackStamina = maxStamina;
		staminaRechargeCounter = 0;
		staminaEnabled = false;
		
		
		/*
		 * Opacity
		 */
		switchOpacity = false;
		switchOpacityCounter = 0;
		
		
		/**
		 * Player attributes
		 * @var maxStamina player's max stamina pool. Default: 120. Max: 300
		 * @var speed player's speed. Default: 3. Max: 5
		 * @var level player's level. Default: 1. Max: 10/15?
		 * @var strength natural amount of damage deals. Default 0. Max: 3
		 * @var defense natural amount of damage negated. Default: 0. Max: 3
		 * @var dexterity chance of dodging an enemy attack. Default: 0. Max: 20 (%)
		 * @var coin amount of coins player has. Def: 0. Max: 999
		 */
		entityType = 0;
		name = "player";
		maxStamina = 120;
		speed = 3;
		level = 1;
		strength = 0;
		dexterity = 0;
		coin = 0;
		experience = 0;
		nextLevelExperience = 9;
		maxLife = 8;
		life = maxLife;
		currentWeapon = new ObjectSword(gp);
		currentShield = new ObjectWoodenShield(gp);
		
		/**
		 * @var attackVal natural player's strength + weapon's attack value
		 * @var defenseVal natural player's defense + shield/armor's defense value
		 */
		attackVal = getAttack();
		defenseVal = getDefense();
		
	}
	
	
	
	public void update() 
	{
		collisionOn = false;
		
		/*
		 * Player presses attack key - J
		 */
		if (playerAttack) 
		{
			if (attackStamina >= attackCost) {
				staminaEnabled = true;
				staminaRechargeCounter = 0;
				attack();
				
			} else {
				playerAttack = false;
			}
			
		/*
		 * Movement and dialogue keys
		 */
		} else if (keyH.upPressed||keyH.downPressed||
				keyH.leftPressed||keyH.rightPressed || keyH.dialoguePressed)
		{
			
			
			/*
			 * Check tile collision
			 */
			gp.cChecker.CheckTile(this);
			
			/*
			 * Monster collision
			 */
			
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
			
			/*
			 * Monster damage contact
			 */
			
			if (monsterIndex != 9999) {
				gp.monsters.get(monsterIndex).damageContact(this);
				this.collisionOn = false;
				gp.playSE(6);
			}
			
			
			/*
			 * Check EVENT collision
			 */
			gp.eHandler.checkEvent();
			
			/*
			 * Check OBJECT collision
			 */
			ObjectPickUp(gp.cChecker.checkObject(this, true));
			
			/*
			 * Check NPC collision
			 */
			if (!collisionNPC(gp.cChecker.checkEntity(this, gp.NPCs)) && keyH.dialoguePressed) {
				keyH.dialoguePressed = false;
			}
			
			
			/*
			 * X and Y values increase as the player moves right and down
			 */
			if (keyH.upPressed) {
				this.direction = "up";
				if (!collisionOn && gp.gameState == gp.playState) {worldY -= speed;} 
			} 
			if (keyH.downPressed) {
				this.direction = "down";
				if (!collisionOn && gp.gameState == gp.playState) {worldY += speed;}
			} 
			if (keyH.leftPressed) {
				this.direction = "left";
				if (!collisionOn && gp.gameState == gp.playState) {worldX -= speed;}
			} 
			if (keyH.rightPressed) {
				this.direction = "right";
				if (!collisionOn && gp.gameState == gp.playState) {worldX += speed;}
			}
		
			/*
			 * Player image changes every 12 frames
			 */
			++spriteCounter;
			if (spriteCounter > 12) {
				if (spriteNum) {
					spriteNum = false;
				}
				
				else if (!spriteNum) {
					spriteNum = true;
				}
				
				spriteCounter = 0;
			}
			
		}
		
		/*
		 * Check INVINCIBILITY time
		 */
		this.checkInvincibilityTime();
		
	}
	private final void ObjectPickUp(int index) {
		
		if (index != 9999) {
		}
	}
	
	private final boolean collisionNPC (int i) {
		if (i != 9999) {
			
			/*
			 * Player collides with NPC and in dialogue state
			 */
			if (keyH.dialoguePressed) {
				gp.gameState = gp.dialogueState;
				gp.NPCs.get(i).speak();
				keyH.dialoguePressed = false;
				return true;
			}
		} 
		return false;
	}
	
	public void draw(Graphics2D g2) {
	
		
		
		BufferedImage image = null;
		int tempScreenX = screenX, tempScreenY = screenY;
		
		/*
		 * Stamina starts to recharge after 1 second of not attacking
		 */
		if (attackStamina < maxStamina && staminaRechargeCounter > 60) {
			++attackStamina;
		}
	
		/*
		 * Attack doesn't have enough stamina 
		 */
		if (!playerAttack || attackStamina < attackCost) {
		switch (direction) {
		case "up":
			if (spriteNum) {image = up1;} else {image = up2;} break;
		case "down":
			if (spriteNum) {image = down1;} else {image = down2;} break;
		case "left":
			if (spriteNum) {image = left1;} else {image = left2;} break;
		case "right":
			if (spriteNum) {image = right1;} else {image = right2;} break;
			}
		
		
		} 
		/*
		 * Player attacks with enough stamina
		 */
			else if (playerAttack && (attackStamina >= attackCost) )
		{
		
		switch (direction) {
		case "up":
			tempScreenY = screenY - gp.tileSize;
			if (spriteNum) {image = attackUp1;} else {image = attackUp2;} break;
		case "down":
			if (spriteNum) {image = attackDown1;} else {image = attackDown2;} break;
		case "left":
			tempScreenX = screenX - gp.tileSize;
			if (spriteNum) {image = attackLeft1;} else {image = attackLeft2;} break;
		case "right":
			if (spriteNum) {image = attackRight1;} else {image = attackRight2;} break;
			}	
		} 
		
		/*
		 * draw effects when damaged
		 */
		if (invincibility) {
			++switchOpacityCounter;
			if (!switchOpacity && switchOpacityCounter > 3) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));	
				switchOpacity = true;
				switchOpacityCounter = 0;
			} else {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));	
				switchOpacity = false;
			}
		}
		
		/*
		 * stamina bar not hidden
		 */
		if (staminaEnabled) {
			double singleStaminaBar = (double) gp.tileSize/this.maxStamina,
					currentStaminaBar = singleStaminaBar * this.attackStamina;
			
			/*
			 * draw gray outline
			 */
			
			g2.setColor(new Color(35, 35, 35));
			g2.fillRect(screenX - 1 , screenY - 21, gp.tileSize + 2, 12);
			
			/*
			 * draw blue stamina bar
			 */
			g2.setColor(new Color(0,100, 255));
			g2.fillRect(screenX, screenY - 20, (int) currentStaminaBar, 10);
			
			if (attackStamina == maxStamina ) {
				staminaEnabled = false;
			}
			
			
		}
		
		++staminaRechargeCounter;
		
		/*
		 * draw image
		 */
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			
	}
	
	@Override
	protected final void checkInvincibilityTime() {

		if (invincibility) {
		++invincibilityCounter;
		if (invincibilityCounter > 90) {
			invincibility = false;
			invincibilityCounter = 0;
			
			}
		}
	}
	
	private final void attack() {

		++spriteCounter;
		
		/*
		 * 10 frames of short attack animation
		 */
		if (spriteCounter < 10) {
			spriteNum = true;
		/*
		 * Next 20 frames of long attack animation
		 */
		} else if (spriteCounter < 30) {
			spriteNum = false;

		/*
		* Save player's current location on map
		*/
			int currWorldX = worldX, 
				currWorldY = worldY,
				currWidth = solidArea.width,
				currHeight = solidArea.height;
			
		switch (this.direction) {
		
		/*
		 * Shift collision area back by the attack area length to account for image stretch
		 */
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width;  break;
			case "right": worldX += attackArea.width; break;
		}
	
		solidArea.width = attackArea.width;
		solidArea.height = attackArea.height;
		
		int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
		damageMonster(monsterIndex);
		
		/*
		 * After checking collision, restore original data
		 */
		worldX = currWorldX;
		worldY = currWorldY;
		solidArea.width = currWidth;
		solidArea.height = currHeight;
			 
		/*
		 * Final frames of receding attack animation
		 */
		} else {
			
			spriteNum = true;
			spriteCounter = 0;
			playerAttack = false;
			attackStamina -= attackCost;
			
		}
		
	}
	
	private final void damageMonster(int i) {
		if (i != 9999) {
			if (!gp.monsters.get(i).invincibility) {
				gp.playSE(5);
				/*
				 * Attack lands
				 */
				gp.monsters.get(i).setLife(gp.monsters.get(i).getLife() - 1);
				gp.monsters.get(i).invincibility = true;
				gp.monsters.get(i).monsterDamageReaction(this);
				if (gp.monsters.get(i).getLife() < 1) {
					gp.monsters.get(i).alive = false;
					gp.monsters.get(i).dying = true;
					gp.playSE(gp.monsters.get(i).returnDeathSound());
				}
			}
		} else {
			/*
			 * Attack misses
			 */
		}
	}
	
	/*
	 * Attack and defense get functions
	 */
	
	private int getAttack () {
		return strength += currentWeapon.getAttackValue();
	}
	
	private int getDefense () {
		return defense += currentShield.getDefenseValue();
	}

	
}
