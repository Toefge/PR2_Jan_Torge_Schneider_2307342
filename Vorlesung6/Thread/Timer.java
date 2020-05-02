package Thread;

public class Timer extends Thread {

	int timer = 0;
	boolean timerRunning;

	
	
	public void setTimerRunning(boolean timerRunning) {
		this.timerRunning = timerRunning;
	}

	


	public int getTimer() {
		return timer;
	}




	@Override
	public void run() {
		super.run();

		while (timerRunning) {
			// Um eine gewisse Zeit zu schaffen, in der der Thread nicht aktiv ist benutzt
			// man sleep.
			// try und catch falls jemand den Thread von auﬂen unterbricht.
			try {
				Thread.sleep(1);
				timer++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
