package datastructure;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;

public class EventMain {

	public static void main(String[] args) {

		HashSet<Event> events = new HashSet<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		Calendar calendar3 = Calendar.getInstance();
		Calendar calendar4 = Calendar.getInstance();
		Calendar calendar5 = Calendar.getInstance();
		
		calendar1.set(2021, 06, 29);
		calendar2.set(2022, 06, 29);
		calendar3.set(2021, 05, 17);
		calendar4.set(2021, 05, 11);
		calendar5.set(2021, 06, 15);
		
		
		events.add(new Event("Wacken", "Wacken", calendar1));
		
		events.add(new Event("Wacken", "Wacken", calendar2));
		
		events.add(new Event("Graspop", "Dessel", calendar3));

		events.add(new Event("Rock am Ring", "Nürburgring", calendar4));
		
		events.add(new Event("Deichbrand", "Seegelflughafen Cuxhaven", calendar5));
		
		Iterator<Event> i = events.iterator(); 
		System.out.println(String.format("%15s %25s %12s","Name","Ort","Datum"));
        while (i.hasNext()) {
        	Event event = i.next();
        	System.out.println(String.format("%15s %25s %12s",event.getName(),event.getOrt(),sdf.format(event.getDate().getTime())));
        }
	
	}

}
