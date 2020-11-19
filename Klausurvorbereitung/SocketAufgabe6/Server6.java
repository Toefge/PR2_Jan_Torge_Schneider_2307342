package SocketAufgabe6;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import java.net.ServerSocket;
import java.net.Socket;



public class Server6 {
	
	public void createServer6() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(3445, 1);
			
			Socket socket = serverSocket.accept();
			
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

			
			try {
				
				Object obj = objectInputStream.readObject();
				if(obj instanceof UserData) {
					UserData userData = (UserData) obj;
					System.out.println("Username: "+userData.getUsername());
					System.out.println("Passwort: "+userData.getPassword());
				}else {
					System.out.println("Falscher Objekt Typ!");
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
			objectInputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server6 server6 = new Server6();
		server6.createServer6();

	}
}
