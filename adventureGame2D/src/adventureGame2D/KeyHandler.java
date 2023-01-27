package adventureGame2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	//Debug
	boolean checkDrawTime = false;
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

	

	//Debug 
	if (code == KeyEvent.VK_T) {
		if (checkDrawTime == false) {
			checkDrawTime = true;
		} else { 
			checkDrawTime = false;
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
