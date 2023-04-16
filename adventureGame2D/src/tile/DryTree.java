package tile;

import java.awt.Color;

import adventureGame2D.GamePanel;
import adventureGame2D.Sound;
import enums_and_constants.Direction;
import enums_and_constants.ToolType;
import object.interfaces.ToolObjectInterface;

public class DryTree extends InteractiveTile {
	
	
	public DryTree(GamePanel gp, int WorldX, int WorldY) {
		super(gp, WorldX, WorldY);
		down1 = setupEntity("drytree", "/tiles/interactive/", gp.getTileSize(), gp.getTileSize());
		destructible = true;
		collisionOn = true;
		life = 3;
		this.WorldX = WorldX * gp.getTileSize();
		this.WorldY = WorldY * gp.getTileSize();
		direction = Direction.DOWN;
		this.pColor = new Color (65, 60, 30);
		this.pSize = 6;
		this.pSpeed = 1;
		this.pDuration = 18;
	}

	public void update() {
		// TODO Auto-generated method stub
		if (invincibility) {
			++invincibilityCounter;
		if (invincibilityCounter > 20) {
			invincibility = false;
			invincibilityCounter = 0;
			
			}
		}
	}

	@Override
	public void interactTile(int index, boolean useTool) {
		if (index != 9999 && this.destructible && useTool && !this.invincibility) {
			if (((ToolObjectInterface)gp.getPlayer().getEquippedTool()).getToolType() == ToolType.AXE) {
				gp.playSE(Sound.CUT_TREE);
				--this.life;
				this.invincibility = true;
				
				this.generateParticles(gp.getInteractiveTiles().get(gp.currentMap).get(index), gp.getInteractiveTiles().get(gp.currentMap).get(index));
				
				if (this.life < 1) {
					gp.getInteractiveTiles().get(gp.currentMap).set(index, new Trunk(gp, this.WorldX/gp.getTileSize(), this.WorldY/gp.getTileSize()));
				}
			}
		}
	}


}
