package adventureGame2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

//Game Panel inherits all components from JPanel
public class GamePanel extends JPanel implements Runnable{
	//*****************************************************************************************************************		
	//------------------------------SCREEN SETTINGS----------------------------------------------------------------------//
	//*****************************************************************************************************************
	
	final int originalTileSize = 16; //16x16 size
	//Scale the 16x16 characters to fit computers' resolutions
	final int scale = 3;
	final int tileSize = originalTileSize*scale;//48x48 tile
	
	//Setting max screen settings 16 tiles x 12 tiles - 4:3 ratio
	final int maxScreenColumns = 18;
	final int maxScreenRows = 14;
	
	//A single tile size is 48 pixels
	final int screenWidth = tileSize * maxScreenColumns; //864 pixels
	final int screenHeight = tileSize * maxScreenRows; //672 pixels
	
	//Player default position
	int playerX = 100;
	int playerY = 100;
	//Could change this later when the player drink some buffs
	int playerSpeed = 4;
	
	//FPS
	int FPS = 60;
	
	//Game Thread
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	
	//-------------------------------CONSTRUCTORS------------------
	public GamePanel() {
		this.setPreferredSize(new Dimension (screenWidth, screenHeight));
		this.setBackground(Color.black);
		//Graphics generated with double buffering to reduce flickering
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		//Set the Game Panel to focus on taking inputs from key presses
		this.setFocusable(true);
		
	}
	
	//-------------------------------CLASS METHODS------------------
	//*******************************THREADING**********************
	public void startGameThread() {
		gameThread = new Thread(this); //Pass in the class it's calling and the thread will run through the game's processes
		gameThread.start();
	}

	@Override
	//Overriding the run method from the Thread class
	//Game loop
	public void run() {
		double drawInterval = 1000000000/FPS; //Draw the screen every 0.0166 seconds
		
		 
		
		while (gameThread!= null) {
			
			
			//1. Update information - character position
			update();
			//2. Draw the screen with the updated information
			
			//Repaint internally calls paint to repaint the component
			repaint();
		}
		
	}
	 
	

//************************************ GAME LOOP METHODS**************

//Takes in KeyH inputs and then updates character model
public void update() {
	//X and Y values increase as the player moves right and down
	if (keyH.upPressed == true) {
		playerY-=playerSpeed;
		
	}
	
	if (keyH.downPressed== true) {
		playerY+=playerSpeed;
	}
	
	if (keyH.leftPressed == true) {
		playerX-=playerSpeed;
	}
	if (keyH.rightPressed==true) {
		playerX+=playerSpeed;
	}
	
	
}

public void paintComponent (Graphics g) {
		
	//Calling parent class JPanel
	super.paintComponent(g);
	
	//Set 1D graphics to 2d Graphics
	Graphics2D g2 = (Graphics2D)g;
	
	g2.setColor(Color.yellow);
	
	g2.fillRect(playerX, playerY, tileSize, tileSize);
	
	
	g2.dispose();
	
	
}

}//End of class
