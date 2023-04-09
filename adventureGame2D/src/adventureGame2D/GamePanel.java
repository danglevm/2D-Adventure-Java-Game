package adventureGame2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Particle;
import entity.Player;
import enums.GameState;
import events.EventHandler;
import monster.Monster;
import projectile.Projectile;
import tile.InteractiveTile;
import tile.TileManager;

//Game Panel inherits all components from JPanel
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable{
	//******************************************************************************************************************		
	//------------------------------SCREEN SETTINGS----------------------------------------------------------------------//
	//*****************************************************************************************************************
	
	final int originalTileSize = 16; //16x16 size
	//Scale the 16x16 characters to fit computers' resolutions
	final int scale = 3;
	private final int tileSize = originalTileSize * scale;//48x48 tile
	
	//Setting max screen settings 18 tiles x 14 tiles
	private final int maxScreenColumns = 20;
	private final int maxScreenRows = 14;
	
	//A single tile size is 48 pixels
	private final int screenWidth = tileSize * maxScreenColumns; //864 pixels
	private final int screenHeight = tileSize * maxScreenRows; //672 pixels
	
	
	//Playing on full screen - draws everything to the buffer before drawing it to the JPANEL
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage bufferScreen;
	Graphics2D g2;
	
	
	//******************************************************************************************************************		
	//SETTINGS----------------------------------------------------------------------//
	//*****************************************************************************************************************
	
	//World Map settings
	private int maxWorldCol = 250;
	private int maxWorldRow = 250;
	private final int maxMap = 10;
	private final int worldWidth = tileSize * maxWorldCol;
	private final int worldHeight = tileSize * maxWorldRow;
	
	
	//Main game state
	private GameState gameState;
	
	//FPS
	private int FPS = 60, FPS_x = screenWidth - tileSize*5, FPS_y = tileSize;
	private String FPS_text = "";
	
	//******************************************************************************************************************		
		//------------------------------IN GAME OBJECTS----------------------------------------------------------------------//
		//*****************************************************************************************************************
	
	//Game Objects
	TileManager tileM = new TileManager(this);
	private KeyHandler keyH = new KeyHandler(this);
	private CollisionCheck collisionChecker = new CollisionCheck(this);
	private UI ui = new UI(this);
	private EventHandler eHandler = new EventHandler (this);
	private AssetPlacement assetPlace = new AssetPlacement(this);
	private Config config = new Config (this);
	
	
	//Entities
	Thread gameThread;
	private Player player = new Player(this, keyH);
	private ArrayList <Entity> NPCs = new ArrayList <Entity> (); 
	private ArrayList <Entity> objects = new ArrayList <Entity> ();
	private ArrayList <Entity> monsters = new ArrayList <Entity> ();
	private ArrayList <Entity> projectiles = new ArrayList <Entity> ();
	private ArrayList <Entity> particles = new ArrayList <Entity> ();
	private ArrayList <Entity> interactiveTiles = new ArrayList <Entity> ();
	//entity with lowest world Y index 0, highest world y final index
	private ArrayList<Entity> entityList = new ArrayList<Entity>();
	private ArrayList <Entity> removeMonsterList = new ArrayList <Entity> ();
	private ArrayList <Entity> removeProjectileList = new ArrayList <Entity> ();
	private ArrayList <Entity> removeParticleList = new ArrayList <Entity> ();

	
	//options menu
	private boolean fullScreen = false;
	private boolean subtitleOn = false;
	
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
		gameState = GameState.TITLE;
	}
	
	//-------------------------------CLASS METHODS------------------
	//*******************************THREADING**********************
	public void startGameThread() {
		gameThread = new Thread(this); //Pass in the class it's calling and the thread will run through the game's processes
		gameThread.start();
	}
	/**
	 * GETTERS and SETTERS
	 */
	public int getMaxWorldCol () { return maxWorldCol; }
	
	public int getMaxWorldRow () { return maxWorldRow; }
	
	public int getScreenWidth () { return screenWidth; }
	
	public int getScreenHeight () { return screenHeight; }
	
	public KeyHandler getKeyHandler () { return keyH; }
	
	public CollisionCheck getCollisionCheck () { return collisionChecker; }
	
	public UI getGameUI () { return ui; }
	
	public EventHandler getEventHandler () { return eHandler;}
	
	public AssetPlacement getAssetPlacement () { return assetPlace;}
	
	public Player getPlayer () { return player;}
	
	public Sound getMusic () { return music; }
	
	public Sound getSoundEffects () { return se;}
	
	public Config getMenuOptionConfig() { return config; }
	
	public ArrayList<Entity> getNPCS() { return NPCs;}
	
	public ArrayList<Entity> getObjects() { return objects;}
	
	public ArrayList<Entity> getMonsters() { return monsters;}
	
	//Setting up the game
	//*******************************GAME SETUP**********************
	public void GameSetup() {
		
		assetPlace.setObject();
		assetPlace.setNPCs();
		assetPlace.setMonsters();
		assetPlace.setInteractiveTiles();
		
		bufferScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D)bufferScreen.getGraphics();
		
		if (this.getFullScreen()) this.drawFullScreen();
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
				drawBuffer();
				drawScreen();
