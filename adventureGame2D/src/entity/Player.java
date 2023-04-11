package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import adventureGame2D.GamePanel;
import adventureGame2D.KeyHandler;
import enums.Direction;
import enums.EntityType;
import enums.GameState;
import enums.ObjectType;
import enums.ToolType;
import monster.Monster;
import npc.NPC;
import object.GameObject;
import object.Key;
import object.attack.Sword;
import object.consummable.HealingPotion;
import object.defense.BlueShield;
import object.defense.WoodenShield;
import object.interfaces.AttackObjectInterface;
import object.interfaces.ConsummableInterface;
import object.interfaces.DefenseObjectInterface;
import object.interfaces.PowerUpObjectInterface;
import object.interfaces.ToolObjectInterface;
import object.tool.Axe;
import projectile.Fireball;
import projectile.Projectile;
import tile.InteractiveTile;

public class Player extends Entity {
	
	//Variables
	KeyHandler keyH;
	BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	BufferedImage axeUp1, axeUp2, axeDown1, axeDown2, axeLeft1, axeLeft2, axeRight1, axeRight2;
	
	protected Rectangle defaultAttackArea = new Rectangle (0, 0, 0, 0);

	//Where the player is drawn on the screen - camera 
	private final int screenX;
	
	private final int screenY;
	
	private boolean switchOpacity;
	
	private int switchOpacityCounter = 0;

	private int actionCost, actionStamina, staminaRechargeCounter, maxStamina;
	
	private int manaRegenCount, healthRegenCount;

	private boolean playerAttack;
	
	private boolean useTool;
	
	private boolean castSpell;
	
	private boolean staminaEnabled;
	
	private ArrayList <GameObject> inventory = new ArrayList<GameObject>();
	
	private int totalEncumbrance, netSpeed;
	
	private LinkedHashMap <String, Integer> attributeUpgrades = new LinkedHashMap <String, Integer>();
	
	private int upgradePoints = 0,
				usedPoints = 0;

	/*
	 * Player attributes
	 */
	private  int level,
				healthRegen,
				mana,
				manaMax,
				manaRegen,
				strength,
				dexterity,
				knockback,
				criticalHit,
				coin,
				experience,
				nextLevelExperience;

	/*
	 * Equipped weapon and shield
	 */
	private GameObject equippedWeapon;
	private GameObject equippedDefense;
	private GameObject equippedTool;
	private GameObject equippedAccessory1;
	private GameObject equippedAccessory2;
	/**
	 * Default weapon/shield
	 */
	private GameObject defaultWeapon;
	private GameObject defaultShield;
	private Projectile currentProjectile;
	
	/*
	 * Item attributes
	 */
	private int attackVal,
				defenseVal;
	
	
	
	
	//-------------------------------CONSTRUCTORS------------------
	/**
	 * 
	 * @param gp
	 * @param keyH
	 */
	public Player (GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		
		//Places the character at the center of the screen
		//Subtract half of the tile length to be at the center of the player character
		screenX = gp.getScreenWidth()/2 - (gp.getTileSize()/2);
		screenY = gp.getScreenHeight()/2 - (gp.getTileSize()/2);
		
		this.setDefaultPlayerValues();
		this.getPlayerImage();	
		this.getPlayerSwordImage();
		this.getPlayerAxeImage();
	}
	
	//-------------------------------CLASS METHODS------------------
	private final void getPlayerSwordImage() {
		
		attackUp1 = setupEntity("boy_attack_up_1", "/player_attack/", gp.getTileSize(), gp.getTileSize()*2);
		attackUp2 = setupEntity("boy_attack_up_2", "/player_attack/", gp.getTileSize(), gp.getTileSize() * 2);
		attackDown1 = setupEntity("boy_attack_down_1", "/player_attack/", gp.getTileSize(), gp.getTileSize() * 2);
		attackDown2 = setupEntity("boy_attack_down_2", "/player_attack/", gp.getTileSize(), gp.getTileSize() * 2);
		attackLeft1 = setupEntity("boy_attack_left_1", "/player_attack/", gp.getTileSize() * 2, gp.getTileSize());
		attackLeft2 = setupEntity("boy_attack_left_2", "/player_attack/", gp.getTileSize() * 2, gp.getTileSize());
		attackRight1 = setupEntity("boy_attack_right_1", "/player_attack/", gp.getTileSize() * 2, gp.getTileSize());
		attackRight2 = setupEntity("boy_attack_right_2", "/player_attack/", gp.getTileSize() * 2, gp.getTileSize());
		
		
	}
	
