package Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class NetworkThread extends Thread{

	private ServerSocket serverSocket;
	private ArrayList<Socket> sockets = new ArrayList<Socket>();
	private ArrayList<WriterThread> writerThreads = new ArrayList<WriterThread>();
	private ArrayList<ReaderThread> readerThreads = new ArrayList<ReaderThread>();
	private BlockingQueue<PlayerData> queue1 = new LinkedBlockingQueue<PlayerData>();
	private BlockingQueue<PlayerData> queue2 = new LinkedBlockingQueue<PlayerData>();
	private ArrayList<String> playerNames = new ArrayList<String>();
	private int connections = 0;
	
	@Override
	public void run() {
		
		try {
			serverSocket = new ServerSocket(3445, 2);
			System.out.println("Server gestartet!");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(connections < 2) {
			try {
				Socket socket = serverSocket.accept();
				sockets.add(socket);
				connections++;
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				Scanner scanner = new Scanner(socket.getInputStream());
				
				if(connections ==1) {
					ReaderThread reader1 = new ReaderThread(scanner, queue2, playerNames);
					reader1.start();
					readerThreads.add(reader1);
					
					WriterThread writer1 = new WriterThread(printWriter, queue1);
					writer1.start();
					writerThreads.add(writer1);
				}else {
					ReaderThread reader2 = new ReaderThread(scanner, queue1, playerNames);
					reader2.start();
					readerThreads.add(reader2);
					
					WriterThread writer2 = new WriterThread(printWriter, queue2);
					writer2.start();
					writerThreads.add(writer2);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void closeServer() {
		System.out.println("Programm geschlossen!");
		PlayerData playerData = new PlayerData("Quit", false);
		queue1.add(playerData);
		queue2.add(playerData);
		
		try {
			for (ReaderThread reader : readerThreads) {
				reader.quit();
			}
			
			for (Socket socket : sockets) {
				socket.close();
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
