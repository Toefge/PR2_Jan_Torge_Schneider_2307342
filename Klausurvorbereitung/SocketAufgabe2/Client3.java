package SocketAufgabe2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client3 {
	
	private boolean isRunning = true;
	
	public void createClient3() {
		try {
			Socket socket = new Socket("127.0.0.1", 3445);
			
			OutputStream outputStream = socket.getOutputStream();
			InputStream inputStream = socket.getInputStream();
			
			Scanner scanner = new Scanner(inputStream);
			PrintWriter printWriter = new PrintWriter(outputStream);
			Scanner keyboard = new Scanner(System.in);
			
			
			while(isRunning) {
				if(keyboard.hasNextLine()) {
					String buffer = keyboard.nextLine();
					printWriter.println(buffer);
					printWriter.flush();
					
					if(buffer.contentEquals("quit")) {
						isRunning =false;
					}
				}
				
				if(scanner.hasNextLine()) {
					String buffer = scanner.nextLine();
					System.out.println(buffer);
				}
			}
			
			
			keyboard.close();
			scanner.close();
			printWriter.close();
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		Client3 client3 = new Client3();
		client3.createClient3();
		
	}
}
