package monster;

import entity.Entity;

interface Monster_Interface{
		//set default values for the entity
		//an abstract method does not have a body
		//classes that implements the interface, the methods are automatically public
		abstract void setDefaultValues();
		abstract void getImage();
		//for overriding by monsters

	
}



