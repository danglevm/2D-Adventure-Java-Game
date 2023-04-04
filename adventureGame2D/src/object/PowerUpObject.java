package object;

import java.awt.image.BufferedImage;

import adventureGame2D.GamePanel;
import object.interfaces.PowerUpObjectInterface;

public abstract class PowerUpObject extends GameObject implements PowerUpObjectInterface{
	
	public PowerUpObject(GamePanel gp) {
		super(gp);
	}
}
