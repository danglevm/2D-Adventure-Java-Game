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
import entity.Player;
import enums.GameState;
import enums.ObjectType;
import enums.InventoryState;
import enums.TitleState;
import object.Heart;
import quotes.PauseQuotes;
import quotes.StatusAttribute;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	//Sub-states
	//0 - welcome screen; 1 - story paths
	private TitleState titleScreenState;
	private InventoryState inventoryState;
	
	//Quotes
	PauseQuotes pauseQuotes = new PauseQuotes();
	String pauseText = "", moveDialogue = "Press ENTER to continue...";
	
	//Font styles
	Font arial_30, arial_50, arial_70, maruMonica, purisa;
	//Dialogue
	private String currentDialogue = "";	
	Color nameColor1 = new Color(253, 218, 13);
	Color nameColor2 = new Color(196, 30, 58);
	
	Color optionColor = new Color(247, 135, 2);
	
	protected int cursorNum = 0, statusCursor = 0, inventoryOptionCursor = 0;
	
	//Drawing hearts
	private BufferedImage heart_full, heart_half, heart_blank;
	
	private String [] statusScreenLabels = {"NEW GAME", "LOAD SAVE", "SETTINGS", "QUIT"};
	
	private int slotCol = 0, slotRow = 0;
	//Drawing SubTitles
	ArrayList <String> subtitleMsg = new ArrayList <String> ();
	ArrayList <Integer> subtitleMsgCount = new ArrayList <Integer> ();
	
	
	//Drawing and setting UI
	public UI(GamePanel gp) {
		this.gp = gp;
		
		titleScreenState = TitleState.WELCOME;
		
		inventoryState = inventoryState.NORMAL;
	
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
		Entity heart = new Heart(gp);
		heart_full = heart.getImage1();
		heart_half = heart.getImage2();
		heart_blank = heart.getImage3();
	
	}
	/**
	 * Setters and getters
	 */
	
	public TitleState getTitleScreenState () { return titleScreenState; }
	
	public InventoryState getInventoryState () { return inventoryState; }
	
	public final int getItemIndex () { return slotCol + (slotRow * 4);}
	
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
		
		case STATUS -> { drawStatusScreen(); } 
		
		case INVENTORY -> { 
			drawInventoryScreen();
		}
		
		
		
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
	
	private final void drawStatusScreen() {
		
		/**
		 * Frame Creation
		 */
		final int frameX = gp.getTileSize(),
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
			rectTailX = (frameX + frameWidth) - gp.getTileSize() * 3;
		
		
		for (int i = 0; i < player.getLabelArray().length; ++i) {
			if (i == statusCursor + 2) g2.setColor(optionColor);
			g2.drawString(player.getLabelEntries(i), valueX, valueY);
			valueY += lineHeight;
			g2.setColor(Color.white);
		}
		
		valueY = frameY + gp.getTileSize();
		int defaultRectTailX = rectTailX;
		g2.setStroke(new java.awt.BasicStroke(1));
		
		for (int i = 0; i < player.getLabelArray().length - 3; ++i) {
			for (int a = 0; a < 3; ++a) {
				if (i == 0 || i == 1) continue;
				//skips the first two labels
				if (i == statusCursor + 2)g2.setColor(optionColor);
				g2.drawRect(rectTailX, valueY - 20, 20, 20);
				rectTailX += 30;
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
				player.getMana(),
				player.getManaRegen(),
				player.getTotalAttack(),
				player.getTotalDefense(),
				player.getDexterity(),
				player.getStamina(),
				player.getSpeed() - 3,
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
		
		g2.drawImage(player.getEquippedWeapon().getDown1(), tailX - gp.getTileSize(), valueY, null);
		g2.drawImage(player.getEquippedShield().getDown1(), tailX, valueY , null);
		
		
		/**
		 * Draw cursor
		 */
		g2.setColor(optionColor);
		int tempValY = defaultY + (lineHeight * statusCursor);
		g2.drawString(">", tailX - gp.getTileSize(), tempValY);
		g2.setColor(Color.white);
		
		/**
		 * DRAWS DESCRIPTION Frame and desc
		 */
		
		final int descFrameX = frameX + frameWidth + 10,
				  descFrameY = frameY,
				  descFrameWidth = gp.getTileSize() * 5,
				  descFrameHeight = gp.getTileSize() * 13;
		drawSubWindow(descFrameX, descFrameY, descFrameWidth, descFrameHeight);
		g2.setColor(nameColor1);
		g2.drawString(player.getLabelEntries(statusCursor + 2), descFrameX + 20, descFrameY + gp.getTileSize());
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
	
	private final void drawInventoryScreen() {
		Player player = gp.getPlayer();
		int tileSize = gp.getTileSize();
		/**
		 * 
		 * INVENTORY FRAME
		 */
		final int frameX = gp.getTileSize(),
			frameY = gp.getTileSize(),
			frameWidth = gp.getTileSize() * 16,
			frameHeight = gp.getTileSize() * 7 + 10;
		
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
				
				//Draw inventory equipment cursor
				if (currentItem.equals(player.getEquippedWeapon())) {
					g2.setColor(new Color(240,190,90));		
					g2.fillRoundRect(slotX, slotY, gp.getTileSize() * 2, gp.getTileSize() * 2, 10, 10);	
				} else if (currentItem.equals(player.getEquippedShield())) {
					g2.setColor(new Color(121,68,59));	
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
			
			if (i % 4 == 3) {
				slotX = defaultSlotX;
				slotY += gp.getTileSize() * 2;
			}
		}
		
		/**
		 * CURSOR 
		 */
		int cursorX = defaultSlotX + (gp.getTileSize() * 2 * slotCol),
			cursorY = defaultSlotY + (gp.getTileSize() * 2 * slotRow),
			cursorWidth = gp.getTileSize() * 2,
			cursorHeight = gp.getTileSize() * 2;
		
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke (8));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
	
		
		/**
		 * DESCRIPTION Frame
		 */
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
		int itemIndex = slotCol + (slotRow * 4);
		
		g2.setFont(maruMonica);
		g2.setFont(g2.getFont().deriveFont(42F));
		
		if (itemIndex < player.getInventory().size()) {
			
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
			
		} else {
			//use 1 for normal items, use 2 for much more serious items
			g2.setColor(nameColor1);
			g2.drawString("Empty Slot", descriptionTextX, descriptionTextY);
			descriptionTextY += 42;
			
			g2.setFont(g2.getFont().deriveFont(36F));
			g2.setColor(Color.white);
			g2.drawString(emptySlotDescription, descriptionTextX, descriptionTextY);
		}
		
		/*
		 * Draw use options for items currently being pointed at by cursor
		 * CHECK the type of the object in inventory then call the correct method to draw the correct one
		*/
		
		
		if (inventoryState == InventoryState.OPTIONS) {			
			if (itemIndex < player.getInventory().size()) {
			ObjectType type = player.getInventory().get(itemIndex).getInventoryType();
			int inventoryOptionsFrameX = tileSize * 10,
				inventoryOptionsFrameY = defaultSlotY,
				inventoryOptionsFrameWidth = tileSize * 3 + 25,
				inventoryOptionsFrameHeight = tileSize * 3 + 5,
				optionCursorX = inventoryOptionsFrameX + inventoryOptionsFrameWidth - 35,
				lineHeight = 36,
				optionCursorY = inventoryOptionsFrameY + tileSize + lineHeight * inventoryOptionCursor;
			
			this.drawSubWindow(inventoryOptionsFrameX, inventoryOptionsFrameY, inventoryOptionsFrameWidth, inventoryOptionsFrameHeight);
			//draw cursor for the thing later
			this.drawInventoryOptions(inventoryOptionsFrameX, inventoryOptionsFrameY, type, inventoryOptionCursor);
			
			g2.setColor(optionColor);
			g2.drawString("<", optionCursorX, optionCursorY);
			g2.setColor(Color.white);
			
			
			} else {
				//value is out of bounds so the inventory state is reset back to normal
				this.inventoryState = InventoryState.NORMAL;
			}
		}
		
	
	}
	
	private final void drawInventoryOptions (int x, int y, ObjectType type, int cursorLoc) {
		int lineHeight = 36,
			optionX = x + 20,
			optionY = y + 50;
		switch (type) {
		case CONSUMMABLE, INTERACT -> {
			
			if (cursorLoc == 0) g2.setColor(optionColor);
			
			g2.drawString("USE", optionX, optionY);
		}
		case ATTACK, DEFENSE, TOOL, ACCESSORY -> {
			
			if (cursorLoc == 0) g2.setColor(optionColor);
			
			g2.drawString("EQUIP", optionX, optionY);
		}
		case NONPICKUP -> throw new IllegalArgumentException("Cannot be defined: " + type);
		default -> throw new IllegalArgumentException("Unexpected value: " + type);
	}
		g2.setColor(Color.white);
		
		if ( cursorLoc == 1) { g2.setColor(optionColor);}
		g2.drawString("DISCARD", optionX, optionY + lineHeight);
		
		g2.setColor(Color.white);
		
		if ( cursorLoc == 2)  g2.setColor(optionColor);
		g2.drawString("CANCEL", optionX, optionY + lineHeight * 2);
}
	

	
}
