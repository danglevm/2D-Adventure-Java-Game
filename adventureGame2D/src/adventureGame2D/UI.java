package adventureGame2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.Obj_key;

public class UI {
	
	GamePanel gp;
	Font arial_30;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message ="";
	private int messageCount=0;
	public boolean gameCompleted = false;
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_30 = new Font ("Arial", Font.PLAIN, 30);
		Obj_key Key = new Obj_key();
		keyImage = Key.image;
	}
	
	public void displayMessage (String text) {
		message = text;
		messageOn = true;
		
	}
	
	
	//Draws the UI
	public void draw(Graphics2D g2) {
		if (gameCompleted == true) {
			g2.setFont(arial_30);
			g2.setColor(Color.white);
			String text;
			int x, y, textLength;
			
			text ="Congratulations";
			
			text = "You finished the game";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize*3);
			g2.drawString(text, x, y);
			
			
			
			
		} else {
			g2.setFont(arial_30);
			g2.setColor(Color.white);
			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			//drawString indicates the baseline of the text - drawString and PaintComponents x y values are different 
			g2.drawString("x " + gp.player.hasKey, 74, 65);
			
			//MESSAGE
			if (messageOn == true) {
				g2.setFont(g2.getFont().deriveFont(30));
				g2.drawString(message, gp.tileSize/2, gp.tileSize*13);
				//120 frames - 2 seconds - duration message is displayed
				++messageCount;
				if (messageCount>120) {
					messageCount=0;
					messageOn=false;
				}
				
			}
		}
		
		
		
	}
}
