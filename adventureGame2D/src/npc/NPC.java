package npc;

import adventureGame2D.GamePanel;
import entity.Entity;
import entity.Player;
import enums.Direction;

public abstract class NPC extends Entity implements FriendlyInterface{

	public NPC(GamePanel gp) {
		super(gp);
	}
	
	
	public void talkingDirection (Player player, Entity NPC) {
		Direction entityDirection = Direction.DOWN;
		switch(player.getDirection()) {
		case UP: entityDirection = Direction.DOWN; break;
		case DOWN: entityDirection = Direction.UP; break;
		case RIGHT: entityDirection = Direction.LEFT; break;
		case LEFT: entityDirection = Direction.RIGHT; break;
		case ANY:
			break;
		default:
			break;
		}
	NPC.setDirection(entityDirection); 
		
	}

}
