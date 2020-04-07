package ufogame;

import java.awt.Color;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import view.GameFrameWork;
import view.IKeyboardListener;
import view.ITickableListener;
import view.Message;

public class Game implements ITickableListener, IKeyboardListener{
	
	//Idea: we want to have multiple instances of an ufo and of a projectile
	//<Datentyp> Name 
	//Arraylist Endlos ELemente
	//Speichert die Projektile des Ships
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	//Speichert die Projektile der Ufos
	private ArrayList<Projectile> projectilesUfos = new ArrayList<>();
	private ArrayList<Ufo> ufos = new ArrayList<>();
	//So war ist mit normalen Arrays
	//private Projectile [] projectiles = new Projectile[30];
	private Ship ship;
	
	private int screenWidth = 900;
	private int screenHeight = 700;
	private GameFrameWork frameWork = new GameFrameWork();
	
	//Speichert die Anzahl der Treffer
	private int hitCounter;
	
	//Speichert eine Nachricht, die später den Trefferstand anzeigen soll.
	Message scoreMessage;
	
	//Speichert die Anzahl an Munition die aktuell verfühgbar ist.
	private int ammo;
	Message ammoMessage;
	private int reloadTimer;
	Message menueMessage;
		
	private boolean gamestatus;
	
	
	private static Sound music = new Sound();
	
	public void menueLoop(){
		
		
		music.loadMenueMusic();
		music.playMenueMusic();
		
		gamestatus=false;
		frameWork.setSize(screenWidth, screenHeight);
		//frameWork.setBackgroundColor(Color.BLACK);
		frameWork.setBackground(new Background("Ufo_V1\\assets\\space17.jpg")); 
		//Jedes mal wenn der Timer tickt wird die Tick Methode aufgerufen
		frameWork.addTick(this);
		frameWork.addIKeyInput(this);
		menueMessage = new Message("Press enter to start the game!", screenWidth/5, screenHeight/2, 40, Color.WHITE);
		frameWork.addMessage(menueMessage);
	}
	
	/**
	 * Initiates everything for the game. Multiple ufos and a ship are created.
	 */
	public void gameLoop() {
		
		music.loadBackgroundMusic();
		music.playBackgroundMusic();
		
		//frameWork.setSize(screenWidth, screenHeight);
		
		frameWork.setBackground(new Background("Ufo_V1\\assets\\space14.jpg")); 
		
		ship = new Ship(screenWidth/2, 4 * screenHeight / 5, screenWidth / 12, screenWidth / 12, 
				"Ufo_V1\\assets\\Ufo Spiel\\Raumschiff\\Raumschiff3.png");
		
		//erst hier wird das ship gezeichnet, davor wird nur das Objekt erstellt
		frameWork.addGameObject(ship);
		
		Ufo ufo = new Ufo(-20, screenHeight / 5, screenWidth / 10, screenWidth / 10, 2, 
				"Ufo_V1\\assets\\Ufo Spiel\\Ufo\\Ufo_Gruen.png");
		ufos.add(ufo);
		
		frameWork.addGameObject(ufo);
		
		//Nicht size sondern eine Konkrete menge mit 10 angeben
		for(int i = 1; i < 10; i++) {
			ufos.add(new Ufo(ufos.get(i-1).getX() - 300, ufos.get(0).getY(), ufos.get(0).getWidth(), 
					ufos.get(0).getHeight(), ufos.get(0).getSpeed(), ufos.get(0).getImagePath()));
			
			frameWork.addGameObject(ufos.get(i));
		}
		
		//Jedes mal wenn der Timer tickt wird die Tick Methode aufgerufen
		frameWork.addTick(this);
		//Für ein Ende des Spiels frameWork.removeTick(this);
		
		frameWork.addIKeyInput(this);
		
		
		//Der hitCounter wird bei der Initialisierung des Spieles auf 0 gesetzt.
		hitCounter = 0;
		
		scoreMessage = new Message("Treffer: " + hitCounter, 100, 100, 30, Color.RED);
		frameWork.addMessage(scoreMessage);
		
		ammo = 5;
		
		ammoMessage = new Message("Munition: " + ammo, 600, 100, 30, Color.RED);
		frameWork.addMessage(ammoMessage);
		//Zum Löschen des Textes:
		//frameWork.removeMessage(message);
		
		reloadTimer = 0;
		
	}
	
