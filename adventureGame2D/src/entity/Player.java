package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import adventureGame2D.GamePanel;
import adventureGame2D.KeyHandler;
import enums.Direction;
import enums.EntityType;
import enums.GameState;
import monster.Monster;
import npc.NPC;
import object.AttackObjectInterface;
import object.DefenseObjectInterface;
import object.GameObject;
import object.Key;
import object.Sword;
import object.WoodenShield;

public class Player extends Entity {
	
	//Variables
	KeyHandler keyH;
	BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	

	//Where the player is drawn on the screen - camera 
	private final int screenX;
	
	private final int screenY;
	
	private boolean switchOpacity;
	
	private int switchOpacityCounter = 0;

	private int attackCost, attackStamina, staminaRechargeCounter, maxStamina;

	private boolean playerAttack;
	
	private boolean staminaEnabled;
	
	private ArrayList <GameObject> inventory = new ArrayList<GameObject>();
	

	/*
	 * Player attributes
	 */
	private  int level,
				healthRegen,
				mana,
				manaRegen,
				strength,
				dexterity,
				stamina,
				knockback,
				criticalHit,
				coin,
				experience,
				nextLevelExperience,
				upgradePoints;
	private String [] labels = {
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
			"Experience",
			"Coins",
			"Equipment",
	};
	
	
	/*
	 * Equipped weapon and shield
	 */
	private GameObject currentWeapon;
	private GameObject currentShield;
	
