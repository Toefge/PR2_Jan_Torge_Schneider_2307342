package Game;

public class GameMain {

	public static void main(String[] args) {
		
		//Man Koente diese main auch in die Klasse GameLoop auslagern und brauchte diese Klasse dann gar nicht mehr.
		//Oder man koennte auch zwei Mal diese Klasse starten, anstatt zwei GameLoop Klassen zu erstellen.
		GameLoop gameLoop = new GameLoop();
		gameLoop.init();
		GameLoop gameLoop2 = new GameLoop();
		gameLoop2.init();
	}
}