	//Tick ist die Game Loop.
	@Override
	public void tick(long elapsedTime) {
		// TODO Auto-generated method stub
		
		//Nachfolgendes soll nur im Spiel, nicht aber im Menü ausgeführt werden.
		if(gamestatus==true) {
			
			//Prüft einen Treffer
			checkCollision();
			
			//Prüft, ob ein Ufo schießen soll
			checkUfoShoot();
			
			//Bewegt die Ufos
			for(Ufo u: ufos) {
				u.move();
			}
			
			//Bewegt die Projektile des Ships
			for(Projectile p: projectiles) {
				p.move();
			}
			
			//Bewegt die Projektile der Ufos
			for(Projectile pu: projectilesUfos) {
				pu.moveDown();
			}
			
			//Entfernt die Projektile, die den Bildschirm verlassen haben.
			//Checken ob, ArrayList gefüllt ist!!!
			if(projectiles.size()>0) {
				if(projectiles.get(0).getY() < 0) {
					
					frameWork.removeGameObject(projectiles.get(0));
					//Entfernen des Projektiles, das nicht mehr zu sehen ist
					projectiles.remove(0); 
				}
			}
			
			//Entfernt die Ufo Projektile, die den Bildschirm verlassen haben.
			if(projectilesUfos.size()>0) {
				if(projectilesUfos.get(0).getY()>screenHeight) {
					
					frameWork.removeGameObject(projectilesUfos.get(0));
					//Entfernen des Projektiles, das nicht mehr zu sehen ist
					projectilesUfos.remove(0);
				}
			}
				
			//Wenn das erste Ufo in der Liste über den Bildschirm hinauswandert, wird ein neues hinzugefühgt
			if(ufos.get(0).getX() > screenWidth) {
				frameWork.removeGameObject(ufos.get(0));
				//Entfernen des Ufos, das nicht mehr zu sehen ist
				ufos.remove(0);
				//Ändert sich nur der X Wert 
				ufos.add(new Ufo(ufos.get(ufos.size() - 1).getX() - 300, ufos.get(0).getY(), ufos.get(0).getWidth(), 
						ufos.get(0).getHeight(), ufos.get(0).getSpeed(), ufos.get(0).getImagePath()));
				frameWork.addGameObject(ufos.get(ufos.size()-1));
			}
			
			//Es muss in der Spielloop geprüft werden, ob die Munition aufgebraucht ist. 
			//Tut man dies (wie bei mir anfänglich) in den Key Funktionen, bekommt das Spiel nie mit, dass die Munition bereits auf 0 ist. 
			//Erst, wenn man ein erneutes mal auf Space drückt.
			if(ammo==0) {
				ammoMessage.setMessage("Press R to relaod.");
			}
			
			//In der Game Loop muss der Timer nach dem Nachladen dekrementiert werden.
			//Wenn dieser kurz vor Null ist, wird die Anzeige geändert und noch einmal dekrementiert.
			if(reloadTimer>1) {
				reloadTimer--;
			}else if(reloadTimer==1){
				ammoMessage.setMessage("Munition: " + ammo);
				reloadTimer--;
			}
			
			//Muss am Ende stehen, ansonsten erhält man eine Exception, wenn bereits alle Elemente gelöscht wurden,
			//aber das Programm noch mal in die if Schleife zurück kehrt.
			if(checkDeath()) {
				
				music.loadDeath();
				music.playDeath();
				
				//Ruft die Methode endGame auf, die die Game Loop beendet und zurück ins Menü kehrt.
				endGame();
			}
		}
	}
	
