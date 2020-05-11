package ufogame;

import java.io.Serializable;

public class Score implements Serializable, Comparable<Score> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  String username;
	private  int points;

	public Score(String username, int points) {
		super();
		this.username = username;
		this.points = points;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getHits() {
		return points;
	}

	public void setHits(int points) {
		this.points = points;
	}

	@Override
	public int compareTo(Score other) {
		if(this.points > other.points) {
			return -1;
		} else if(this.points < other.points) {
			return 1;
		}
		return username.compareTo(other.username);
	}
	
	

}
