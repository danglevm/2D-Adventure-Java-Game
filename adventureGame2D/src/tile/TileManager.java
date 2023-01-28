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
	String filePath1 = "/maps/spawnmap";
	
	//-------------------------------CONSTRUCTORS------------------
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile =  new Tile[50];
		mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap(filePath1);
	}
	//-------------------------------CLASS METHODS------------------
	//Store tile images as entries inside the Tile array
	public void getTileImage() {
		
			//Streams (sequences of bytes) are searched and found, then decoded into buffered image and stored into an object
			tileSetup(0, "000", false);
			tileSetup(1, "001", false);
			tileSetup(2, "002", false);
			tileSetup(3, "003", false);
			tileSetup(4, "004", false);
			tileSetup(5, "005", false);
			
			tileSetup(6, "006", false);
			tileSetup(7, "007", false);
			tileSetup(8, "008", false);
			tileSetup(9, "009", false);
			tileSetup(10, "010", false);
			tileSetup(11, "011", false);
			
			tileSetup(12, "012", false);
			tileSetup(13, "013", false);
			tileSetup(14, "014", false);
			tileSetup(15, "015", false);
			tileSetup(16, "016", true);
			tileSetup(17, "017", false);

			tileSetup(18, "018", true);
			tileSetup(19, "019", true);
			tileSetup(20, "020", true);
			tileSetup(21, "021", true);
			tileSetup(22, "022", true);
			tileSetup(23, "023", true);
			
			tileSetup(24, "024", true);
			tileSetup(25, "025", true);
			tileSetup(26, "026", true);
			tileSetup(27, "027", true);
			tileSetup(28, "028", true);
			tileSetup(29, "029", true);
			
			tileSetup(30, "030", true);
			tileSetup(31, "031", true);
			tileSetup(32, "032", true);
			tileSetup(33, "033", true);
			tileSetup(34, "034", false);
			tileSetup(35, "035", true);
			
			tileSetup(36, "036", false);
			
			tileSetup(37, "037", false);
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
