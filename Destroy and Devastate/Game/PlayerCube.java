package Game;

import views.IGameObject;

public class PlayerCube implements IGameObject{

	private int x;
	private int y;
	private int width;
	private int height;
	private String sprite;
	
	public PlayerCube(int x, int y, int width, int height, String sprite) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
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
}
