package monster;

import adventureGame2D.GamePanel;
import entity.Entity;

public abstract class Monster extends Entity implements MonsterInterface{

	//Holder class so that all monsters the extends this class can be downcasted from Entity
	//and also be forced to implement methods from MonsterInterface and then have their methods
	//invoked when it is needed
	//This class is here because it implements the Monster Interface and also extends from Entity
	//Do not want Entity to implement MonsterInterface for downcasting
	public Monster(GamePanel gp) {
		super(gp);
	}
	
	
	public void checkInvincibilityTime() {
		if (invincibility) {
			++invincibilityCounter;
			if (invincibilityCounter > 120) {
				invincibility = false;
				invincibilityCounter = 0;
				
				}
			}
	}


}
