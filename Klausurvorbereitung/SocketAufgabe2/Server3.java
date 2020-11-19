package SocketAufgabe2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server3 {

	private boolean isRunning = true;
	
	public void createServer3() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(3445, 10);
			
			while(isRunning) {
				Socket socket = serverSocket.accept();
				clientThread(socket);
				
			}
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}


	private void clientThread(Socket socket) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					OutputStream outputStream = socket.getOutputStream();
					InputStream inputStream = socket.getInputStream();
					
					Scanner scanner = new Scanner(inputStream);
					PrintWriter printWriter = new PrintWriter(outputStream);
					
					while(isRunning) {
						
						if(scanner.hasNextLine()) {
							String buffer = scanner.nextLine();
							System.out.println(buffer);
							printWriter.println("Nachricht empfangen und gespeichert!");
							printWriter.flush();
							
							if(buffer.contentEquals("quit")) {
								isRunning = false;
							}
						}
						
					}
					
					
					printWriter.close();
					scanner.close();
					socket.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server3 server3 = new Server3();
		server3.createServer3();

	}

}