	//Methode die überprüft, ob ein Projektil ein Ufo getroffen hat.
	//Für mein Programm muss Collision nicht unbedingt vom Typ boolean sein.
	public void checkCollision() {
		/*
		 * Alte Version, hätte noch eine weitere for- Schleife gebraucht, um zu überprüfen, welche Objekte das Attribut collided gesetzt haben.
		 * Neue Version unten.
		 * 
		for(Ufo u: ufos) {
			for(Projectile p: projectiles) {
				
				if(p.getY()==u.getY()+u.getHeight() && p.getX()>=u.getX() && p.getX()<=u.getX()+u.getWidth()) {
					
					//Auskommentieren zur Prüfung der Funktion
					//System.out.printf("Getroffen\n");
					
					p.setCollided(true);
					u.setCollided(true);
					
					//Der hitCounter wird inkrementiert. Anschließend wird der String mit in dem Objekt message mit dem aktuellen Counter neu gesetzt.
					hitCounter++;
					message.setMessage("Treffer: " + hitCounter);
				}
			}
		}
		*/
		
		//Es werden alle Ufos - Projektil Kombinationen durchlaufen und geprüft, ob deren sich deren X und Y Werte Überschneiden.
		//Das Attribut isCollided von Ufo und Projectile und Ufo wird gar nicht gesetzt sondern die Objekte werden direkt gelöscht.
		for(int u = 0; u<ufos.size(); u++) {
			for(int p = 0; p<projectiles.size(); p++) {
				
				//In dieser Version ist die Abfrage nach der Kollision etwas länger als in der alten.
				//Buffer wird auf true gesetzt, wenn der Ausdruck auf der rechten Seite stimmt. Dieser überprüft, ob ein Projectil mit einem Ufo kollidiert.
				//Zusammengefasst: PorjekY==UfoY+HalbeUfoHöhe && (ProjekX liegt zwischen den X Werten des Ufo)
				boolean buffer = projectiles.get(p).getY() == ufos.get(u).getY()+ufos.get(u).getHeight()/2 && projectiles.get(p).getX() >= ufos.get(u).getX() && projectiles.get(p).getX() <= ufos.get(u).getX()+ufos.get(u).getWidth();
				
				//Wenn die beiden Objekte kollidieren, werden beide vom Frame und aus den ArrayListen gelöscht.
				//Anschließden wird noch ein neues Ufo an die ArrayList angehängt, da sonst nach 10 Treffern ein Fehler augegeben wird, da die Liste leer ist.
				if(buffer) {
					
					//Auskommentieren zur Prüfung der Funktion
					//System.out.printf("Getroffen\n");
					
					//Ufo entfernen
					frameWork.removeGameObject(ufos.get(u));
					ufos.remove(u);
					
					//Projektil entfernen
					frameWork.removeGameObject(projectiles.get(p));
					projectiles.remove(p);
					
					//Damit das Spiel nicht aufhört, nachdem man 10 Ufos abgeschossen hat.
					ufos.add(new Ufo(ufos.get(ufos.size() - 1).getX() - 300, ufos.get(0).getY(), ufos.get(0).getWidth(), 
							ufos.get(0).getHeight(), ufos.get(0).getSpeed(), ufos.get(0).getImagePath()));
					frameWork.addGameObject(ufos.get(ufos.size()-1));
					
					//Der hitCounter wird inkrementiert. Anschließend wird der String mit in dem Objekt message mit dem aktuellen Counter neu gesetzt.
					hitCounter++;
					scoreMessage.setMessage("Treffer: " + hitCounter);
					
					music.loadExplosion();
					music.playExplosion();
				}
			}	
		}
	}
	
	//Methode die Prüft, ob ein Ufo Projektil das Ship getroffen hat.
	//Ist dies der Fall wird ein true zurück gegeben. Ansonsten liefert die Methode immer nur false als return Wert.
	public boolean checkDeath() {
		
		for(int pu = 0; pu<projectilesUfos.size(); pu++) {
			
			//Koordinatenvergleich
			boolean buffer = projectilesUfos.get(pu).getY() == ship.getY() && projectilesUfos.get(pu).getX() >= ship.getX() && projectilesUfos.get(pu).getX() <= ship.getX()+ship.getWidth();	
					
			if(buffer) {
				return true;	
			}
		}
		
		return false;
	}
	
