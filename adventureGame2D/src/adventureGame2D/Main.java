package adventureGame2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	ImageIcon jarLogo = new ImageIcon(this.getClass().getClassLoader().getResource("player/"
			+ "boy_down_1.png"));
	public static JFrame window;
	
		
		//*****************************************************************************************************************		
		//------------------------------GAME WINDOW----------------------------------------------------------------------//
		//*****************************************************************************************************************
		
	public Main() {
		window = new JFrame("Game Window");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable (false);
		window.setTitle("Man having a mid-life crisis set out to beat up some monsters");
		window.setUndecorated(true);
		window.setIconImage(jarLogo.getImage());
			
		//Game panel object
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		gamePanel.getMenuOptionConfig().loadConfig();
		if (gamePanel.getFullScreen()) {
			window.setUndecorated(true);
		} else {
			window.setUndecorated(false);
		}
			
		window.pack(); //Resizes the frame so all contents are at or above preferred size
		window.setLocationRelativeTo(null);//Center the GUI on the screen
		window.setVisible(true);
			
		gamePanel.GameSetup();
		gamePanel.startGameThread();

	}
	
	
	public static void main(String[]args) {
		
			new Main();

	}
	
}
