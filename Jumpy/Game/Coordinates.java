package Game;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Coordinates {

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = screenSize.width;
	private int screenHeight = screenSize.height;
	private int inputWidth = 300;
	private int inputHeight = 200;
	private int yBase = screenHeight/6*5;
	private int playerSize = 50;
	private int playerBase = yBase-playerSize;
	private int player1SpawnX = screenWidth/6;
	private int player2SpawnX = screenWidth/6*5-playerSize;
	
	public Coordinates() {
		
	}
	
	public Coordinates(Dimension screenSize, int screenWidth, int screenHeight, int inputWidth, int inputHeight,
			int yBase, int playerSize, int playerBase, int player1SpawnX, int player2SpawnX) {
		super();
		this.screenSize = screenSize;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.inputWidth = inputWidth;
		this.inputHeight = inputHeight;
		this.yBase = yBase;
		this.playerSize = playerSize;
		this.playerBase = playerBase;
		this.player1SpawnX = player1SpawnX;
		this.player2SpawnX = player2SpawnX;
	}

	public Dimension getScreenSize() {
		return screenSize;
	}
	public void setScreenSize(Dimension screenSize) {
		this.screenSize = screenSize;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}
	public int getInputWidth() {
		return inputWidth;
	}
	public void setInputWidth(int inputWidth) {
		this.inputWidth = inputWidth;
	}
	public int getInputHeight() {
		return inputHeight;
	}
	public void setInputHeight(int inputHeight) {
		this.inputHeight = inputHeight;
	}
	public int getyBase() {
		return yBase;
	}
	public void setyBase(int yBase) {
		this.yBase = yBase;
	}
	public int getPlayerSize() {
		return playerSize;
	}
	public void setPlayerSize(int playerSize) {
		this.playerSize = playerSize;
	}
	public int getPlayerBase() {
		return playerBase;
	}
	public void setPlayerBase(int playerBase) {
		this.playerBase = playerBase;
	}
	
	public int getPlayer1SpawnX() {
		return player1SpawnX;
	}

	public void setPlayer1SpawnX(int player1SpawnX) {
		this.player1SpawnX = player1SpawnX;
	}

	public int getPlayer2SpawnX() {
		return player2SpawnX;
	}

	public void setPlayer2SpawnX(int player2SpawnX) {
		this.player2SpawnX = player2SpawnX;
	}
}
