package Game;


import java.io.ObjectOutputStream;
import java.util.concurrent.BlockingQueue;

public class Writer extends Thread {

	private ObjectOutputStream outputStream;
	private BlockingQueue<PlayerData> queue;
	private PlayerData playerData;
	private boolean isRunning = true;

	public Writer(ObjectOutputStream outputStream , BlockingQueue<PlayerData> queue) {
		super();
		this.outputStream = outputStream;
		this.queue = queue;
	}
	
	@Override
	public void run() {
		try {
			while (isRunning) {
				
				Object os = queue.take();
				if (os instanceof PlayerData) {
					playerData = (PlayerData) os;
					
					if(playerData.isRunning()) {
						outputStream.writeObject(playerData);
						outputStream.flush();
					}else {
						isRunning = false;
					}
				}
			}
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void quit() {
		isRunning = false;
	}
}