package datastructure;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class AccountMain {

	public static void main(String[] args) {
		
		//Info: Für einen Durchlauf der HashSet wird ein Iterator benötigt.
		
		HashSet<Account> accounts = new HashSet<>();
		
		Scanner scanner = new Scanner(System.in);
		
		accounts.add(new Account("Baby Yoda",123456789L));
		accounts.add(new Account("Jabba the Hutt",987654321L));
		accounts.add(new Account("Rancor",01760175L));
		accounts.add(new Account("Gamorreaner",666666L));
		accounts.add(new Account("Gamorreaner",666666L));
		accounts.add(new Account("Gamorreaner",123456L));
		
		boolean runTime = true;
		
		while(runTime) {
			
			System.out.println("Gib eine neue Handynummer ein: ");
			
			long number = scanner.nextLong();
	
			System.out.println("Gib einen Benutzernamen ein: ");
			
			String name = scanner.next();
			
			
			boolean accountValid = true;
			
			Iterator<Account> i = accounts.iterator(); 

	        while (i.hasNext()) {
	        	
	        	boolean checkDublicate = i.next().equals(new Account(name,number));
	        	
	            if(checkDublicate) {
	            	accountValid=false;
	            	System.out.println("Die Handynummer "+number+" oder der Benutzername "+name+" existiert bereits.");
	            }
	        }
			
	        if(accountValid) {
	        	
	        	accounts.add(new Account(name,number));
	        	
	        	Iterator<Account> it = accounts.iterator(); 
	        	
	        	while (it.hasNext()) {
	        		Account account = it.next();
		            System.out.println(String.format("%20s: %d", account.getName(), account.getNumber())); 
		        }
	        }
	        
	        System.out.println("Mit quit verlässt du das Programm, mit new erställst du einen weiteren Account: ");
			String abbruch = scanner.next();
			if(abbruch.equals("quit")) {
				runTime = false;
			}
		
		}
		scanner.close();
	}
}
