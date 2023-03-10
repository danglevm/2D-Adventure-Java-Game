package events;

import adventureGame2D.GamePanel;
import enums.Direction;
import enums.GameState;

public class EventHandler {
	
	
	GamePanel gp;
	Event eventObject;
	EventRectangle eventRect[][];
	int previousEventX, previousEventY;
	boolean touchEvent = true, interact = false;
	
	//return interaction state
	public boolean getInteraction() {
		return interact;
	}
	
	public EventHandler (GamePanel gp) {
		this.gp = gp;
		int col = 0, row = 0;
		eventRect = new EventRectangle [gp.maxWorldCol][gp.maxWorldRow];
		
		while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
			
			//Initializes every tile to be a potential event tile
			
			eventObject = new Event (gp);
			//x, y, width, height
			eventRect[col][row] = new EventRectangle();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].width = 2;
			eventRect[col][row].height = 2;
			eventRect[col][row].eventDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventDefaultY = eventRect[col][row].y;
			
			++col;
			if (col == gp.maxWorldCol) {
				col = 0;
				row ++;
			}
		}
		
	}
	

	public void checkEvent() {
		int pitX = 121, pitY = 122; 
		int healX = 115, healY = 121;
		int tpXto = 132, tpYto = 152;
		int tpXback = 153, tpYback = 150;
		
		//Check to see if player is 1 tile away from previous event  - works for lava or some damage things
		int xDistance = Math.abs(gp.player.getWorldX() - previousEventX),
			yDistance = Math.abs(gp.player.getWorldX() - previousEventY),
			distance = Math.max(xDistance, yDistance);
		
		
		//damage player
			if (eventCollision(pitX, pitY, Direction.ANY)) {
				eventObject.DamagePit(GameState.DIALOGUE);
				eventRect[pitX][pitY].eventTriggered = true;
			}
		
			//heal player
			if (eventCollision(healX, healY, Direction.ANY)) {
				eventObject.healingPool(GameState.DIALOGUE);
				eventRect[healX][healY].eventTriggered = true;
			}
		
			//Handles all cases when player chooses to interact with an event
			//should work in most cases.... hopefully.... because it determines where the player is before triggering the correct event
		
			
			//Boat teleport 
			if (eventCollision(tpXto, tpYto, Direction.ANY)) {
				//interact is true --> UI draws the string
				interact = true;
				if (gp.keyH.allowInteraction) {
					eventObject.teleport(GameState.DIALOGUE, tpXback, tpYback);
					gp.keyH.allowInteraction = false;
				}
			} else if (distance > gp.getTileSize()) {
				interact = false;
			}
			
			
			if (eventCollision (tpXback, tpYback, Direction.ANY)) {
				interact = true;
				if (gp.keyH.allowInteraction) {
					eventObject.teleport(GameState.DIALOGUE, tpXto, tpYto);
					gp.keyH.allowInteraction = false;
				}
			} else if (distance > gp.getTileSize()) {
				interact = false;
			}
			
			
		
	}
	
	public boolean eventCollision (int col, int row, Direction reqDirection)
		{
			boolean playerCollision = false;
			
			//get location of player and event rectangle solid area
			gp.player.getSolidArea().x = gp.player.getWorldX() + gp.player.getSolidArea().x;
			gp.player.getSolidArea().y = gp.player.getWorldY() + gp.player.getSolidArea().y;
			eventRect[col][row].x = col * gp.getTileSize() + eventRect[col][row].x;
			eventRect [col][row].y = row * gp.getTileSize() + eventRect[col][row].y;
			
			
			//if player hits the event and the event has not been triggered
			if (gp.player.getSolidArea().intersects(eventRect[col][row]) && !eventRect[col][row].eventTriggered) {
				if (gp.player.getDirection() == reqDirection || reqDirection == Direction.ANY) {
					playerCollision = true;
					
					previousEventX = gp.player.getWorldX();
					previousEventY = gp.player.getWorldY();
				}
			}
			
			gp.player.getSolidArea().x = gp.player.getSolidAreaDefaultX();
			gp.player.getSolidArea().y = gp.player.getSolidAreaDefaultY();
			eventRect[col][row].x = eventRect[col][row].eventDefaultX;
			eventRect[col][row].y = eventRect[col][row].eventDefaultY;
		
			return playerCollision;
		}

}
