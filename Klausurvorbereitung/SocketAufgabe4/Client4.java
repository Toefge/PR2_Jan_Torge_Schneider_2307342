package SocketAufgabe4;

import java.io.IOException;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client4 {

private boolean isRunning = true;
	
	public void createClient4() {
		try {
			Socket socket = new Socket("127.0.0.1", 3445);
			
			OutputStream outputStream = socket.getOutputStream();
			
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
			}
			
			
			keyboard.close();
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
		Client4 client4 = new Client4();
		client4.createClient4();
		
	}
}
