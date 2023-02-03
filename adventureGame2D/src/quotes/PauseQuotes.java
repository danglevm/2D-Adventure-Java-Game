package quotes;

import java.util.Random;

public class PauseQuotes {
	//Row determines the state of the game to use which sets of quotes
	//Column determines which quote in that game state to use which quote
	//0 - starting state
	//1 - combat already
	public String [][] pauseQuoteArray = new String[3][10];
	
	public PauseQuotes() {
		setPauseQuotes();
	}
	
	public String getPauseQuote(int i) {
		return pauseQuoteArray[0][i];
	}
	
	private void setPauseQuotes() {
		pauseQuoteArray[0][0] = "You can put your game on pause but not your life. Take a break after some gaming!";
		pauseQuoteArray[0][1] = "Don't be a chicken and stop the game when you are low health";
		pauseQuoteArray[0][2] = "The narrator can sometimes be an entitled and snobbish jerk. Be kind, his job is tough";
		pauseQuoteArray[0][3] = "I love being a corny pause screen. It pisses off the overserious kid";
	}

}
