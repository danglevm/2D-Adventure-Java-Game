package tile;

import adventureGame2D.GamePanel;
import enums.Direction;

public class Trunk extends InteractiveTile{

	public Trunk(GamePanel gp, int WorldX, int WorldY) {
		super(gp, WorldX, WorldY);
		this.WorldX = WorldX * gp.getTileSize();
		this.WorldY = WorldY * gp.getTileSize();
		down1 = setupEntity("trunk", "/tiles/interactive/", gp.getTileSize(), gp.getTileSize());
		direction = Direction.DOWN;
		collisionOn = false;
		
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 0;
		solidArea.height = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void interactTile(int index, boolean useTool) {
		// TODO Auto-generated method stub
		
	}


}
