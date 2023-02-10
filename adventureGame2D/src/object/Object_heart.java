package object;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import entity.Entity;

public class Object_heart extends Entity{
	public Object_heart(GamePanel gp) {
		super(gp);
		name = "Heart";
		image = setupCharacter("heart_full", "/heart/");
		image2 = setupCharacter("heart_half", "/heart/");
		image = setupCharacter("heart_blank", "/heart/");

	}
}
