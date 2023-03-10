package adventureGame2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import enums.GameState;

public class KeyHandler implements KeyListener{

	GamePanel gp;

	private boolean upPress, downPress, leftPress, rightPress; 
	private boolean dialoguePress; 
	private boolean fpsDisplay; 
	private boolean pauseQuote;
	private boolean interaction;

	
	public KeyHandler (GamePanel gp) {
		this.gp = gp;
		dialoguePress = false;
		fpsDisplay = false;
		pauseQuote = false;
		interaction = false;
	}
	
	/**
	 * Setters and getters
	 */
	public boolean getDialoguePress () { return dialoguePress;}
	public void setDialoguePress (boolean dialoguePressed) { this.dialoguePress = dialoguePressed; }
	
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
	* TITLE STATE
	*/
	if (gp.getGameState() == GameState.TITLE) {
			
			if (gp.getGameUI().titleScreenState == 0) {
				
				defaultTitleState(code);	
				
			} else if (gp.getGameUI().titleScreenState == 1) {
				
				firstTitleState (code);
				
			}
			
	/*
	* PLAY STATE
	*/
	} else if (gp.getGameState() == GameState.PLAY) {
		
		playState(code);
	
	/*
	* PAUSE STATE - resumes the game with escape is pressed
	*/	
	} else if (gp.getGameState() == GameState.PAUSE) {
			
		pauseState (code);
	
	/*
	* DIALOGUE STATE - resumes the game with escape is pressed
	*/
	} else if (gp.getGameState() == GameState.DIALOGUE){
			
		dialogueState(code);
	 /*
	 * DIALOGUE STATE - resumes the game with escape is pressed
	 */		
	} else if (gp.getGameState() == GameState.STATUS) {
		
			statusState (code);
		
		}
	
	}
	
	/**
	 * 
	 * PRIVATE METHODS
	 * 
	 **/
	
	private void defaultTitleState (int code) {
		
		if (code == KeyEvent.VK_W) {
			--gp.getGameUI().cursorNum;
		} 
		if (code == KeyEvent.VK_S) {
			++gp.getGameUI().cursorNum;
		} 
		
		if (code == KeyEvent.VK_ENTER) {
			/*
			 * New game
			 */
			if (gp.getGameUI().cursorNum == 0) gp.getGameUI().titleScreenState = 1;
			
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
		}
		
		if (gp.getGameUI().cursorNum > 3) gp.getGameUI().cursorNum = 3;
		
		if (gp.getGameUI().cursorNum < 0) gp.getGameUI().cursorNum = 0;
		
	}
	
	private void firstTitleState (int code) {
		
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
		}
		
		if (gp.getGameUI().cursorNum > 1) gp.getGameUI().cursorNum = 1;
		 
		if (gp.getGameUI().cursorNum < 0) gp.getGameUI().cursorNum = 0;
		
	}
	
	private void playState (int code) {
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
		
		if (code == KeyEvent.VK_C) gp.setGameState(GameState.STATUS);
		
		
		if (code == KeyEvent.VK_ENTER) {
			if (!dialoguePress) {
				dialoguePress = !dialoguePress;
			}
		} 

		/*
		 * display FPS and player X and Y
		 */
		if (code == KeyEvent.VK_T) {
			if (!fpsDisplay) {
				fpsDisplay = !fpsDisplay;
			}
		}
		
		/*
		 * Trigger player attack
		 */
		if (code == KeyEvent.VK_J) gp.getPlayer().setPlayerAttack(true);
		
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
	
	private void pauseState (int code) {
		if (code == KeyEvent.VK_ESCAPE) gp.setGameState(GameState.PLAY);
	}
	
	private void dialogueState (int code) {
		if (code == KeyEvent.VK_ENTER) gp.setGameState(GameState.PLAY);
		
	}
	
	private void statusState (int code) {
		
		if (code == KeyEvent.VK_C) gp.setGameState(GameState.PLAY);  
		
		
		if (code == KeyEvent.VK_W) --gp.getGameUI().statusCursor;
		
		if (code == KeyEvent.VK_S) ++gp.getGameUI().statusCursor;
		
		if (gp.getGameUI().statusCursor > 11) gp.getGameUI().statusCursor = 11;
		 
		if (gp.getGameUI().statusCursor < 0) gp.getGameUI().statusCursor = 0;
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		/*
		 * Check if keys are in use
		 */
		if (code == KeyEvent.VK_W) upPress = false;
		
		if (code == KeyEvent.VK_A) leftPress = false;
		 
		if (code == KeyEvent.VK_S) downPress = false;
		
		if (code == KeyEvent.VK_D) rightPress = false;
	
	}
	

}