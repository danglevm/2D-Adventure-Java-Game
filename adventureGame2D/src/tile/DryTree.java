package tile;

import adventureGame2D.GamePanel;
import enums.Direction;

public class DryTree extends InteractiveTile {

	public DryTree(GamePanel gp, int WorldX, int WorldY) {
		super(gp, WorldX, WorldY);
		down1 = setupEntity("drytree", "/tiles/interactive/", gp.getTileSize(), gp.getTileSize());
		destructible = true;
		collisionOn = true;
		this.WorldX = WorldX * gp.getTileSize();
		this.WorldY = WorldY * gp.getTileSize();
		direction = Direction.DOWN;
	}

	@Override
	public void updateInteractiveTile() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactTile(int index) {
		if (index != 9999 && this.destructible) {
			gp.getInteractiveTiles().set(index, null);
		}
	}

}
