package Netzwerk;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMain {

	private boolean isRunning = true;
	public static void main(String[] args) {

		ClientMain client = new ClientMain();
		client.creatClient();;
	}
	
	public void creatClient(){
		try {
			
			Socket socket = new Socket("127.0.0.1",3445);
			
			PrintWriter printWirter = new PrintWriter(socket.getOutputStream());
			
			Scanner scanner = new Scanner(socket.getInputStream());
			
			System.out.println("Bitte gib eine Nachricht ein:");
			
			
			
			//Prozess des Eingebens wird in eine Methode ausgelagert und dort ein eigener Thread erstellt.
			writeMessage(printWirter);
			
			
//			Nachrichten vom Server lesen (immer wieder)
			while(isRunning) {
				System.out.println(scanner.nextLine());
			}
			
			
			
			
			scanner.close();
			printWirter.close();
			socket.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeMessage(PrintWriter printWirter) {
		
		//Wird in einem Thread ausgelagert
		//Schnellversion um einen Thread zu erstellen (geht mit jedem Interface).
		//normalerweise müsste man eine Thread Klasse erstellen (Dann kann man auch Werte übergeben etc.)
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				Scanner keyboard = new Scanner(System.in);
				
				//Immer wieder Daten einlesen und übers Netzwerk schreiben.
				while(isRunning) {
					
					String text = keyboard.next();
					if(text.equalsIgnoreCase("Quit")) {
						isRunning = false;
					}else {
						printWirter.println(text);
						printWirter.flush();
					}
				}
				keyboard.close();
			}
			
		}).start();
	}
	
}
