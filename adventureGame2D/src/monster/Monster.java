package monster;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import adventureGame2D.GamePanel;
import entity.Entity;
import projectile.Projectile;

public abstract class Monster extends Entity implements MonsterInterface{

	protected GamePanel gp;
	
	protected Integer experience;
	
	protected Projectile projectile;
	
	//Holder class so that all monsters the extends this class can be downcasted from Entity
	//and also be forced to implement methods from MonsterInterface and then have their methods
	//invoked when it is needed
	//This class is here because it implements the Monster Interface and also extends from Entity
	//Do not want Entity to implement MonsterInterface for downcasting
	public Monster(GamePanel gp) {
		super(gp);
		this.gp = gp;
	}
	
	
	public void checkInvincibilityTime() {
		if (invincibility) {
			++invincibilityCounter;
			if (invincibilityCounter > 60) {
				invincibility = false;
				invincibilityCounter = 0;
				
				}
			}
	}
	
	//Draws dying animation for monsters
	public void drawDeathAnimation (Graphics2D g2) {
		++deathCount;
		
		if (deathCount < 41) {
			if (deathCount % 5 == 0 && deathCount % 10 != 0) 
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));	
			else if (deathCount % 10 == 0)
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));	
		} else {
			dying = false;
		}
		
	}
	
	public Integer getMonsterExperience () { return experience;}

	public abstract void getMonsterDrop();
	

}
