package quotes;

import java.util.ArrayList;
import java.util.Random;

public class PauseQuotes {
	
	/*
	 * Row determines the state of the game to use which sets of quotes
	 * Column determines which quote in that game state to use which quote
	 * 0 -starting state
	 * 1 - combat already
	 * 
	 */

	private ArrayList <String> pauseQuoteArray = new ArrayList <String>();
	
	public PauseQuotes() {
		setPauseQuotes();
	}
	
	public String getPauseQuote(int i) {
		return pauseQuoteArray.get(i);
	}
	
	private void setPauseQuotes() {
		pauseQuoteArray.add("You can put your game on pause but not your life. Take a break after some gaming!"); 
		pauseQuoteArray.add("Don't be a chicken and stop the game when you are low health");
		pauseQuoteArray.add("The narrator can sometimes be an entitled and snobbish jerk. Be kind, his job is tough"); 
		pauseQuoteArray.add("I love being a corny pause screen. It pisses off the overserious kid");
	}

}