	private final void getPlayerImage() {
		up1 = setupEntity("boy_up_1", "/player/", gp.getTileSize(), gp.getTileSize());
		up2 = setupEntity("boy_up_2", "/player/", gp.getTileSize(), gp.getTileSize());
		down1 = setupEntity("boy_down_1", "/player/", gp.getTileSize(), gp.getTileSize());
		down2 = setupEntity("boy_down_2", "/player/", gp.getTileSize(), gp.getTileSize());
		left1 = setupEntity("boy_left_1", "/player/", gp.getTileSize(), gp.getTileSize());
		left2 = setupEntity("boy_left_2", "/player/", gp.getTileSize(), gp.getTileSize());
		right1 = setupEntity("boy_right_1", "/player/", gp.getTileSize(), gp.getTileSize());
		right2 = setupEntity ("boy_right_2", "/player/", gp.getTileSize(), gp.getTileSize());
		
	}
	
	private final void getPlayerAxeImage() {
		axeUp1 = setupEntity("boy_axe_up_1", "/player/axe/", gp.getTileSize(), gp.getTileSize() * 2);
		axeUp2 = setupEntity("boy_axe_up_2", "/player/axe/", gp.getTileSize(), gp.getTileSize() * 2);
		axeDown1 = setupEntity("boy_axe_down_1", "/player/axe/", gp.getTileSize(), gp.getTileSize() * 2);
		axeDown2 = setupEntity("boy_axe_down_2", "/player/axe/", gp.getTileSize(), gp.getTileSize() * 2);
		axeLeft1 = setupEntity("boy_axe_left_1", "/player/axe/", gp.getTileSize() * 2, gp.getTileSize());
		axeLeft2 = setupEntity("boy_axe_left_2", "/player/axe/", gp.getTileSize() * 2, gp.getTileSize());
		axeRight1 = setupEntity("boy_axe_right_1", "/player/axe/", gp.getTileSize() * 2, gp.getTileSize());
		axeRight2 = setupEntity ("boy_axe_right_2", "/player/axe/", gp.getTileSize() * 2, gp.getTileSize());
	}
	
	
	public final void setDefaultPlayerValues() {
		//Default player values
//		WorldX = gp.getTileSize() * 122;
//		WorldY = gp.getTileSize() * 132;
		
		//Near the forest values
		WorldX = gp.getTileSize() * 136;
		WorldY = gp.getTileSize() * 124;
		
		//Trade starting location
//		WorldX = gp.getTileSize() * 112;
//		WorldY = gp.getTileSize() * 115;
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
		
		
		
		playerAttack = false;
		useTool = false;
		castSpell = false;
		
		this.pColor = new Color (136, 8, 8);
		this.pSize = 6;
		this.pSpeed = 1;
		this.pDuration = 18;
		
		//stamina
		actionCost = 60;
		maxStamina = 120;
		actionStamina = maxStamina;
		staminaRechargeCounter = 0;
		staminaEnabled = false;
		manaRegenCount = 0;
		healthRegenCount = 0;
		
		//player opacity
		switchOpacity = false;
		switchOpacityCounter = 0;
		
		/**
		 * Player attributesentityDirection
		 * @var maxStamina player's max stamina pool.	ToolType type = ((ToolObjectInterface)this.equippedTool).getToolType(); Default: 120. Max: 300
		 * @var speed player's speed. Default: 3. Max: 5
		 * @var level player's level. Default: 1. Max: 10/15?
		 * @var strength natural amount of damage deals. Default 0. Max: 3
		 * @var defense natural amount of damage negated. Default: 0. Max: 3
		 * @var dexterity chance of dodging an enemy attack. Default: 0. Max: 20 (%)
		 * @var coin amount of coins player has. Def: 0. Max: 999
		 */
		entityType = EntityType.PLAYER;
		name = "dr8d";
		totalEncumbrance = 0;
		level = 1;
		life = 12;	
		mana = 6;
		coin = 0;
		experience = 0;
		invincibility = false;
	
		nextLevelExperience = 9;
		upgradePoints = 10;
		
		defaultWeapon = new Sword(gp, 0, 0);
		defaultShield = new WoodenShield(gp, 0, 0);
		currentProjectile = new Fireball (gp);
		
		equippedWeapon = defaultWeapon;
		equippedDefense = defaultShield;
		equippedAccessory1 = null;
		equippedAccessory2 = null;
		equippedTool = null;
		
		
	
		
		addDefaultInventoryItems();
		setAttributeDefaultUpgrades();
		
	}
	
