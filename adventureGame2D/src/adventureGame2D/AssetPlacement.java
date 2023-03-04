package adventureGame2D;

import entity.NPC_OldDude;
import monster.MonsterGreenSlime;
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
		gp.objects.add(new Obj_key(gp, 121, 140));
		gp.objects.add(new Obj_door(gp, 121, 123));
		gp.objects.add(new Obj_boots(gp, 121, 124));

	}
	
	public void setNPCs() {
		gp.NPCs.add(new NPC_OldDude(gp, 121, 139));
	}
	
	public void setMonsters() {
		gp.monsters.add(new MonsterGreenSlime (gp, 126, 136));
		gp.monsters.add(new MonsterGreenSlime (gp, 110, 120));
	}
	
}
