package npc;

import java.util.ArrayList;

import adventureGame2D.GamePanel;
import entity.Entity;
import entity.Player;
import enums.Direction;
import object.GameObject;

public abstract class NPC extends Entity {
	
	protected GamePanel gp;
	
	protected ArrayList<String> dialogues = new ArrayList <String>();
	
	protected ArrayList<GameObject> npcInventory = new ArrayList <GameObject> ();

	protected int dialogueIndex = 0;
	
	public NPC(GamePanel gp) {
		super(gp);
		this.gp = gp;
	}
	
	abstract void getImage();
	
	abstract void setDialogue();
	
	abstract void setInventory();
	//Set direction when talking to player

	public void speak() {
		
		try {
			if (dialogues.get(dialogueIndex) != null) {
				gp.getGameUI().setCurrentDialogue(dialogues.get(dialogueIndex));
				++dialogueIndex;
				}
			} catch (IndexOutOfBoundsException e){}
				talkingDirection(gp.getPlayer(), this);
	};
	
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
