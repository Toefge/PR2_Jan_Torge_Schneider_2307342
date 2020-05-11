package ufogame;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Highscore implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<Score> highscore = new ArrayList<>();

	public Highscore() {
		
	}

	public ArrayList<Score> getHighscore() {
		return highscore;
	}

	public void setHighscore(Score score) {
		highscore.add(score);
	}
	
	public void sortHighscoreList() {
		Collections.sort(highscore);
	}
	
	
}
