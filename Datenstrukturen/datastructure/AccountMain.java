package datastructure;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class AccountMain {

	public static void main(String[] args) {
		
		//Info: F�r einen Durchlauf der HashSet wird ein Iterator ben�tigt.
		
		HashSet<Account> accounts = new HashSet<>();
		
		Scanner scanner = new Scanner(System.in);
		
		accounts.add(new Account("Baby Yoda",123456789L));
		accounts.add(new Account("Jabba the Hutt",987654321L));
		accounts.add(new Account("Rancor",01760175L));
		
		//WARUM WIRD DAS HIER BEIDES IN DIE HASHSET GESCHRIEBEN??? :(
		accounts.add(new Account("Gamorreaner",666666L));
		accounts.add(new Account("Gamorreaner",666666L));
		
		Iterator<Account> its = accounts.iterator(); 
		
		boolean runTime = true;
		
		while(runTime) {
			
			System.out.println("Gib eine neue Handynummer ein: ");
			
			long buffer1 = scanner.nextLong();
	
			System.out.println("Gib einen Benutzernamen ein: ");
			
			String buffer2 = scanner.next();
			
			//DER AUSKOMMENTIERTE TEIL VERMEIDET MANUELL EINE DOPPLUNG.
			//boolean accountValid = true;
			
			//Iterator<Account> i = accounts.iterator(); 

	        //while (i.hasNext()) {
	        	
	        	//Mal eine Frage: An dieser Stelle wird einen neues Objekt erstellt um die compareTo Methode zu nutzen.
	        	//Was passiert damit Rechner intern, da es ja nirgendwo abgespeichert wird?
	        	//Wird das Objekt auch gel�scht, wenn das Programm beendet wird?
	        	//Und eine weitere Frage: ist es so sinnvoll, an dieser Stelle eine Methode compareTo einzubauen?
	        	//K�nnte man den Vergleich der Werte nicht auch �ber den Zugriff auf die Getter realisieren?
//	        	int checkValues = i.next().equals(new Account(buffer1,buffer2));
//	        	
//	            if(checkValues==-1) {
//	            	accountValid=false;
//	            	System.out.println("Die Handynummer "+buffer1+" existiert bereits.");
//	            }
//	            if(checkValues==1) {
//	            	accountValid=false;
//	            	System.out.println("Der Benutzername "+buffer2+" existiert bereits.");
//	            }
//	        }
			
			//Sonst steht hier accountValid anstelle von true
	        if(true) {
	        	
	        	accounts.add(new Account(buffer2,buffer1));
	        	
	        	Iterator<Account> it = accounts.iterator(); 
	        	
	        	while (it.hasNext()) {
	        		Account account = it.next();
		            System.out.println(String.format("%20s: %d", account.getName(), account.getNumber())); 
		        }
	        }
	        
	        System.out.println("Mit quit verl�sst du das Programm, mit new erst�llst du einen weiteren Account: ");
			String abbruch = scanner.next();
			if(abbruch.equals("quit")) {
				runTime = false;
			}
		
		}
		scanner.close();
	}
}
