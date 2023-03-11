package adventureGame2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import enums.GameState;
import enums.TitleState;
import object.ObjectHeart;
import quotes.PauseQuotes;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	//Sub-states
	//0 - welcome screen; 1 - story paths
	private TitleState titleScreenState;
	
	//Quotes
	PauseQuotes pauseQuotes = new PauseQuotes();
	String pauseText = "", moveDialogue = "Press ENTER to continue...";
	
	//Font styles
	Font arial_30, arial_50, arial_70, maruMonica, purisa;
	//Dialogue
	private String currentDialogue = "";	
	
	protected int cursorNum = 0, statusCursor = 0;
	
	//Drawing hearts
	private BufferedImage heart_full, heart_half, heart_blank;
	
	//Drawing SubTitles
	ArrayList <String> subtitleMsg = new ArrayList <String> ();
	ArrayList <Integer> subtitleMsgCount = new ArrayList <Integer> ();
	
	
	//Drawing and setting UI
	public UI(GamePanel gp) {
		this.gp = gp;
		
		titleScreenState = TitleState.WELCOME;
		
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
		}
		
		
		//Create HUD 
		Entity heart = new ObjectHeart(gp);
		heart_full = heart.getImage1();
		heart_half = heart.getImage2();
		heart_blank = heart.getImage3();
		
	}
	/**
	 * Setters and getters
	 */
	
	public TitleState getTitleScreenState () { return titleScreenState; }
	public void setTitleScreenState (TitleState titleScreenState) { this.titleScreenState = titleScreenState; }
	
	public void setCurrentDialogue (String dialogue) { currentDialogue = dialogue;}
	
	
	/*
	 * String aligning values getters
	 */
	private int getXCenter (String text) {
		int str_length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return gp.getScreenWidth()/2 - str_length/2;
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
		
		/*
		 * Draw corresponding game state entities/UI widgets
		 */
		
		switch (gp.getGameState()) {
		
		case TITLE -> { drawTitleScreen(); }
		
		case DIALOGUE -> {
			drawDialogueScreen();
		}
		
		case PAUSE -> {	
			drawPauseScreen();
			drawRandomPauseQuotes();
		}
		
		case PLAY -> {
			drawPlayerHearts();
			drawSubtitleMsg();
			if (gp.getEventHandler().getInteraction()) { drawInteractionKey(); }
		
		}
		
		case STATUS -> {drawStatusScreen();} 
		
		default -> throw new IllegalArgumentException("Unknown Game State: " + gp.getGameState());
		}
		
	}
	

	//draw screen when paused
	private void drawPauseScreen () {		
	
		//Draw background
		Color background = new Color (0,0,0, 220);
		g2.setColor(background);
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
			
		
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80));
		g2.setColor(Color.white);
		String text = "Game Paused";
		int x = getXCenter(text), y = gp.getScreenHeight()/2;
		g2.drawString(text,x, y);
		
	}
	
	private void drawRandomPauseQuotes() {
		//Draws a random quote from Pause Quote array
		g2.setFont(purisa);
		g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 30));
		int max = 2, 
				min = 0,
				range = max - min + 1,
				rand = (int)(Math.random() * range)+min;
		if (gp.getKeyHandler().getPauseQuote()) {
			pauseText = pauseQuotes.getPauseNoCombat(rand);
			gp.getKeyHandler().setPauseQuote(false);
		}
		int y = gp.getScreenHeight()/2 + gp.getTileSize()*2, x = getXCenter (pauseText);
		g2.drawString(pauseText,x, y);
		
	}
	
	private void drawTitleScreen() {
		g2.setColor(new Color (70,120,80));
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		if (titleScreenState == TitleState.WELCOME) {
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
			x = gp.getScreenWidth()/2 - gp.getTileSize() * 2;
			y += gp.getTileSize() * 1.5;
			g2.drawImage(gp.getPlayer().getDown1(), x, y, gp.getTileSize() * 4, gp.getTileSize() * 4, null);
			
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
		} else if (titleScreenState == TitleState.CHARACTERSELECT) {
			
			//STORY PATH SELECTION
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Select a story";
			int x = getXCenter(text),
				y = gp.getTileSize()*2;
			g2.drawString(text,x,y);
			
			text = "Blue Boy";
			x = getXCenter(text) - gp.getTileSize()*4;
			y = gp.getScreenHeight()/2;
			g2.drawString(text, x, y);
			if (cursorNum == 0) {g2.drawString(">", x-gp.getTileSize(), y);}
			
			text = "Yellow Girl";
			x = getXCenter(text) + gp.getTileSize()*4;
			y = gp.getScreenHeight()/2;
			g2.drawString(text, x, y);
			if (cursorNum == 1) {g2.drawString(">", x-gp.getTileSize(), y);}
			
			
		}

	}
	
	private void drawPlayerHearts() {
		int xLocation = gp.getTileSize()/2, 
			yLocation = gp.getTileSize()/2, 
			i = 0;
		int playerMaxLife = gp.getPlayer().getMaxLife();
		int currentPlayerLife = gp.getPlayer().getLife();
		
		
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
			width = gp.getScreenWidth()- (gp.getTileSize()*4), 
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
			y = gp.getScreenHeight()/2 - gp.getTileSize();
		g2.setColor(Color.white);
		g2.drawRect(gp.getPlayer().getScreenX() + 3, gp.getPlayer().getScreenY() - 48, 28, 28);
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
		
		
		for (int i = 0; i < gp.getPlayer().getLabelArray().length; ++i) {
			g2.drawString(gp.getPlayer().getLabelEntries(i), valueX, valueY);
			valueY += lineHeight;
		}
		
		valueY = frameY + gp.getTileSize();
		int defaultRectTailX = rectTailX;
		g2.setStroke(new java.awt.BasicStroke(1));
		
		for (int i = 0; i < gp.getPlayer().getLabelArray().length - 3; ++i) {
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
		
		g2.drawString(gp.getPlayer().getName(), getXValuesAlign(gp.getPlayer().getName(), tailX), valueY);
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getLevel()), getXValuesAlign(String.valueOf(gp.getPlayer().getLevel()), tailX), valueY);
		valueY += lineHeight;
	
		
		String value = String.valueOf(gp.getPlayer().getLife() + "/" + gp.getPlayer().getMaxLife());
		g2.drawString(value, getXValuesAlign(value, tailX), valueY);
		if (statusCursor == 0) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getHealthRegen()), getXValuesAlign(String.valueOf(gp.getPlayer().getHealthRegen()), tailX), valueY);
		if (statusCursor == 1) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getMana()), getXValuesAlign(String.valueOf(gp.getPlayer().getMana()), tailX), valueY);
		if (statusCursor == 2) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getManaRegen()), getXValuesAlign(String.valueOf(gp.getPlayer().getManaRegen()), tailX), valueY);
		if (statusCursor == 3) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getTotalAttack()), getXValuesAlign(String.valueOf(gp.getPlayer().getTotalDefense()), tailX), valueY);
		if (statusCursor == 4) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getTotalDefense()), getXValuesAlign(String.valueOf(gp.getPlayer().getTotalDefense()), tailX), valueY);
		if (statusCursor == 5) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getDexterity()), getXValuesAlign(String.valueOf(gp.getPlayer().getDexterity()), tailX), valueY);
		if (statusCursor == 6) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getStamina()), getXValuesAlign(String.valueOf(gp.getPlayer().getStamina()), tailX), valueY);
		if (statusCursor == 7) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getSpeed() - 3), getXValuesAlign(String.valueOf(gp.getPlayer().getSpeed()), tailX), valueY);
		if (statusCursor == 8) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getKnockback()), getXValuesAlign(String.valueOf(gp.getPlayer().getKnockback()), tailX), valueY);
		if (statusCursor == 9) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getCriticalHit()), getXValuesAlign(String.valueOf(gp.getPlayer().getCriticalHit()), tailX), valueY);
		if (statusCursor == 10) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		valueY += lineHeight;
		
		value = String.valueOf(gp.getPlayer().getExperience() + "/" + gp.getPlayer().getNextLevelExperience());
		g2.drawString(value, getXValuesAlign(value, tailX - gp.getTileSize() * 2), valueY);
		g2.drawString("Reset", getXValuesAlign("Reset", tailX), valueY);
		if (statusCursor == 11) {g2.drawString(">", tailX - gp.getTileSize(), valueY);}
		g2.drawString("Points: ", getXValuesAlign("Points: ", tailX + gp.getTileSize() * 2), valueY);
		g2.drawString(String.valueOf(gp.getPlayer().getUpgradePoints()), getXValuesAlign(String.valueOf(gp.getPlayer().getUpgradePoints()), tailX + gp.getTileSize() * 3), valueY);
		valueY += lineHeight;
		
		g2.drawString(String.valueOf(gp.getPlayer().getCoin()), getXValuesAlign(String.valueOf(gp.getPlayer().getCoin()), tailX), valueY);	
		valueY += 11;
		
		g2.drawImage(gp.getPlayer().getEquippedWeapon().getDown1(), tailX - gp.getTileSize(), valueY, null);
		g2.drawImage(gp.getPlayer().getEquippedShield().getDown1(), tailX, valueY , null);
	}
	
	public void addSubtitleMsg (String message) {
		this.subtitleMsg.add(message);
		this.subtitleMsgCount.add(0);
	}
	
	public void drawSubtitleMsg() {
		
		int x = gp.getTileSize() / 2, 
			y = gp.getTileSize() * 4;
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));
		
	
		for (int i = 0; i < subtitleMsg.size(); ++i) {
			if (subtitleMsg.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(subtitleMsg.get(i), x + 2, y + 2);
				g2.setColor(Color.white);
				g2.drawString(subtitleMsg.get(i), x, y);
			
				//make the message disappear after a certain amount of time
				subtitleMsgCount.set(i, subtitleMsgCount.get(i) + 1);
				y += 50;
			
				if (subtitleMsgCount.get(i) > 90) {
					subtitleMsg.remove(i);
					subtitleMsgCount.remove(i);
				}
			}
		}
		
		
	}
	
	
}
