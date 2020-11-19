package Game;

import java.util.Scanner;

public class Server {

	private boolean isRunning = true;
	private NetworkThread networkThread;
	private Scanner scanner;
	
	public void createServer() {
		
		try {
			
			networkThread = new NetworkThread();
			networkThread.start();
			
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
			networkThread.closeServer();
			scanner.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.createServer();
	}
}
