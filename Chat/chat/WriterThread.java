package chat;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner; 
import java.util.concurrent.BlockingQueue;

public class WriterThread extends Thread {
	
	private ArrayList<PrintWriter> printWriter;
	private BlockingQueue<String> queue;
	private boolean isRunning = true;

	public WriterThread(ArrayList<PrintWriter> printWriter, BlockingQueue<String> queue) {
		super();
		this.printWriter = printWriter;
		this.queue = queue;
		
//		System.out.println("WriterThread erstellt.");
	}

	@Override
	public void run() {
		System.out.println("WriterThread läuft.");

		while(isRunning) {
			
			try {
				
				if(queue.size()!=0) {
					
					System.out.println("Test");
					
					String buffer = queue.take();
					
					for(PrintWriter clientWriter : printWriter) {
						clientWriter.println(buffer);
						clientWriter.flush();
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void quit() {
		isRunning = false;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
