package Game;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class WriterThread extends Thread {

	private PrintWriter printWriter;
	private BlockingQueue<PlayerData> queue;
	private PlayerData playerData = new PlayerData("");
	private boolean isRunning = true;
	private enum sendData {PLAYERDATA, ENDOFGAME}

	public WriterThread(PrintWriter printWriter , BlockingQueue<PlayerData> queue) {
		super();
		this.printWriter = printWriter;
		this.queue = queue;
	}
	
	@Override
	public void run() {
		
		waitOnOtherPlayerName();
		Object os;
		try {
			while (isRunning) {
				
				os = queue.take();
				if (os instanceof PlayerData) {
					playerData = (PlayerData) os;
					
					if(playerData.isRunning()) {
						sendPlayerData(sendData.PLAYERDATA);
					}else {
						sendPlayerData(sendData.ENDOFGAME);
						break;
					}
				}
			}
			System.out.println("Server Output Stream geschlossen.");
			printWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendPlayerData(sendData data) {
		switch(data) {
		case PLAYERDATA:
			printWriter.println(playerData.getSpieler());
			printWriter.println(playerData.getX());
			printWriter.println(playerData.getY());
			printWriter.println(playerData.isRunning());
			printWriter.flush();
			break;
		case ENDOFGAME:
			printWriter.println(0);
			printWriter.println(0);
			printWriter.println(0);
			printWriter.println(false);
			printWriter.flush();
			isRunning = false;
			break;
		}
	}

	private void waitOnOtherPlayerName() {
		Object os;
		try {
			os = queue.take();
			if (os instanceof PlayerData) {
				playerData = (PlayerData) os;
				printWriter.println(playerData.getSpieler());
				printWriter.println(playerData.getName());
				printWriter.flush();
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}