package adventureGame2D;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[]args) {
		
//*****************************************************************************************************************		
//------------------------------GAME WINDOW----------------------------------------------------------------------//
//*****************************************************************************************************************
	JFrame window = new JFrame("Game Window");
	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	window.setResizable (false);
	window.setTitle("Man having a mid-life crisis set out to beat up some monsters");

	
	//Game panel object
	GamePanel gamePanel = new GamePanel();
	window.add(gamePanel);
		
	
	window.pack(); //Resizes the frame so all contents are at or above preferred size
	window.setLocationRelativeTo(null);//Center the GUI on the screen
	window.setVisible(true);
	
	gamePanel.GameSetup();
	gamePanel.startGameThread();

	
	

	}
}
