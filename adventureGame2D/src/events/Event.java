package events;

import adventureGame2D.GamePanel;

public class Event {
	
	GamePanel gp;
	public Event(GamePanel gp) {
		this.gp = gp ;
	}
	
	public void DamagePit(int gameState) {
		gp.gameState = gameState;
		gp.ui.setCurrentDialogue("You fell into a pit");
		int currentPlayerLife = gp.player.getLife();
		currentPlayerLife -= 2;
		gp.player.setLife(currentPlayerLife);
	}
	
	public void healingPool (int gameState) {
		gp.gameState = gameState;
		gp.ui.setCurrentDialogue("You recovered some of your strength");
		int currentPlayerLife = gp.player.getLife();
		int maxLife = gp.player.getMaxLife();
		if (currentPlayerLife<maxLife) {currentPlayerLife+=2;}
		gp.player.setLife(currentPlayerLife);
	}
}