	//Methode die überprüft, ob sich ein Ufo direkt über dem Ship befindet. Ist dies der Fall soll eine weitere Methode aufgerufen werden.
	public void checkUfoShoot() {
		
		//Die Position aller Ufos im Verhältnis zum Ship wird geprüft.
		for(int u = 0; u<ufos.size(); u++) {
				
			//Zusammengefasst: SchiffX+HalbeBreiteShiff == UfoX+HalbeBreiteUfo 
			boolean buffer = ship.getX()+ship.getWidth()/2 == ufos.get(u).getX()+ufos.get(u).getWidth()/2;
			
			if(buffer) {
				
				//Der Methode ufoShoot werden die X und Y Werte des jeweiligen Ufos übergeben. An dieser Stelle soll dann vom Ufo aus ein Projektil abgefeuert werden
				//Die Zusammensetzung der einzelnen Werte wurde im Verlauf angepasst.
				ufoShoot(ufos.get(u).getX()+ufos.get(u).getWidth()/2-ship.getWidth()/4, ufos.get(u).getY()+ufos.get(u).getHeight()/2);
			}
		}
	}
	
	//Methode die einen Schuss abgibt.
	public void shoot() {
		//create a projectile
		Projectile projectile = new Projectile(ship.getX() + ship.getWidth()/4, ship.getY(), 
		ship.getWidth()/2, ship.getWidth()/2, 3, "Ufo_V1\\assets\\Ufo Spiel\\Geschoss\\Geschoss1.png");
		
		//Fügt das Projektil dem Frame und der ArrayList hinzu.
		frameWork.addGameObject(projectile);
		projectiles.add(projectile);
		
		/*
		 * Andere Möglichkeiten aus der Vorlesung.
		 * 
		projectiles.get(0).getWidth();
		
		projectiles.size();
		
		//Normaler Array
		projectiles[0] = projectiles;
		*/
		
		//Die Munition wird dekrementiert und die neue Anzahl wird auf dem Frame angezeigt.
		ammo--;
		ammoMessage.setMessage("Munition: " + ammo);
		
		music.loadShootShip();
		music.playShootShip();
	}
	
	//Methode lässt die Ufos ein Projektil abfeuern an der Stelle XY
	public void ufoShoot(int X, int Y) {
		
		//create a projectile
		Projectile projectilesUfo = new Projectile(X, Y, 
		ship.getWidth()/2, ship.getWidth()/2, 3, "Ufo_V1\\assets\\Ufo Spiel\\Geschoss\\Geschoss6.png");
		//Fügt das Projektil dem Frame und der ArrayList hinzu.
		frameWork.addGameObject(projectilesUfo);
		projectilesUfos.add(projectilesUfo);
		
		music.loadShootUfo();
		music.playShootUfo();
	}
	
	//Legt fest, welche Tasten ausgelesen werden sollen.
	@Override
	public int[] getKeys() {
		//Es wird auf die Tasten Space und Enter gehört.
		int [] keys = {KeyEvent.VK_SPACE, KeyEvent.VK_ENTER,KeyEvent.VK_R,KeyEvent.VK_A,KeyEvent.VK_D};
		return keys;
	}

