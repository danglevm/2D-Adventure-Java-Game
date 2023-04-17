package adventureGame2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Config {
	
	GamePanel gp;
	

	public Config (GamePanel gp) {
		this.gp = gp;
	}
	
	public final void saveConfig()  {
		try {
			Path saveData = Path.of("config.txt");
			
			//auto write the file for you, so no need for manual file creation
			BufferedWriter bw = new BufferedWriter (new FileWriter(saveData.toFile()));
			/*
			 * fullscreen
			 */
			if (gp.getFullScreen()) bw.write("fullscreen,on");
			else if (!gp.getFullScreen()) bw.write("fullscreen,off");
			
			/*
			 * music volume
			 */
			bw.newLine();
			bw.write("music," + String.valueOf(gp.getMusic().getVolumeScale()));
			
			//SE volume
			bw.newLine();
			bw.write("sound," + String.valueOf(gp.getSoundEffects().getVolumeScale()));
			
		
			
			bw.close();
			
		} catch (Exception e) {
			
		}
		
		
		
	}
	
	public final void loadConfig() {
		try {
			BufferedReader br = new BufferedReader (new FileReader("config.txt"));
			
			String config = "";
			
			while ((config = br.readLine()) != null) {
				String[] options = config.split(",");
				//full screen
				//double equals compare references not values. Use equals
				if (options[0].equalsIgnoreCase("fullscreen")) {
					if (options[1].equalsIgnoreCase("on")) {
						gp.setFullScreen(true);
					} else if (options [1].equalsIgnoreCase("off")) {
						gp.setFullScreen(false);
					}
				}
				
				//Music
				if (options[0].equalsIgnoreCase("music")) {
					gp.getMusic().setVolumeScale(Integer.parseInt(options[1]));
				}
				
				//Sound effects
				if (options[0].equalsIgnoreCase("sound")) {
					gp.getSoundEffects().setVolumeScale(Integer.parseInt(options[1]));
				}
			}
			
			
			br.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
