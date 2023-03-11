package adventureGame2D;

import monster.MonsterGreenSlime;
import npc.OldDudeNPC;
import object.ObjectBoots;
import object.ObjectChest;
import object.ObjectDoor;
import object.ObjectKey;

public class AssetPlacement {
	//Class managed object placement
	GamePanel gp;
	
	public AssetPlacement (GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		gp.getObjects().add(new ObjectKey(gp, 121, 140));
		gp.getObjects().add(new ObjectDoor(gp, 121, 123));
		gp.getObjects().add(new ObjectBoots(gp, 121, 124));

	}
	
	public void setNPCs() {
		gp.getNPCS().add(new OldDudeNPC(gp, 121, 139));
	}
	
	public void setMonsters() {
		gp.getMonsters().add(new MonsterGreenSlime (gp, 126, 136));
		gp.getMonsters().add(new MonsterGreenSlime (gp, 110, 120));
	}
	
}
