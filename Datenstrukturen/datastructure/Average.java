package datastructure;

//import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;

/**
 * Average berechnet den Mittelwert aus einer beliebigen Anzahl von doubel Werten
 * und gibt das Ergebnis in der Konsole aus.
 * 
 * Für diese Aufgabe wurde eine LinkedList gewählt, 
 * da diese für schnelles einfügen bei beliebiger Größe optimiert ist.
 * Maps und Sets kommen nicht in Frage, da keine Werte verknüpft werden sollen
 * und auch eine Dopplung der Werte möglich sein muss.
 * 
 *
 * @author Jan Torge Schneider 
 * @Matrikelnummer 2307342
 * @version 1.0
 */

public class Average {

	public static void main(String[] args) {
		
		LinkedList<Double> numbers = new LinkedList<>();
		
		Scanner scanner = new Scanner(System.in);
		
		boolean mainLoop = true;
		boolean waitForInput = true;
		
		while(mainLoop) {
			
			System.out.println("Gib deine Double Werte ein, um den Mittelwert auszurechnen. Beende die Eingabe mit quit .");

			while(waitForInput) {
				
				if(scanner.hasNextDouble()) {
					numbers.add(scanner.nextDouble());
				}
				
				scanner.nextLine();
				if(scanner.hasNext("quit")) {
					waitForInput = false;
				}	
			}
			
			System.out.println(calcAverage(numbers));
			
			scanner.nextLine();
			if(scanner.hasNext("no")) {
				mainLoop = false;
			}else {
				waitForInput = true;
				numbers.clear();
			}
			
		}
				
		scanner.close();
	}
	
	private static String calcAverage(LinkedList<Double> numbers) {
		
		double average = 0.0;
		
		for(Double number : numbers) {
			average = average + number;
		}
		
		return ("Mittelwert: "+ Math.round(100.0*average/numbers.size())/100.0+"\nNeuen Mittelwert berechnen? yes oder no eingeben.");
		
	}
		
}
