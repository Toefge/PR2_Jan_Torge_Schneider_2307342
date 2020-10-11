package Game;

import java.io.ObjectInputStream;
import java.util.concurrent.BlockingQueue;

public class Reader extends Thread {

	private ObjectInputStream inputStream;
	private BlockingQueue<PlayerData> queue;
	private boolean isRunning = true;
	private PlayerData playerData;

	public Reader(ObjectInputStream inputStream, BlockingQueue<PlayerData> queue) {
		super();
		this.inputStream = inputStream;
		this.queue = queue;
	}

	@Override
	public void run() {

		try {
			while(isRunning) {
				
				//Der Kollege wirft immer Fehler beim Schlieﬂen....
				//Habs in der kurzen Zeit leider einfach nicht weg bekommen....
				Object os = inputStream.readObject();
				
				if (os instanceof PlayerData) {
					playerData = (PlayerData) os;

					if (playerData.isRunning()) {
						
						queue.add(playerData);
					} else {
						isRunning = false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void quit() {
		isRunning = false;
	}
}
