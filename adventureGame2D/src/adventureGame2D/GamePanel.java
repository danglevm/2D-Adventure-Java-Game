package adventureGame2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

//Game Panel inherits all components from JPanel
public class GamePanel extends JPanel implements Runnable{
	//*****************************************************************************************************************		
	//------------------------------SCREEN SETTINGS----------------------------------------------------------------------//
	//*****************************************************************************************************************
	
	final int originalTileSize = 16; //16x16 size
	//Scale the 16x16 characters to fit computers' resolutions
	final int scale = 3;
	public final int tileSize = originalTileSize*scale;//48x48 tile
	
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
	
	//Game Objects
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this, keyH);
	
	
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
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer =0;
		int drawCount =0;
		
		while (gameThread!= null) {
			
			currentTime = System.nanoTime();
			
			//Find the change in time
			delta += (currentTime-lastTime)/drawInterval;
			timer +=(currentTime-lastTime);
			lastTime = currentTime;
			
			
			//When delta reach drawInterval that is equals to 1
			if (delta>=1) {
				//1. Update information - character position
				update();
				//2. Draw the screen with the updated information
				//Repaint internally calls paint to repaint the component
				repaint();
				delta--;
				drawCount++;
				
			}
			//Display FPS
			if (timer>=1000000000) {
				System.out.println("FPS: " +drawCount);
				drawCount = 0;
				timer = 0;
			}
		
		}
		
		
	}
	 
	

//************************************ GAME LOOP METHODS**************

//Takes in KeyH inputs and then updates character model
public void update() {
	
	player.update();
	
}

public void paintComponent (Graphics g) {
		
	//Calling parent class JPanel
	super.paintComponent(g);
	
	//Set 1D graphics to 2d Graphics
	Graphics2D g2 = (Graphics2D)g;
	
	player.draw(g2);
	
	
	g2.dispose();
	
	
}

}//End of class
