package adventureGame2D;

import object.Obj_boots;
import object.Obj_chest;
import object.Obj_door;
import object.Obj_key;

public class AssetPlacement {
	//Class managed object placement
	GamePanel gp;
	
	public AssetPlacement (GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new Obj_key();
		gp.obj[0].worldX = 23 *gp.tileSize;
		gp.obj[0].worldY = 7*gp.tileSize;
		
		gp.obj[1] = new Obj_key();
		gp.obj[1].worldX = 23*gp.tileSize;
		gp.obj[1].worldY = 40 *gp.tileSize;
		
		gp.obj[2] = new Obj_door();
		gp.obj[2].worldX = 11*gp.tileSize;
		gp.obj[2].worldY = 35 *gp.tileSize;
		
		gp.obj[3] = new Obj_chest();
		gp.obj[3].worldX = 13*gp.tileSize;
		gp.obj[3].worldY = 37 *gp.tileSize;
		
		gp.obj[4] = new Obj_boots();
		gp.obj[4].worldX = 15*gp.tileSize;
		gp.obj[4].worldY = 12*gp.tileSize;
	}
}
