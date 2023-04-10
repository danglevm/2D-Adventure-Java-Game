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
		gp.getGameUI().setCurrentDialogue("You fell into a pit");
		gp.getPlayer().setLife(gp.getPlayer().getLife() - 2);
	}
	
	public void healingPool (GameState gameState) {
		gp.setGameState(gameState);
		gp.getGameUI().setCurrentDialogue("You recovered some of your strength");
		if (gp.getPlayer().getLife() < gp.getPlayer().getMaxLife()) {gp.getPlayer().setLife(gp.getPlayer().getLife() + 2);}
	}
	
	public void teleport (GameState gameState, int xTiles, int yTiles) {
		gp.setGameState(gameState);
		gp.getGameUI().setCurrentDialogue("You sailed to the nearest island");
		gp.getPlayer().setWorldX(xTiles * gp.getTileSize());
		gp.getPlayer().setWorldY(yTiles * gp.getTileSize()); 
	}
	
	public void mapTransition (GameState gameState, int xTiles, int yTiles, int transitionMap) {
		gp.currentMap = transitionMap;
		gp.setGameState(gameState);
		gp.getPlayer().setWorldX(xTiles * gp.getTileSize());
		gp.getPlayer().setWorldY(yTiles * gp.getTileSize()); 
	}
}
