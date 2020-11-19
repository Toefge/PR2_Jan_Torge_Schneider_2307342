package Game;

public class PlayerData {
	
	private int spieler;
	private int x;
	private int y;
	private String name;
	private boolean isRunning = true;

	public PlayerData(String name) {
		super();
		this.name = name;
	}
	
	public PlayerData(String name, boolean isRunning) {
		this.name = name;
		this.isRunning = isRunning;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public int getSpieler() {
		return spieler;
	}

	public void setSpieler(int spieler) {
		this.spieler = spieler;
	}
}
