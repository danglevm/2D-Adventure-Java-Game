package entity;

import enums.Direction;

public interface NPCInterface {
		abstract void setDefaultValues();
		abstract void getImage();
		default void setDialogue() {};
		//Set direction when talking to player
		default void talkingDirection (Player player, Entity NPC) {
			Direction entityDirection = Direction.DOWN;
			switch(player.getDirection()) {
			case UP: entityDirection = Direction.UP; break;
			case DOWN: entityDirection = Direction.DOWN; break;
			case RIGHT: entityDirection = Direction.RIGHT; break;
			case LEFT: entityDirection = Direction.LEFT; break;
			case ANY:
				break;
			default:
				entityDirection = Direction.DOWN; 
			}
			NPC.setDirection(entityDirection); 
			
		}
		abstract void speak();
}
