package chat;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ConnectionThread extends Thread  {

	
	private ServerSocket serverSocket;
	private boolean isRunning = true;
	private ArrayList<ReaderThread> readerThread;
	private ArrayList<PrintWriter> printWriter;
	private BlockingQueue<String> queue;
	

	public ConnectionThread(ServerSocket serverSocket, ArrayList<ReaderThread> readerThread, ArrayList<PrintWriter> printWriter,
			BlockingQueue<String> queue) {
		super();
		this.serverSocket = serverSocket;
		this.readerThread = readerThread;
		this.printWriter = printWriter;
		this.queue = queue;
	}

	@Override
	public void run() {
		
		
			
		while(isRunning) {
		
			try {
				
				Socket socket = serverSocket.accept();
				System.out.println("Connection von einem Client angenommmen.");
				
				PrintWriter clientWriter = new PrintWriter(socket.getOutputStream());
				Scanner clientScanner = new Scanner(socket.getInputStream());
				
				clientWriter.println("Hallo Client! Hier kannst du eine Nachricht eingeben:)");
				clientWriter.flush();
				synchronized(printWriter) {
					printWriter.add(clientWriter);
				}
				
				ReaderThread clientReaderThread = new ReaderThread(clientScanner, queue);
				clientReaderThread.start();
				synchronized(readerThread) {
					readerThread.add(clientReaderThread);
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void quit() {
		isRunning = false;
	}
}
