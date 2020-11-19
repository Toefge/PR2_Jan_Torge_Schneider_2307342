package SocketAufgabeKlausur;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class ClientKlausur {
	
	UserDataKlausur userDataKlausur;
	private String bufferUsername;
	private String bufferPassword;
	
	public void createClientKlausur() {
		
		try {
			
			Socket socket = new Socket("127.0.0.1",3445);
			
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
			
			Scanner keyboard = new Scanner(System.in);
			
			System.out.println("Usernamen eingeben :");
			bufferUsername = keyboard.nextLine();
			
			System.out.println("Passwort eingeben :");
			bufferPassword = keyboard.nextLine();
			
			userDataKlausur = new UserDataKlausur(bufferUsername, bufferPassword);
			
			objectOutputStream.writeObject(userDataKlausur);
			objectOutputStream.flush();
			
			keyboard.close();
			outputStream.close();
			objectOutputStream.close();
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
		ClientKlausur clientKlausur = new ClientKlausur();
		clientKlausur.createClientKlausur();
	}
}
