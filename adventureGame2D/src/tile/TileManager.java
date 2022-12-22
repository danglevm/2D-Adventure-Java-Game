package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import adventureGame2D.GamePanel;

public class TileManager {

	GamePanel gp;
	Tile[]tile;
	int mapTileNum[][];
	//Change file path for maps
	String filePath1 = "/maps/map01.txt";
	//-------------------------------CONSTRUCTORS------------------
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile =  new Tile[10];
		mapTileNum = new int [gp.maxScreenColumns][gp.maxScreenRows];
		
		getTileImage();
		loadMap(filePath1);
	}
	//-------------------------------CLASS METHODS------------------
	//Store tile images as entries inside the Tile array
	public void getTileImage() {
		
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try{
			//Store link to text file as variable and read the text file
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;
			
			while (col<gp.maxScreenColumns&&row<gp.maxScreenRows) {
				String line = br.readLine();
				
				while (col<gp.maxScreenColumns) {
					//Get the numbers one by one
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row]= num;
					++col;
				}
				col =0;
				++row;
			}
			br.close();
		}catch(Exception e) {
			
		}
	}
	
	
	//Draw tiles in the background
	public void draw(Graphics2D g2) {
		
		int col =0;
		int row =0;
		int x = 0;
		int y =0;
		
		//While the tiles are still within max row and column size
		while (col<gp.maxScreenColumns && row<gp.maxScreenRows) {
			int tileNum = mapTileNum[col][row];
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			++col;
			x+=gp.tileSize;
			
			if (col==gp.maxScreenColumns) {
				col=0;
				x=0;
				row++;
				y+=gp.tileSize;
			}
		}
	}
	
}