	//Übernimmt die Auswertung der Tastenanschläge.
	@Override
	public void keyDown(int key) {
		
		//Hier muss der Status des Spiels abgefragt werden, da im Menü die Tasten anders belegt sind als im Spiel.
		if(gamestatus==true) {
			
			/*Ruft die Methode zum Schießen auf, wenn Space gedrückt wird. 
			 * Allerdings nur, wenn nicht gerade nachgeladen wird und auch nur, wenn man noch Munition über hat.
			 * Diese Methode bekommt in key den Int Wert einer Taste übergegeben. 
			 * Durch eine If-Abfrage kann man auslesen, welche Taste gedrückt wurde und eine enstprechende Aktion ausführen.
			*/
			if(reloadTimer == 0 && ammo > 0 && key==KeyEvent.VK_SPACE) {
				
				//ruft die Methode shoot auf
				shoot();
			}
			
			//Nur wenn der reloadTimer einmalig auf 0 ist und auch die Munition leer ist, kann die R Taste zum nachladen gedrückt werden.
			if(reloadTimer== 0 && ammo==0 && key==KeyEvent.VK_R) {
				
				//Ändert den Text bei der Anzeige der Munition in "Reloading"
				ammoMessage.setMessage("Reloading!");
				
				//Hier wird festgelegt, wie lange die Nachladezeit beträgt.
				reloadTimer = 30;
				//Die Munition wird zurück auf 5 gesetzt.
				ammo = 5;
				
				music.loadReload();
				music.playReload();
			}
			
			//Wenn in Game Enter gedrückt wird, gelangt man zurück zum Menü.
			if(key==KeyEvent.VK_ENTER) {
				
				//In dieser Methode werden alle Objekte entfernt und gelöscht.
				endGame();
				
			}
			
			if(key==KeyEvent.VK_A) {
				ship.moveLeft();
			}
			
			if(key==KeyEvent.VK_D) {
				ship.moveRight();
			}
			
		//Folgende Tastaturauswertung geschieht nur im Menü. 
		}else if(key==KeyEvent.VK_ENTER){
			
			//Wenn im Menü Enter gedrückt wird, ändert sich der Spielstatus.
			//Gelcihzeitig müssen alle Menü Objekte entfernt werden.
			gamestatus= true;
			frameWork.removeMessage(menueMessage);
			frameWork.removeTick(this);
			frameWork.removeIKeyboardInput(this);
			
			music.stopMenueMusic();
			
			//Nun wird die Funktion init aufgerufen um das Spiel zu initialisieren.
			gameLoop();
		}
	}
	
	//Methode die alle Spielobjekte entfernt und löscht.
	public void endGame() {
		
		//Spielstatus wird auf "nicht spielen" gesetzt.
		gamestatus= false;
		
		//Löscht alle Objekte auf dem Frame.
		frameWork.removeTick(this);
		frameWork.removeIKeyboardInput(this);
		frameWork.removeGameObject(ship);
		frameWork.removeMessage(ammoMessage);
		frameWork.removeMessage(menueMessage);
		frameWork.removeMessage(scoreMessage);
		
		//Null oder gar kein Parameter müssten normalerweise für die weitere Implemenitierung ausreichen.
		//frameWork.removeBackground(null);
		
		/*
		 * Erste Variante alle Objekte in den ArrayLists zu löschen.
		 * 
		for(int i = 0;i<ufos.size();i++) {
			frameWork.removeGameObject(ufos.get(i));
			ufos.remove(i);
		}
		for(int i = 0;i<projectiles.size();i++) {
			frameWork.removeGameObject(projectiles.get(i));
			projectiles.remove(i);
		}
		*/
		
		//Zweite Variante.
		
		while(ufos.size() > 0) {
			frameWork.removeGameObject(ufos.get(0));
			ufos.remove(0);
		}
		while(projectiles.size() > 0) {
			frameWork.removeGameObject(projectiles.get(0));
			projectiles.remove(0);
		}
		while(projectilesUfos.size() > 0) {
			frameWork.removeGameObject(projectilesUfos.get(0));
			projectilesUfos.remove(0);
		}
		
		/*
		 * Ich habe erst im nachhinein die Methode clear() entdeckt.
		 * Allerdings habe ich deren implementierung noch nicht ganz durchschaut.
		ufos.clear();
		projectiles.clear();
		*/
		
		//Kehrt zurück zur Menü Schleife.
		music.stopBackgorundMusic();
		menueLoop();
	}
}
