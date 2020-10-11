package SocketAufgabe1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server1 {
	
	private boolean isRunning = true;
	
	public void createServer1() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(3445, 1);
			
			Socket socket= serverSocket.accept();
			
			OutputStream outputStream = socket.getOutputStream();
			InputStream inputStream = socket.getInputStream();
			
			writerAndReaderThread(outputStream, inputStream);
		
			do {
				
			}while(isRunning);
			
			serverSocket.close();
			socket.close();

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writerAndReaderThread(OutputStream outputStream, InputStream inputStream) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Scanner scanner = new Scanner(inputStream);
				PrintWriter printWriter = new PrintWriter(outputStream);
				
				while(isRunning) {
					if(scanner.hasNextLine()) {
						String buffer = scanner.nextLine();
						System.out.println(buffer);
						printWriter.println(buffer);
						printWriter.flush();
						if(buffer.contentEquals("quit")) {
							isRunning = false;
						}
					}
				}
				
				scanner.close();
				printWriter.close();
			}
		}).start();
	}

	public static void main(String[] args) {
		Server1 server1 = new Server1();
		server1.createServer1();
	}
}
