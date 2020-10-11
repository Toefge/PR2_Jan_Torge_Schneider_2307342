package SocketAufgabe4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server4 {

	private boolean isRunning = true;
	
	public void createServer4() {
		
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
					
					String path = "Klausurvorbereitung\\SocketAufgabe4\\speicher.txt";
					
					File file = new File(path);
					
					if(file.exists()) {
						System.out.println("Die Datei existiert!");
					}else {
						System.out.println("Datei wird angelegt");
						try {
							//file.mkdir();
							file.createNewFile();
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					
					FileWriter fileWriter = new FileWriter(file);
					PrintWriter printWriter = new PrintWriter(fileWriter);
					
					InputStream inputStream = socket.getInputStream();
					
					Scanner scanner = new Scanner(inputStream);
					
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
					
					
					printWriter.close();
					scanner.close();
					socket.close();
					serverSocket.close();
					printWriter.close();
					fileWriter.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server4 server4 = new Server4();
		server4.createServer4();

	}
}
