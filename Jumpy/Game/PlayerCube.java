package Game;

import views.IGameObject;

public class PlayerCube implements IGameObject{

	private int x;
	private int y;
	private int width;
	private int height;
	private String sprite;
	public enum movement
	{
		NO, LEFT, RIGHT, UP, DOWN
	}
	private movement horizontal;
	private movement vertical;
	private int jumpHeight;
	private int playerLives = 3;
	
	public PlayerCube(int x, int y, int width, int height, String sprite) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	public void resetMovmentValues() {
		jumpHeight=0;
		vertical = movement.NO;
		horizontal = movement.NO;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return sprite;
	}
	
	public void moveLeft() {
		x = x - 10;
	}
	
	public void moveRight() {
		x = x + 10;
	}
	
	public void moveDown() {
		y = y + 5;
	}
	
	public void moveUp() {
		y = y - 5;
	}

	public movement getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(movement horizontal) {
		this.horizontal = horizontal;
	}

	public movement getVertical() {
		return vertical;
	}

	public void setVertical(movement vertical) {
		this.vertical = vertical;
	}

	public int getJumpHeight() {
		return jumpHeight;
	}

	public void setJumpHeight(int jumpHeight) {
		this.jumpHeight = jumpHeight;
	}

	public int getPlayerLives() {
		return playerLives;
	}

	public void setPlayerLives(int playerLives) {
		this.playerLives = playerLives;
	}
	
	public void decrementPlayerLives() {
		this.playerLives--;
	}
	
	public void decrementJumpHeight() {
		this.jumpHeight--;
	}
	
	public void incrementJumpHeight() {
		this.jumpHeight++;
	}
}
