package compareAble;

public class HighScore implements Comparable<HighScore>{
	private String name;
	private int points;

	public HighScore(String name, int points) {
		super();
		this.name = name;
		this.points = points;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
	@Override
	public int compareTo(HighScore o) {
		if(this.points > o.points) {
			return -1;
		}else if(this.points < o.points) {
			return 1;
		}else {
			return name.compareTo(o.name);
		}
		
	}

}
