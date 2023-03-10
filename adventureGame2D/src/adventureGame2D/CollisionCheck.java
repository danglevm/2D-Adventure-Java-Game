package adventureGame2D;

import java.util.ArrayList;

import entity.Entity;


public class CollisionCheck{

	GamePanel gp;
	
	public CollisionCheck(GamePanel gp) {
		this.gp = gp;
	}
	
	public void CheckTile (Entity entity) {
		//Find the coordinates of the player's solidArea collision area WorldX and Y
		
		
		//left side of the solid area - left side of the Entity tile + the entity's X distance from that left side of the tile
		int entityLeftWorldX = entity.getWorldX() + entity.getSolidArea().x;
		
		//right side of the solid area - left side of the Entity tile + the entity's X distance from that left side of the tile + entity width gives the right side
		int entityRightWorldX = entity.getWorldX() + entity.getSolidArea().x + entity.getSolidArea().width;
		
		
		//Starts from the top side of the tile
		int entityTopWorldY = entity.getWorldY() + entity.getSolidArea().y;
		
		int entityBotWorldY = entity.getWorldY() + entity.getSolidArea().y + entity.getSolidArea().height;
		
		
		//Finds the columns and rows of these locations in tiles
		int entityLeftCol = entityLeftWorldX/gp.getTileSize();
		int entityRightCol = entityRightWorldX/gp.getTileSize();
		int entityTopRow = entityTopWorldY/gp.getTileSize();
		int entityBotRow = entityBotWorldY/gp.getTileSize();
		
		int tileNum1, tileNum2;
		
		switch (entity.getDirection()) {
		case UP:
			//Predict to see where the player will end up next - blocks up ahead
			//Check the left and right corner of the entity to see if it will collide
			entityTopRow = (entityTopWorldY - entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tilesList.get(tileNum1).collision||gp.tileM.tilesList.get(tileNum2).collision) {
				entity.setCollisionOn(true);
			}
			break;
		case DOWN:
			entityBotRow = (entityBotWorldY + entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBotRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBotRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tilesList.get(tileNum1).collision||gp.tileM.tilesList.get(tileNum2).collision) {
				entity.setCollisionOn(true);
			}
			break;
		case LEFT:
			entityLeftCol = (entityLeftWorldX - entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBotRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tilesList.get(tileNum1).collision||gp.tileM.tilesList.get(tileNum2).collision) {
				entity.setCollisionOn(true);
			}
			break;
		case RIGHT:
			entityRightCol = (entityRightWorldX + entity.getSpeed())/gp.getTileSize();
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBotRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tilesList.get(tileNum1).collision||gp.tileM.tilesList.get(tileNum2).collision) {
				entity.setCollisionOn(true);
			}
			break;
		default:
			break;
		}
	
	}
	
	public final int checkObject(Entity entity, boolean player) {
		
		//If player is colliding with any object. If player is, returns index of the object
		int index = 9999;
		
		for (int i = 0; i < gp.objects.size(); i++) {
			
			if (gp.objects.get(i) != null) {
				
				//Get entity's solid area position
				//Location of entity + entity solid area size - 0 is default, can be changed later
				entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
				entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;
				
				//Get object's solid area position
				gp.objects.get(i).getSolidArea().x = gp.objects.get(i).getWorldX() + gp.objects.get(i).getSolidArea().x;
				gp.objects.get(i).getSolidArea().y = gp.objects.get(i).getWorldY() + gp.objects.get(i).getSolidArea().y;
				
				//NPC cannot pick up objects
				switch (entity.getDirection()) {
				case UP:
					entity.getSolidArea().y -= entity.getSpeed();
					break;
					
				case DOWN:
					entity.getSolidArea().y += entity.getSpeed();
					break;
				case LEFT:
					entity.getSolidArea().x -= entity.getSpeed();
					break;
					
				case RIGHT:
					entity.getSolidArea().x += entity.getSpeed();
					break;
				default:
					break;
				}
				
				//entity rectangle intersects with object rectangle
				if (entity.getSolidArea().intersects(gp.objects.get(i).getSolidArea())) {
					entity.setCollisionOn(true);
				};
				if (player) {
					index = i;
				}
				//reset entity's solid Area or else the values increase indefinitely
				entity.getSolidArea().x = entity.getSolidAreaDefaultX();
				entity.getSolidArea().y = entity.getSolidAreaDefaultY();
				gp.objects.get(i).getSolidArea().x = gp.objects.get(i).getSolidAreaDefaultX();
				gp.objects.get(i).getSolidArea().y = gp.objects.get(i).getSolidAreaDefaultY();
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
						entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
						entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;
							
						//Get target's solid area position
						entities.get(i).getSolidArea().x = entities.get(i).getWorldX() + entities.get(i).getSolidArea().x;
						entities.get(i).getSolidArea().y = entities.get(i).getWorldY() + entities.get(i).getSolidArea().y;						
						
						switch (entity.getDirection()) {
						case UP:
							entity.getSolidArea().y -= entity.getSpeed();
							break;
						case DOWN:
							entity.getSolidArea().y += entity.getSpeed();
							break;
						case LEFT:
							entity.getSolidArea().x -= entity.getSpeed();
							break;
						case RIGHT:
							entity.getSolidArea().x += entity.getSpeed();
							break;
						default:
							break;
						}
						//reset entity's solid Area or else the values increase indefinitely
						if (entity.getSolidArea().intersects(entities.get(i).getSolidArea())) {
							if (entities.get(i) != entity) {
								entity.setCollisionOn(true);
								index = i;
							}
						};
						entity.getSolidArea().x = entity.getSolidAreaDefaultX();
						entity.getSolidArea().y = entity.getSolidAreaDefaultY();
						entities.get(i).getSolidArea().x = entities.get(i).getSolidAreaDefaultX();
						entities.get(i).getSolidArea().y = entities.get(i).getSolidAreaDefaultY();
					}
				}
				
				return index;
		
	}
	
	public final boolean checkPlayer (Entity entity) {
		//Get entity's solid area position
		//Location of entity + entity solid area size - 0 is default, can be changed later
		entity.getSolidArea().x = entity.getWorldX() + entity.getSolidArea().x;
		entity.getSolidArea().y = entity.getWorldY() + entity.getSolidArea().y;
		
		//Get target's solid area position
		gp.player.getSolidArea().x = gp.player.getWorldX() + gp.player.getSolidArea().x;
		gp.player.getSolidArea().y = gp.player.getWorldY() + gp.player.getSolidArea().y;
		
		
		switch (entity.getDirection()) {
		case UP:
			entity.getSolidArea().y -= entity.getSpeed();
			break;
		case DOWN:
			entity.getSolidArea().y += entity.getSpeed();
			break;
		case LEFT:
			entity.getSolidArea().x -= entity.getSpeed();
			break;
		case RIGHT:
			entity.getSolidArea().x += entity.getSpeed();
			break;
		default:
			break;
		}
		//entity rectangle intersects with target's rectangle
		if (entity.getSolidArea().intersects(gp.player.getSolidArea())) {
			entity.setCollisionOn(true);
			return true;
		}
		//reset entity's solid Area or else the values increase indefinitely
		entity.getSolidArea().x = entity.getSolidAreaDefaultX();
		entity.getSolidArea().y = entity.getSolidAreaDefaultY();
		gp.player.getSolidArea().x = gp.player.getSolidAreaDefaultX();
		gp.player.getSolidArea().y = gp.player.getSolidAreaDefaultY();
		return false;
	}
}

