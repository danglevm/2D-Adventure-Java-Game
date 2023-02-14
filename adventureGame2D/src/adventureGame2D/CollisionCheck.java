package adventureGame2D;

import java.util.ArrayList;

import entity.Entity;
import tile.Tile;

public class CollisionCheck{

	GamePanel gp;
	
	public CollisionCheck(GamePanel gp) {
		this.gp = gp;
	}
	
	public void CheckTile (Entity entity) {
		//Find the coordinates of the player's solidArea collision area WorldX and Y
		
		
		//left side of the solid area - left side of the Entity tile + the entity's X distance from that left side of the tile
		int entityLeftWorldX = entity.WorldX + entity.solidArea.x;
		
		//right side of the solid area - left side of the Entity tile + the entity's X distance from that left side of the tile + entity width gives the right side
		int entityRightWorldX = entity.WorldX + entity.solidArea.x + entity.solidArea.width;
		
		
		//Starts from the top side of the tile
		int entityTopWorldY = entity.WorldY + entity.solidArea.y;
		
		int entityBotWorldY = entity.WorldY + entity.solidArea.y + entity.solidArea.height;
		
		
		//Finds the columns and rows of these locations in tiles
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBotRow = entityBotWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		switch (entity.direction) {
		case "up":
			//Predict to see where the player will end up next - blocks up ahead
			//Check the left and right corner of the entity to see if it will collide
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tilesList.get(tileNum1).collision||gp.tileM.tilesList.get(tileNum2).collision) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBotRow = (entityBotWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBotRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBotRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tilesList.get(tileNum1).collision||gp.tileM.tilesList.get(tileNum2).collision) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBotRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tilesList.get(tileNum1).collision||gp.tileM.tilesList.get(tileNum2).collision) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBotRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tilesList.get(tileNum1).collision||gp.tileM.tilesList.get(tileNum2).collision) {
				entity.collisionOn = true;
			}
			break;
		}
	
	}
	
	private final boolean checkObjectCollision (Entity entity, Entity other) {
		if (entity.solidArea.intersects(other.solidArea)) {
			if (other.collisionOn) {
				return true;
			}
		}
		return false;
	}
	
	public final int checkObject(Entity entity, boolean player) {
		
		//If player is colliding with any object. If player is, returns index of the object
		int index = 9999;
		
		for (int i = 0; i < gp.objects.size(); i++) {
			
			if (gp.objects.get(i) != null) {
				
				//Get entity's solid area position
				//Location of entity + entity solid area size - 0 is default, can be changed later
				entity.solidArea.x = entity.WorldX + entity.solidArea.x;
				entity.solidArea.y = entity.WorldY + entity.solidArea.y;
				
				//Get object's solid area position
				gp.objects.get(i).solidArea.x = gp.objects.get(i).WorldX + gp.objects.get(i).solidArea.x;
				gp.objects.get(i).solidArea.y = gp.objects.get(i).WorldY + gp.objects.get(i).solidArea.y;
				
				//NPC cannot pick up objects
				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					break;
					
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
					
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}
				
				//entity rectangle intersects with object rectangle
				if (this.checkObjectCollision(entity, gp.objects.get(i))) {
					entity.collisionOn = true;
				};
				if (player) {
					index = i;
				}
				//reset entity's solid Area or else the values increase indefinitely
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.objects.get(i).solidArea.x = gp.objects.get(i).solidAreaDefaultX;
				gp.objects.get(i).solidArea.y = gp.objects.get(i).solidAreaDefaultY;
			}
		}
		
		return index;
	}
	
	//NPCs and monster collision
	public final int checkEntity (Entity entity, ArrayList <Entity> entities) {
		//If player is colliding with a NPC. If player is, returns index of the object
				int index = 9999;
				
				for (int i = 0; i < entities.size(); i++) {
					
					if (entities.get(i) != null) {
						
						//Get entity's solid area position
						//Location of entity + entity solid area size - 0 is default, can be changed later
						entity.solidArea.x = entity.WorldX + entity.solidArea.x;
						entity.solidArea.y = entity.WorldY + entity.solidArea.y;
						
						
						
						//Get target's solid area position
						entities.get(i).solidArea.x = entities.get(i).WorldX + entities.get(i).solidArea.x;
						entities.get(i).solidArea.y = entities.get(i).WorldY + entities.get(i).solidArea.y;
						
						
						switch (entity.direction) {
						case "up":
							entity.solidArea.y -= entity.speed;
							break;
						case "down":
							entity.solidArea.y += entity.speed;
							break;
						case "left":
							entity.solidArea.x -= entity.speed;
							break;
						case "right":
							entity.solidArea.x += entity.speed;
							break;
						}
						//reset entity's solid Area or else the values increase indefinitely
						if (this.checkObjectCollision(entity, entities.get(i))) {
							if (entities.get(i) != entity) {
								entity.collisionOn = true;
								index = i;
							}
						};
						entity.solidArea.x = entity.solidAreaDefaultX;
						entity.solidArea.y = entity.solidAreaDefaultY;
						entities.get(i).solidArea.x = entities.get(i).solidAreaDefaultX;
						entities.get(i).solidArea.y = entities.get(i).solidAreaDefaultY;
					}
				}
				
				return index;
		
	}
	
	public final boolean checkPlayer (Entity entity) {
		//Get entity's solid area position
		//Location of entity + entity solid area size - 0 is default, can be changed later
		entity.solidArea.x = entity.WorldX + entity.solidArea.x;
		entity.solidArea.y = entity.WorldY + entity.solidArea.y;
		
		
		
		//Get target's solid area position
		gp.player.solidArea.x = gp.player.WorldX+gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.WorldY+gp.player.solidArea.y;
		
		
		switch (entity.direction) {
		case "up":
			entity.solidArea.y -= entity.speed;
			break;
		case "down":
			entity.solidArea.y += entity.speed;
			break;
		case "left":
			entity.solidArea.x -= entity.speed;
			break;
		case "right":
			entity.solidArea.x += entity.speed;
			break;
		}
		//entity rectangle intersects with target's rectangle
		if (entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			return true;
		}
		//reset entity's solid Area or else the values increase indefinitely
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		return false;
	}
}

