package Netzwerk;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMain {

	public static void main(String[] args) {
		ServerMain server = new ServerMain();
		server.creatServer();
	}
	
	public void creatServer(){
		
		try {
			
			ServerSocket serverSocket = new ServerSocket(3445,1);
			//Wartet bis sich ein Client verbunden hat und es wird ein Socket erstellt.
			Socket socket = serverSocket.accept();
			
//			PrintWriter wird erstellt und er soll alle Nachrichten über das Socket und dieses schreibt es ins Netzwerk
			PrintWriter printwriter = new PrintWriter(socket.getOutputStream());
			
//			Nachrichten lesen aus dem Netzwerk
			Scanner scanner = new Scanner(socket.getInputStream());
			
			
			
			//Willkommensnachricht mit dem PrintWirter ausgeben
			printwriter.print("Hallo Client :)");
			printwriter.println("Wie gehts dir?");
			printwriter.flush();
			
			serverSocket.close();
			socket.close();
			printwriter.close();
			scanner.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
