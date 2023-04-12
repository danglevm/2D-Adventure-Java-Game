package events;

import adventureGame2D.GamePanel;
import enums_and_constants.GameState;

public class Event {
	
	GamePanel gp;
	
	private int tempMap, tempCol, tempRow;

	public Event(GamePanel gp) {
		this.gp = gp ;
	}
	
	public void DamagePit(GameState gameState) {
		gp.setGameState(gameState);
		gp.getGameUI().setCurrentDialogue("You fell into a pit");
		gp.getPlayer().setLife(gp.getPlayer().getLife() - 2);
		gp.getEventHandler().touchEvent = false;
	}
	
	public void healingPool (GameState gameState) {
		gp.setGameState(gameState);
		gp.getGameUI().setCurrentDialogue("You recovered some of your strength");
		if (gp.getPlayer().getLife() < gp.getPlayer().getMaxLife()) {gp.getPlayer().setLife(gp.getPlayer().getLife() + 2);}
		gp.getEventHandler().touchEvent = false;
	}
	
	public void teleport (GameState gameState, int xTiles, int yTiles) {
		gp.setGameState(gameState);
		gp.getGameUI().setCurrentDialogue("You sailed to the nearest island");
		gp.getPlayer().setWorldX(xTiles * gp.getTileSize());
		gp.getPlayer().setWorldY(yTiles * gp.getTileSize()); 
		gp.getEventHandler().touchEvent = false;
	}
	
	public void mapTransition (GameState gameState, int xTiles, int yTiles, int transitionMap) {
		tempMap = transitionMap;
		tempRow = xTiles;
		tempCol = yTiles;
		gp.setGameState(gameState);
		gp.getEventHandler().touchEvent = false;
	}
	
	
	public final void transitionBackEffect () {
		gp.setGameState(GameState.PLAY);
		gp.currentMap = this.tempMap;
		gp.getPlayer().setWorldX(this.tempRow * gp.getTileSize());
		gp.getPlayer().setWorldY(this.tempCol * gp.getTileSize());
		gp.getEventHandler().setPreviousEventX(gp.getPlayer().getWorldX());
		gp.getEventHandler().setPreviousEventY(gp.getPlayer().getWorldY());
		gp.getEventHandler().touchEvent = false;
	}
}
