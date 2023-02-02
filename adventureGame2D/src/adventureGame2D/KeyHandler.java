package adventureGame2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	GamePanel gp;
	public KeyHandler (GamePanel gp) {
		this.gp = gp;
	}
	public boolean upPressed, downPressed, leftPressed, rightPressed, dialoguePressed = false, FPS_display = false;
	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		//Check if WASD is in use
		//PLAY state
	if (gp.gameState == gp.playState) {
		if (code == KeyEvent.VK_W) {
			upPressed=true;
		} 
		if (code == KeyEvent.VK_A) {
			leftPressed=true;
		} 
		if (code == KeyEvent.VK_S) {
			downPressed=true;
		} 
		if (code == KeyEvent.VK_D) {
			rightPressed=true;
		} 
		
		//Pause the game
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.pauseState;
		} 
		
		if (code == KeyEvent.VK_ENTER) {
			dialoguePressed = true;
		} 


	

	//display FPS
	if (code == KeyEvent.VK_T) {
		if (FPS_display == false) {
			FPS_display = true;
		} else { 
			FPS_display = false;
		}
			}
	
	
		}
		
		else if (gp.gameState ==gp.pauseState) {
			//Pause state
			//Resumes the game
			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			} 
			
		}
		
		else{
			//Dialogue state
			if (code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.playState;
			}
			
			
			
			
		}
		
		
		
		
		
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		//Check if WASD is in use
		if (code == KeyEvent.VK_W) {
			upPressed=false;
		} 
		if (code == KeyEvent.VK_A) {
			leftPressed=false;
		} 
		if (code == KeyEvent.VK_S) {
			downPressed=false;
		} 
		if (code == KeyEvent.VK_D) {
			rightPressed=false;
		
		}
		
	
	}//keyReleased class
	

}//Key Handler class ends
