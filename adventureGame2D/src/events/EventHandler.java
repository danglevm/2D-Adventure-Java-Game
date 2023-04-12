package events;

import adventureGame2D.GamePanel;
import enums_and_constants.Direction;
import enums_and_constants.GameState;
import enums_and_constants.MapsConstants;
import enums_and_constants.TradeState;
import npc.NPC;

public class EventHandler {
	
	
	GamePanel gp;
	Event eventObject;
	EventRectangle[][][] eventRect;
	private int previousEventX, previousEventY;
	protected boolean touchEvent = true;
	
	private final int pitX = 121, pitY = 122; 
	private final int healX = 110, healY = 128;
	private final int tradeX = 134, tradeY = 125, tradeBackX = 134, tradeBackY = 126;
	private final int tpXto = 132, tpYto = 152, tpXback = 153, tpYback = 150;
	

	public EventHandler (GamePanel gp) {
		this.gp = gp;
		int map = 0;
		int col = 0, row = 0;
		eventRect = new EventRectangle [gp.MAX_MAP][gp.getMaxWorldCol()][gp.getMaxWorldRow()];
		
		while (map < gp.MAX_MAP && col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
			
			//Initializes every tile to be a potential event tile
			
			eventObject = new Event (gp);
			//x, y, width, height
			eventRect[map][col][row] = new EventRectangle();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventDefaultY = eventRect[map][col][row].y;
			
			++col;
			if (col == gp.getMaxWorldCol()) {
				col = 0;
				row ++;
				
				if (row == gp.getMaxWorldRow()) {
					row = 0;
					map++;
				}
			}
		}
		
	}
	

	public void checkEvent() {
	
		
		//Check to see if player is 1 tile away from previous event  - works for lava or some damage things
		int xDistance = Math.abs(gp.getPlayer().getWorldX() - previousEventX),
			yDistance = Math.abs(gp.getPlayer().getWorldX() - previousEventY),
			distance = Math.max(xDistance, yDistance);
		
		if (distance > gp.getTileSize()) { this.touchEvent = true; }
		
		if (this.touchEvent) {
		//damage player
			if (eventCollision(MapsConstants.SPAWN, pitX, pitY, Direction.ANY)) {
				eventObject.DamagePit(GameState.DIALOGUE);
				eventRect[MapsConstants.SPAWN][pitX][pitY].eventTriggered = true;
			}
		
			//heal player
			if (eventCollision(MapsConstants.SPAWN, healX, healY, Direction.ANY)) {
				eventObject.healingPool(GameState.DIALOGUE);
				eventRect[MapsConstants.SPAWN][healX][healY].eventTriggered = true;
			}
		
			//Handles all cases when player chooses to interact with an event
			//should work in most cases.... hopefully.... because it determines where the player is before triggering the correct event
		
			
			//Boat teleport 
			if (eventCollision(MapsConstants.SPAWN, tpXto, tpYto, Direction.ANY)) {
				eventObject.teleport(GameState.DIALOGUE, tpXback - 1, tpYback + 1);
//				gp.getKeyHandler().setInteraction(false);
			}
			
			
			if (eventCollision (MapsConstants.SPAWN, tpXback, tpYback, Direction.ANY)) {
				eventObject.teleport(GameState.DIALOGUE, tpXto, tpYto - 1);
			} 
			
			//go into trading hut
			if (eventCollision(MapsConstants.SPAWN, tradeX, tradeY, Direction.ANY)) {
				eventObject.mapTransition(GameState.TRANSITION, MapsConstants.TRADE_SPAWN_X, MapsConstants.TRADE_SPAWN_Y, MapsConstants.TRADE);
				gp.playSE(19);
			}
			
			//exit trading hut
			if (eventCollision(MapsConstants.TRADE, MapsConstants.TRADE_BACK_X, MapsConstants.TRADE_BACK_Y, Direction.ANY)) {
				eventObject.mapTransition(GameState.TRANSITION, tradeBackX, tradeBackY, MapsConstants.SPAWN);
				gp.playSE(19);
			}
			
			//talk to merchant npc
			if (eventCollision(MapsConstants.TRADE, MapsConstants.TRADE_PLATFORM_X, MapsConstants.TRADE_PLATFORM_Y, Direction.UP)) {
				((NPC) gp.getNPCS().get(MapsConstants.TRADE).get(0)).speak();
				gp.setGameState(GameState.TRADE);
				gp.getGameUI().setTradeState(TradeState.SELECT);
				touchEvent = false;
			}
			
		}
		
	}
	
	public boolean eventCollision (int map, int col, int row, Direction reqDirection)
		{
			boolean playerCollision = false;
			
			if (map == gp.currentMap) {
				//get location of player and event rectangle solid area
				gp.getPlayer().getSolidArea().x = gp.getPlayer().getWorldX() + gp.getPlayer().getSolidArea().x;
				gp.getPlayer().getSolidArea().y = gp.getPlayer().getWorldY() + gp.getPlayer().getSolidArea().y;
				eventRect[map][col][row].x = col * gp.getTileSize() + eventRect[map][col][row].x;
				eventRect[map][col][row].y = row * gp.getTileSize() + eventRect[map][col][row].y;
				
				
				//if player hits the event and the event has not been triggered
				if (gp.getPlayer().getSolidArea().intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventTriggered) {
					if (gp.getPlayer().getDirection() == reqDirection || reqDirection == Direction.ANY) {
						playerCollision = true;
						previousEventX = gp.getPlayer().getWorldX();
						previousEventY = gp.getPlayer().getWorldY();
					}
				}
				
				gp.getPlayer().getSolidArea().x = gp.getPlayer().getSolidAreaDefaultX();
				gp.getPlayer().getSolidArea().y = gp.getPlayer().getSolidAreaDefaultY();
				eventRect[map][col][row].x = eventRect[map][col][row].eventDefaultX;
				eventRect[map][col][row].y = eventRect[map][col][row].eventDefaultY;
			}
			

		
			return playerCollision;
		}

	public Event getEventObject () { return eventObject; }
	
	public final void setPreviousEventX (int previousEventX) { this.previousEventX = previousEventX;}
	
	public final void setPreviousEventY (int previousEventY) { this.previousEventY = previousEventY;}
	
	
}
