package datastructure;

import java.util.HashMap;
import java.util.Set;

public class Einkaufsliste {

	public static void main(String[] args) {
		
		HashMap<String, String> liste = new HashMap<>();

		liste.put("Plutonium", "3 mg");
		liste.put("Zarenbombe", "5 Stück sollten für den Anfang reichen.");
		liste.put("schreiendes gelbes Gummi-Huhn ", "500");
		liste.put("Enigma", "2 Mal");
		liste.put("Pygmäe", "12, damit denen nicht langweilig wird");
		liste.put("Raven Mocker", "Wenn überhaupt vorhanden, dann sollte einer reichen.");
		liste.put("Chupacabra", "Das gleiche wie beim Raven Mocker");

		Set<String> goods = liste.keySet();
		
		for(String article : goods) {
			System.out.println(article+": "+liste.get(article));
		}
		
	}

}
