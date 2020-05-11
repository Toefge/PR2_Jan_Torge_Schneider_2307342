package chat;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner; 
import java.util.concurrent.BlockingQueue;

public class ReaderThread extends Thread {

	private Scanner scanner;
	private BlockingQueue<String> queue;
	private boolean isRunning = true;

	public ReaderThread(Scanner scanner, BlockingQueue<String> queue) {
		super();
		this.scanner = scanner;
		this.queue = queue;
	}

	@Override
	public void run() {
		
		//System.out.println("ReaderThread läuft.");

		while(isRunning) {
			
			try {
					
				if(scanner.hasNextLine()) {
					String message = scanner.nextLine();
					
					if(message.equalsIgnoreCase("quit")) {
						isRunning = false;
					} else {
						queue.put(message);
					
						//System.out.println("Die Nachricht: " + message +" sollte eingelesen und in der Blocking Queue aufgenommen sein.");
					}
				}
					
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		scanner.close();
		//Alle informieren, dass vorbei ist. 
	}
	
	public void quit() {
		isRunning = false;
	}

	public boolean isRunning() {
		return isRunning;
	}
	
	

}
