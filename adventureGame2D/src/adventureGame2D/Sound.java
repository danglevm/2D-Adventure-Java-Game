package adventureGame2D;

import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	public static final int TRADE_DOOR = 19;

	//Open audio files
	Clip clip;
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	
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
		soundURL.add (getClass().getResource("/sounds/player/cursor.wav")); //9
		soundURL.add (getClass().getResource("/sounds/player/inventory-open.wav")); //10
		soundURL.add (getClass().getResource("/sounds/game/cursor_select.wav")); //11
		soundURL.add (getClass().getResource("/sounds/player/inventory_select.wav")); //12
		soundURL.add (getClass().getResource("/sounds/player/upgrade-failed.wav")); //13
		soundURL.add (getClass().getResource("/sounds/objects/health-potion-drink.wav")); //14
		soundURL.add (getClass().getResource("/sounds/player/upgrade-success.wav")); //15
		soundURL.add (getClass().getResource("/sounds/objects/burning.wav")); //16
		soundURL.add (getClass().getResource("/sounds/player/cuttree.wav")); //17
		soundURL.add (getClass().getResource("/sounds/game/gameover.wav")); //18
		soundURL.add (getClass().getResource("/sounds/game/trade-door.wav")); //19
		
		
		
	}
	
	public void setFile(int i) {
		try {
			
			//Obtain the clip that can be used for playback
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL.get(i));
			clip = AudioSystem.getClip();
			clip.open(ais);
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			this.adjustVolume();
			
		} catch(Exception e){
			
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
	
	public final void adjustVolume () {
		switch (volumeScale) {
		case 0 -> {volume = -80f;}
		case 1 -> {volume = -20f;}
		case 2 -> {volume = -12f;}
		case 3 -> {volume = -5f;}
		case 4 -> {volume = 1f;}
		case 5 -> {volume = 6f;}
		}
		
		fc.setValue(volume);
	}
	
	public final int getVolumeScale () {
		return volumeScale;
	}
	
	public final void setVolumeScale (int volumeScale) {
		this.volumeScale = volumeScale;
	}
}