//				repaint();
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
	
	
	if (gameState == GameState.PLAY) {
		//Player
		player.update();
		//NPCs
		updateEntities (particles);
		updateEntities(objects);
		updateEntities(NPCs);
		updateEntities(monsters);
		updateEntities (projectiles);
		updateEntities(interactiveTiles);
		
		
		monsters.removeAll(removeMonsterList);
		projectiles.removeAll(removeProjectileList);
		particles.removeAll(removeParticleList);
		removeMonsterList.clear();
		removeProjectileList.clear();
			
		
		
	} else {
		//nothing happens
	}
	
}

public void drawBuffer() {
	if (gameState == GameState.TITLE) {
		ui.draw(g2);
		
	} else {
	
		//Draw the tiles first before the player characters
		//TILE
		tileM.draw(g2);

		entityList.add(player);
	
		//Add both npcs and objects to the array list 
		addtoEntityList(NPCs);
		addtoEntityList(objects);
		addtoEntityList(monsters);
		addtoEntityList (projectiles);
		addtoEntityList(interactiveTiles);
		addtoEntityList(particles);
		
		//Sort the entityList
		Collections.sort(entityList, new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				int result = Integer.compare(e1.getWorldY(), e2.getWorldY());
				
				return result;
				
			}
			
		});
		
		//Draw entities
		for (Entity currentEntity : entityList) {
			if (currentEntity != null) {
				if (currentEntity != player) {
					currentEntity.draw(g2, this);
				} else {
					player.draw(g2);
				
				}
			}
		}
		//Empty entity list after drawing
		entityList.clear();
		
		ui.draw(g2);
	
	//draws FPS and player location
		if (keyH.getFpsDisplay() && this.gameState == GameState.PLAY) {
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,25));
			g2.drawString(FPS_text, FPS_x, FPS_y);
			g2.drawString("X: " + (player.getWorldX())/tileSize + " Y: " + (player.getWorldY())/tileSize, FPS_x - tileSize/2, FPS_y + tileSize);
		}
	}
}

public void drawScreen() {
	Graphics g = getGraphics();
	g.drawImage(bufferScreen, 0, 0, screenWidth2, screenHeight2, null);
	g.dispose();
}

public void drawFullScreen() {
	
	//Get screen device parameters
	GraphicsEnvironment gEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	GraphicsDevice gDevice = gEnvironment.getDefaultScreenDevice();
	gDevice.setFullScreenWindow(Main.window);
	
	screenWidth2 = Main.window.getWidth();
	screenHeight2 = Main.window.getHeight();
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
		entities.forEach(entity -> {
				entityList.add(entity);
		});
	}
	
	private final void updateEntities (ArrayList <Entity> entities) {
		entities.forEach(entity -> {
			
			if (!entity.getAlive() ) {
				if (entity instanceof Monster && !entity.getDying()) {
					removeMonsterList.add(entity);
				}
				if (entity instanceof Projectile) {
					removeProjectileList.add(entity);
				}
				
				if (entity instanceof Particle) {
					removeParticleList.add(entity);
				}
			
			} else if (entity.getAlive()) {
				entity.update();
			}
		});
	}
	
//	private final void updateInteractiveTiles () {
//		for (int i = 0; i < interactiveTiles.size(); ++i) {
//			if (interactiveTiles.get(i) != null) {
//				((InteractiveTile)interactiveTiles.get(i)).updateInteractiveTile();
//			}
//		}
//	}

	
	/**
	 * GETTERS and SETTERS
	 */
	public GameState getGameState () { return gameState; }
	
	public void setGameState (GameState gameState) { this.gameState = gameState;}
	
	public int getTileSize () { return tileSize; }
	
	public ArrayList<Entity> getProjectiles () { return projectiles;}
	
	public ArrayList<Entity> getInteractiveTiles () { return interactiveTiles;}
	
	public ArrayList<Entity> getParticles() { return particles; }

	public final boolean getFullScreen() { return fullScreen;}
	
	public final void setFullScreen(boolean fullScreen) {this.fullScreen = fullScreen; }
	
	public final boolean getSubtitleState() { return subtitleOn; }
	
	public final void setSubtitileState (boolean subtitleOn) {this.subtitleOn = subtitleOn; }
}



