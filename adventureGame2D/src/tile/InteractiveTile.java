package tile;

import adventureGame2D.GamePanel;
import entity.Entity;

public abstract class InteractiveTile extends Entity {
	
	protected GamePanel gp;
	
	protected boolean destructible = false;
	
	public InteractiveTile(GamePanel gp, int WorldX, int WorldY) {
		super(gp);
		this.gp = gp;
	}
	@Override
	public abstract void update();
	
	public abstract void interactTile(int index, boolean useTool);

	
}
