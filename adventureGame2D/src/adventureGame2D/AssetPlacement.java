package adventureGame2D;

import entity.OldDudeNPC;
import monster.MonsterGreenSlime;
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
		gp.objects.add(new ObjectKey(gp, 121, 140));
		gp.objects.add(new ObjectDoor(gp, 121, 123));
		gp.objects.add(new ObjectBoots(gp, 121, 124));

	}
	
	public void setNPCs() {
		gp.NPCs.add(new OldDudeNPC(gp, 121, 139));
	}
	
	public void setMonsters() {
		gp.monsters.add(new MonsterGreenSlime (gp, 126, 136));
		gp.monsters.add(new MonsterGreenSlime (gp, 110, 120));
	}
	
}
