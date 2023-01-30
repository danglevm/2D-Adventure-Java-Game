package adventureGame2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

//Game Panel inherits all components from JPanel
public class GamePanel extends JPanel implements Runnable{
	//******************************************************************************************************************		
	//------------------------------SCREEN SETTINGS----------------------------------------------------------------------//
	//*****************************************************************************************************************
	
	final int originalTileSize = 16; //16x16 size
	//Scale the 16x16 characters to fit computers' resolutions
	final int scale = 3;
	public final int tileSize = originalTileSize*scale;//48x48 tile
	
	//Setting max screen settings 18 tiles x 14 tiles
	public final int maxScreenColumns = 18;
	public final int maxScreenRows = 14;
	
	//A single tile size is 48 pixels
	public final int screenWidth = tileSize * maxScreenColumns; //864 pixels
	public final int screenHeight = tileSize * maxScreenRows; //672 pixels
	
	//World Map settings
	public int maxWorldCol = 250;
	public int maxWorldRow = 250;
	public final int maxMap = 10;
	public final int worldWidth = tileSize*maxWorldCol;
	public final int worldHeight = tileSize*maxWorldRow;
	
	
	//Game state
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	
	//FPS
	int FPS = 60;
	
	//Game Objects
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	public CollisionCheck cChecker = new CollisionCheck(this);
	public UI ui = new UI(this);
	
	//Entities
	Thread gameThread;
	public Player player = new Player(this, keyH);
	public Entity npcs[] = new Entity[20]; 

	
	//sound
	Sound music = new Sound();
	Sound se = new Sound();
	
	
	//In-game objects
	public AssetPlacement assetPlace = new AssetPlacement(this);
	//Display up to only 10 objects on screen - decide later
	public SuperObject obj [] = new SuperObject[50];
	
	
	
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
	//Setting up the game
	//*******************************THREADING**********************
	public void GameSetup() {
		
		assetPlace.setObject();
		assetPlace.setNPCs();
		playMusic(0);
		stopMusic();
		gameState = playState;
		
		
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
	
	
	if (gameState == playState) {
		//Player
		player.update();
		//NPCs
		for (int i = 0; i <npcs.length;++i) {
			if (npcs[i]!=null) {
				npcs[i].update();
			}
		}
		
		
	} else {
		//nothing happens
	}
	
	
	
	
	
}

public void paintComponent (Graphics g) {
	
	//Calling parent class JPanel
	super.paintComponent(g);
	
	//Set 1D graphics to 2d Graphics
	Graphics2D g2 = (Graphics2D)g;
	
	//DEBUG
	long drawStart = 0;
	if (keyH.checkDrawTime == true) {
		drawStart = System.nanoTime();	
	}
	
	//Draw the tiles first before the player characters
	//TILE
	tileM.draw(g2);
	
	//OBJECT
	for (int i = 0; i < obj.length;i++) {
		//Check if the object is null or not
		if (obj [i] != null) {
			obj[i].draw(g2, this);
		}
	}
	
	//NPCS
	for (int i =0 ; i<npcs.length;++i) {
		if (npcs[i]!=null) { 
			npcs[i].draw(g2, this); 
		}
	}
	
	//PLAYER
	player.draw(g2);
	
	//Drawing the UI
	ui.draw(g2);
	
	
	//debug
	if (keyH.checkDrawTime==true) {
		long drawEnd = System.nanoTime();
		long passed = drawEnd - drawStart;
		g2.setColor(Color.white);
		g2.drawString("Draw Time: " + passed, 10, 400);
		System.out.println("Draw time: " + passed);
	}
	g2.dispose();
	
	
}

//Music playing methods
	public void playMusic (int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic () {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}