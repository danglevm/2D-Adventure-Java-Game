package adventureGame2D;

import java.awt.Rectangle;

public class EventHandler {
	
	
	GamePanel gp;
	Event eventObject;
	Rectangle eventRectangle;
	protected int eventRectangleDefaultX, eventRectangleDefaultY;
	
	public EventHandler (GamePanel gp) {
		this.gp = gp;
		eventObject = new Event (gp);
		//x, y, width, height
		eventRectangle = new Rectangle(23, 23, 2, 2);
		eventRectangleDefaultX = eventRectangle.x;
		eventRectangleDefaultY = eventRectangle.y;
		
	}
	

	
	public void checkEvent() {
		
		//damage player
		if (eventCollision(121,122, "any")) {
			eventObject.DamagePit(gp.dialogueState);
		}
		
		//heal player
		if (eventCollision(115,121, "any")) {
			eventObject.healingPool(gp.dialogueState);
		}
	}
	
	public boolean eventCollision (int eventCol, int eventRow, String reqDirection)
		{
			boolean playerCollision = false;
			
			gp.player.solidArea.x = gp.player.WorldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.WorldY + gp.player.solidArea.y;
			eventRectangle.x = eventCol * gp.tileSize + eventRectangle.x;
			eventRectangle.y = eventRow * gp.tileSize + eventRectangle.y;
			
			if (gp.player.solidArea.intersects(eventRectangle)) {
				if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					playerCollision = true;
				}
			}
			
			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRectangle.x = eventRectangleDefaultX;
			eventRectangle.y = eventRectangleDefaultY;
			
			return playerCollision;
		}

}
