package adventureGame2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	GamePanel gp;
	public KeyHandler (GamePanel gp) {
		this.gp = gp;
	}
	public boolean upPressed, downPressed, leftPressed, rightPressed;
	//Debug
	boolean FPS_display = false;
	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		//Check if WASD is in use
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
			if (gp.gameState == gp.playState) {
				gp.gameState = gp.pauseState;
			} else {
				gp.gameState = gp.playState;
			}
		} 

	

	//display FPS
	if (code == KeyEvent.VK_T) {
		System.out.println(FPS_display);
		if (FPS_display == false) {
			FPS_display = true;
		} else { 
			FPS_display = false;
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
