package SocketAufgabe5;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server5 {
	
private boolean isRunning = true;
	
	public void createServer5() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(3445, 1);
			
			while(isRunning) {
				Socket socket = serverSocket.accept();
				clientThread(socket, serverSocket);
				 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}


	private void clientThread(Socket socket, ServerSocket serverSocket) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					
					InputStream inputStream = socket.getInputStream();
					OutputStream outputStream = socket.getOutputStream();
					Scanner scanner = new Scanner(inputStream);
					PrintWriter printWriter = new PrintWriter(outputStream);
					
					
					while(isRunning) {
						
						if(scanner.hasNextLine()) {
							 
							String path = scanner.nextLine();
							
							File file = new File(path);
							
							if(file.exists()) {
								System.out.println("Die Datei existiert!");
								
								Scanner fileScanner = new Scanner(file);
								
								while(fileScanner.hasNext()) {
									String buffer = fileScanner.nextLine();
									printWriter.println(buffer);
									
								}
								printWriter.flush();
								fileScanner.close();
								
							}else {
								System.out.println("Die Datei existier nicht!");
								printWriter.println("Die Datei existier nicht!");
								printWriter.flush();
							}
							
							if(path.contentEquals("quit")) {
								isRunning = false;
							}
						}
						
					}
					
					printWriter.close();
					scanner.close();
					socket.close();
					serverSocket.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server5 server5 = new Server5();
		server5.createServer5();

	}
}
