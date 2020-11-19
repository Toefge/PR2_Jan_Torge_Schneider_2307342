package Game;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ReaderThread extends Thread {

	private Scanner scanner;
	private BlockingQueue<PlayerData> queue;
	private boolean isRunning = true;
	private PlayerData playerData;
	private ArrayList<String> playerNames;

	public ReaderThread(Scanner scanner, BlockingQueue<PlayerData> queue,  ArrayList<String> playerNames) {
		super();
		this.scanner = scanner;
		this.queue = queue;
		this.playerNames = playerNames;
	}

	@Override
	public void run() {
		
		getOtherPlayerName();
		try {
			while(isRunning) {
				if(scanner.hasNextLine()) {
					getPlayerData();
					
					if (playerData.isRunning()) {
						queue.add(playerData);
					} else {
						isRunning = false;
					}
				}
			}
			System.out.println("Server Input Stream geschlossen.");
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getOtherPlayerName() {
		String thisPlayerName = scanner.nextLine();
		playerData = new PlayerData(thisPlayerName);
		synchronized (playerNames) {
			if(playerNames.size() == 0) {
				playerData.setSpieler(1);
			}else {
				playerData.setSpieler(2);
			}
			playerNames.add(thisPlayerName);
		}
		queue.add(playerData);
	}

	private void getPlayerData() {
		playerData.setSpieler(Integer.parseInt(scanner.nextLine()));
		playerData.setX(Integer.parseInt(scanner.nextLine()));
		playerData.setY(Integer.parseInt(scanner.nextLine()));
		playerData.setRunning(Boolean.parseBoolean(scanner.nextLine()));
	}
	
	public void quit() {
		isRunning = false;
	}
}
