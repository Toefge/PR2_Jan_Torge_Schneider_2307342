package Game;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

	//BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	//ArrayList<ReaderThread> readerThread = new ArrayList<ReaderThread>();
	//ArrayList<PrintWriter> printWriter = new ArrayList<PrintWriter>();
	private boolean isRunning = true;
	
	private BlockingQueue<PlayerData> queue1 = new LinkedBlockingQueue<PlayerData>();
	private BlockingQueue<PlayerData> queue2 = new LinkedBlockingQueue<PlayerData>();
	private ObjectInputStream inputStream1;
	private ObjectOutputStream outputStream1;
	private ObjectInputStream inputStream2;
	private ObjectOutputStream outputStream2;
	private Reader reader1;
	private Reader reader2;
	private Writer writer1;
	private Writer writer2;
	private Socket socket1;
	private Socket socket2;
	
	private Scanner scanner;
	
	public void createServer() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(3445, 2);
			System.out.println("Server l‰uft.");
			
			//Ich habe mich gegen einen Connection Thread entschieden, da ich sowieso nur zwei Verbindungen zum Server erlauben will.
			//Das ich alles so umstaendlich als private var deklariert habe, ist dem fehlschlagenden Schlieﬂverhalten des Programms zu verschulden.
			//Ich wollte in der Lage sein, am Ende alle Verbindungen einzeln schlieﬂen zu koennen.
			socket1 = serverSocket.accept();

			inputStream1 = new ObjectInputStream(socket1.getInputStream());
			outputStream1 = new ObjectOutputStream(socket1.getOutputStream());

			reader1 = new Reader(inputStream1, queue2);
			reader1.start();

			writer1 = new Writer(outputStream1, queue1);
			writer1.start();

			socket2 = serverSocket.accept();

			inputStream2 = new ObjectInputStream(socket2.getInputStream());
			outputStream2 = new ObjectOutputStream(socket2.getOutputStream());

			reader2 = new Reader(inputStream2, queue1);
			reader2.start();

			writer2 = new Writer(outputStream2, queue2);
			writer2.start();
					
			/*
			 *Das "saubere" Schlieﬂen des Programmes funktioniert nicht wie gewuenscht....
			 *Es scheitert immer daran, dass die Reader Threads nicht richtig geschlossen werden.
			 *Aus dem Client heraus sollte das ueber die Eingabe von Enter funktionieren und in der Console des Servers kann man "quit" eingeben.
			 *Der Reader Thread meckert dann entweder, dass das Socket Objekt bereits getrennt wurde.
			 *Oder er wirft eine EOFException an der Stelle, wo sonst ein neues Objekt eingelesen wird.
			 *Mit dem Scanner hatte ich diese Probleme nicht. 
			 *Zur Vermeidung der EOFException kann ich leider auch nicht abfragen, ob der ObjectInputStream noch was empfangen will oder bereits ein null Byte empfangen hat.
			 */
			scanner = new Scanner(System.in);
			System.out.println("Gib quit ein um das Programm sauber zu beenden.");
			
			while(isRunning) {	
				if(scanner.hasNextLine()) {
					String message = scanner.nextLine();
					if(message.equals("quit")) {
						isRunning = false;
					}
				}
			}
			System.out.println("Programm geschlossen!");
			writer1.quit();
			writer2.quit();
			reader1.quit();
			reader2.quit();
			outputStream1.close();
			outputStream2.close();
			inputStream1.close();
			inputStream2.close();
			socket1.close();
			socket2.close();
			serverSocket.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.createServer();
	}
}
