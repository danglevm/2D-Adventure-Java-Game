package adventureGame2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import enums.GameState;
import enums.InventoryState;
import enums.TitleState;

public class KeyHandler implements KeyListener{

	GamePanel gp;

	private boolean upPress, downPress, leftPress, rightPress; 
	private boolean dialogue; 
	private boolean fpsDisplay; 
	private boolean pauseQuote;
	private boolean interaction;
	

	
	public KeyHandler (GamePanel gp) {
		this.gp = gp;
		dialogue = false;
		fpsDisplay = false;
		pauseQuote = false;
		interaction = false;
	}
	
	/**
	 * Setters and getters
	 */
	public boolean getDialoguePress () { return dialogue;}
	public void setDialoguePress (boolean dialoguePressed) { this.dialogue = dialoguePressed; }
	
	public boolean getFpsDisplay () { return fpsDisplay;}
	public void getFpsDisplay (boolean fpsDisplay) { this.fpsDisplay = fpsDisplay; }
	
	public boolean getInteraction () { return interaction;}
	public void setInteraction (boolean interaction) { this.interaction = interaction; }
	
	public boolean getPauseQuote () { return pauseQuote;}
	public void setPauseQuote (boolean pauseQuote) { this.pauseQuote = pauseQuote;} 
	
	public boolean getUpPress () { return upPress;}
	public void setUpPress (boolean upPress) { this.upPress = upPress;} 
	
	public boolean getDownPress () { return downPress;}
	public void setDownPress (boolean downPress) { this.downPress = downPress;} 
	
	public boolean getRightPress () { return rightPress;}
	public void setRightPress (boolean rightPress) { this.rightPress = rightPress;} 
	
	public boolean getLeftPress () { return leftPress;}
	public void setLeftPress (boolean leftPress) { this.leftPress = leftPress;} 
			
