package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;
import adventureGame2D.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[]tile;
	public int mapTileNum[][];
	//Change file path for maps
	String filePath1 = "/maps/world01.txt";
	//-------------------------------CONSTRUCTORS------------------
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile =  new Tile[10];
		mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap(filePath1);
	}
	//-------------------------------CLASS METHODS------------------
	//Store tile images as entries inside the Tile array
	public void getTileImage() {
		
			//Streams (sequences of bytes) are searched and found, then decoded into buffered image and stored into an object
			tileSetup(0, "grass", false);
			tileSetup(1, "wall", false);
			tileSetup(2, "water", false);
			tileSetup(3, "earth", false);
			tileSetup(4, "tree", false);
			tileSetup(5, "sand", false);
	}
	
	public void tileSetup(int index, String imageName, boolean collision) {
		 
		UtilityTool uTool = new UtilityTool();
		
		//handle image instantiation, import, scale and collision
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imageName+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void loadMap(String filePath) {
		try{
			//Store link to text file as variable and read the text file
			InputStream is = getClass().getResourceAsStream(filePath);
			//Convert bytes from the stream into chars (in this case, numbers 0 - 2)
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			
			while (col<gp.maxWorldCol &&row<gp.maxWorldRow) {
				String line = br.readLine();
				
				while (col<gp.maxWorldCol) {
					//Get the numbers one by one and store them into mapTileNum
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row]= num;
					++col;
				}
				if (col == gp.maxWorldCol) {
				col =0;
				++row;
				}
			}
			br.close();
		}catch(Exception e) {
			
		}
	}
	
	
	//Draw tiles in the background
	public void draw(Graphics2D g2) {
		
		int worldCol =0;
		int worldRow =0;

		
		//While the tiles are still within max row and column size, draw the tile
		while (worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow) {
			//Retrieve the tile from mapTileNum
			int tileNum = mapTileNum[worldCol][worldRow];
			//Find the drawing location
			int worldX = worldCol*gp.tileSize;
			int worldY = worldRow*gp.tileSize;
			int screenX = worldX-gp.player.WorldX + gp.player.screenX;
			int screenY = worldY - gp.player.WorldY + gp.player.screenY;
			if (worldX+gp.tileSize>gp.player.WorldX-gp.player.screenX && 
				worldX-gp.tileSize<gp.player.WorldX+gp.player.screenX &&
				worldY+gp.tileSize>gp.player.WorldY-gp.player.screenY&&
				worldY-gp.tileSize <gp.player.WorldY+gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			
			++worldCol;
			
			
			if (worldCol==gp.maxWorldCol) {
				worldCol=0;
				worldRow++;
			}
		}
	}
	
}
