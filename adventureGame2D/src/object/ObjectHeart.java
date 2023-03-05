package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class ObjectHeart extends Entity implements ObjectInterface {
	
	GamePanel gp;
	
	public ObjectHeart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		setDefaultAttributes();
	}

	public void setDefaultAttributes() {
		name = "Heart";
		image = setupCharacter("heart_full", "/heart/", gp.tileSize, gp.tileSize);
		image2 = setupCharacter("heart_half", "/heart/", gp.tileSize, gp.tileSize);
		image3 = setupCharacter("heart_blank", "/heart/", gp.tileSize, gp.tileSize);
		
	}
}