	//store the number of upgrades
	private final void setAttributeDefaultUpgrades() {
		attributeUpgrades.put ("Name", null);
		attributeUpgrades.put ("Level", null);
		attributeUpgrades.put ("HP", 0);
		attributeUpgrades.put ("HP Regeneration", 0);
		attributeUpgrades.put ("Mana", 0);
		attributeUpgrades.put ("Mana Regeneration", 0);
		attributeUpgrades.put ("Power", 0);
		attributeUpgrades.put ("Defense", 0);
		attributeUpgrades.put ("Dexterity", 0);
		attributeUpgrades.put ("Stamina", 0);
		attributeUpgrades.put ("Speed", 0);
		attributeUpgrades.put ("Knockback", 0);
		attributeUpgrades.put ("Critical Hit", 0);
		attributeUpgrades.put ("Experience", null);
		attributeUpgrades.put ("Coins", null);
		attributeUpgrades.put ("Equipment", null);
	}
	
	private final void updatePlayerAttributes() {
		
		/**
		 * netspeed = default speed of 3 + speed upgrades - encumbrance - temporarily not
		 */
		netSpeed = 3;

		
		//set the speed to be affected by equipment's encumbrance
		if (equippedDefense != null) totalEncumbrance += equippedDefense.getEncumbrance();
		
		if (equippedWeapon != null) totalEncumbrance += equippedWeapon.getEncumbrance();
		
		if (equippedAccessory1 != null) totalEncumbrance += equippedAccessory1.getEncumbrance();

		if (equippedAccessory2 != null) totalEncumbrance += equippedAccessory2.getEncumbrance();
		
		if (equippedTool != null) totalEncumbrance += equippedTool.getEncumbrance();
				
		speed = netSpeed - totalEncumbrance + attributeUpgrades.get("Speed");
		
		if (speed < 0) speed = 0;
		
		totalEncumbrance = 0;
		
		
		/**
		 * Attack area
		 */
		if (equippedWeapon != null)attackArea = equippedWeapon.attackArea;
		else attackArea = defaultAttackArea;
		
		/**
		 * UPDATING ATTRIBUTES AND UPGRADES
		 */
		maxLife = 12 + 2 * attributeUpgrades.get("HP");
		healthRegen = 1 + attributeUpgrades.get("HP Regeneration");
		knockback = 0 + attributeUpgrades.get("Knockback");
		criticalHit = 0 + attributeUpgrades.get("Critical Hit");
		manaMax = 6 + attributeUpgrades.get("Mana");
		manaRegen = 1 + attributeUpgrades.get("Mana Regeneration");
		strength = 0 + attributeUpgrades.get("Power");
		defense = 0 + attributeUpgrades.get("Defense");
		dexterity = 0 + attributeUpgrades.get("Dexterity");
		maxStamina = 120 + attributeUpgrades.get("Stamina") * 60;
		
		
		/**
		 * @var attackVal natural player's strength + weapon's attack value
		 * @var defenseVal natural player's defense + shield/armor's defense value
		 */
		if (equippedWeapon != null) attackVal = strength + ((AttackObjectInterface)equippedWeapon).getAttackValue();
		else attackVal = strength;
		
		if (equippedDefense != null) defenseVal = defense + ((DefenseObjectInterface)equippedDefense).getDefenseValue();
		else defenseVal = defense;
		
		
	}
	
	
	public void update() 
	{
		if (life < 1) { 
			gp.setGameState(GameState.GAMEOVER);
			gp.playSE(18);;
		}
		this.updatePlayerAttributes();
		collisionOn = false;	
		this.checkLevelUp();
		if ((playerAttack && this.equippedWeapon != null) || (useTool && this.equippedTool != null)) 
		{
			if (actionStamina >= actionCost) {
				staminaEnabled = true;
				staminaRechargeCounter = 0;	
				
				if (playerAttack) {
					attackVal = strength + ((AttackObjectInterface)equippedWeapon).getAttackValue();
				} else if (useTool) {
					attackVal = strength + ((AttackObjectInterface)equippedTool).getAttackValue();
				}
				
				swing();
			} else {
				playerAttack = false;
				useTool = false;
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
					if (gp.getMonsters().get(gp.currentMap).get(monsterIndex) instanceof Monster) {
						((Monster) gp.getMonsters().get(gp.currentMap).get(monsterIndex)).damagePlayer();
					}
					
				}
			}
			
			//Collision checker receive subclass
			gp.getCollisionCheck().CheckTile(this);
			
			//Check event collision
			gp.getEventHandler().checkEvent();
			
			//Check object collision
			ObjectPickUp(gp.getCollisionCheck().checkObject(this));
			
			//Check Collision with interactive Tiles
			gp.getCollisionCheck().checkEntity(this, gp.getInteractiveTiles());
			
			
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
				spriteNum = !spriteNum;
				spriteCounter = 0;
			}
			
		}
		//player casts this spell and the last fireball shot has dissipated
		if (this.castSpell && this.mana >= this.currentProjectile.getSpellCost()) {
			this.mana -= this.currentProjectile.getSpellCost();
			Projectile projectile = new Fireball(gp);
			projectile.set(this.WorldX, this.WorldY, this.direction, this);
			gp.getProjectiles().get(gp.currentMap).add(projectile);
			gp.playSE(16);
			castSpell = false;
		}
		
		if (this.mana < this.manaMax )++manaRegenCount;
		else manaRegenCount = 0;
		
		if (this.life < this.maxLife) ++healthRegenCount;
		else healthRegenCount = 0;
		
		
		if (manaRegenCount > 480) {
			if (this.mana < this.manaMax) {
				this.mana += manaRegen;
			}
			
			manaRegenCount = 0;
		}
		
		if (healthRegenCount > 600) {
			if (this.life < this.maxLife) {
				this.life += healthRegen;
				
			}
			
			healthRegenCount = 0;
		}
		
	
		this.checkInvincibilityTime();
		
	}//update
	
	private final void ObjectPickUp(int index) {
		
		String text = "";
		
		if (index != 9999) {
			
			GameObject currentObject = (GameObject) gp.getObjects().get(gp.currentMap).get(index);
			
			//12 is max inventory size
			if (inventory.size() != 12 && currentObject.getPickUpState()) {
				gp.playSE(1);
				text =  "You picked up " + currentObject.getName() + "!";
				gp.getObjects().get(gp.currentMap).remove(index);
				
				if (currentObject instanceof PowerUpObjectInterface) 
					((PowerUpObjectInterface)currentObject).grantPowerUpEffects();
				else inventory.add(currentObject);
					
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
				if (gp.getNPCS().get(gp.currentMap).get(i) instanceof NPC) {
					((NPC) gp.getNPCS().get(gp.currentMap).get(i)).speak();
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
		if (actionStamina < maxStamina && staminaRechargeCounter > 60) {
			actionStamina += 1 * (attributeUpgrades.get("Stamina") + 1);
		}
	
		//attack cool down period is on
		if (!playerAttack || !useTool ||actionStamina < actionCost) {
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
		//player attack so uses sword attack
		if (playerAttack && (actionStamina >= actionCost) && equippedWeapon != null)
		{
			
		useTool = false;
		
		switch (direction) {
		
		case UP -> {
			tempScreenY = screenY - gp.getTileSize();
			if (spriteNum) {image = attackUp1;} else {image = attackUp2;} break;
		}
		case DOWN -> {
			if (spriteNum) {image = attackDown1;} else {image = attackDown2;} break;
		}
		case LEFT -> {
			tempScreenX = screenX - gp.getTileSize();
			if (spriteNum) {image = attackLeft1;} else {image = attackLeft2;} break;
		}
		case RIGHT -> {
			if (spriteNum) {image = attackRight1;} else {image = attackRight2;} break;
			}
		}
		
		} else {
			playerAttack = false;
		}
		
		
		//Player uses tool so uses axe image
		if (useTool && (actionStamina >= actionCost) && equippedTool != null && equippedTool != null) {
			
		playerAttack = false;
			
		ToolType type = ((ToolObjectInterface)this.equippedTool).getToolType();
			
		if (type == ToolType.AXE) {
			switch (direction) {
			
			case UP -> {
				tempScreenY = screenY - gp.getTileSize();
				if (spriteNum) {image = axeUp1;} else {image = axeUp2;} break;
			}
			case DOWN -> {
				if (spriteNum) {image = axeDown1;} else {image = axeDown2;} break;
			}
			case LEFT -> {
				tempScreenX = screenX - gp.getTileSize();
				if (spriteNum) {image = axeLeft1;} else {image = axeLeft2;} break;
			}
			case RIGHT -> {
				if (spriteNum) {image = axeRight1;} else {image = axeRight2;} break;
			}
			}
			
		}
		
		if (type == ToolType.PICKAXE) {
			
		}
	
		} else {
			useTool = false;
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
					currentStaminaBar = singleStaminaBar * this.actionStamina;
			//Draw stamina bar
			//Gray outline
			//x, y, width, height
			
			g2.setColor(new Color(35, 35, 35));
			g2.fillRect(screenX - 1 , screenY - 21, gp.getTileSize() + 2, 12);
			
			//Blue stamina bar
			g2.setColor(new Color(0,100, 255));
			g2.fillRect(screenX, screenY - 20, (int) currentStaminaBar, 10);
			
			if (actionStamina == maxStamina ) {
				staminaEnabled = false;
			}
			
		}
		++staminaRechargeCounter;
		
		//16 pixels
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
			
	}
	
	public final void checkInvincibilityTime() {

		if (invincibility) {
			++invincibilityCounter;
		if (invincibilityCounter > 90) {
			invincibility = false;
			invincibilityCounter = 0;
			
			}
		}
	}
	
	//Method for swinging anything that is a weapon or a tool
	private final void swing() {
		//Attacking
		++spriteCounter;
		
		//10 frames of short attack animation
		if (spriteCounter < 15) {
			spriteNum = true;
		//next 20 frames of long attack animation
		} else if (spriteCounter < 35) {
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
		if (monsterIndex != 9999) {
			damageMonster(monsterIndex);
			this.generateParticles(this, gp.getMonsters().get(gp.currentMap).get(monsterIndex));
		}
		
		//Check collision with an interactive tile
		int interactiveTileIndex = gp.getCollisionCheck().checkEntity(this, gp.getInteractiveTiles());
		if (interactiveTileIndex != 9999) {
			((InteractiveTile)gp.getInteractiveTiles().get(gp.currentMap).get(interactiveTileIndex)).interactTile(interactiveTileIndex, useTool);
		}
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
			useTool = false;
			actionStamina -= actionCost;
			
		}
		
	}
	

	public final void damageMonster(int i) {
		if (i != 9999) {
			Entity monster = gp.getMonsters().get(gp.currentMap).get(i);
			
			if (!monster.invincibility) {
				gp.playSE(5);
				//attack lands
				int damage = monster.getLife() + monster.getDefense() - this.attackVal;
				monster.setLife(damage);

				monster.invincibility = true;
				
				
				if (monster instanceof Monster) {
					((Monster) monster).monsterDamageReaction();
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
						((Monster)monster).getMonsterDrop();
						
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
	
	private final void addDefaultInventoryItems() {
		inventory.clear();
		inventory.add(equippedWeapon);
		inventory.add(equippedDefense);
		inventory.add(new BlueShield(gp, 0, 0));
		inventory.add(new Axe(gp, 0, 0));
		inventory.add(new Key(gp, 0, 0));
		inventory.add(new HealingPotion(gp, 0, 0));
	}
	
	public final void handleInventoryOptions(int option) {
		GameObject selectedItem = inventory.get(gp.getGameUI().getItemIndex());
		ObjectType type = selectedItem.getInventoryType();
		
		if (option == 0) {
			switch (type) {
			case CONSUMMABLE -> {
				//if player successfully uses consummable
				if (((ConsummableInterface)selectedItem).useConsummable()) {
					inventory.remove(selectedItem);
				}
			}
			case ACCESSORY -> throw new UnsupportedOperationException("Unimplemented case: " + type);
			case ATTACK -> {
				if (selectedItem == this.equippedWeapon) {
					this.equippedWeapon = null;
				} else {
					this.equippedWeapon = selectedItem;
				}
			}
			case DEFENSE -> {
				if (selectedItem == this.equippedDefense) {
					this.equippedDefense = null;
				} else {
					this.equippedDefense = selectedItem;
				}
			}
			case INTERACT -> throw new UnsupportedOperationException("Unimplemented case: " + type);
			case TOOL -> {
				if (selectedItem == this.equippedTool) {
					this.equippedTool = null;
				} else {
					this.equippedTool = selectedItem;
				}
			}
			case NONPICKUP -> throw new UnsupportedOperationException("Unknown case: " + type);
			default -> throw new IllegalArgumentException("Unknown case: " + type);
			
			}
			
			
		} else if (option == 1){
			//inventory.remove(selectedItem);
			inventory.remove(selectedItem);
			switch (type) {
			case CONSUMMABLE -> {
				//Doesn't do anything besides removing the item
			}
			case ACCESSORY -> throw new UnsupportedOperationException("Unimplemented case: " + type);
			case ATTACK -> {
				this.equippedWeapon = null;
			}
			case DEFENSE -> {
				if (selectedItem == this.equippedDefense) this.equippedDefense = null;
			}
			case INTERACT -> {}
			case TOOL -> {
				if (selectedItem == this.equippedTool) this.equippedTool = null;
			}
			case NONPICKUP -> throw new UnsupportedOperationException("Unknown case: " + type);
			default -> throw new IllegalArgumentException("Unknown case: " + type);
			
			}
			
		} else if (option == 2) {
			gp.setGameState(GameState.INVENTORY);
		}
	}
	
	public final boolean upgradeAttribute (int attributeLocation) {
		
		Iterator<Entry<String, Integer>> upgradeIterator = this.attributeUpgrades.entrySet().iterator();
		Map.Entry<String,Integer> entry = null;
		
		//Set the starting cursor of the iterator
		for (int a  = 0; a < 3; ++a) {
			if (upgradeIterator.hasNext()) {
				entry = upgradeIterator.next();
			}
		}
		for (int i  = 0; i < attributeLocation; ++i) {
			if (upgradeIterator.hasNext()) {
				entry = upgradeIterator.next();
			}
		}
		
		if (entry.getKey() == "Experience" && entry.getValue() == null) {
			resetUpgradePoints();
			return true;
		}
		
		if (entry.getValue() < 3 && entry.getValue() != null && upgradePoints != 0) {
			attributeUpgrades.put(entry.getKey(), attributeUpgrades.getOrDefault(entry.getKey(), 0) + 1);
			--upgradePoints;
			++usedPoints;
			return true;
		}
		
	
		
		return false;
	}
	
	private final void resetUpgradePoints () {
		upgradePoints += usedPoints;
		usedPoints = 0;
		this.setAttributeDefaultUpgrades();
	}

	
	/*
	 * SETTERS and GETTERS
	 */
	public int getScreenX () { return screenX; }
	
	public int getScreenY () { return screenY; }
	
	public boolean getPlayerAttack () { return playerAttack; }
	
	public void setPlayerAttack (boolean playerAttack) { this.playerAttack = playerAttack; }
	
	public boolean getPlayerUseTool () { return useTool; }
	
	public void setPlayerUseTool (boolean useTool) { this.useTool = useTool; }
	
	public boolean getPlayerCastSpell () { return castSpell; }
	
	public void setPlayerCastSpell (boolean castSpell) { this.castSpell = castSpell; }
	
	public int getLevel () { return level; }
	
	public int getHealthRegen () { return healthRegen; }
	
	public int getMana () { return mana; }
	
	public void setMana (int mana) { this.mana = mana; }
	
	public int getMaxMana () { return manaMax; }
	
	public int getManaRegen () { return manaRegen; }
	
	public int getTotalAttack () { return attackVal; }

	public int getTotalDefense () { return defenseVal; }
	
	public int getDexterity () { return dexterity; }
	
	public int getStamina () { return maxStamina; }
	
	public int getKnockback () { return knockback; }
	
	public int getCriticalHit () { return criticalHit; }
	
	public int getCoin () { return coin; }
	
	public void setCoin (int coin) { this.coin = coin; }
	
	public int getExperience () { return experience; }
	
	public int getNextLevelExperience () { return nextLevelExperience; }
	
	public int getUpgradePoints () { return upgradePoints; }
	
	public GameObject getEquippedWeapon () { return equippedWeapon; }
	
	public GameObject getEquippedDefense () { return equippedDefense; }
	
	public GameObject getEquippedTool () { return equippedTool; }
	
	public Projectile getCurrentProjectile() { return currentProjectile;}
	
	public ArrayList <GameObject> getInventory () { return inventory; } 
	
	public LinkedHashMap <String, Integer> getUpgradeValue() { return attributeUpgrades;}

	
	
	
}
