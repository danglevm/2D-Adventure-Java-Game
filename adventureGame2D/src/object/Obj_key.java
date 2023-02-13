package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class Obj_key extends Entity{
	
	public Obj_key(GamePanel gp, int worldX, int worldY) {
		super (gp);
		this.WorldX = worldX;
		this.WorldY = worldY;
		name = "Key";
		
		
	}
	
}

