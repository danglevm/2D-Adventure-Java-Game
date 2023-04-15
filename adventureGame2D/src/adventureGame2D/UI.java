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
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import entity.Entity;
import entity.Player;
import enums_and_constants.GameState;
import enums_and_constants.InventoryState;
import enums_and_constants.MapsConstants;
import enums_and_constants.ObjectType;
import enums_and_constants.PauseState;
import enums_and_constants.TitleState;
import enums_and_constants.TradeState;
import npc.Merchant;
import npc.NPC;
import object.BronzeCoin;
import object.GameObject;
import object.Heart;
import object.Mana;
import quotes.PauseQuotes;
import quotes.StatusAttribute;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	private TitleState titleScreenState;
	private InventoryState inventoryState;
	protected TradeState tradeState;
	protected PauseState pauseState;
	
	//Quotes
	PauseQuotes pauseQuotes = new PauseQuotes();
	String pauseText = "", moveDialogue = "Press ENTER to continue...";
	
	//Font styles
	Font arial_30, arial_50, arial_70, maruMonica, purisa;
	//Dialogue
	private String currentDialogue = "";
	Color nameColor1 = new Color(253, 218, 13);
	Color nameColor2 = new Color(196, 30, 58);
	
	
	//Inventory option cursor color
	Color optionColor = new Color(247, 135, 2);
	
	Color equippedWeaponColor = new Color(240,190,90),
		  equippedDefenseColor = new Color(121,68,59),
		  equippedToolColor = new Color(175, 225, 175);
	
	protected int cursorNum = 0, statusCursor = 0, inventoryOptionCursor = 0, 
			pauseCursor = 0, gameOverCursor = 0, tradeCursor = 0;
	
	//Drawing hearts
	private BufferedImage heart_full, heart_half, heart_blank;
	GameObject heart;
	
	//drawing mana crystals
	private BufferedImage crystal_full, crystal_blank;
	GameObject mana;
	
	private String [] statusScreenLabels = {"NEW GAME", "LOAD SAVE", "SETTINGS", "QUIT"};
	
	//Player inventory column and row
	private int slotCol = 0, slotRow = 0;
	
	//NPC inventory column and row
	private int NPCslotCol = 0, NPCslotRow = 0;
	
	//Drawing SubTitles
	ArrayList <String> subtitleMsg = new ArrayList <String> ();
	ArrayList <Integer> subtitleMsgCount = new ArrayList <Integer> ();
	
	BronzeCoin coin;
	

	
	private int transitionCounter = 0;
	
	private NPC npc; 
	
	//Drawing and setting UI
	public UI(GamePanel gp) {
		this.gp = gp;
		
		titleScreenState = TitleState.WELCOME;
		
		inventoryState = InventoryState.NORMAL;
		
		tradeState = TradeState.SELECT;
		
		pauseState = PauseState.MENU;
		
		coin = new BronzeCoin(gp, 0, 0);
	
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
		/*
		 * HP
		 */
		heart = new Heart(gp, 0, 0);
		heart_full = heart.getImage1();
		heart_half = heart.getImage2();
		heart_blank = heart.getImage3();
		/*
		 * Mana
		 */
		mana = new Mana (gp, 0, 0);
		crystal_full = mana.getImage1();
		crystal_blank = mana.getImage2();
	}
	/**
	 * Setters and getters
	 */
	
	public TitleState getTitleScreenState () { return titleScreenState; }
	
	public InventoryState getInventoryState () { return inventoryState; }
	
	public TradeState getTradeState() { return tradeState; }
	
	public void setTradeState(TradeState tradeState) { this.tradeState = tradeState; }
	
	public final int getItemIndex () { 
		if (tradeState != TradeState.SELL) return slotCol + (slotRow * 4);
		
		return slotCol + (slotRow * 6);
	}
	
	public final void setTitleScreenState (TitleState titleScreenState) { this.titleScreenState = titleScreenState; }
	
	public final void setInventoryState (InventoryState inventoryState) { this.inventoryState = inventoryState; }
	
	public final void setCurrentDialogue (String dialogue) { currentDialogue = dialogue;}
	
	public final int getSlotColumn () { return slotCol; }
	
	public final int getSlotRow() { return slotRow; }
	
	public final void setSlotColumn (int slotColumn) { this.slotCol = slotColumn;}
	
	public final void setSlotRow (int slotRow) { this.slotRow = slotRow;}
	 
	
	public final void addSubtitleMsg (String message) {
		this.subtitleMsg.add(message);
		this.subtitleMsgCount.add(0);
	}
	
	public final NPC getNPC() { return npc; }
	
	public final void setNPC(NPC npc) { this.npc = npc; }
	
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
			drawPlayerHUD();
			if (gp.getSubtitleState()) drawSubtitleMsg();
		
		}
		
		case STATUS -> { drawStatusScreen(); } 
		
		case INVENTORY -> { 
			drawPlayerInventoryScreen();
		}
		
		case GAMEOVER ->{drawGameOverScreen();}
		
		case TRANSITION -> {drawTransition();}
		
		case TRADE -> {drawTradeScreen(); }
		
		default -> throw new IllegalArgumentException("Unknown Game State: " + gp.getGameState());
		}
		
	}
	

	//draw screen when paused
	private void drawPauseScreen () {		
	
		//Draw dark background
		Color background = new Color (0,0,0, 220);
		g2.setColor(background);
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		int menuFrameX = gp.getTileSize() * 4,
				menuFrameY = gp.getTileSize(),
				menuFrameWidth = gp.getTileSize() * 12,
				menuFrameHeight = gp.getTileSize() * 11;
			this.drawSubWindow(menuFrameX, menuFrameY, menuFrameWidth, menuFrameHeight);
			
		
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36));
		g2.setColor(Color.white);
		String pauseText = "GAME PAUSED";
		int pauseX = getXCenter(pauseText), pauseY = gp.getTileSize() * 2;
		g2.drawString(pauseText, pauseX, pauseY);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24));
		String optionsText = "OPTIONS";
		int optionsX = getXCenter(optionsText), optionsY = gp.getTileSize() * 3 - gp.getTileSize()/3;
		g2.drawString(optionsText, optionsX, optionsY);
		
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		
		
		switch (pauseState) {
		case MENU -> {pauseLabels(menuFrameX, menuFrameY);}
		case FULLSCREEN -> {fullScreenNotification(menuFrameX, menuFrameY);}
		case KEYBINDINGS -> {keyBindings(menuFrameX, menuFrameY);}
		}
	
		
	}
	
	private final void pauseLabels (int frameX, int frameY) {
		int textX = frameX + gp.getTileSize(), 
			textY = gp.getTileSize() * 4;
		g2.drawString("Full Screen", textX, textY);
		g2.drawString("Music", textX, textY  += gp.getTileSize());
		g2.drawString("Sound Effects", textX, textY  += gp.getTileSize());
		g2.drawString("Keybindings", textX, textY  += gp.getTileSize());
		g2.drawString("Show Subtitles", textX, textY += gp.getTileSize());
		g2.drawString("Load last save", textX, textY += gp.getTileSize());
		g2.drawString("Save", textX, textY += gp.getTileSize());
		g2.drawString("Save and Quit to Menu", textX, textY  += gp.getTileSize());
		g2.drawString(">", textX - 20, gp.getTileSize() * 4 + gp.getTileSize() * pauseCursor);

		
		g2.setStroke(new BasicStroke(3));
		this.fullScreenCheckbox(frameX, 3);
		//Music volume slider
		this.drawMusicSlider(frameX, 4);
		
		//Sound effect slider
		this.drawSoundSlider(frameX, 5);
		
		this.subtitleCheckbox(frameX, 7);
		
		gp.getMenuOptionConfig().saveConfig();
		
	}
	
	private final void fullScreenCheckbox (int frameX, int valueY) {
		g2.drawRect(frameX + gp.getTileSize() * 9, gp.getTileSize() * valueY + 23, 24, 24);
		if (gp.getFullScreen()) {
			g2.fillRect(frameX + gp.getTileSize() * 9, gp.getTileSize() * valueY + 23, 24, 24);
		}
	}
	
	private final void fullScreenNotification (int frameX, int frameY) {
		int textX = frameX + gp.getTileSize();
		int textY = frameY + gp.getTileSize() * 3;
		
		String message = "The changes will take effect after you \nrestart the game.";
		
		for (String line : message.split("\n")) {
			g2.drawString(line, textX, textY);
			//increase the display
			textY += 40;
		}
		
		g2.drawString(" [ESC] Go back to menu ", textX, textY + 40);
	}
	
	private final void drawMusicSlider (int frameX, int valueY) {
		g2.drawRect(frameX + gp.getTileSize() * 7, gp.getTileSize() * valueY + 23, 120, 24); //120/5 = 24
		int volumeWidth = 24 * gp.getMusic().getVolumeScale();
		g2.fillRect(frameX + gp.getTileSize() * 7, gp.getTileSize() * valueY + 23, volumeWidth, 24);
	}
	
	private final void drawSoundSlider (int frameX, int valueY) {
		g2.drawRect(frameX + gp.getTileSize() * 7, gp.getTileSize() * valueY + 23, 120, 24); //120/5 = 24
		int volumeWidth = 24 * gp.getSoundEffects().getVolumeScale();
		g2.fillRect(frameX + gp.getTileSize() * 7, gp.getTileSize() * valueY + 23, volumeWidth, 24);
	}
	
	private final void subtitleCheckbox (int frameX, int valueY) {
		g2.drawRect(frameX + gp.getTileSize() * 9, gp.getTileSize() * valueY + 23, 24, 24);
		if (gp.getSubtitleState()) {
			g2.fillRect(frameX + gp.getTileSize() * 9, gp.getTileSize() * valueY + 23, 24, 24);
		}
	}
	
	private final void keyBindings (int frameX, int frameY) {
		int textX = frameX + gp.getTileSize();
		int textY = frameY + gp.getTileSize();

		g2.drawString("Movement", textX, textY += gp.getTileSize());
		g2.drawString("Attack", textX, textY += gp.getTileSize());
		g2.drawString("Use Tools", textX, textY += gp.getTileSize());
		g2.drawString("Cast Spells", textX, textY += gp.getTileSize());
		g2.drawString("Display Info", textX, textY += gp.getTileSize());
		g2.drawString("Open Inventory", textX, textY += gp.getTileSize());
		g2.drawString("Open Status and Upgrades", textX, textY += gp.getTileSize());
		g2.drawString("Upgrades and inventory interaction", textX, textY += gp.getTileSize());
		g2.drawString("[ESC] Go back to menu", textX, textY += gp.getTileSize());
		
		textX = frameX + gp.getTileSize() * 10;
		textY = frameY + gp.getTileSize() * 2;
		g2.drawString("WASD", textX, textY);
		g2.drawString("J", textX, textY += gp.getTileSize());
		g2.drawString("K", textX, textY += gp.getTileSize());
		g2.drawString("L", textX, textY += gp.getTileSize());
		g2.drawString("T", textX, textY += gp.getTileSize());
		g2.drawString("E", textX, textY += gp.getTileSize());
		g2.drawString("C", textX, textY += gp.getTileSize());
		g2.drawString("ENTER", textX, textY += gp.getTileSize());
	}
	
	private void drawRandomPauseQuotes() {
		//Draws a random quote from Pause Quote array
		g2.setFont(purisa);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16));
		int max = 2, 
				min = 0,
				range = max - min + 1,
				rand = (int)(Math.random() * range)+min;
		if (gp.getKeyHandler().getPauseQuote()) {
			pauseText = pauseQuotes.getPauseNoCombat(rand);
			gp.getKeyHandler().setPauseQuote(false);
		}
		int y = gp.getScreenHeight() - gp.getTileSize(), x = getXCenter (pauseText);
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
			
			
			y += gp.getTileSize()*5.5;
			int defaultY = y;
			//Menu options
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			for (int i = 0; i < statusScreenLabels.length; ++i) {
				text = statusScreenLabels[i];
				x = getXCenter (text);
				g2.drawString(text, x, y);
				y += gp.getTileSize();
				
			}
			
			y = defaultY + (gp.getTileSize() * cursorNum);
			g2.drawString(">", x-gp.getTileSize()*2, y);
			
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
	
	private void drawPlayerHUD() {
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
				g2.drawImage(heart_full, xLocation, yLocation, null);
			}
			++i;
			xLocation += gp.getTileSize();
		}
		
		xLocation = gp.getTileSize()/2;
		yLocation = gp.getTileSize() * 2;
		i = 0;
		
		//Draw blank mana - max mana
		while (i < gp.getPlayer().getMaxMana()) {
			g2.drawImage(crystal_blank, xLocation, yLocation, null);
			++i;
			xLocation += gp.getTileSize();
		}
		
		i = 0;
		xLocation = gp.getTileSize()/2;
		yLocation = gp.getTileSize() * 2;
		//Draw the current player's mana
		while (i < gp.getPlayer().getMana()) {
			g2.drawImage(crystal_full, xLocation, yLocation, null);
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
		if (gp.getGameState() != GameState.TRADE) {
			g2.drawString(moveDialogue, dialogueX, dialogueY);
		}
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
	
	
	private final void drawStatusScreen() {
		
		/**
		 * Frame Creation
		 */
		final int frameX = gp.getTileSize() * 2,
				  frameY = gp.getTileSize()/2,
				  frameWidth = gp.getTileSize() * 11,
				  frameHeight = gp.getTileSize() * 13 ;
		Player player = gp.getPlayer();
		
		drawSubWindow (frameX, frameY, frameWidth, frameHeight);
		
		/**
		 * Draw Labels
		 */
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(30F));
		
		int valueX = frameX + 30,
			valueY = frameY + gp.getTileSize(),
			lineHeight = 36,
			rectTailX = (frameX + frameWidth) - gp.getTileSize() * 3,
			tempCount = 0;
		
		/**
		 * DRAWS DESCRIPTION Frame 
		 */
		
		final int descFrameX = frameX + frameWidth + 10,
				  descFrameY = frameY,
				  descFrameWidth = gp.getTileSize() * 5,
				  descFrameHeight = gp.getTileSize() * 13;
		drawSubWindow(descFrameX, descFrameY, descFrameWidth, descFrameHeight);
		
		for (String label : player.getUpgradeValue().keySet()) {
			if (tempCount == statusCursor + 2)  {
				g2.setColor(nameColor1);
				g2.drawString(label, frameX + frameWidth + 30, frameY + gp.getTileSize());
				g2.setColor(optionColor);
			}
			g2.drawString(label, valueX, valueY);
			++tempCount;
			valueY += lineHeight;
			g2.setColor(Color.white);
		}
		
		valueY = frameY + gp.getTileSize();
		int defaultRectTailX = rectTailX;
		g2.setStroke(new java.awt.BasicStroke(1));
		
		
		/**
		 * DRAW UPGRADE SQUARES
		 */
		//Use iterator to loop through the hashmap
		//entry.set().iterator - retrieves all the elements from the hashmap and places the cursor of the
		//iterator at the first element
		Iterator<Entry<String, Integer>> upgradeIterator = player.getUpgradeValue().entrySet().iterator();
		int upgradeLevel = 0;
		
		for (int i = 0; i < player.getUpgradeValue().size() - 3; ++i) {
			
			if (upgradeIterator.hasNext()) {
					Map.Entry<String,Integer> entry = upgradeIterator.next();
					if (entry.getValue() != null) { 
						upgradeLevel = entry.getValue();
					}
			}
			
		
			for (int a = 0; a < 3; ++a) {
				if (i == 0 || i == 1) continue;
				//skips the first two labels
				if (i == statusCursor + 2) g2.setColor(optionColor);
				g2.drawRect(rectTailX + 30 * a, valueY - 20, 21, 21);
				for (int b = 0; b < upgradeLevel; ++b) {
					g2.fillRect(rectTailX + 2 + 30 * b, valueY - 18, 18, 18);
				}
			}
			g2.setColor(Color.white);
			valueY += lineHeight;
			rectTailX = defaultRectTailX;
		}
		
		
		/**
		 * Draw Matching Values
		 */
		int tailX = (frameX + frameWidth) - gp.getTileSize() * 4;
		valueY = frameY + gp.getTileSize();
		
		g2.drawString(player.getName(), getXValuesAlign(player.getName(), tailX), valueY);
		valueY += lineHeight;
		
		
		
		int [] playerAttributes = {
				player.getHealthRegen(),
				player.getMaxMana(),
				player.getManaRegen(),
				player.getTotalAttack(),
				player.getTotalDefense(),
				player.getDexterity(),
				player.getStamina(),
				player.getSpeed(),
				player.getKnockback(),
				player.getCriticalHit(),
		};
		
		g2.drawString(String.valueOf(player.getLevel()), getXValuesAlign(String.valueOf(player.getLevel()), tailX), valueY);
		valueY += lineHeight;
		int defaultY = valueY;
	
		if (statusCursor == 0) g2.setColor(optionColor);
		String value = String.valueOf(player.getLife() + "/" + player.getMaxLife());
		g2.drawString(value, getXValuesAlign(value, tailX), valueY);
		valueY += lineHeight;
		g2.setColor(Color.white);
		
		for (int i = 0; i < playerAttributes.length; ++i) {
			if (i == statusCursor - 1) g2.setColor(optionColor);
			g2.drawString(String.valueOf(playerAttributes[i]), getXValuesAlign(String.valueOf(playerAttributes[i]), tailX), valueY);
			valueY += lineHeight;
			g2.setColor(Color.white);
		}
	
		if (statusCursor == 11) g2.setColor(optionColor);
		value = String.valueOf(player.getExperience() + "/" + gp.getPlayer().getNextLevelExperience());
		g2.drawString(value, getXValuesAlign(value, tailX - gp.getTileSize() * 2), valueY);
		g2.drawString("Reset", getXValuesAlign("Reset", tailX), valueY);
		g2.drawString("Points: ", getXValuesAlign("Points: ", tailX + gp.getTileSize() * 2), valueY);
		g2.drawString(String.valueOf(player.getUpgradePoints()), getXValuesAlign(String.valueOf(player.getUpgradePoints()), tailX + gp.getTileSize() * 3), valueY);
		valueY += lineHeight;
		g2.setColor(Color.white);
		
		g2.drawString(String.valueOf(player.getCoin()), getXValuesAlign(String.valueOf(gp.getPlayer().getCoin()), tailX), valueY);	
		valueY += 11;
		if (player.getEquippedWeapon() != null) {
			g2.drawImage(player.getEquippedWeapon().getDown1(), tailX - gp.getTileSize(), valueY, null);
		}
		if (player.getEquippedDefense() != null) {
			g2.drawImage(player.getEquippedDefense().getDown1(), tailX, valueY , null);
		}
		
		if (player.getEquippedTool() != null) {
			g2.drawImage(player.getEquippedTool().getDown1(), tailX + gp.getTileSize(), valueY , null);
		}
		
		/**
		 * Draw status cursor
		 */
		g2.setColor(optionColor);
		int tempValY = defaultY + (lineHeight * statusCursor);
		g2.drawString(">", tailX - gp.getTileSize(), tempValY);
		g2.setColor(Color.white);
		
		
		g2.drawString("CURRENT LEVEL: ", descFrameX + 20, descFrameY + descFrameHeight - 20);
		g2.setColor(Color.white);
		
		int descLine = descFrameY;
		
		for (String desc : StatusAttribute.getAttributeDescription(statusCursor).split("\n")) {
			
			g2.drawString(desc, descFrameX + 20, descLine + gp.getTileSize() * 2);
			descLine += 40;
			
		}
	
	}
	
	private final void drawSubtitleMsg() {
		
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
	
	private final void drawPlayerInventoryScreen() {
		Player player = gp.getPlayer();
		int tileSize = gp.getTileSize();
		/**
		 * 
		 * INVENTORY FRAME
		 */
		int frameX = tileSize *2,			
			frameWidth = tileSize * 16,
			frameHeight = tileSize * 7 + 10,
			frameY = tileSize;
		
		if (this.tradeState == TradeState.BUY || this.tradeState == TradeState.SELL) {
			frameWidth = tileSize * 13 + 40;
			frameHeight = tileSize * 5 + 20;
			frameY = tileSize * 8;
		}
		
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
	
		
		final int defaultSlotX = frameX + 40,
				  defaultSlotY = frameY + 30;
		int slotX = defaultSlotX,
			slotY = defaultSlotY;
		
	
		/**
		 * DRAW PLAYER'S ITEMS
		 */
		//max inventory size is 12, as soon as there is no object inside the array, starts drawing out those x'es inside the inventory slot
		for (int i = 0; i < 12; ++i) {
			g2.setColor(Color.LIGHT_GRAY);
			g2.setStroke(new BasicStroke (2));
			if (i < player.getInventory().size()) {	
				Entity currentItem = (Entity) player.getInventory().get(i);
				
				//Draw equipped inventory items
				if (currentItem.equals(player.getEquippedWeapon())) {
					g2.setColor(equippedWeaponColor);		
					g2.fillRoundRect(slotX, slotY, gp.getTileSize() * 2, gp.getTileSize() * 2, 10, 10);	
				} else if (currentItem.equals(player.getEquippedDefense())) {
					g2.setColor(equippedDefenseColor);	
					g2.fillRoundRect(slotX, slotY, gp.getTileSize() * 2, gp.getTileSize() * 2, 10, 10);	
				} else if (currentItem.equals(player.getEquippedTool())) {
					g2.setColor(equippedToolColor);
					g2.fillRoundRect(slotX, slotY, gp.getTileSize() * 2, gp.getTileSize() * 2, 10, 10);	
				}
					
				//Draws the actual object after equip color
				g2.drawImage(UtilityTool.scaleImage(currentItem.getDown1(), tileSize * 2, tileSize * 2), slotX, slotY, null);
				g2.drawRoundRect(slotX, slotY, tileSize * 2, tileSize * 2, 10, 10);
				
			} else {
				g2.drawRoundRect(slotX, slotY, gp.getTileSize() * 2, gp.getTileSize() * 2, 10, 10);
				g2.setStroke(new BasicStroke (1));
				g2.drawLine(slotX, slotY, slotX + gp.getTileSize() * 2, slotY + gp.getTileSize() * 2);
				g2.drawLine(slotX + gp.getTileSize() * 2, slotY, slotX, slotY + gp.getTileSize() * 2);
			}
			
		
			
			slotX += gp.getTileSize() * 2;
		
			
			if (this.tradeState == TradeState.SELECT || this.tradeState == TradeState.LEAVE) {
				if (i % 4 == 3) {
					slotX = defaultSlotX;
					slotY += gp.getTileSize() * 2;
				}
			} else {
				if (i % 6 == 5) {
					slotX = defaultSlotX;
					slotY += gp.getTileSize() * 2;
				}
			}
			
			
		}
		
		/**
		 * CURSOR 
		 */
		if (this.tradeState == TradeState.SELL || this.tradeState == TradeState.SELECT || this.tradeState == TradeState.LEAVE) {
		int cursorX = defaultSlotX + (gp.getTileSize() * 2 * slotCol),
			cursorY = defaultSlotY + (gp.getTileSize() * 2 * slotRow),
			cursorWidth = gp.getTileSize() * 2,
			cursorHeight = gp.getTileSize() * 2;
		
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke (8));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
	
		}
		
		/**
		 * DESCRIPTION Frame
		 */
		int itemIndex = slotCol + (slotRow * 4);
		if (this.tradeState != TradeState.BUY && this.tradeState != TradeState.SELL) {
		int descriptionFrameX = frameX,
			descriptionFrameY = frameY + frameHeight + 10,
			descriptionFrameWidth = frameWidth,
			descriptionFrameHeight = gp.getTileSize() * 4;
		drawSubWindow(descriptionFrameX, descriptionFrameY, descriptionFrameWidth, descriptionFrameHeight);
		
		//Description text
		int descriptionTextX = descriptionFrameX + 20,
			descriptionTextY = descriptionFrameY + gp.getTileSize();
		String emptySlotDescription = "Nothing to look. Nothing to see. Completely empty... OR?";
		
		/*
		 * Get item index
		 * 
		 * Max of 3 rows and 4 columns
		 */
	
		
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(42F));
		
		if (itemIndex < player.getInventory().size()) {
			
		try {
			//use 1 for normal items, use 2 for much more serious items
			g2.setColor(nameColor1);
			g2.drawString(player.getInventory().get(itemIndex).getName(), descriptionTextX, descriptionTextY);
			descriptionTextY += 42;
			
			g2.setFont(g2.getFont().deriveFont(36F));
			g2.setColor(Color.white);
			
			for (String desc : player.getInventory().get(itemIndex).getObjectDescription().split("\n")) {
			
			g2.drawString(desc, descriptionTextX, descriptionTextY);
			descriptionTextY += 40;
			
		}
		}catch (Exception e) {
			
		}
		} else {
			//use 1 for normal items, use 2 for much more serious items
			g2.setColor(nameColor1);
			g2.drawString("Empty Slot", descriptionTextX, descriptionTextY);
			descriptionTextY += 42;
			
			g2.setFont(g2.getFont().deriveFont(36F));
			g2.setColor(Color.white);
			g2.drawString(emptySlotDescription, descriptionTextX, descriptionTextY);
		}
		}
		/*
		 * Draw use options for items currently being pointed at by cursor
		 * CHECK the type of the object in inventory then call the correct method to draw the correct one
		*/
		
		
		if (inventoryState == InventoryState.OPTIONS) {			
			if (itemIndex < player.getInventory().size()) {
			ObjectType type = player.getInventory().get(itemIndex).getInventoryType();
			GameObject selectedItem = player.getInventory().get(itemIndex);
			int inventoryOptionsFrameX = tileSize * 11,
				inventoryOptionsFrameY = defaultSlotY,
				inventoryOptionsFrameWidth = tileSize * 3 + 25,
				inventoryOptionsFrameHeight = tileSize * 3 + 5,
				optionCursorX = inventoryOptionsFrameX + inventoryOptionsFrameWidth - 35,
				lineHeight = 36,
				optionCursorY = inventoryOptionsFrameY + tileSize + lineHeight * inventoryOptionCursor;
			
			this.drawSubWindow(inventoryOptionsFrameX, inventoryOptionsFrameY, inventoryOptionsFrameWidth, inventoryOptionsFrameHeight);
			//draw cursor for the thing later
			this.drawInventoryOptions(inventoryOptionsFrameX, inventoryOptionsFrameY, type, inventoryOptionCursor, selectedItem);
			
			g2.setColor(optionColor);
			g2.drawString("<", optionCursorX, optionCursorY);
			g2.setColor(Color.white);
			
			
			} else {
				//value is out of bounds so the inventory state is reset back to normal
				this.inventoryState = InventoryState.NORMAL;
			}
		}
		
	
	}
	
	private final void drawNPCInventoryScreen(Entity entity) {
		
		int tileSize = gp.getTileSize();
		
		if (tradeState == TradeState.BUY || tradeState == TradeState.SELL) {
		/**
		 * 
		 * INVENTORY FRAME
		 */
		final int frameX = tileSize * 2,			
			frameWidth = tileSize * 13 + 40,
			frameHeight = tileSize * 3 + 20,
			frameY = tileSize;
	
		drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		final int defaultSlotX = frameX + 40,
				  defaultSlotY = frameY + 30;
		int slotX = defaultSlotX,
			slotY = defaultSlotY;
		
		NPC npc = (NPC) entity;
		
	
		/**
		 * DRAW NPC's ITEMS
		 */
		//max inventory size for an npc is 6, as soon as there is no object inside the array, starts drawing out those x'es inside the inventory slot
		for (int i = 0; i < 6; ++i) {
			g2.setColor(Color.LIGHT_GRAY);
			g2.setStroke(new BasicStroke (2));
			if (i < npc.getNPCInventory().size()) {	
				Entity currentItem = npc.getNPCInventory().get(i);
		
				
				g2.drawImage(UtilityTool.scaleImage(currentItem.getDown1(), tileSize * 2, tileSize * 2), slotX, slotY, null);
				
			} 
			g2.drawRoundRect(slotX, slotY, tileSize * 2, tileSize * 2, 10, 10);
//				g2.setStroke(new BasicStroke (1));
//				g2.drawLine(slotX, slotY, slotX + gp.getTileSize() * 2, slotY + gp.getTileSize() * 2);
//				g2.drawLine(slotX + gp.getTileSize() * 2, slotY, slotX, slotY + gp.getTileSize() * 2);
			
			
		
			
			slotX += gp.getTileSize() * 2;
			
				if (i % 6 == 5) {
					slotX = defaultSlotX;
					slotY += gp.getTileSize() * 2;
				}
			
			
			
		}
		
		if (npc instanceof Merchant && this.tradeState == TradeState.BUY) {
			int cursorX = defaultSlotX + (tileSize * 2 * slotCol),
				cursorY = defaultSlotY + (tileSize * 2 * slotRow),
				cursorWidth = tileSize * 2,
				cursorHeight = tileSize * 2;
			
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke (8));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		
		}
		}
	}
	
	private final void drawInventoryOptions (int x, int y, ObjectType type, int cursorLoc, GameObject selectedItem) {
		int lineHeight = 36,
			optionX = x + 20,
			optionY = y + 50;
		Player player = gp.getPlayer();
		if (cursorLoc == InventoryState.EQUIP_OPTION) g2.setColor(optionColor);
		switch (type) {
		case CONSUMMABLE, INTERACT -> {
			g2.drawString("USE", optionX, optionY);
		}
		case ATTACK, DEFENSE, TOOL, ACCESSORY -> {
			if (selectedItem.equals(player.getEquippedDefense()) || selectedItem.equals(player.getEquippedWeapon()) 
					|| selectedItem.equals(player.getEquippedTool())){
				g2.drawString("UNEQUIP", optionX, optionY);
			} else {
				g2.drawString("EQUIP", optionX, optionY);
			}
			
		} 
		case NONPICKUP -> throw new IllegalArgumentException("Cannot be defined: " + type);
		default -> throw new IllegalArgumentException("Unexpected value: " + type);
	}
		
		g2.setColor(Color.white);
		
		if ( cursorLoc == InventoryState.DISCARD_OPTION) { g2.setColor(optionColor);}
		g2.drawString("DISCARD", optionX, optionY + lineHeight);
		
		g2.setColor(Color.white);
		
		if ( cursorLoc == InventoryState.CANCEL_OPTION)  g2.setColor(optionColor);
		g2.drawString("CANCEL", optionX, optionY + lineHeight * 2);
}
	
	private final void drawGameOverScreen() {
		//Draw dark background
		Color background = new Color (0,0,0, 220);
		g2.setColor(background);
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
	
		int x, y;
		g2.setFont(maruMonica);
		String text = "You Died";
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));
		
		//Letters shadow
		g2.setColor(Color.black);
		x = getXCenter(text);
		y = gp.getTileSize() * 4;
		g2.drawString(text, x, y);
		
		//Main class
		g2.setColor(Color.white);
		g2.drawString(text, x - 6, y - 6);
		
		
		//New game
		g2.setFont(g2.getFont().deriveFont(50f));
		
		//Restart from last save
		text = "Load Last Save";
		g2.drawString(text, getXCenter(text), y += gp.getTileSize() * 4);
		
		//Start a new game
		text = "New Game";
		g2.drawString(text, getXCenter(text), y + gp.getTileSize() * 2);
		
		//Quit to title screen
		text = "Quit";
		g2.drawString(text, getXCenter(text), y + gp.getTileSize() * 4);
		
		g2.drawString(">", x - gp.getTileSize(), y + gp.getTileSize() * 2 * gameOverCursor);
		
				
		
		
	
	}

	private final void drawTransition() {
		++transitionCounter;
		
		//alpha value increasing from 0 to 250 - transparent to opaque
		g2.setColor(new Color(0, 0, 0, transitionCounter * 5));
		g2.fillRect(0, 0, gp.getScreenWidth(), gp.getScreenHeight());
		
		if (transitionCounter >= 50) {
			transitionCounter = 0;
			gp.getEventHandler().getEventObject().transitionBackEffect();
		}
	}

	private final void drawTradeScreen() {
		switch (tradeState) {
		case SELECT ->{drawTradeSelect();}
		case BUY ->{drawTradeBuy();}
		case SELL ->{drawTradeSell();}
		case LEAVE -> {}
		default -> {}
		}
		gp.getKeyHandler().setDialoguePress(false);
	}
	
	private final void drawTradeSelect() {
		drawDialogueScreen();
		int x = gp.getTileSize() * 14,
			y = gp.getTileSize() * 4 + 15,
			width = gp.getTileSize() * 4,
			height = gp.getTileSize() * 4;
		drawSubWindow(x, y, width, height);
	

		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(40F));
		//DRAW SELECT ITEM WINDOW
		g2.setColor(optionColor);
		g2.drawString(">", x + 15, y + gp.getTileSize() * (tradeCursor + 1));
		g2.setColor(Color.white);
		
		if (tradeCursor == TradeState.BUY_OPTION) g2.setColor(optionColor);
		g2.drawString("BUY", x + gp.getTileSize(), y + gp.getTileSize());
		g2.setColor(Color.white);
		
		if (tradeCursor == TradeState.SELL_OPTION) g2.setColor(optionColor);
		g2.drawString("SELL", x + gp.getTileSize(), y + gp.getTileSize() * 2);
		g2.setColor(Color.white);
		
		if (tradeCursor == TradeState.LEAVE_OPTION) g2.setColor(optionColor);
		g2.drawString("GOODBYE!", x + gp.getTileSize(), y + gp.getTileSize() * 3);
		g2.setColor(Color.white);
		
		
		
	}
	private final void drawTradeBuy() {
		drawPlayerInventoryScreen();
		drawTradeItemDescription();
		drawNPCInventoryScreen(gp.getNPCS().get(MapsConstants.TRADE).get(Merchant.MERCHANT_INDEX));
		drawPlayerCoinTrade();
		drawPlayerGoBackInventory();
	}
	
	private final void drawTradeSell() {
		drawPlayerInventoryScreen();
		drawTradeItemDescription();
		drawNPCInventoryScreen(gp.getNPCS().get(MapsConstants.TRADE).get(Merchant.MERCHANT_INDEX));
		drawPlayerCoinTrade();
		drawPlayerGoBackInventory();
	}
	
	private final void drawPlayerCoinTrade () {
		//Draw player's coin window
		int x = gp.getTileSize() * 16, 
			y = gp.getTileSize() * 8,
			width = gp.getTileSize() * 3,
			height = (gp.getTileSize() * 5 ) / 2;
		int coinWidth = 40;
		drawSubWindow(x, y, width, height);
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(48F));
		if (gp.getPlayer().getCoin() >= 100) { coinWidth = 20; }
		if (gp.getPlayer().getCoin() >= 1000) { coinWidth = 10; }
		g2.drawString("" + gp.getPlayer().getCoin(), x + coinWidth, y + height/2 + 20);
		g2.drawImage(coin.getDown1(), x + width/2 - 10, y + height/2 - gp.getTileSize(), gp.getTileSize() * 2, gp.getTileSize() * 2, null);
	}
	
	private final void drawPlayerGoBackInventory() {
		int x = gp.getTileSize() * 16, 
				y = gp.getTileSize() * 8 + ((gp.getTileSize() * 5 + 40) / 2),
				width = gp.getTileSize() * 3,
				height = (gp.getTileSize() * 5 ) / 2;
		drawSubWindow(x, y, width, height);
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(28F));
		g2.drawString("[ESC] Leave ", x + 10, y + height/2);
	}
	
	private final void drawTradeItemDescription () { 
		int tileSize = gp.getTileSize();
		int itemIndex;
		final int frameX = tileSize * 2,
				frameWidth = tileSize * 3 + 40,
				frameHeight = tileSize * 3 + 20,
				frameY = tileSize * 4 + 25;
	
		
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(38F));
		
		int textY = frameY + frameHeight/2;
		int textX = frameX + 20;
		int descriptionFrameWidth = tileSize * 10;
		this.drawSubWindow(frameX, frameY, frameWidth, frameHeight);
		
		this.drawSubWindow(frameX * 3 - 5, frameY, descriptionFrameWidth, frameHeight);
		
		
		this.drawSubWindow(frameX * 7, frameY + frameHeight - tileSize, tileSize * 2, frameHeight/2);
		if (this.tradeState == TradeState.SELL) {
			itemIndex = slotCol + (slotRow * 6);
		try {
			GameObject currentItem = gp.getPlayer().getInventory().get(itemIndex);
			String tradeName = currentItem.getTradeName();
			g2.setColor(Color.YELLOW);
			for (String line : tradeName.split("\n")) {
				g2.drawString(line, textX + 10, textY - 10);
				//increase the display
				textY += gp.getTileSize();
			}
			g2.setColor(Color.WHITE);
			g2.drawString(currentItem.getTradeDescription(), frameX * 3 + 20, textY - tileSize * 2 + 10);
			
			g2.setColor(Color.GREEN);
			g2.drawString("" + currentItem.getSellPrice(), frameX * 7 + tileSize, textY - 10);
			
		} catch (Exception e) {
				
		}
			
		} else if (this.tradeState == TradeState.BUY) {
			itemIndex = slotCol;
			try {
				GameObject currentItem = ((NPC)gp.getNPCS().get(MapsConstants.TRADE).get(Merchant.MERCHANT_INDEX)).getNPCInventory().get(itemIndex);
				String tradeName = currentItem.getTradeName();
				g2.setColor(Color.YELLOW);
				for (String line : tradeName.split("\n")) {
					g2.drawString(line, textX + 10, textY - 10);
					//increase the display
					textY += gp.getTileSize();
				}
				
				
				g2.setColor(Color.WHITE);
				g2.drawString(currentItem.getTradeDescription(), frameX * 3 + 20, textY - tileSize * 2 + 10);
				
				g2.setColor(Color.RED);
				g2.drawString("" + currentItem.getBuyPrice(), frameX * 7 + tileSize, textY - 10);
				} catch (Exception e) {
					
				}
			
				
		}
		
		g2.drawImage(coin.getDown1(), frameX * 7, frameY + frameHeight - tileSize + 10, tileSize + 10, tileSize + 10, null);
		
		g2.setColor(Color.WHITE);
		
		

				
	}
	
	
}
