package adventureGame2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import events.EventHandler;
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
	
	
	//******************************************************************************************************************		
	//------------------------------IN GAME SETTINGS----------------------------------------------------------------------//
	//*****************************************************************************************************************
	
	//World Map settings
	public int maxWorldCol = 250;
	public int maxWorldRow = 250;
	public final int maxMap = 10;
	public final int worldWidth = tileSize*maxWorldCol;
	public final int worldHeight = tileSize*maxWorldRow;
	
	
	//Main game state
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	
	
	
	//FPS
	private int FPS = 60, FPS_x = screenWidth - tileSize*3, FPS_y = tileSize;
	private String FPS_text = "";
	
	//******************************************************************************************************************		
		//------------------------------IN GAME OBJECTS----------------------------------------------------------------------//
		//*****************************************************************************************************************
	
	//Game Objects
	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	public CollisionCheck cChecker = new CollisionCheck(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler (this);
	public AssetPlacement assetPlace = new AssetPlacement(this);
	
	//Entities
	Thread gameThread;
	public Player player = new Player(this, keyH);
	public ArrayList <Entity> NPCs = new ArrayList <> (); 
	public ArrayList <Entity> objects = new ArrayList <> ();
	public ArrayList <Entity> monsters = new ArrayList <> ();
	//entity with lowest world Y index 0, highest world y final index
	private ArrayList<Entity> entityList = new ArrayList<>();
	
	
	//sound
	Sound music = new Sound();
	Sound se = new Sound();
	
	
	
	
	
	
	//-------------------------------CONSTRUCTORS------------------
	public GamePanel() {
		this.setPreferredSize(new Dimension (screenWidth, screenHeight));
		this.setBackground(Color.black);
		//Graphics generated with double buffering to reduce flickering
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		//Set the Game Panel to focus on taking inputs from key presses
		this.setFocusable(true);
		entityList.ensureCapacity(100);
		
	}
	
	//-------------------------------CLASS METHODS------------------
	//*******************************THREADING**********************
	public void startGameThread() {
		gameThread = new Thread(this); //Pass in the class it's calling and the thread will run through the game's processes
		gameThread.start();
	}
	//Setting up the game
	//*******************************GAME SETUP**********************
	public void GameSetup() {
		
		assetPlace.setObject();
		assetPlace.setNPCs();
		assetPlace.setMonsters();
		playMusic(0);
		stopMusic();
		gameState = titleState;
		
		
	}
	
	@Override
	//Overriding the run method from the Thread class
	//Game loop
	public void run() {
		double drawInterval = 1000000000/FPS; //Draw the screen every 0.0166 seconds
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		int timer = 0, drawCount = 0;
		
		
		
		while (gameThread != null) {
			
			currentTime = System.nanoTime();
			
			//Find the change in time
			delta += (currentTime - lastTime)/drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			
			//When delta reach drawInterval that is equals to 1
			if (delta >= 1) {
				
				//1. Update information - character position
				update();
				//2. Draw the screen with the updated information
				//Repaint internally calls paint to repaint the component
				repaint();
				delta--;
				++drawCount;
				
			}
			//FPS counter
			if (timer >= 1000000000) {
				FPS_text = "FPS: "+ drawCount;	
				timer = 0;
				drawCount = 0;
					
		
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
		this.updateEntities(objects);
		this.updateEntities(NPCs);
		this.updateEntities(monsters);
		
	} else {
		//nothing happens
	}
	
}


public void paintComponent (Graphics g) {
	
	//Calling parent class JPanel
	super.paintComponent(g);
	
	//Set 1D graphics to 2d Graphics
	Graphics2D g2 = (Graphics2D)g;
	
	if (gameState == titleState) {
		ui.draw(g2);
		
	} else {
	
		//Draw the tiles first before the player characters
		//TILE
		tileM.draw(g2);
	
		entityList.add(player);
	
		//Add both npcs and objects to the array list 
		this.addtoEntityList(NPCs);
		this.addtoEntityList(objects);
		this.addtoEntityList(monsters);
		
		//Sort the entityList
		Collections.sort(entityList, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				int result = Integer.compare(e1.worldY, e2.worldY);
				
				return result;
				
			}
			
		});
		
		//Draw entities
		for (Entity currentEntity : entityList) {
			if (currentEntity != player) {
				currentEntity.draw(g2, this);
			} else {
				player.draw(g2);
				
			}
		}
		//Empty entity list after drawing
		entityList.clear();
		
		ui.draw(g2);
	
	//draws FPS and player location
		if (keyH.FPS_display) {
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25));
			g2.drawString(FPS_text, FPS_x, FPS_y);
			g2.drawString("X: " + (player.worldX)/tileSize + " Y: " + (player.worldY)/tileSize, FPS_x - tileSize*2, FPS_y + tileSize);
		}
	
	g2.dispose();
	
	
	}

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
	
	
	//Add from array to array List
	private final void addtoEntityList (ArrayList <Entity> entities) {
		for (Entity currentEntity : entities) {
			if (currentEntity != null) {
				entityList.add(currentEntity);
			}
		}
	}
	
	private final void updateEntities (ArrayList <Entity> entities) {

		for (Entity currentEntity : entities) {
			if (currentEntity.getAlive()) {
				currentEntity.update();
			} else if (!currentEntity.getAlive() &&  !currentEntity.getDying()) {
				entities.remove(currentEntity);
		} 
	}

}
}



