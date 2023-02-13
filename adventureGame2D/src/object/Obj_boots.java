package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class Obj_boots extends Entity{
	
	public Obj_boots(GamePanel gp, int worldX, int worldY) {
		super(gp);
		name = "Boots";
		this.WorldX = worldX;
		this.WorldY = worldY;
		down1 = setupCharacter("boots", "/objects/");
		}
}

