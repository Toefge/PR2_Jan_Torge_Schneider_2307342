package Thread;

import java.util.Scanner;

public class ThreadsMain {

	public static void main(String[] args) {
		
		/*
		 
		//2 Instanzen erstellt. Beide sollen von 1-20 Zählen und arbeiten dabei parallel
		
		CalculatingThread calThread = new CalculatingThread();
		CalculatingThread calThread2 = new CalculatingThread();
		
		//Ist bisher nur eine Instanz von Runnable aber noch noch kein Thread.
		MinionRunner runner = new MinionRunner();
		
		//Um auf die Methoden start etc zugreifen zu können, einen neuen Thread machen und diesem den runner übergeben.
		//Der Threat nimmt einfach die Methode run() auf dem Interface.
		Thread minionThread= new Thread(runner);
		
		minionThread.start();
		
		//Startet den Thread (Man kann auch die run(); Methode aufrufen. Allerdings laufen die Prozesse nicht parallel. Also niemal run(); aufrufen!)
		
		calThread.start();
				
		calThread2.start();

		//polling: Wenn man etwas immer und immer wieder prüft.
		//Verbraucht CPU, daher macht man sowas nicht:
//		while(calThread.isAlive() || calThread2.isAlive()) {
//			System.out.println("bin fertig");
//		}
		
		//Keine Unnötigen Abrfragen, die die CPU blockieren.
		//join()
		try {
			calThread.join();
			calThread2.join();
			minionThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("bin fertig");
		* 
		*/
		
		Timer timerThread = new Timer();
		timerThread.start();
		timerThread.setTimerRunning(true);
		
		Scanner eingabewert = new Scanner(System.in);
		int L = eingabewert.nextInt();
		if(L==1) {
			timerThread.setTimerRunning(false);
			System.out.println(timerThread.getTimer());
		}
	}

}
