package entity;

interface NPC_Interface {
	abstract void setDefaultValues();
	abstract void getImage();
	default void setDialogue() {};
}
