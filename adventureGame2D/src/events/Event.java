package events;

import adventureGame2D.GamePanel;
import enums.GameState;

public class Event {
	
	GamePanel gp;

	public Event(GamePanel gp) {
		this.gp = gp ;
	}
	
	public void DamagePit(GameState gameState) {
		gp.setGameState(gameState);
		gp.ui.setCurrentDialogue("You fell into a pit");
		int currentPlayerLife = gp.player.getLife();
		currentPlayerLife -= 2;
		gp.player.setLife(currentPlayerLife);
	}
	
	public void healingPool (GameState gameState) {
		gp.setGameState(gameState);;
		gp.ui.setCurrentDialogue("You recovered some of your strength");
		int currentPlayerLife = gp.player.getLife();
		int maxLife = gp.player.getMaxLife();
		if (currentPlayerLife < maxLife) {currentPlayerLife += 2;}
		gp.player.setLife(currentPlayerLife);
	}
	
	public void teleport (GameState gameState, int xTiles, int yTiles) {
		gp.setGameState(gameState);;
		gp.ui.setCurrentDialogue("You sailed to the nearest island");
		gp.player.WorldX = xTiles *gp.tileSize;
		gp.player.WorldY = yTiles * gp.tileSize;
	}
}
