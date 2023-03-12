package quotes;

import java.util.ArrayList;

public class PauseQuotes {
	//Row determines the state of the game to use which sets of quotes
	//Column determines which quote in that game state to use which quote
	//0 - starting state
	//1 - combat already
	private ArrayList <String> pauseNoCombat = new ArrayList<String> ();
	
	public PauseQuotes() {
		setNoCombat();
	}
	
	public String getPauseNoCombat(int i) {
		return pauseNoCombat.get(i);
	}
	
	private void setNoCombat() {
		pauseNoCombat.add("You can put your game on pause but not your life. Take a break after some gaming!");
		pauseNoCombat.add("Don't be a chicken and stop the game when you are low health");
		pauseNoCombat.add("The narrator can sometimes be an entitled and snobbish jerk. Be kind, his job is tough");
		pauseNoCombat.add("I love being a corny pause screen. It pisses off the overserious kid");
	}

}
