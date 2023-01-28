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
		
		gp.obj[0] = new Obj_key(gp);
		gp.obj[0].worldX = 115 *gp.tileSize;
		gp.obj[0].worldY = 145*gp.tileSize;
		
		
		gp.obj[2] = new Obj_door(gp);
		gp.obj[2].worldX = 121*gp.tileSize;
		gp.obj[2].worldY = 123 *gp.tileSize;
		
		gp.obj[3] = new Obj_chest(gp);
		gp.obj[3].worldX = 121*gp.tileSize;
		gp.obj[3].worldY = 122 *gp.tileSize;
		
		gp.obj[4] = new Obj_boots(gp);
		gp.obj[4].worldX = 137*gp.tileSize;
		gp.obj[4].worldY = 122*gp.tileSize;
	}
	
}
