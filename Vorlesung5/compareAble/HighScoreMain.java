package compareAble;

import java.util.ArrayList;
import java.util.Collections;

public class HighScoreMain {
	public static void main(String[] args) {
		ArrayList<HighScore> highscores = new ArrayList<>();
		highscores.add(new HighScore("Pika", 42));
		highscores.add(new HighScore("Chuck", 10000));
		highscores.add(new HighScore("Vegeta", 9001));
		highscores.add(new HighScore("Edmund W", 42));
		highscores.add(new HighScore("Covid", 19));
		highscores.add(new HighScore("Terminator", 1337));
		
		Collections.sort(highscores);
		
		for(HighScore score : highscores) {
			//String.format formatiert das Object.
			//%s Platzhalter für String, %d Platzhalter für eine Zahl
			//%20s Hat 20 Zeichen als Platzhalter
			System.out.println(String.format("%20s", score.getName() + ":\t" + score.getPoints()));
		}
	}
}
