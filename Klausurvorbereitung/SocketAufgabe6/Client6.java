package SocketAufgabe6;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class Client6 {
	
	public void createClient6() {
		try {
			Socket socket = new Socket("127.0.0.1", 3445);
			
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			
			PrintWriter printWriter = new PrintWriter(outputStream);
			Scanner keyboard = new Scanner(System.in);
			
			UserData userData = new UserData("deafult", "default");
			String buffer;
			
			System.out.println("Gib einen Usernamen ein: ");
			buffer = keyboard.nextLine();
			userData.setUsername(buffer);
			
			System.out.println("Gib ein Passwort ein: ");
			buffer = keyboard.nextLine();
			userData.setPassword(buffer);
			
			objectOutputStream.writeObject(userData);
			objectOutputStream.flush();
	
			objectOutputStream.close();
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
		Client6 client6 = new Client6();
		client6.createClient6();
		
	}
}
