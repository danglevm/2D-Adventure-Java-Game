package adventureGame2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import enums_and_constants.Direction;
import enums_and_constants.GameOverState;
import enums_and_constants.GameState;
import enums_and_constants.InventoryState;
import enums_and_constants.MapsConstants;
import enums_and_constants.PauseState;
import enums_and_constants.TitleState;
import enums_and_constants.TradeState;
import npc.Merchant;
import npc.NPC;
import object.GameObject;

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
	
	case TRADE -> {tradeState(code);}
	
	case GAMEOVER -> { gameOverState(code);}
	
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
			if (gp.getGameUI().cursorNum == TitleState.NEW_GAME) gp.getGameUI().setTitleScreenState(TitleState.CHARACTERSELECT);
			
			else if (gp.getGameUI().cursorNum == TitleState.LOAD_SAVE) {
				/*
				 * Load save
				 */
			} else if (gp.getGameUI().cursorNum == TitleState.SETTINGS) {
				/*
				 * Settings
				 */
			} else if (gp.getGameUI().cursorNum == TitleState.EXIT){
				System.exit(0);
			}
			gp.playSE(Sound.SELECT_OPTION);
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
			if (gp.getGameUI().cursorNum == TitleState.BLUE_BOY) {
				gp.setGameState(GameState.PLAY);
				gp.playMusic(Sound.BACKGROUND_MUSIC);
			} else {
				/*
				 * Yellow girl
				 */	
			}
			gp.playSE(Sound.SELECT_OPTION);
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
			gp.playSE(Sound.INVETORY_OPEN);
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
	
	}
	
	
	/*
	 * Game state methods
	 */
	
	private final void pauseState (int code) {
		
		if (code == KeyEvent.VK_ESCAPE) { 
			
			switch (gp.getGameUI().pauseState) {
			case MENU -> {
				gp.setGameState(GameState.PLAY);
				gp.playSE(Sound.SELECT_OPTION);
			}
			
			case FULLSCREEN, KEYBINDINGS -> {
				gp.getGameUI().pauseState = PauseState.MENU;
				gp.playSE(Sound.SELECT_OPTION);
			}
			}
			
			
		}
		
		if (code == KeyEvent.VK_W) --gp.getGameUI().pauseCursor;
		
		if (code == KeyEvent.VK_S) ++gp.getGameUI().pauseCursor;
		
		if (gp.getGameUI().pauseCursor > 7) gp.getGameUI().pauseCursor = 7;
		 
		if (gp.getGameUI().pauseCursor < 0) gp.getGameUI().pauseCursor = 0;
		
		if (code == KeyEvent.VK_ENTER) {
			switch (gp.getGameUI().pauseCursor) {
			case PauseState.FULLSCREEN_OPTION -> {
				if (gp.getGameUI().pauseState == PauseState.MENU) {
					gp.setFullScreen(!gp.getFullScreen());
					gp.getGameUI().pauseState = PauseState.FULLSCREEN;
				}
			}
			
			case PauseState.MUSIC_OPTION -> {}
			
			case PauseState.SOUND_OPTION -> {}
			
			case PauseState.KEYBINDING_OPTION -> {
				if (gp.getGameUI().pauseState == PauseState.MENU) {
					gp.getGameUI().pauseState = PauseState.KEYBINDINGS;
				}
			}
			
			case PauseState.SUBTITLE_OPTION -> { 
				gp.setSubtitileState(!gp.getSubtitleState());
			}
			
			case PauseState.SAVEQUIT_OPTION -> {
				gp.getMusic().stop();
				gp.getGameUI().setTitleScreenState(TitleState.WELCOME);
				gp.setGameState(GameState.TITLE);
			}
			
			}
			gp.playSE(Sound.SELECT_OPTION);
		}
		if (gp.getGameUI().pauseState == PauseState.MENU)
			if (gp.getGameUI().pauseCursor == PauseState.MUSIC_OPTION) {
				if (code == KeyEvent.VK_A && gp.getMusic().getVolumeScale() > 0) {
					gp.getMusic().setVolumeScale(gp.getMusic().getVolumeScale() - 1);
					gp.getMusic().adjustVolume();
				}
				
				if (code == KeyEvent.VK_D && gp.getMusic().getVolumeScale() < 5) {
					gp.getMusic().setVolumeScale(gp.getMusic().getVolumeScale() + 1);
					gp.getMusic().adjustVolume();
				}
			}
		
			if (gp.getGameUI().pauseCursor == PauseState.SOUND_OPTION) {
				if (code == KeyEvent.VK_A && gp.getSoundEffects().getVolumeScale() > 0) {
					gp.getSoundEffects().setVolumeScale(gp.getSoundEffects().getVolumeScale() - 1);
				}
			
				if (code == KeyEvent.VK_D && gp.getSoundEffects().getVolumeScale() < 5) {
					gp.getSoundEffects().setVolumeScale(gp.getSoundEffects().getVolumeScale() + 1);
				}
			}
			
		
	}
	
	private final void dialogueState (int code) {
		if (code == KeyEvent.VK_ENTER) {
			if (gp.getGameUI().tradeState != TradeState.SELL || gp.getGameUI().tradeState != TradeState.BUY) {
				gp.setGameState(GameState.PLAY);
			}
		}
		
	}
	
	private final void statusState (int code) {
		
		if (code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE) gp.setGameState(GameState.PLAY);  
		
		if (code == KeyEvent.VK_W) --gp.getGameUI().statusCursor;
		
		if (code == KeyEvent.VK_S) ++gp.getGameUI().statusCursor;
		
		if (gp.getGameUI().statusCursor > 11) gp.getGameUI().statusCursor = 11;
		 
		if (gp.getGameUI().statusCursor < 0) gp.getGameUI().statusCursor = 0;
		
		if (code == KeyEvent.VK_ENTER) 
			if (gp.getPlayer().upgradeAttribute(gp.getGameUI().statusCursor)) {
				gp.playSE(Sound.UPGRADE_SUCCESS);
			} else {
				gp.playSE(Sound.ACTION_FAILED);
			};
		
	}
	
	private final void inventoryState (int code) {
		
		UI ui = gp.getGameUI();
		
		if (code == KeyEvent.VK_E || code == KeyEvent.VK_ESCAPE) gp.setGameState(GameState.PLAY);
		
		if (code == KeyEvent.VK_D) { ui.setSlotColumn(ui.getSlotColumn() + 1); gp.playSE(Sound.INVENTORY_CURSOR); }
		
		if (code == KeyEvent.VK_A) { ui.setSlotColumn(ui.getSlotColumn() - 1); gp.playSE(Sound.INVENTORY_CURSOR); }
		
		if (code == KeyEvent.VK_W) { ui.setSlotRow(ui.getSlotRow() - 1); gp.playSE(Sound.INVENTORY_CURSOR); }
		
		if (code == KeyEvent.VK_S) { ui.setSlotRow(ui.getSlotRow() + 1); gp.playSE(Sound.INVENTORY_CURSOR); }
		
		if (code == KeyEvent.VK_ENTER) { 
			ui.setInventoryState(InventoryState.OPTIONS);
			gp.playSE(Sound.INVENTORY_SELECT);
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
			gp.playSE(Sound.INVENTORY_SELECT);
			gp.getPlayer().handleInventoryOptions(ui.inventoryOptionCursor);
			ui.inventoryOptionCursor = 0;
		}
		
		if (code == KeyEvent.VK_ESCAPE) {
			ui.setInventoryState(InventoryState.NORMAL);
			ui.inventoryOptionCursor = 0;
		}
		
	}
	
	private final void gameOverState(int code) {
		if (code == KeyEvent.VK_S) ++gp.getGameUI().gameOverCursor;
		
		if (code == KeyEvent.VK_W) --gp.getGameUI().gameOverCursor;
		
		if (gp.getGameUI().gameOverCursor < 0) gp.getGameUI().gameOverCursor = 0;
		
		if (gp.getGameUI().gameOverCursor > 2) gp.getGameUI().gameOverCursor = 2;
		
		if (code == KeyEvent.VK_ENTER) {
			switch (gp.getGameUI().gameOverCursor) {
			case GameOverState.LOADSAVE_OPTION -> {}
			case GameOverState.NEWGAME_OPTION -> {
				gp.getPlayer().setDefaultPlayerValues();
				gp.setGameState(GameState.PLAY);
				gp.GameSetup();
			}
			case GameOverState.QUIT_OPTION -> {
				gp.getMusic().stop();
				gp.getGameUI().setTitleScreenState(TitleState.WELCOME);
				gp.setGameState(GameState.TITLE);
			}
			
			}
			gp.playSE(Sound.SELECT_OPTION);
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
	
	private final void tradeState (int code) {
		UI ui = gp.getGameUI();
		
		if (ui.tradeState == TradeState.SELECT) {
			
		if (code == KeyEvent.VK_W) --ui.tradeCursor;
		
		if (code == KeyEvent.VK_S) ++ui.tradeCursor;
		
		if (ui.tradeCursor < 0) ui.tradeCursor = 0;
		
		if (ui.tradeCursor > 2) ui.tradeCursor = 2;
		
		}
		
		switch (ui.tradeState) {
			
			case BUY -> {
				
				
				if (code == KeyEvent.VK_ESCAPE) ui.setTradeState(TradeState.SELECT);
				
				if (code == KeyEvent.VK_D) { ui.setSlotColumn(ui.getSlotColumn() + 1); gp.playSE(Sound.INVENTORY_CURSOR); }
				
				if (code == KeyEvent.VK_A) { ui.setSlotColumn(ui.getSlotColumn() - 1); gp.playSE(Sound.INVENTORY_CURSOR); }
				
				if (ui.getSlotColumn() > 5) ui.setSlotColumn(5);
				
				if (ui.getSlotColumn() < 0) ui.setSlotColumn(0);
				
				int itemIndex = ui.getSlotColumn();
				
				if (code == KeyEvent.VK_ENTER) {
					try {
					GameObject currentItem = ((NPC)gp.getNPCS().get(MapsConstants.TRADE).get(Merchant.MERCHANT_INDEX)).getNPCInventory().get(itemIndex);
					if (currentItem.getBuyPrice() > gp.getPlayer().getCoin()) {
					
						gp.playSE(Sound.ACTION_FAILED);
						gp.setGameState(GameState.DIALOGUE);
						ui.setCurrentDialogue("You don't have enough coins, buddy!\nYou tryna rob me?");
					
					} else if (gp.getPlayer().getInventory().size() == 12) {
						gp.playSE(Sound.ACTION_FAILED);
						gp.setGameState(GameState.DIALOGUE);
						ui.setCurrentDialogue("Your backpack is full of stuffs, man!\nNot sure you can put anything more in.");
					} else {
						gp.getPlayer().setCoin(gp.getPlayer().getCoin() - currentItem.getBuyPrice());
						gp.getPlayer().getInventory().add(currentItem);
						((NPC)gp.getNPCS().get(MapsConstants.TRADE).get(Merchant.MERCHANT_INDEX)).getNPCInventory().remove(itemIndex);
						gp.playSE(Sound.TRADE_SUCCESS);
					}
					} catch (Exception e) {
						
					}
				}
			}
			case SELECT -> {
				if (code == KeyEvent.VK_ENTER) {
				if (ui.tradeCursor == TradeState.BUY_OPTION) {
					ui.tradeState = TradeState.BUY;
					ui.setSlotColumn(0);
					ui.setSlotRow(0);
					
					this.tradeState(9999);
				}
				if (ui.tradeCursor == TradeState.SELL_OPTION) {
					ui.tradeState = TradeState.SELL;
					ui.setSlotColumn(0);
					ui.setSlotRow(0);
					this.tradeState(9999);
				}
				if (ui.tradeCursor == TradeState.LEAVE_OPTION) {
					ui.tradeCursor = 0;
					gp.setGameState(GameState.DIALOGUE);
					ui.setCurrentDialogue("Come back with some money boy!\nThe goods are always here.");
					ui.setTradeState(TradeState.LEAVE);
					gp.getPlayer().setDirection(Direction.DOWN);
				}
				gp.playSE(Sound.INVENTORY_SELECT);
				}
			}
			case SELL -> {
				
				int itemIndex = ui.getItemIndex();
				
				if (code == KeyEvent.VK_ESCAPE) ui.setTradeState(TradeState.SELECT);
				
				if (code == KeyEvent.VK_D) { ui.setSlotColumn(ui.getSlotColumn() + 1); gp.playSE(Sound.INVENTORY_CURSOR); }
				
				if (code == KeyEvent.VK_A) { ui.setSlotColumn(ui.getSlotColumn() - 1); gp.playSE(Sound.INVENTORY_CURSOR); }
				
				if (code == KeyEvent.VK_W) { ui.setSlotRow(ui.getSlotRow() - 1); gp.playSE(Sound.INVENTORY_CURSOR); }
				
				if (code == KeyEvent.VK_S) { ui.setSlotRow(ui.getSlotRow() + 1); gp.playSE(Sound.INVENTORY_CURSOR); }
				
				if (ui.getSlotRow() < 0) ui.setSlotRow(0);
				
				if (ui.getSlotRow() > 1) ui.setSlotRow(1);
				
				if (ui.getSlotColumn() < 0) ui.setSlotColumn(0);
				
				if (ui.getSlotColumn() > 5) ui.setSlotColumn(5);
				
				if (code == KeyEvent.VK_ENTER) {
					try {
					gp.getPlayer().setCoin(gp.getPlayer().getCoin() + gp.getPlayer().getInventory().get(itemIndex).getSellPrice());
					gp.playSE(Sound.TRADE_SUCCESS);
					gp.getPlayer().handleInventoryOptions(InventoryState.DISCARD_OPTION);
					} catch (Exception e) {
	
					}
					
				}
				
			}
			
			default -> {}
			
			}
			
		
		
	
		
	}
	

	

}