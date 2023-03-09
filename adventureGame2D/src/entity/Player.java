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
import enums.Direction;
import enums.GameState;
import object.AttackObjectInterface;
import object.DefenseObjectInterface;
import object.ObjectSword;
import object.ObjectWoodenShield;

public class Player extends Entity {
	
	//Variables
	KeyHandler keyH;
	BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	

	//Where the player is drawn on the screen - camera 
	public final int screenX;
	public final int screenY;
	private boolean switchOpacity;
	private int switchOpacityCounter = 0;

	private int attackCost, attackStamina, staminaRechargeCounter, maxStamina;

	public boolean playerAttack;
	
	private boolean staminaEnabled;

	/*
	 * Player attributes
	 */
	public	String name;
	public  int level,
				healthRegen,
				mana,
				manaRegen,
				strength,
				dexterity,
				stamina,
				attack,
				defense,
				knockback,
				criticalHit,
				coin,
				displaySpeed,
				experience,
				nextLevelExperience,
				upgradePoints;
	public String [] labels = {
			"Name",
			"Level",
			"HP",
			"HP Regeneration",
			"Mana",
			"Mana Regeneration",
			"Strength",
			"Defense",
			"Dexterity",
			"Stamina",
			"Speed",
			"Knockback",
			"Critical Hit",
			"Coins",
			"Experience",
			"Equipment",
	};
	
	
	/*
	 * Equipped weapon and shield
	 */
	private AttackObjectInterface currentWeapon;
	private DefenseObjectInterface currentShield;
	
	public Entity equippedWeapon,
					equippedShield;
				
