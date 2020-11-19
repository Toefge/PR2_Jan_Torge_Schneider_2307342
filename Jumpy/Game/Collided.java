package Game;

public class Collided {
	
	private PlayerCube[] player;
	private Coordinates coordinates;
	private Base base;
	
	public Collided(PlayerCube[] player, Coordinates coordinates, Base base) {
		super();
		this.player = player;
		this.coordinates = coordinates;
		this.base = base;
	}

	public boolean player1LeftPlayer2Right() {
		
		return (((player[1].getX()+coordinates.getPlayerSize() == player[2].getX()) 
				&& ((player[1].getY() <= player[2].getY() 
				&& player[2].getY() <= player[1].getY()+coordinates.getPlayerSize()) 
				|| (player[1].getY() <= player[2].getY()+coordinates.getPlayerSize()
				&& player[2].getY()+coordinates.getPlayerSize() <= player[1].getY()+coordinates.getPlayerSize()))) 
				|| (player[1].getX()+coordinates.getPlayerSize() == base.getX()+base.getWidth())
				|| player[2].getX() == base.getX());
	}
	
	public boolean player2LeftPlayer1Right() {
		
		return (((player[2].getX()+coordinates.getPlayerSize() == player[1].getX()) 
				&& ((player[2].getY() <= player[1].getY() 
				&& player[1].getY() <= player[2].getY()+coordinates.getPlayerSize())
				|| (player[2].getY() <= player[1].getY()+coordinates.getPlayerSize() 
				&& player[1].getY()+coordinates.getPlayerSize() <= player[2].getY()+coordinates.getPlayerSize())))
				|| (player[2].getX()+coordinates.getPlayerSize() == base.getX()+base.getWidth())
				|| player[1].getX() == base.getX());
	}
	
	public boolean player1OnPlayer2() {
		
		return ((player[1].getY()+coordinates.getPlayerSize() == player[2].getY()) 
				&& ((player[1].getX() <= player[2].getX() 
				&& player[2].getX() <= player[1].getX()+coordinates.getPlayerSize()) 
				|| ((player[1].getX() <= player[2].getX()+coordinates.getPlayerSize() 
				&& player[2].getX() <= player[1].getX()+coordinates.getPlayerSize()))));
	}
	
	public boolean player2OnPlayer1() {
		
		return ((player[2].getY()+coordinates.getPlayerSize() == player[1].getY()) 
				&& ((player[2].getX() <= player[1].getX() 
				&& player[1].getX() <= player[2].getX()+coordinates.getPlayerSize()) 
				|| ((player[2].getX() <= player[1].getX()+coordinates.getPlayerSize() 
				&& player[1].getX() <= player[2].getX()+coordinates.getPlayerSize()))));
	}
	
	public boolean playerOutsideTheBase(int buffer) {
		return ((player[buffer].getX() <= base.getX() - coordinates.getPlayerSize())
				|| (player[buffer].getX() >= base.getX() + base.getWidth()));
	}
}
