package Netzwerk;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ReaderThread extends Thread{
	
	private Scanner scanner;
	private BlockingQueue<String> queue;
	private boolean isRunning = true;
	
	
	public ReaderThread(Scanner scanner, BlockingQueue<String> queue) {
		super();
		this.scanner = scanner;
		this.queue =queue;
	}


	@Override
	public void run() {
		
		while(isRunning) {
			try {
				String message = scanner.nextLine();
				System.out.println(message);
				queue.put(message);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void quit(){
		isRunning = false;
	}
}
