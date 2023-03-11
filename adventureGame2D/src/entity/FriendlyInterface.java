package entity;

import enums.Direction;

interface FriendlyInterface {
		abstract void setDefaultValues();
		abstract void getImage();
		default void setDialogue() {};
		//Set direction when talking to player
		default void talkingDirection (Player player, Entity NPC) {
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
		abstract void speak();
}
