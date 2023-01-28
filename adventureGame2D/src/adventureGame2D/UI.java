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
	Graphics2D g2;
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
		
	}
	
	public void displayMessage (String text) {
		message = text;
		messageOn = true;
		
	}
	
	
	//Draws the UI
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_30);
		g2.setColor(Color.white);
		
		if (gp.gameState == gp.playState) {
			//game state
			
		} else {
			//pause state
			drawPauseScreen();
		}
		
	}
	
	public void drawPauseScreen () {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80));
		String text = "Game Paused";
		int x = getXCenter(text), y = gp.screenHeight/2;
		g2.drawString(text, x ,y );
	}
	
	public int getXCenter (String text) {
		int str_length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - str_length/2;
		return x;
	}
	
}
