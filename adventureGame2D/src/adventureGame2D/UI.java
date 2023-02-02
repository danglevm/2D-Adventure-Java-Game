package adventureGame2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import object.Obj_key;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	
	//Stylizing
	Font arial_30, arial_50, arial_70, maruMonica, purisa;
	//Dialogue
	private String currentDialogue = "";	
	public void setCurrentDialogue(String dialogue) {
		currentDialogue = dialogue;
	}
	
	//Drawing and setting UI
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_30 = new Font ("Arial", Font.PLAIN, 30);
		arial_50 = new Font ("Arial", Font.PLAIN, 50);
		arial_70 = new Font ("Arial", Font.BOLD, 70);
		
		InputStream is = getClass().getResourceAsStream("/fonts/Purisa Bold.ttf");
		try {
			purisa = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	public int getXCenter (String text) {
		int str_length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - str_length/2;
		return x;
	}
	
	
	
	//Draws the UI
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(purisa);
		g2.setRenderingHint (RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		
		//Drawing gamestate
		if (gp.gameState == gp.playState) {
			//game state
			
		} else if (gp.gameState ==gp.pauseState) {
			//pause state
			drawPauseScreen();
		} else {
			//dialog state
			drawDialogueScreen();
		}
		
		
		//displays FPS
		
		
	}
	

	//draw screen when paused
	public void drawPauseScreen () {
		
		g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 80));
		String text = "Game Paused";
		int x = getXCenter(text), y = gp.screenHeight/2;
		g2.drawString(text, x ,y );
	}
	
	public void drawDialogueScreen() {
		
		// Dialogue window
		int x = gp.tileSize*2, 
			y = gp.tileSize/2, 
			width = gp.screenWidth - (gp.tileSize*4), 
			height = gp.tileSize*4;
		
			drawDialogueWindow (x, y, width, height);
			
		//Dialogue display inside window
		int dialogueX = gp.tileSize*3-20,
			dialogueY = gp.tileSize/2 +gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
		
		//Split the string into many parts
		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, dialogueX, dialogueY);
			//increase the display
			dialogueY+= 40;
		}
		
	}
	
	//Receives input and actually draw the window inside the dialogue box
	public void drawDialogueWindow (int x, int y, int width, int height) {
		
		//black color
		//alpha value - 220 - some opacity
		Color background = new Color (0,0,0, 220);
		g2.setColor(background);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		Color frameColor = new Color (255,255,255);
		//width of the stroke
		g2.setColor(frameColor);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
	}
	
	
}