	private GameObject hotBarItem;
	

				
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
		screenX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);
		screenY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);
		
		this.setDefaultPlayerValues();
		this.getPlayerImage();
		this.getPlayerAttackImage();
	}
	
	//-------------------------------CLASS METHODS------------------
	private final void getPlayerImage() {
		
		attackUp1 = setupEntity("boy_attack_up_1", "/player_attack/", gp.getTileSize(), gp.getTileSize()*2);
		attackUp2 = setupEntity("boy_attack_up_2", "/player_attack/", gp.getTileSize(), gp.getTileSize() * 2);
		attackDown1 = setupEntity("boy_attack_down_1", "/player_attack/", gp.getTileSize(), gp.getTileSize() * 2);
		attackDown2 = setupEntity("boy_attack_down_2", "/player_attack/", gp.getTileSize(), gp.getTileSize() * 2);
		attackLeft1 = setupEntity("boy_attack_left_1", "/player_attack/", gp.getTileSize() * 2, gp.getTileSize());
		attackLeft2 = setupEntity("boy_attack_left_2", "/player_attack/", gp.getTileSize() * 2, gp.getTileSize());
		attackRight1 = setupEntity("boy_attack_right_1", "/player_attack/", gp.getTileSize() * 2, gp.getTileSize());
		attackRight2 = setupEntity("boy_attack_right_2", "/player_attack/", gp.getTileSize() * 2, gp.getTileSize());
		
		
	}
	
	private final void getPlayerAttackImage() {
		
		up1 = setupEntity("boy_up_1", "/player/", gp.getTileSize(), gp.getTileSize());
		up2 = setupEntity("boy_up_2", "/player/", gp.getTileSize(), gp.getTileSize());
		down1 = setupEntity("boy_down_1", "/player/", gp.getTileSize(), gp.getTileSize());
		down2 = setupEntity("boy_down_2", "/player/", gp.getTileSize(), gp.getTileSize());
		left1 = setupEntity("boy_left_1", "/player/", gp.getTileSize(), gp.getTileSize());
		left2 = setupEntity("boy_left_2", "/player/", gp.getTileSize(), gp.getTileSize());
		right1 = setupEntity("boy_right_1", "/player/", gp.getTileSize(), gp.getTileSize());
		right2 = setupEntity ("boy_right_2", "/player/", gp.getTileSize(), gp.getTileSize());
		
	}
	
	
	private final void setDefaultPlayerValues() {
		//Default player values
		WorldX = gp.getTileSize() * 122;
		WorldY = gp.getTileSize() * 132;
		direction = Direction.DOWN;
		
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
		//Attack area inherits from object's attackArea
//		attackArea.width = 32;
//		attackArea.height = 32;
		
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
		entityType = EntityType.PLAYER;
		name = "dr8d";
		healthRegen = 0;
		maxStamina = 120;
		stamina = 0;
		speed = 3;
		level = 1;
		mana = 0;
		manaRegen = 0;
		strength = 0;
		defense = 0;
		dexterity = 0;
		stamina = 0;
		coin = 0;
		experience = 8;
		knockback = 0;
		criticalHit = 0;
		nextLevelExperience = 9;
		upgradePoints = 0;
		maxLife = 10;
		life = maxLife;
		currentWeapon = new Sword(gp);
		currentShield = new WoodenShield(gp);
		
		attackArea = currentWeapon.attackArea;
		
		
		/**
		 * @var attackVal natural player's strength + weapon's attack value
		 * @var defenseVal natural player's defense + shield/armor's defense value
		 */
		attackVal = strength + ((AttackObjectInterface)currentWeapon).getAttackValue();
		defenseVal = defense + ((DefenseObjectInterface)currentShield).getDefenseValue();
		
		addInventoryItems();
	}
	
	
	public void update() 
	{
		collisionOn = false;	
		this.checkLevelUp();
		if (playerAttack) 
		{
			if (attackStamina >= attackCost) {
				staminaEnabled = true;
				staminaRechargeCounter = 0;
				attack();	
			} else {
				playerAttack = false;
			}
			
		} else if (keyH.getUpPress()||keyH.getDownPress()||
				keyH.getLeftPress()||keyH.getRightPress() || keyH.getDialoguePress())
		{
			
			//X and Y values increase as the player moves right and down
			//Check tile collision
			
			//monster collision;
			//this might return 9999
			int monsterIndex = gp.getCollisionCheck().checkEntity(this, gp.getMonsters());
			if (monsterIndex != 9999) {
				if (!this.invincibility) {
					//Downcasting Entity to Monster to get player index
					if (gp.getMonsters().get(monsterIndex) instanceof Monster) {
						((Monster) gp.getMonsters().get(monsterIndex)).damagePlayer(this);;
					}
					gp.playSE(6);
				}
			}
			
			//Collision checker receive subclass
			gp.getCollisionCheck().CheckTile(this);
			
			//Check event collision
			gp.getEventHandler().checkEvent();
			
			//Check object collision
			ObjectPickUp(gp.getCollisionCheck().checkObject(this));
			
			
			//check NPC collision - not colliding but the dialogue key is pressed --> reset it to false
			if (!collisionNPC(gp.getCollisionCheck().checkEntity(this, gp.getNPCS())) && keyH.getDialoguePress()) {
				keyH.setDialoguePress(false);
			}
			
	
			if (keyH.getUpPress()) {
				this.direction = Direction.UP;
				if (!collisionOn && gp.getGameState() == GameState.PLAY) {WorldY -= speed;} 
			} 
			if (keyH.getDownPress()) {
				this.direction = Direction.DOWN;
				if (!collisionOn && gp.getGameState() == GameState.PLAY) {WorldY += speed;}
			} 
			if (keyH.getLeftPress()) {
				this.direction = Direction.LEFT;
				if (!collisionOn && gp.getGameState() == GameState.PLAY) {WorldX -= speed;}
			} 
			if (keyH.getRightPress()) {
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
		
		String text = "";
		
		if (index != 9999) {
			
			GameObject currentObject = (GameObject) gp.getObjects().get(index);
			
			//12 is max inventory size
			if (inventory.size() != 12 && currentObject.getPickUpState()) {
				inventory.add(currentObject);
				gp.playSE(1);
				text =  "You picked up " + currentObject.getName() + "!";
				gp.getObjects().remove(index);
				
			} else if (!currentObject.getPickUpState()){
				//if you are colliding with something that cannot be picked up
			} else {
				text = "You cannot carry any more items!";
			}
		
			gp.getGameUI().addSubtitleMsg(text);
		}
	}
	
	private final boolean collisionNPC (int i) {
		if (i != 9999) {
			//player touching npc
			if (keyH.getDialoguePress()) {
				gp.setGameState(GameState.DIALOGUE);;
				if (gp.getNPCS().get(i) instanceof NPC) {
					((NPC) gp.getNPCS().get(i)).speak();
				}
				
				keyH.setDialoguePress(false);
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
		default: break;
			}
		
		
		} //attack cool down period is over - stamina
			else if (playerAttack && (attackStamina >= attackCost) )
		{
		
		switch (direction) {
		
		case UP:
			tempScreenY = screenY - gp.getTileSize();
			if (spriteNum) {image = attackUp1;} else {image = attackUp2;} break;
			
		case DOWN:
			if (spriteNum) {image = attackDown1;} else {image = attackDown2;} break;
			
		case LEFT:
			tempScreenX = screenX - gp.getTileSize();
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
			switchOpacity = !switchOpacity;
			if (!switchOpacity && switchOpacityCounter > 3) {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));	
				switchOpacityCounter = 0;
			} else {
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));	
			}
		}
		
		//stamina bar not hidden
		if (staminaEnabled) {
			double singleStaminaBar = (double) gp.getTileSize()/this.maxStamina,
					currentStaminaBar = singleStaminaBar * this.attackStamina;
			//Draw stamina bar
			//Gray outline
			//x, y, width, height
			
			g2.setColor(new Color(35, 35, 35));
			g2.fillRect(screenX - 1 , screenY - 21, gp.getTileSize() + 2, 12);
			
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
		
		int monsterIndex = gp.getCollisionCheck().checkEntity(this, gp.getMonsters());
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
			Entity monster = gp.getMonsters().get(i);
			
			if (!monster.invincibility) {
				gp.playSE(5);
				//attack lands
				int damage = monster.getLife() + monster.getDefense()- this.attackVal;
				monster.setLife(damage);
				monster.invincibility = true;
				
				if (monster instanceof Monster) {
					((Monster) monster).monsterDamageReaction(this);
				}
				
				gp.getGameUI().addSubtitleMsg(this.name + " damaged " + monster.getName());
				
				if (monster.getLife() < 1) {
					
					gp.getGameUI().addSubtitleMsg(this.name + " killed " + monster.getName());
					
					monster.alive = false;
					monster.dying = true;
					
					if (monster instanceof Monster) {
						gp.getGameUI().addSubtitleMsg(this.name + " gained " + ((Monster)monster).getMonsterExperience()
																+ " experience ");
						gp.playSE(((Monster) monster).returnDeathSound());
						this.experience += ((Monster)monster).getMonsterExperience();
						
					}
					
				}
			}
		}
			//Miss the attack
	}
	
	private final void checkLevelUp() {
		if (this.experience >= this.nextLevelExperience) {
			gp.playSE(8);
			++level;
			++upgradePoints;
			this.experience -= this.nextLevelExperience;
			nextLevelExperience =  nextLevelExperience * 2;
			gp.setGameState(GameState.DIALOGUE);
			gp.getGameUI().setCurrentDialogue("You leveled up to " + level + "!");
			
		}
	}
	
	private final void addInventoryItems() {
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		hotBarItem = inventory.get(0);
	}
	
	/*
	 * SETTERS and GETTERS
	 */
	public int getScreenX () { return screenX; }
	
	public int getScreenY () { return screenY; }
	
	public boolean getPlayerAttack () { return playerAttack; }
	public void setPlayerAttack (boolean playerAttack) { this.playerAttack = playerAttack; }
	
	public String[] getLabelArray () { return labels; }
	
	public String getLabelEntries (int i) { return labels[i]; }
	
	public int getLevel () { return level; }
	
	public int getHealthRegen () { return healthRegen; }
	
	public int getMana () { return mana; }
	
	public int getManaRegen () { return manaRegen; }
	
	public int getTotalAttack () { return attackVal; }

	public int getTotalDefense () { return defenseVal; }
	
	public int getDexterity () { return dexterity; }
	
	public int getStamina () { return stamina; }
	
	public int getKnockback () { return knockback; }
	
	public int getCriticalHit () { return criticalHit; }
	
	public int getCoin () { return coin; }
	
	public int getExperience () { return experience; }
	
	public int getNextLevelExperience () { return nextLevelExperience; }
	
	public int getUpgradePoints () { return upgradePoints; }
	
	public GameObject getEquippedWeapon () { return currentWeapon; }
	
	public GameObject getEquippedShield () { return currentShield; }
	
	public ArrayList <GameObject> getInventory () { return inventory; } 
	
	
}
