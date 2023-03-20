package object.interfaces;

import entity.Player;

public interface ConsummableInterface {

	//returns true if player can use consummable and false if player can not
	abstract boolean useConsummable (Player player);
}