	/**
	 * CLASS Methods
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		
	/*
	 * Get correct keys for each game states
	 */
	switch (gp.getGameState()) {
	
	case TITLE -> {
		/**
		 * Multiple states in title screen
		 */
		
		switch (gp.getGameUI().getTitleScreenState()) {
		
		case WELCOME -> { welcomeTitleState(code); }
		
		case CHARACTERSELECT -> { characterSelectionTitleState(code); }
		}
		
	}
	
	case PLAY -> { playState (code); }
	
	case PAUSE -> { pauseState (code); }
	
	case DIALOGUE -> { dialogueState (code); }
	
	case STATUS -> { statusState (code); }
	
	case INVENTORY -> { 
		switch (gp.getGameUI().getInventoryState()) {
		
		case NORMAL -> { inventoryState (code); }
		
		case OPTIONS -> { inventoryOptionsState(code); }
		}
	}
	
	}
	}
	
	/**
	 * 
	 * PRIVATE METHODS
	 * 
	 **/
	
	private final void welcomeTitleState (int code) {
		
		if (code == KeyEvent.VK_W) --gp.getGameUI().cursorNum;  
		
		if (code == KeyEvent.VK_S) ++gp.getGameUI().cursorNum;
		
		
		if (code == KeyEvent.VK_ENTER) {
			
		
			/*
			 * New game
			 */
			if (gp.getGameUI().cursorNum == 0) gp.getGameUI().setTitleScreenState(TitleState.CHARACTERSELECT);
			
			else if (gp.getGameUI().cursorNum == 1) {
				/*
				 * Load save
				 */
			} else if (gp.getGameUI().cursorNum == 2) {
				/*
				 * Settings
				 */
			} else {
				System.exit(0);
			}
			gp.playSE(11);
		}
		
		if (gp.getGameUI().cursorNum > 3) gp.getGameUI().cursorNum = 3;
		
		if (gp.getGameUI().cursorNum < 0) gp.getGameUI().cursorNum = 0;
		
	}
	
	private final void characterSelectionTitleState (int code) {
		
		if (code == KeyEvent.VK_A) --gp.getGameUI().cursorNum;

		if (code == KeyEvent.VK_D) ++gp.getGameUI().cursorNum;
		
		
		if (code == KeyEvent.VK_ENTER) {
			/*
			 * Blue boy
			 */	
			if (gp.getGameUI().cursorNum == 0) {
				gp.setGameState(GameState.PLAY);
				gp.playMusic(0);
			} else {
				/*
				 * Yellow girl
				 */	
			}
			gp.playSE(11);
		}
		
		if (gp.getGameUI().cursorNum > 1) gp.getGameUI().cursorNum = 1;
		 
		if (gp.getGameUI().cursorNum < 0) gp.getGameUI().cursorNum = 0;
		
	}
	
	private final void playState (int code) {
		/*
		 * Player moving
		 */	
		if (code == KeyEvent.VK_W) upPress = true;
		
		if (code == KeyEvent.VK_A) leftPress = true;
	
		if (code == KeyEvent.VK_S) downPress = true;
		
		if (code == KeyEvent.VK_D) rightPress = true;
		
		
		/*
		 * Pause the game
		 */
		if (code == KeyEvent.VK_ESCAPE) {
			gp.setGameState(GameState.PAUSE);
			pauseQuote = true;
		} 
		
		//Change to status state
		if (code == KeyEvent.VK_C) gp.setGameState(GameState.STATUS);
		
		if (code == KeyEvent.VK_ENTER) dialogue = !dialogue;
		
		//Change to inventory state
		if (code == KeyEvent.VK_E) {
			gp.setGameState(GameState.INVENTORY);
			gp.playSE(10);
		}
		

		/*
		 * display FPS and player X and Y
		 */
		if (code == KeyEvent.VK_T)	fpsDisplay = !fpsDisplay;
		
		
		/*
		 * Trigger player to use current equipped weapon
		 */
		if (code == KeyEvent.VK_J) gp.getPlayer().setPlayerAttack(true);
		
		/**
		 * Trigger player to use current equipped tool
		 */
		if (code == KeyEvent.VK_K) gp.getPlayer().setPlayerUseTool(true);
	
		/**
		 * Trigger player to use current spell
		 */
		if (code == KeyEvent.VK_L) gp.getPlayer().setPlayerCastSpell(true);
		/*
		 * Player stands near interactive event and can trigger events
		 */
		if (gp.getEventHandler().getInteraction()) {
			if (code == KeyEvent.VK_X) {
				interaction = true;
			}
		}
	}
	
	
	/*
	 * Game state methods
	 */
	
	private final void pauseState (int code) {
		if (code == KeyEvent.VK_ESCAPE) gp.setGameState(GameState.PLAY);
		
		if (code == KeyEvent.VK_W) --gp.getGameUI().pauseCursor;
		
		if (code == KeyEvent.VK_S) ++gp.getGameUI().pauseCursor;
		
		if (gp.getGameUI().pauseCursor > 7) gp.getGameUI().pauseCursor = 7;
		 
		if (gp.getGameUI().pauseCursor < 0) gp.getGameUI().pauseCursor = 0;
	}
	
	private final void dialogueState (int code) {
		if (code == KeyEvent.VK_ENTER) gp.setGameState(GameState.PLAY);
		
	}
	
	private final void statusState (int code) {
		
		if (code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE) gp.setGameState(GameState.PLAY);  
		
		if (code == KeyEvent.VK_W) --gp.getGameUI().statusCursor;
		
		if (code == KeyEvent.VK_S) ++gp.getGameUI().statusCursor;
		
		if (gp.getGameUI().statusCursor > 11) gp.getGameUI().statusCursor = 11;
		 
		if (gp.getGameUI().statusCursor < 0) gp.getGameUI().statusCursor = 0;
		
		if (code == KeyEvent.VK_ENTER) 
			if (gp.getPlayer().upgradeAttribute(gp.getGameUI().statusCursor)) {
				gp.playSE(15);
			} else {
				gp.playSE(13);
			};
		
	}
	
	private final void inventoryState (int code) {
		
		UI ui = gp.getGameUI();
		
		if (code == KeyEvent.VK_E || code == KeyEvent.VK_ESCAPE) gp.setGameState(GameState.PLAY);
		
		if (code == KeyEvent.VK_D) { ui.setSlotColumn(ui.getSlotColumn() + 1); gp.playSE(9); }
		
		if (code == KeyEvent.VK_A) { ui.setSlotColumn(ui.getSlotColumn() - 1); gp.playSE(9); }
		
		if (code == KeyEvent.VK_W) { ui.setSlotRow(ui.getSlotRow() - 1); gp.playSE(9); }
		
		if (code == KeyEvent.VK_S) { ui.setSlotRow(ui.getSlotRow() + 1); gp.playSE(9); }
		
		if (code == KeyEvent.VK_ENTER) { 
			ui.setInventoryState(InventoryState.OPTIONS);
			gp.playSE(12);
			}
		
		if (ui.getSlotRow() < 0) ui.setSlotRow(0);
		
		if (ui.getSlotRow() > 2) ui.setSlotRow(2);
		
		if (ui.getSlotColumn() < 0) ui.setSlotColumn(0);
		
		if (ui.getSlotColumn() > 3) ui.setSlotColumn(3);
	
		
	}
	
	private final void inventoryOptionsState (int code) {
		UI ui = gp.getGameUI();
		
		if (code == KeyEvent.VK_W) --ui.inventoryOptionCursor;
		
		if (code == KeyEvent.VK_S) ++ui.inventoryOptionCursor;
		
		if (ui.inventoryOptionCursor < 0) ui.inventoryOptionCursor = 0;
		
		if (ui.inventoryOptionCursor > 2) ui.inventoryOptionCursor = 2;
		
		if (code == KeyEvent.VK_ENTER) {
			ui.setInventoryState(InventoryState.NORMAL); 
			gp.playSE(12);
			gp.getPlayer().handleInventoryOptions(ui.inventoryOptionCursor);
			ui.inventoryOptionCursor = 0;
		}
		
		if (code == KeyEvent.VK_ESCAPE) {
			ui.setInventoryState(InventoryState.NORMAL);
			ui.inventoryOptionCursor = 0;
		}
		
	}
	
		
	@Override
	public final void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		/*
		 * Check if keys are being pressed
		 */
		if (code == KeyEvent.VK_W) upPress = false;
		
		if (code == KeyEvent.VK_A) leftPress = false;
		 
		if (code == KeyEvent.VK_S) downPress = false;
		
		if (code == KeyEvent.VK_D) rightPress = false;
		
		if (code == KeyEvent.VK_L) gp.getPlayer().setPlayerCastSpell(false);
	
	}
	

}