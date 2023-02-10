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
import java.util.Random;

import entity.Entity;
import object.Obj_key;
import object.Object_heart;
import quotes.PauseQuotes;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	//Sub-states
	//0 - welcome screen; 1 - story paths
	public int titleScreenState = 0;
	
	//Quotes
	PauseQuotes pauseQuotes = new PauseQuotes();
	String pauseText = "";
	
	//Stylizing
	Font arial_30, arial_50, arial_70, maruMonica, purisa;
	//Dialogue
	private String currentDialogue = "";	
	public void setCurrentDialogue (String dialogue) { currentDialogue = dialogue;}
	public int cursorNum = 0;
	
	//Drawing hearts
	private BufferedImage heart_full, heart_half, heart_blank;
	
	
	//Drawing and setting UI
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_30 = new Font ("Arial", Font.PLAIN, 30);
		arial_50 = new Font ("Arial", Font.PLAIN, 50);
		arial_70 = new Font ("Arial", Font.BOLD, 70);
		
		InputStream is = getClass().getResourceAsStream("/fonts/Purisa Bold.ttf");
		try {
			purisa = Font.createFont(Font.TRUETYPE_FONT, is);
			InputStream is2 = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is2);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Create HUD 
		Entity heart = new Object_heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
	}
	

	
	protected int getXCenter (String text) {
		int str_length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - str_length/2;
		return x;
	}
	
	
	
	//Draws the UI
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(purisa);
		g2.setColor(Color.white);
		
		
		//Drawing gamestate
		if (gp.gameState == gp.titleState) {
			//titleScreen
			this.drawTitleScreen();
			
		} 
		if (gp.gameState == gp.pauseState) {
			//pause state
			drawPauseScreen();
			drawRandomPauseQuotes();
			
		}
		if (gp.gameState == gp.playState){
			//playing state
			this.drawPlayerHearts();
			if (gp.eHandler.getInteraction()) {
				drawInteractionKey();
			}
			
		} 
		if (gp.gameState == gp.dialogueState){
			//dialogue state
			
			this.drawDialogueScreen();
			this.drawPlayerHearts();
		}
		
		
	}
	

	//draw screen when paused
	protected void drawPauseScreen () {
		
	
		//Draw background
		Color background = new Color (0,0,0, 220);
		g2.setColor(background);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
		
		g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 80));
		g2.setColor(Color.white);
		String text = "Game Paused";
		int x = getXCenter(text), y = gp.screenHeight/2;
		g2.drawString(text,x, y);
		
		
	
		
	}
	
	protected void drawRandomPauseQuotes() {
		//Draws a random quote from Pause Quote array
		g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 30));
		int max = 2, 
				min = 0,
				range = max - min + 1,
				rand = (int)(Math.random()*range)+min;
		if (gp.keyH.pauseQuote) {
			pauseText = pauseQuotes.getPauseQuote(rand);
			gp.keyH.pauseQuote=false;
		}
		int y = gp.screenHeight/2 + gp.tileSize*2, x = getXCenter (pauseText);
		g2.drawString(pauseText,x, y);
		
	}
	
	protected void drawTitleScreen() {
		g2.setColor(new Color (70,120,80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		if (titleScreenState == 0) {
			//Draw background
			
			
			//Title name
			g2.setFont(maruMonica);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
			String text = "A Fever Dream";
			int x = getXCenter(text),
				y = gp.tileSize*3;
			
			//Draw the shadow
			g2.setColor(Color.black);
			g2.drawString(text, x+5, y+5);
			
			//Draw the actual text
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			//Character image
			x = gp.screenWidth/2-gp.tileSize*2;
			y += gp.tileSize*1.5;
			g2.drawImage(gp.player.down1, x, y, gp.tileSize*4, gp.tileSize*4, null);
			
			//Menu options
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			text = "NEW GAME";
			x = getXCenter(text);
			y += gp.tileSize*5.5;
			g2.drawString(text, x, y);
			if (cursorNum == 0) {g2.drawString(">", x-gp.tileSize, y);}
			
			text = "LOAD SAVE";
			x = getXCenter(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (cursorNum == 1) {g2.drawString(">", x-gp.tileSize, y);}
			
			text = "SETTINGS";
			x = getXCenter(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (cursorNum == 2) {g2.drawString(">", x-gp.tileSize, y);}
		
			text = "QUIT";
			x = getXCenter(text);
			y += gp.tileSize;
			g2.drawString(text,x, y);
			if (cursorNum == 3) {g2.drawString(">", x-gp.tileSize, y);}
		} else if (titleScreenState == 1) {
			
			//STORY PATH SELECTION
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Select a story";
			int x = getXCenter(text),
				y = gp.tileSize*2;
			g2.drawString(text,x,y);
			
			text = "Blue Boy";
			x = getXCenter(text) - gp.tileSize*4;
			y = gp.screenHeight/2;
			g2.drawString(text, x, y);
			if (cursorNum == 0) {g2.drawString(">", x-gp.tileSize, y);}
			
			text = "Yellow Girl";
			x = getXCenter(text) + gp.tileSize*4;
			y = gp.screenHeight/2;
			g2.drawString(text, x, y);
			if (cursorNum == 1) {g2.drawString(">", x-gp.tileSize, y);}
			
			
		}

	}
	
	protected void drawPlayerHearts() {
		int xLocation = gp.tileSize/2, 
			yLocation = gp.tileSize/2, 
			i = 0;
		int playerMaxLife = gp.player.getMaxLife();
		int currentPlayerLife = gp.player.getLife();
		
		//Draw blank hearts - max hp
		while (i < playerMaxLife/2) {
			g2.drawImage(heart_blank, xLocation, yLocation, null);
			++i;
			xLocation += gp.tileSize;
		}
		
		
		//Reset drawing location
		xLocation = gp.tileSize/2;
		yLocation = gp.tileSize/2;
		i = 0;
		
		//Draw current hp
		
		while (i < currentPlayerLife) {
			g2.drawImage(heart_half, xLocation, yLocation, null);
			++i;
			//if it's an odd value, then continue to draw again
			if (i < currentPlayerLife) {
				g2.drawImage(heart_full, xLocation, yLocation,null);
			}
			++i;
			xLocation += gp.tileSize;
		}
		
	}
	
	protected void drawDialogueScreen() {
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
	protected void drawDialogueWindow (int x, int y, int width, int height) {
		
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
	
	
	//Method for drawing a simple string above the player head for event interaction
	public void drawInteractionKey() {
			String text = "Press X to interact";
			int x = getXCenter(text) - gp.tileSize*2, y = gp.screenHeight/2 - gp.tileSize;
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(20F));
			g2.drawString(text, x, y);
	}
	
}
