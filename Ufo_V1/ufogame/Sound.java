package ufogame;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;

public class Sound {
	
	private static File sound0;
	
	private static File sound1;
	
	private static File sound2;
	
	private static File sound3;
	
	private static File sound4;
	
	private static File sound5;
	
	private static File sound6;
	
	public static float volume;
	
	Clip shootUfo;
	
	Clip explosion;
	
	Clip backgroundMusic;
	
	Clip menueMusic;
	
	Clip shootShip;
	
	Clip death;
	
	Clip reload;
	

	public Sound() {
		
	   volume = -30f;

	}
	
	public void loadShootUfo() {
		
		sound0 = new File("Ufo_V1\\assets\\ufo_shoot.wav");
		
	}
	
	public void loadExplosion() {
		
		sound1 = new File("Ufo_V1\\assets\\explosion.wav");
		
	}
	
	public void loadBackgroundMusic() {
		
		sound2 = new File("Ufo_V1\\assets\\game_background.wav");
		
	}
	
	public void loadMenueMusic() {
		
		sound3 = new File("Ufo_V1\\assets\\alarm_kurz.wav");
		
	}
	
	public void loadShootShip() {
		
		sound4 = new File("Ufo_V1\\assets\\ship_shoot.wav");
		
	}
	
	public void loadDeath() {
		
		sound5 = new File("Ufo_V1\\assets\\death.wav");
		
	}
	
	public void loadReload() {
		
		sound6 = new File("Ufo_V1\\assets\\reload.wav");
		
	}
	
	public void playShootUfo() {
		
		//Falls ein Fehler auftritt, wird eine Exception benötigt.
		try {
			
			//Clip zuweisen und den Input Stream öffnen um die Datei einzulesen
			shootUfo = AudioSystem.getClip();
			shootUfo.open(AudioSystem.getAudioInputStream(sound0));
			
			//Lautstärke anpassen
			FloatControl gainConrtol = (FloatControl) shootUfo.getControl(FloatControl.Type.MASTER_GAIN);
			gainConrtol.setValue(-20f);
			
			shootUfo.start();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
	}
	
	public void playExplosion() {
		
		//Falls ein Fehler auftritt, wird eine Exception benötigt.
		try {
			
			//Clip zuweisen und den Input Stream öffnen um die Datei einzulesen
			explosion = AudioSystem.getClip();
			explosion.open(AudioSystem.getAudioInputStream(sound1));
			
			//Lautstärke anpassen
			FloatControl gainConrtol = (FloatControl) explosion.getControl(FloatControl.Type.MASTER_GAIN);
			gainConrtol.setValue(-10f);
			
			explosion.start();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
	}
	
	public void playBackgroundMusic() {
		
		//Falls ein Fehler auftritt, wird eine Exception benötigt.
		try {
			
			//Clip zuweisen und den Input Stream öffnen um die Datei einzulesen
			backgroundMusic = AudioSystem.getClip();
			backgroundMusic.open(AudioSystem.getAudioInputStream(sound2));
			
			//Lautstärke anpassen
			FloatControl gainConrtol = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
			gainConrtol.setValue(volume);
			
			//Clip als loop abspielen
			backgroundMusic.loop(100000);
			backgroundMusic.start();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
	}
	
	public void playMenueMusic() {
		
		//Falls ein Fehler auftritt, wird eine Exception benötigt.
		try {
			
			//Clip zuweisen und den Input Stream öffnen um die Datei einzulesen
			menueMusic = AudioSystem.getClip();
			menueMusic.open(AudioSystem.getAudioInputStream(sound3));
			
			//Lautstärke anpassen
			FloatControl gainConrtol = (FloatControl) menueMusic.getControl(FloatControl.Type.MASTER_GAIN);
			gainConrtol.setValue(volume);
			
			//Clip als loop abspielen
			menueMusic.loop(100000);
			menueMusic.start();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
	}
	
	public void playShootShip() {
		
		//Falls ein Fehler auftritt, wird eine Exception benötigt.
		try {
			
			//Clip zuweisen und den Input Stream öffnen um die Datei einzulesen
			shootShip = AudioSystem.getClip();
			shootShip.open(AudioSystem.getAudioInputStream(sound4));
			
			//Lautstärke anpassen
			FloatControl gainConrtol = (FloatControl) shootShip.getControl(FloatControl.Type.MASTER_GAIN);
			gainConrtol.setValue(volume);
			
			shootShip.start();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
	}
	
	public void playDeath() {
		
		//Falls ein Fehler auftritt, wird eine Exception benötigt.
		try {
			
			//Clip zuweisen und den Input Stream öffnen um die Datei einzulesen
			death = AudioSystem.getClip();
			death.open(AudioSystem.getAudioInputStream(sound5));
			
			//Lautstärke anpassen
			FloatControl gainConrtol = (FloatControl) death.getControl(FloatControl.Type.MASTER_GAIN);
			gainConrtol.setValue(volume);
			
			death.start();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
	}
	
	public void playReload() {
		
		//Falls ein Fehler auftritt, wird eine Exception benötigt.
		try {
			
			//Clip zuweisen und den Input Stream öffnen um die Datei einzulesen
			reload = AudioSystem.getClip();
			reload.open(AudioSystem.getAudioInputStream(sound6));
			
			//Lautstärke anpassen
			FloatControl gainConrtol = (FloatControl) reload.getControl(FloatControl.Type.MASTER_GAIN);
			gainConrtol.setValue(volume);
			
			reload.start();
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
	}
	
	//Zum stoppen der beiden Loops
	public void stopBackgorundMusic() {
		backgroundMusic.stop();
	}
	
	public void stopMenueMusic() {
		menueMusic.stop();
	}
	   
}
