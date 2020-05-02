package Thread;

public class CalculatingThread extends Thread{
	
	@Override
	public void run() {
		super.run();
		
		for(int i = 0; i < 20; i++) {
			System.out.println(i);
			
			//Um eine gewisse Zeit zu schaffen, in der der Thread nicht aktiv ist benutzt man sleep.
			//try und catch falls jemand den Thread von außen unterbricht.
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
