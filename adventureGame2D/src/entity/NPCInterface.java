package entity;

public interface NPCInterface {
		abstract void setDefaultValues();
		abstract void getImage();
		default void setDialogue() {}
		abstract void speak();;
}
