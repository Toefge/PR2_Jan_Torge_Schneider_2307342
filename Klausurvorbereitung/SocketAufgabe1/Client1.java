package SocketAufgabe1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client1 {

	private boolean isRunning = true;
	
	public void createClient1() {
		
		try {
			
			Socket socket = new Socket("127.0.0.1", 3445);
			
			writerThread(socket);
			
			readerThread(socket);
			
			do {
				
			}while(isRunning);
			
			socket.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	private void readerThread(Socket socket) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try {
					InputStream inputStream = socket.getInputStream();
					
					Scanner scanner = new Scanner(inputStream);
					while(isRunning) {
						if(scanner.hasNextLine()) {
							System.out.println(scanner.nextLine());
						}
					}
					scanner.close();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}


	private void writerThread(Socket socket) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try {
					OutputStream outputStream = socket.getOutputStream();
					
					Scanner keyboardScanner = new Scanner(System.in);
					PrintWriter printWriter = new PrintWriter(outputStream);
					
					do {
						if(keyboardScanner.hasNext()) {
							String buffer = keyboardScanner.nextLine();
							printWriter.println(buffer);
							printWriter.flush();
							
							if(buffer.contentEquals("quit")) {
								isRunning = false;
							}
							
						}
						
					}while(isRunning);
					
					keyboardScanner.close();
					printWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client1 client1 = new Client1();
		client1.createClient1();
		
	}

}
