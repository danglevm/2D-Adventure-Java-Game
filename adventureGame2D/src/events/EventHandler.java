package events;

import java.awt.Rectangle;

import adventureGame2D.GamePanel;

public class EventHandler {
	
	
	GamePanel gp;
	Event eventObject;
	EventRectangle eventRect[][];
	
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
			eventRect[col][row].width = 23;
			eventRect[col][row].height = 23;
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
		
		//damage player
		if (eventCollision(pitX, pitY, "any")) {
			eventObject.DamagePit(gp.dialogueState);
			eventRect[pitX][pitY].eventTriggered = true;
		}
		
		//heal player
		if (eventCollision(healX, healY, "any")) {
			eventObject.healingPool(gp.dialogueState);
			eventRect[healX][healY].eventTriggered = true;
		}
	}
	
	public boolean eventCollision (int col, int row, String reqDirection)
		{
			boolean playerCollision = false;
			
			//get location of player and event rectangle solid area
			gp.player.solidArea.x = gp.player.WorldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.WorldY + gp.player.solidArea.y;
			eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
			eventRect [col][row].y = row * gp.tileSize + eventRect[col][row].y;
			
			
			//if player hits the event and the event has not been triggered
			if (gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventTriggered) {
				if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					playerCollision = true;
				}
			}
			
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[col][row].x = eventRect[col][row].eventDefaultX;
			eventRect[col][row].y = eventRect[col][row].eventDefaultY;
		
			return playerCollision;
		}

}
