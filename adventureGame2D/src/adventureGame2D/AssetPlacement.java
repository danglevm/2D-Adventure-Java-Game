package adventureGame2D;

import entity.NPC_OldDude;
import monster.Monster_Green_Slime;
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
		//121, 123
		gp.objects.add(new Obj_key(gp, 126, 123));
		gp.objects.add(new Obj_door(gp, 121, 123));
	}
	
	public void setNPCs() {
		gp.NPCs.add(new NPC_OldDude(gp, 121, 136));
	}
	
	public void setMonsters() {
		gp.monsters.add(new Monster_Green_Slime (gp, 126, 136));
		gp.monsters.add(new Monster_Green_Slime (gp, 110, 120));
	}
	
}
