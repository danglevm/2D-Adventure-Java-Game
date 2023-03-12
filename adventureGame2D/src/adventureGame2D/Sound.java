package adventureGame2D;

import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	//Open audio files
	Clip clip;
	
	//Store file path of sound files
	ArrayList <URL> soundURL = new ArrayList<>();
	
	public Sound() {
		//link to all sounds file
		soundURL.add (getClass().getResource("/sounds/game/BlueBoyAdventure.wav")); //0
		soundURL.add (getClass().getResource("/sounds/objects/coin.wav")); //1
		soundURL.add (getClass().getResource("/sounds/objects/powerup.wav")); //2
		soundURL.add (getClass().getResource("/sounds/objects/unlock.wav")); //3
		soundURL.add (getClass().getResource("/sounds/game/fanfare.wav")); //4
		soundURL.add (getClass().getResource("/sounds/monsters/hitmonster.wav")); //5
		soundURL.add (getClass().getResource("/sounds/player/playerdamaged.wav")); //6
		soundURL.add (getClass().getResource("/sounds/monsters/slime-death-sound.wav")); //7
		soundURL.add (getClass().getResource("/sounds/player/levelup.wav")); // 8
		
		
	}
	
	public void setFile(int i) {
		try {
			
			//Obtain the clip that can be used for playback
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL.get(i));
			clip = AudioSystem.getClip();
			clip.open(ais);
			
			
		}catch(Exception e){
			
		}
	}
	
	public void play() {
		
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stop() {
		clip.stop();
	}
}
