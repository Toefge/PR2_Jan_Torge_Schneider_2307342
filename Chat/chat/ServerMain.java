package chat;
import java.io.PrintWriter;  
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ServerMain {

	BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	ArrayList<ReaderThread> readerThread = new ArrayList<ReaderThread>();
	ArrayList<PrintWriter> printWriter = new ArrayList<PrintWriter>();
	private boolean isRunning = true;
	
	public void createServer() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(3445, 5);
			
			WriterThread writerThread = new WriterThread(printWriter, queue);
			writerThread.start();
			
			ConnectionThread connectionThread = new ConnectionThread(serverSocket, readerThread, printWriter, queue);
			connectionThread.start();
			
			while(isRunning) {
				
				for(ReaderThread reader : readerThread) {
					if(reader.isRunning()==false) {
						isRunning = false;
					}
				}
				System.out.println("Test");
			}
			
			for(PrintWriter writer : printWriter) {
				writer.close();
			}
			for(ReaderThread reader : readerThread) {
				reader.quit();
			}
			
			writerThread.quit();
			
			connectionThread.quit();
			
			queue.clear();
			serverSocket.close();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		ServerMain server = new ServerMain();
		server.createServer();
	}
}
