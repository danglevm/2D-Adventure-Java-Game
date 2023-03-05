package entity;

interface InterfaceNPC {
	abstract void setDefaultValues();
	abstract void getImage();
	default void setDialogue() {}
	abstract void speak();;
}
