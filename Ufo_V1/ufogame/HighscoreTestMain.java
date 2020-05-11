package ufogame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//import datastructures.HighScore;

public class HighscoreTestMain {

	public static void main(String[] args) {
		
		Highscore highscores = new Highscore();
		
		highscores.setHighscore(new Score("Test1", 120));
		highscores.setHighscore(new Score("Test2", 40));
		highscores.setHighscore(new Score("Test3", 60));
		highscores.setHighscore(new Score("Test4", 80));
		highscores.setHighscore(new Score("Test5", 100));
		
		highscores.sortHighscoreList();
				
		File file = new File("Ufo_V1\\assets\\data\\Highscores.txt");
		
		if(file.exists()) {
			
			System.out.println("Die Datei existiert");
			
		} else {
			
			System.out.println("Die Datei wird angelegt");
			
			try {
				
//					file.mkdir();
				file.createNewFile();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		try (FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos)){
			
			oos.writeObject(highscores);
			oos.flush();
			oos.close();
			fos.close();
			System.out.println("Objekt weg geschrieben.");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Object obj = ois.readObject();
			fis.close();
			ois.close();
			
			//Typprüfung vor dem Casten, Achtung es gibt auch Hacker!
			if (obj instanceof Highscore) {
				Highscore highscoreAusgabeObjekt = (Highscore) obj;
				
				ArrayList<Score> highscoreAusgabe = highscoreAusgabeObjekt.getHighscore();
				
				for(Score score : highscoreAusgabe) {
					System.out.println(String.format("%20s:\t%d", score.getUsername(), score.getHits()));
				}
				
				System.out.println("Erfolgreich weg geschrieben und auch wieder einglesen.");
			}else {
				System.out.println("Falscher Objekttyp beim Einlesen");
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
