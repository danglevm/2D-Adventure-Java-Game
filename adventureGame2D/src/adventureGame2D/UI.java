package adventureGame2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.Obj_key;

public class UI {
	
	GamePanel gp;
	Font arial_30, arial_50, arial_70;
	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message ="";
	private int messageCount=0;
	public boolean gameCompleted = false;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_30 = new Font ("Arial", Font.PLAIN, 30);
		arial_50 = new Font ("Arial", Font.PLAIN, 50);
		arial_70 = new Font ("Arial", Font.BOLD, 70);
		Obj_key Key = new Obj_key(gp);
		keyImage = Key.image;
	}
	
	public void displayMessage (String text) {
		message = text;
		messageOn = true;
		
	}
	
	
	//Draws the UI
	public void draw(Graphics2D g2) {
		if (gameCompleted == true) {
			g2.setFont(arial_50);
			g2.setColor(Color.white);
			String text;
			int x, y, textLength;
			
			//Draws the end game scene
			text = "You found the ultimate treasure";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 - (gp.tileSize);
			g2.drawString(text, x, y);
			
			text = "Your total playtime is: " + dFormat.format(playTime)
+ "!";			
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*4);
			g2.drawString(text, x, y);
			
			
			g2.setFont(arial_70);
			g2.setColor(Color.YELLOW);
			text ="Congratulations";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			x = gp.screenWidth/2 - textLength/2;
			y = gp.screenHeight/2 + (gp.tileSize*2);
			g2.drawString(text, x, y);
			
			
			
			//stops the thread and the game
			gp.gameThread = null;
			
			
			
			
		} else {
			g2.setFont(arial_30);
			g2.setColor(Color.white);
			g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
			//drawString indicates the baseline of the text - drawString and PaintComponents x y values are different 
			g2.drawString("x " + gp.player.hasKey, 74, 65);
			
			//Tracks time
			playTime += (double)1/60;
			g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize*13, 65);
			
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
