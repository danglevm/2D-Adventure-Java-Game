package adventureGame2D;

import entity.Entity;

public class CollisionCheck {

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
			if (gp.tileM.tile[tileNum1].collision==true||gp.tileM.tile[tileNum2].collision==true) {
				entity.collisionOn=true;
			}
			break;
		case "down":
			entityBotRow = (entityBotWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBotRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBotRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tile[tileNum1].collision==true||gp.tileM.tile[tileNum2].collision==true) {
				entity.collisionOn=true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBotRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tile[tileNum1].collision==true||gp.tileM.tile[tileNum2].collision==true) {
				entity.collisionOn=true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBotRow];
			
			//Check the type of tile and see whether it has collision on
			if (gp.tileM.tile[tileNum1].collision==true||gp.tileM.tile[tileNum2].collision==true) {
				entity.collisionOn=true;
			}
			break;
		}
	
	}
}

