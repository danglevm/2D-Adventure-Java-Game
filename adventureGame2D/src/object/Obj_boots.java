package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class Obj_boots extends Entity{
	
	public Obj_boots(GamePanel gp) {
		super(gp);
	
		name = "Boots";
		
		down1 = setupCharacter("boots", "/objects/");
		}
}

