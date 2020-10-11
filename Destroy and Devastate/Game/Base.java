package Game;

import views.IGameObject;

public class Base implements IGameObject{

	private int x;
	private int y;
	private int width;
	private int height;
	private String sprite;

	public Base(int x, int y, int width, int height, String sprite) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
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
}
