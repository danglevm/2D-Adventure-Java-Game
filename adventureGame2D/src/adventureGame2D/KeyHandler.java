package adventureGame2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	GamePanel gp;
	public KeyHandler (GamePanel gp) {
		this.gp = gp;
	}
	public boolean upPressed, downPressed, leftPressed, rightPressed, 
				   dialoguePressed = false, 
				   FPS_display = false, 
				   pauseQuote = false,
				   allowInteraction = false;
				 
	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
	/*
	* TITLE STATE
	*/
	if (gp.gameState == gp.titleState) {
			
			if (gp.ui.titleScreenState == 0) {
				
				defaultTitleState(code);	
				
			} else if (gp.ui.titleScreenState == 1) {
				
				firstTitleState (code);
				
			}
			
			
	/*
	* PLAY STATE
	*/
	} else if (gp.gameState == gp.playState) {
		
		playState(code);
	
	/*
	* PAUSE STATE - resumes the game with escape is pressed
	*/	
	} else if (gp.gameState == gp.pauseState) {
			
		pauseState (code);
	
	/*
	* DIALOGUE STATE - resumes the game with escape is pressed
	*/
	} else if (gp.gameState == gp.dialogueState){
			
		dialogueState(code);
	 /*
	 * DIALOGUE STATE - resumes the game with escape is pressed
	 */		
	} else if (gp.gameState == gp.statusState) {
		
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
			--gp.ui.cursorNum;
		} 
		if (code == KeyEvent.VK_S) {
			++gp.ui.cursorNum;
		} 
		
		if (code == KeyEvent.VK_ENTER) {
			/*
			 * New game
			 */
			if (gp.ui.cursorNum == 0) {
				gp.ui.titleScreenState = 1;
			} 
			else if (gp.ui.cursorNum == 1) {
				/*
				 * Load save
				 */
			} else if (gp.ui.cursorNum == 2) {
				/*
				 * Settings
				 */
			} else {
				System.exit(0);
			}
		}
		
		if (gp.ui.cursorNum > 3) {
			gp.ui.cursorNum = 3;
		} 
		if (gp.ui.cursorNum < 0) {
			gp.ui.cursorNum = 0;
		}
		
	}
	
	private void firstTitleState (int code) {
		
		if (code == KeyEvent.VK_A) {
			--gp.ui.cursorNum;
		} 
		if (code == KeyEvent.VK_D) {
			++gp.ui.cursorNum;
		} 
		
		if (code == KeyEvent.VK_ENTER) {
			/*
			 * Blue boy
			 */	
			if (gp.ui.cursorNum == 0) {
				gp.gameState = gp.playState;
				gp.playMusic(0);
			} else {
				/*
				 * Yellow girl
				 */	
			}
		}
		
		if (gp.ui.cursorNum > 1) {
			gp.ui.cursorNum = 1;
		} 
		if (gp.ui.cursorNum < 0) {
			gp.ui.cursorNum = 0;
		}
	}
	
	private void playState (int code) {
		/*
		 * Player moving
		 */	
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		} 
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		} 
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		} 
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		} 
		
		/*
		 * Pause the game
		 */
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.pauseState;
			pauseQuote = true;
		} 
		
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.statusState;
		}
		
		if (code == KeyEvent.VK_ENTER) {
			if (!dialoguePressed) {
				dialoguePressed = true;
			} else {
				dialoguePressed = false;
			}
		} 

		/*
		 * display FPS and player X and Y
		 */
		if (code == KeyEvent.VK_T) {
			if (!FPS_display) {
				FPS_display = true;
			} else { 
				FPS_display = false;
			}
		}
		
		/*
		 * Trigger player attack
		 */
		if (code == KeyEvent.VK_J){
			gp.player.playerAttack = true;
		}
		
		/*
		 * Player stands near interactive event and can trigger events
		 */
		if (gp.eHandler.getInteraction()) {
			if (code == KeyEvent.VK_X) {
				allowInteraction = true;
			}
		}
	}
	
	
	/*
	 * Game state methods
	 */
	
	private void pauseState (int code) {

		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
			
		} 
	}
	
	private void dialogueState (int code) {
		if (code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
		
	}
	
	private void statusState (int code) {
		
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		/*
		 * Check if keys are in use
		 */
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		} 
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		} 
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		} 
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		
		}
		
	
	}
	

}