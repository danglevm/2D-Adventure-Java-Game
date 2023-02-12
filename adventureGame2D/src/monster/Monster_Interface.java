package monster;

public class Monster_Interface {
	public static interface Monster{
		
		//set default values for the entity
		//an abstract method does not have a body
		//classes that implements the interface, the methods are automatically public
		abstract void setDefaultValues();
		abstract void getImage();
	}
}
