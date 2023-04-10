package adventureGame2D;

import enums.MapsConstants;
import monster.MonsterGreenSlime;
import npc.OldDude;
import object.Boots;
import object.BronzeCoin;
import object.Heart;
import object.Key;
import object.Mana;
import object.attack.Sword;
import object.nonpickup.Chest;
import object.nonpickup.Door;
import object.tool.Axe;
import tile.DryTree;

public class AssetPlacement {
	//Class managed object placement
	GamePanel gp;
	
	
	
	public AssetPlacement (GamePanel gp) {
		this.gp = gp;
	}
	
	public final void setObject() {
		gp.getObjects().get(MapsConstants.SPAWN).clear();
		gp.getObjects().get(MapsConstants.SPAWN).add(new Door(gp, 121, 123));
		gp.getObjects().get(MapsConstants.SPAWN).add(new Boots(gp, 121, 124));
		gp.getObjects().get(MapsConstants.SPAWN).add(new Axe(gp, 121, 125));
		gp.getObjects().get(MapsConstants.SPAWN).add(new Sword(gp, 124, 126));
		gp.getObjects().get(MapsConstants.SPAWN).add(new Key(gp, 125, 126));
		gp.getObjects().get(MapsConstants.SPAWN).add(new BronzeCoin(gp, 114, 132));
		gp.getObjects().get(MapsConstants.SPAWN).add(new Heart(gp, 115, 132));
		gp.getObjects().get(MapsConstants.SPAWN).add(new Mana (gp, 116, 132));

	}
	
	public final void setNPCsSpawn() {
		gp.getNPCS().get(MapsConstants.SPAWN).clear();
		gp.getNPCS().get(0).add(new OldDude(gp, 121, 139));
	}
	
	public final void setNPCsInterior() {
		
	}
	
	public final void setMonsters() {
		gp.getMonsters().get(MapsConstants.SPAWN).clear();
		gp.getMonsters().get(MapsConstants.SPAWN).add(new MonsterGreenSlime (gp, 126, 136));
		gp.getMonsters().get(MapsConstants.SPAWN).add(new MonsterGreenSlime (gp, 125, 120));
		gp.getMonsters().get(MapsConstants.SPAWN).add(new MonsterGreenSlime (gp, 124, 121));
		gp.getMonsters().get(MapsConstants.SPAWN).add(new MonsterGreenSlime (gp, 127, 122));
		gp.getMonsters().get(MapsConstants.SPAWN).add(new MonsterGreenSlime (gp, 127, 123));
		gp.getMonsters().get(MapsConstants.SPAWN).add(new MonsterGreenSlime (gp, 127, 124));
	}
	
	final void setInteractiveTiles() {
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).clear();
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree (gp, 140, 130));
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree (gp, 137, 127));
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree (gp, 136, 126));
//		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree (gp, 135, 127));
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree (gp, 136, 127));
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree (gp, 137, 126));
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree (gp, 138, 128));
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree (gp, 138, 127));
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree (gp, 138, 129));
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree (gp, 139, 128));
		gp.getInteractiveTiles().get(MapsConstants.SPAWN).add(new DryTree(gp, 121, 140));
		
	}
	
}
