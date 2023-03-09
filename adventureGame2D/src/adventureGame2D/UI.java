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
import enums.GameState;
import object.ObjectHeart;
import quotes.PauseQuotes;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	//Sub-states
	//0 - welcome screen; 1 - story paths
	public int titleScreenState = 0;
	
	//Quotes
	PauseQuotes pauseQuotes = new PauseQuotes();
	String pauseText = "", moveDialogue = "Press ENTER to continue...";
	
	//Stylizing
	Font arial_30, arial_50, arial_70, maruMonica, purisa;
	//Dialogue
	private String currentDialogue = "";	
	public void setCurrentDialogue (String dialogue) { currentDialogue = dialogue;}
	protected int cursorNum = 0, statusCursor = 0;
	
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
		Entity heart = new ObjectHeart(gp);
		heart_full = heart.getImage1();
		heart_half = heart.getImage2();
		heart_blank = heart.getImage3();
		
	}
	

	
	private int getXCenter (String text) {
		int str_length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.screenWidth/2 - str_length/2;
	}
	
	
	private int getXValuesAlign(String text, int tailX) {
		int str_length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return tailX - str_length/2;
	};
	
	
	
	//Draws the UI
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(purisa);
		g2.setColor(Color.white);
		
		
		//Drawing gamestate
		if (gp.getGameState() == GameState.TITLE) {
			//titleScreen
			this.drawTitleScreen();
			
			
		} 
		if (gp.getGameState() == GameState.PAUSE) {
			//pause state
			drawPauseScreen();
			drawRandomPauseQuotes();
			
		}
		if (gp.getGameState() == GameState.PLAY){
			//playing state
			this.drawPlayerHearts();
			if (gp.eHandler.getInteraction()) {
				drawInteractionKey();
			}
			
		} 
		if (gp.getGameState() == GameState.DIALOGUE){
			//dialogue state
			drawDialogueScreen();
			drawPlayerHearts();
		}
		
		if (gp.getGameState() == GameState.STATUS) {
			drawStatusScreen();
		}
		
		
	}
	

	//draw screen when paused
	private void drawPauseScreen () {
		
	
		//Draw background
		Color background = new Color (0,0,0, 220);
		g2.setColor(background);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			
		
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
		g2.setColor(Color.white);
		String text = "Game Paused";
		int x = getXCenter(text), y = gp.screenHeight/2;
		g2.drawString(text,x, y);
		
		
	
		
	}
	
	private void drawRandomPauseQuotes() {
		//Draws a random quote from Pause Quote array
		g2.setFont(purisa);
		g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 30));
		int max = 2, 
				min = 0,
				range = max - min + 1,
				rand = (int)(Math.random()*range)+min;
		if (gp.keyH.pauseQuote) {
			pauseText = pauseQuotes.getPauseQuote(rand);
			gp.keyH.pauseQuote=false;
		}
		int y = gp.screenHeight/2 + gp.getTileSize()*2, x = getXCenter (pauseText);
		g2.drawString(pauseText,x, y);
		
	}
	
	private void drawTitleScreen() {
		g2.setColor(new Color (70,120,80));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		if (titleScreenState == 0) {
			//Draw background
			
			
			//Title name
			g2.setFont(maruMonica);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
			String text = "A Fever Dream";
			int x = getXCenter(text),
				y = gp.getTileSize()*3;
			
			//Draw the shadow
			g2.setColor(Color.black);
			g2.drawString(text, x + 5, y + 5);
			
			//Draw the actual text
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			//Character image
			x = gp.screenWidth/2 - gp.getTileSize() * 2;
			y += gp.getTileSize() * 1.5;
			g2.drawImage(gp.player.getDown1(), x, y, gp.getTileSize() * 4, gp.getTileSize() * 4, null);
			
			//Menu options
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			text = "NEW GAME";
			x = getXCenter(text);
			y += gp.getTileSize()*5.5;
			g2.drawString(text, x, y);
			if (cursorNum == 0) {g2.drawString(">", x-gp.getTileSize(), y);}
			
			text = "LOAD SAVE";
			x = getXCenter(text);
			y += gp.getTileSize();
			g2.drawString(text, x, y);
			if (cursorNum == 1) {g2.drawString(">", x-gp.getTileSize(), y);}
			
			text = "SETTINGS";
			x = getXCenter(text);
			y += gp.getTileSize();
			g2.drawString(text, x, y);
			if (cursorNum == 2) {g2.drawString(">", x-gp.getTileSize(), y);}
		
			text = "QUIT";
			x = getXCenter(text);
			y += gp.getTileSize();
			g2.drawString(text,x, y);
			if (cursorNum == 3) {g2.drawString(">", x-gp.getTileSize(), y);}
		} else if (titleScreenState == 1) {
			
			//STORY PATH SELECTION
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Select a story";
			int x = getXCenter(text),
				y = gp.getTileSize()*2;
			g2.drawString(text,x,y);
			
			text = "Blue Boy";
			x = getXCenter(text) - gp.getTileSize()*4;
			y = gp.screenHeight/2;
			g2.drawString(text, x, y);
			if (cursorNum == 0) {g2.drawString(">", x-gp.getTileSize(), y);}
			
			text = "Yellow Girl";
			x = getXCenter(text) + gp.getTileSize()*4;
			y = gp.screenHeight/2;
			g2.drawString(text, x, y);
			if (cursorNum == 1) {g2.drawString(">", x-gp.getTileSize(), y);}
			
			
		}

	}
	
	private void drawPlayerHearts() {
		int xLocation = gp.getTileSize()/2, 
			yLocation = gp.getTileSize()/2, 
			i = 0;
		int playerMaxLife = gp.player.getMaxLife();
		int currentPlayerLife = gp.player.getLife();
		
		
		//Draw blank hearts - max hp
		while (i < playerMaxLife/2) {
			g2.drawImage(heart_blank, xLocation, yLocation, null);
			++i;
			xLocation += gp.getTileSize();
		}
		
		
		//Reset drawing location
		xLocation = gp.getTileSize()/2;
		yLocation = gp.getTileSize()/2;
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
			xLocation += gp.getTileSize();
	}
	
		
	}
	
	private void drawDialogueScreen() {
		// Dialogue window
		int x = gp.getTileSize()*2, 
			y = gp.getTileSize()/2, 
			width = gp.screenWidth - (gp.getTileSize()*4), 
			height = gp.getTileSize()*4;
		
			drawSubWindow (x, y, width, height);
			
		//Dialogue display inside window
		int dialogueX = gp.getTileSize()*3-20,
			dialogueY = gp.getTileSize()/2 +gp.getTileSize();
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25));
		//Split the string into many parts
		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, dialogueX, dialogueY);
			//increase the display
			dialogueY += 40;
		}
		
		//Draw the press next to move on key notification
		dialogueX += 390;
		dialogueY = (gp.getTileSize()/2) + gp.getTileSize() + 120;
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 14));
		g2.drawString(moveDialogue, dialogueX, dialogueY);
	}
	
	//Receives input and actually draw the window inside the dialogue box
	private void drawSubWindow (int x, int y, int width, int height) {
		
		//black color
		//alpha value - 220 - some opacity
		Color background = new Color (0,0,0, 220);
		g2.setColor(background);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		Color frameColor = new Color (255,255,255);
		//width of the stroke
		g2.setColor(frameColor);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
		
	}
	
	
	//Method for drawing a simple string above the player head for event interaction
	private void drawInteractionKey() {
		String text = "X Interact";
		int x = getXCenter(text) - gp.getTileSize()/4, 
			y = gp.screenHeight/2 - gp.getTileSize();
		g2.setColor(Color.white);
		g2.drawRect(gp.player.getScreenX() + 3, gp.player.getScreenY() - 48, 28, 28);
		g2.setFont(g2.getFont().deriveFont(24F));
		g2.drawString(text, x, y);
	}
	
	private void drawStatusScreen() {
		
		/**
		 * Frame Creation
		 */
		final int frameX = gp.getTileSize(),
				  frameY = gp.getTileSize()/2,
				  frameWidth = gp.getTileSize() * 11,
				  frameHeight = gp.getTileSize() * 13 ;
		
		drawSubWindow (frameX, frameY, frameWidth, frameHeight);
		
		/**
		 * Draw Labels
		 */
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(30F));
		
		int valueX = frameX + 30,
			valueY = frameY + gp.getTileSize(),
			lineHeight = 36,
			rectTailX = (frameX + frameWidth) - gp.getTileSize() * 3;
		
		for (int i = 0; i < gp.player.labels.length; ++i) {
			g2.drawString(gp.player.labels[i], valueX, valueY);
			valueY += lineHeight;
		}
		
		valueY = frameY + gp.getTileSize();
		int defaultRectTailX = rectTailX;
		g2.setStroke(new java.awt.BasicStroke(1));
		
		for (int i = 0; i < gp.player.labels.length - 3; ++i) {
			for (int a = 0; a < 3; ++a) {
				if (i == 0 || i == 1) continue;
				g2.drawRect(rectTailX, valueY - 20, 20, 20);
				rectTailX += 30;
			}
			valueY += lineHeight;
			rectTailX = defaultRectTailX;
		}
		
		
		/**
		 * Draw Matching Values
		 */
		int tailX = (frameX + frameWidth) - gp.getTileSize() * 4;
		valueY = frameY + gp.getTileSize();
		
		g2.drawString(gp.player.name, getXValuesAlign(gp.player.name, tailX), valueY);
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.level), getXValuesAlign(String.valueOf(gp.player.level), tailX), valueY);
		valueY += lineHeight;
	
		
		String value = String.valueOf(gp.player.getLife() + "/" + gp.player.getMaxLife());
		g2.drawString(value, getXValuesAlign(value, tailX), valueY);
		if (statusCursor == 0) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.healthRegen), getXValuesAlign(String.valueOf(gp.player.healthRegen), tailX), valueY);
		if (statusCursor == 1) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.mana), getXValuesAlign(String.valueOf(gp.player.mana), tailX), valueY);
		if (statusCursor == 2) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.manaRegen), getXValuesAlign(String.valueOf(gp.player.manaRegen), tailX), valueY);
		if (statusCursor == 3) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.strength), getXValuesAlign(String.valueOf(gp.player.strength), tailX), valueY);
		if (statusCursor == 4) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.defense), getXValuesAlign(String.valueOf(gp.player.defense), tailX), valueY);
		if (statusCursor == 5) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.dexterity), getXValuesAlign(String.valueOf(gp.player.dexterity), tailX), valueY);
		if (statusCursor == 6) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.stamina), getXValuesAlign(String.valueOf(gp.player.stamina), tailX), valueY);
		if (statusCursor == 7) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.displaySpeed), getXValuesAlign(String.valueOf(gp.player.displaySpeed), tailX), valueY);
		if (statusCursor == 8) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.knockback), getXValuesAlign(String.valueOf(gp.player.knockback), tailX), valueY);
		if (statusCursor == 9) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.criticalHit), getXValuesAlign(String.valueOf(gp.player.criticalHit), tailX), valueY);
		if (statusCursor == 10) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		value = String.valueOf(gp.player.experience + "/" + gp.player.nextLevelExperience);
		g2.drawString(value, getXValuesAlign(value, tailX - gp.getTileSize() * 2), valueY);
		g2.drawString("Reset", getXValuesAlign("Reset", tailX), valueY);
		if (statusCursor == 11) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		g2.drawString("Points: ", getXValuesAlign("Points: ", tailX + gp.getTileSize() * 2), valueY);
		g2.drawString(String.valueOf(gp.player.upgradePoints), getXValuesAlign(String.valueOf(gp.player.upgradePoints), tailX + gp.getTileSize() * 3), valueY);
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.player.coin), getXValuesAlign(String.valueOf(gp.player.coin), tailX), valueY);	
		valueY += 11;
		
		g2.drawImage(gp.player.equippedWeapon.getDown1(), tailX - gp.getTileSize(), valueY, null);
		g2.drawImage(gp.player.equippedShield.getDown1(), tailX, valueY , null);
	}
	
}