	/*
	 * Item attributes
	 */
	private int attackVal,
				defenseVal;
				

	
	
	
	//-------------------------------CONSTRUCTORS------------------
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
		right2 = setupEntity ("boy_right_2", "/player/", gp.tileSize, gp.tileSize);
		
	}
	
	
	private final void setDefaultPlayerValues() {
		//Default player values
		WorldX = gp.tileSize * 122;
		WorldY= gp.tileSize * 132;
		entityType = 0;
		direction = Direction.DOWN;
		maxLife = 8;
		life = maxLife;
		
		//x, y, width, length
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 8;
		//Default values so x and y values of the rectangle can be changed later
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 24;
		//attack cooldown period has not 
		solidArea.height = 32;
		
		
		//Attack area - could really increase this for potions
		attackArea.width = 32;
		attackArea.height = 32;
		playerAttack = false;
		
		
		//stamina
		attackCost = 60;
		maxStamina = 120;
		attackStamina = maxStamina;
		staminaRechargeCounter = 0;
		staminaEnabled = false;
		
		
		//player opacity
		switchOpacity = false;
		switchOpacityCounter = 0;
		
		/**
		 * Player attributesentityDirection
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
		healthRegen = 0;
		maxStamina = 120;
		stamina = 0;
		displaySpeed = 0;
		speed = 3;
		level = 1;
		mana = 0;
		manaRegen = 0;
		strength = 0;
		dexterity = 0;
		stamina = 0;
		coin = 0;
		experience = 0;
		knockback = 0;
		criticalHit = 0;
		nextLevelExperience = 9;
		upgradePoints = 0;
		maxLife = 10;
		life = maxLife;
		currentWeapon = new ObjectSword(gp);
		currentShield = new ObjectWoodenShield(gp);
		
		equippedWeapon = new ObjectSword(gp);
		equippedShield = new ObjectWoodenShield(gp);
		
		/**
		 * @var attackVal natural player's strength + weapon's attack value
		 * @var defenseVal natural player's defense + shield/armor's defense value
		 */
		attackVal = strength + currentWeapon.getAttackValue();
		defenseVal = defense + currentShield.getDefenseValue();
		
		
	}
	
	
	
	public void update() 
	{
		collisionOn = false;				
		if (playerAttack) 
		{
			if (attackStamina >= attackCost) {
				staminaEnabled = true;
				staminaRechargeCounter = 0;
				attack();	
			} else {
				playerAttack = false;
			}
			
		} else if (keyH.upPressed||keyH.downPressed||
				keyH.leftPressed||keyH.rightPressed || keyH.dialoguePressed)
		{
			
			//X and Y values increase as the player moves right and down
			//Check tile collision
			
			
			//monster collision;
			//this might return 9999
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
			if (monsterIndex != 9999) {
				gp.monsters.get(monsterIndex).damageContact(this);
				gp.playSE(6);
			}
			
			//Collision checker receive subclass
			gp.cChecker.CheckTile(this);
			
			//Check event collision
			gp.eHandler.checkEvent();
			
			//Check object collision
			ObjectPickUp(gp.cChecker.checkObject(this, true));
			
			
			//check NPC collision
			if (!collisionNPC(gp.cChecker.checkEntity(this, gp.NPCs)) && keyH.dialoguePressed) {
				keyH.dialoguePressed = false;
			}
			
			
	
			if (keyH.upPressed) {
				this.direction = Direction.UP;
				if (!collisionOn && gp.getGameState() == GameState.PLAY) {WorldY -= speed;} 
			} 
			if (keyH.downPressed) {
				this.direction = Direction.DOWN;
				if (!collisionOn && gp.getGameState() == GameState.PLAY) {WorldY += speed;}
			} 
			if (keyH.leftPressed) {
				this.direction = Direction.LEFT;
				if (!collisionOn && gp.getGameState() == GameState.PLAY) {WorldX -= speed;}
			} 
			if (keyH.rightPressed) {
				this.direction = Direction.RIGHT;
				if (!collisionOn && gp.getGameState() == GameState.PLAY) {WorldX += speed;}
			}
		
			
			
			
			
			
			++spriteCounter;
			//Player image changes every 12 frames
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
		
		//Call superclass invincibility time
		this.checkInvincibilityTime();
		
	}//update
	
	private final void ObjectPickUp(int index) {
		
		if (index != 9999) {
		}
	}
	
	private final boolean collisionNPC (int i) {
		if (i != 9999) {
			//player touching npc
			if (keyH.dialoguePressed) {
				gp.setGameState(GameState.DIALOGUE);;
				gp.NPCs.get(i).speak();
				keyH.dialoguePressed = false;
				return true;
			}
		} 
		return false;
	}
	
	public void draw(Graphics2D g2) {
		/*
		g2.setColor(Color.yellow);
		g2.fillRect(x, y, gp.tileSize,gp.tileSize );
		*/
		
		
		BufferedImage image = null;
		int tempScreenX = screenX, tempScreenY = screenY;
		
		//stamina starts recharging after 1 second of not attacking and attackStamina recharges
		if (attackStamina < maxStamina && staminaRechargeCounter > 60) {
			++attackStamina;
		}
	
		//attack cool down period is on
		if (!playerAttack || attackStamina < attackCost) {
		switch (direction) {
		case UP:
			if (spriteNum) {image = up1;} else {image = up2;} break;
		case DOWN:
			if (spriteNum) {image = down1;} else {image = down2;} break;
		case LEFT:
			if (spriteNum) {image = left1;} else {image = left2;} break;
		case RIGHT:
			if (spriteNum) {image = right1;} else {image = right2;} break;
		default:
			break;
			}
		
		
		} //attack cool down period is over - stamina
			else if (playerAttack && (attackStamina >= attackCost) )
		{
		
		switch (direction) {
		case UP:
			tempScreenY = screenY - gp.tileSize;
			if (spriteNum) {image = attackUp1;} else {image = attackUp2;} break;
		case DOWN:
			if (spriteNum) {image = attackDown1;} else {image = attackDown2;} break;
		case LEFT:
			tempScreenX = screenX - gp.tileSize;
			if (spriteNum) {image = attackLeft1;} else {image = attackLeft2;} break;
		case RIGHT:
			if (spriteNum) {image = attackRight1;} else {image = attackRight2;} break;
		default:
			break;
			}	
		} 
		
		//Draw effect when player gets damaged
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
		
		
		//stamina bar not hidden
		if (staminaEnabled) {
			double singleStaminaBar = (double) gp.tileSize/this.maxStamina,
					currentStaminaBar = singleStaminaBar * this.attackStamina;
			//Draw stamina bar
			//Gray outline
			//x, y, width, height
			
			g2.setColor(new Color(35, 35, 35));
			g2.fillRect(screenX - 1 , screenY - 21, gp.tileSize + 2, 12);
			
			//Blue stamina bar
			g2.setColor(new Color(0,100, 255));
			g2.fillRect(screenX, screenY - 20, (int) currentStaminaBar, 10);
			
		
			
			if (attackStamina == maxStamina ) {
				staminaEnabled = false;
			}
			
			
		}
		
		++staminaRechargeCounter;
		
		
		
		
		//16 pixels
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
		//Attacking
		++spriteCounter;
		
		//10 frames of short attack animation
		if (spriteCounter < 10) {
			spriteNum = true;
		//next 20 frames of long attack animation
		} else if (spriteCounter < 30) {
			spriteNum = false;

			//Save player's current location on map
			int currWorldX = WorldX, 
				currWorldY = WorldY,
				currWidth = solidArea.width,
				currHeight = solidArea.height;
			
		switch (this.direction) {
		//shift collision area back by the attack area length
			case UP: WorldY -= attackArea.height; break;
			case DOWN: WorldY += attackArea.height; break;
			case LEFT: WorldX -= attackArea.width;  break;
			case RIGHT: WorldX += attackArea.width; break;
		default:
			break;
		}
	
		
		solidArea.width = attackArea.width;
		solidArea.height = attackArea.height;
		
		int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
		damageMonster(monsterIndex);
		
		//After checking collision, restore original data
		WorldX = currWorldX;
		WorldY = currWorldY;
		solidArea.width = currWidth;
		solidArea.height = currHeight;
			 
		//Final frames of receding attack animation
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
				//attack lands
				gp.monsters.get(i).setLife(gp.monsters.get(i).getLife() - this.attackVal);
				gp.monsters.get(i).invincibility = true;
				gp.monsters.get(i).monsterDamageReaction(this);
				if (gp.monsters.get(i).getLife() < 1) {
					gp.monsters.get(i).alive = false;
					gp.monsters.get(i).dying = true;
					gp.playSE(gp.monsters.get(i).returnDeathSound());
				}
			}
		} else {
			//Miss the attack
		}
	}
	
	
	
}
