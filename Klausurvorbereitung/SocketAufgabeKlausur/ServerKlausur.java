package SocketAufgabeKlausur;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerKlausur {
	
	public void createServerKlausur() {
		
		
		try {
			
			ServerSocket serverSocket = new ServerSocket(3445, 1);
			Socket socket = serverSocket.accept();
			
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			
			
			
			try {
				Object obj;
				obj = objectInputStream.readObject();
				
				if(obj instanceof UserDataKlausur) {
					UserDataKlausur userDataKlausur;
					//Typecast
					userDataKlausur = (UserDataKlausur) obj;
					
					System.out.println("Objekt erfolgreich eingelesen!");
					System.out.println("Username: "+userDataKlausur.getUsername());
					System.out.println("Password: "+userDataKlausur.getPassword());
				}else {
					System.out.println("Objekt nicht vom typ UserData!");
				}
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			objectInputStream.close();
			inputStream.close();
			socket.close();
			serverSocket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args) {
		ServerKlausur serverKlausur = new ServerKlausur();
		serverKlausur.createServerKlausur();
	}
}
