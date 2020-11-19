package Game;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Game.PlayerCube.movement;
import views.GameFrameWork;
import views.IKeyboardListener;
import views.ITickableListener;
import views.Message;

public class GameLoop implements ITickableListener, IKeyboardListener {

	private Coordinates coordinates = new Coordinates();
	
	private JFrame frame;
	private JButton button;
	private JLabel label;
	private JTextField textfield;
	
	private Message headline;
	private String[] playerNames = new String[2];
	private Message[] playerStats = new Message[2];
	
	private GameFrameWork frameWork;
	
	private Socket socket;
	private Scanner scanner;
	private PrintWriter printWriter;
	private boolean isRunning = false;
	
	private Base base;
	private PlayerCube[] player = new PlayerCube[3];
	
	private Collided collided;
	
	
	public void init() {
		try {
			
			socket = new Socket("127.0.0.1", 3445);
			scanner = new Scanner(socket.getInputStream());
			printWriter = new PrintWriter(socket.getOutputStream());

			getUserName();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getUserName() {
		
		frame = new JFrame("Spielername");

		button = new JButton("OK");
		button.setBounds(70, 100, 140, 40);

		label = new JLabel();
		label.setText("Gib einen Spielernamen ein :");
		label.setBounds(60, 10, 300, 20);

		textfield = new JTextField();
		textfield.setBounds(70, 50, 140, 40);

		frame.add(textfield);
		frame.add(label);
		frame.add(button);
		frame.setSize(coordinates.getInputWidth(), coordinates.getInputHeight());
		frame.setLocation(
				coordinates.getScreenWidth()/2-coordinates.getInputWidth()/2, 
				coordinates.getScreenHeight()/2-coordinates.getInputHeight()/2);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.getRootPane().setDefaultButton(button);
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String thisPlayerName = textfield.getText();
				printWriter.println(thisPlayerName);
				printWriter.flush();
				
				frame.setVisible(false);
				frame.dispose();
				initGame(thisPlayerName);
			}
		});
	}
	
	private void initGame(String thisPlayerName) {
		frameWork = new GameFrameWork();
		frameWork.setSize(coordinates.getScreenWidth(), coordinates.getScreenHeight());
		frameWork.setBackgroundColor(Color.LIGHT_GRAY);
		
		new Thread(new Runnable() {
			int buffer = 0;
			String otherPlayerName;
			
			@Override
			public void run() {
				while(!isRunning) {
					if(scanner.hasNextLine()) {
						buffer = Integer.parseInt(scanner.nextLine());
						otherPlayerName = scanner.nextLine();
						isRunning = true;
					}
				}
				
				if(buffer==1) {
					playerNames[0] = otherPlayerName;
					playerNames[1] = thisPlayerName;
				}else if(buffer==2){
					playerNames[0] = thisPlayerName;
					playerNames[1] = otherPlayerName;
				}
				instantiateGameScreen();
			}
		}).start();
	}
	
	private void instantiateGameScreen() {
		
		player[1] = new PlayerCube(
				coordinates.getPlayer1SpawnX(),
				coordinates.getPlayerBase(), 
				coordinates.getPlayerSize(), 
				coordinates.getPlayerSize(), 
				"Jumpy\\assets\\Bilder\\Charakter\\blau.png");
		frameWork.addGameObject(player[1]);
		player[1].resetMovmentValues();
		
		player[2] = new PlayerCube(
				coordinates.getPlayer2SpawnX(),
				coordinates.getPlayerBase(),
				coordinates.getPlayerSize(), 
				coordinates.getPlayerSize(), 
				"Jumpy\\assets\\Bilder\\Charakter\\gruen.jpg");
		frameWork.addGameObject(player[2]);
		player[2].resetMovmentValues();
		
		base = new Base(
				coordinates.getScreenWidth()/8, 
				coordinates.getyBase(), 
				coordinates.getScreenWidth()/8*6, 
				coordinates.getScreenHeight(), 
				"Jumpy\\assets\\Bilder\\Base\\schwarz.png");
		frameWork.addGameObject(base);
		
		playerStats[0] = new Message(
				playerNames[0]+" : "+ player[1].getPlayerLives(), 
				coordinates.getScreenWidth()/6, 
				coordinates.getScreenHeight()/3, 
				40, 
				Color.BLACK);
		frameWork.addMessage(playerStats[0]);
		playerStats[1] = new Message(
				playerNames[1]+" : "+ player[2].getPlayerLives(), 
				coordinates.getScreenWidth()/6*5-50, 
				coordinates.getScreenHeight()/3,
				40, 
				Color.BLACK);
		frameWork.addMessage(playerStats[1]);
		
		frameWork.addTick(this);
		frameWork.addIKeyInput(this);
		
		collided = new Collided(player, coordinates, base);
		
		getInputFromOtherPlayer();
	}
	
	private void getInputFromOtherPlayer() {
		new Thread(new Runnable() {	
			
			@Override
			public void run() {
				
				try {
					while (isRunning) {
						
						if (scanner.hasNextLine()) {
							int buffer = Integer.parseInt(scanner.nextLine());
							if(buffer == 0) {
								isRunning = false;
								break;
							}else {
								player[buffer].setX(Integer.parseInt(scanner.nextLine()));
								player[buffer].setY(Integer.parseInt(scanner.nextLine()));
								isRunning = Boolean.parseBoolean(scanner.nextLine());
							}
						}
					}

					printWriter.close();
					scanner.close();
					socket.close();
					System.exit(0);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public int[] getKeys() {
		int [] keys = {
				KeyEvent.VK_ENTER, 
				KeyEvent.VK_W, 
				KeyEvent.VK_A,
				KeyEvent.VK_S,
				KeyEvent.VK_D, 
				KeyEvent.VK_UP,
				KeyEvent.VK_DOWN, 
				KeyEvent.VK_LEFT, 
				KeyEvent.VK_RIGHT};
		return keys;
	}

	@Override
	public void keyUp(int key) {
		
		if(key==KeyEvent.VK_A) {
			player[1].setHorizontal(movement.NO);
		}
		
		if(key==KeyEvent.VK_D) {
			player[1].setHorizontal(movement.NO);
		}
		
		if(key==KeyEvent.VK_RIGHT) {
			player[2].setHorizontal(movement.NO);
		}
		
		if(key==KeyEvent.VK_LEFT) {
			player[2].setHorizontal(movement.NO);
		}
	}

	@Override
	public void keyDown(int key) {
		
		if(key==KeyEvent.VK_W && player[1].getVertical() == movement.NO) {
			player[1].setVertical(movement.UP);
		}
		
		if(key==KeyEvent.VK_S && player[1].getVertical() == movement.UP) {
			player[1].setVertical(movement.DOWN);
		}
		
		if(key==KeyEvent.VK_UP && player[2].getVertical() == movement.NO) {
			player[2].setVertical(movement.UP);
		}
		
		if(key==KeyEvent.VK_DOWN && player[2].getVertical() == movement.UP) {
			player[2].setVertical(movement.DOWN);
		}
		
		if(key==KeyEvent.VK_A) {
			player[1].setHorizontal(movement.LEFT);
		}
		
		if(key==KeyEvent.VK_D) {
			player[1].setHorizontal(movement.RIGHT);
		}
		
		if(key==KeyEvent.VK_LEFT) {
			player[2].setHorizontal(movement.LEFT);
		}
		
		if(key==KeyEvent.VK_RIGHT) {
			player[2].setHorizontal(movement.RIGHT);
		}
		
		//Mit Enter soll man alles schliessen koennen.
		if(key==KeyEvent.VK_ENTER) {
			isRunning = false;
			
			sendeDatenSpieler(0);
		}
	}

	@Override
	public void tick(long elapsedTime) {
		
		checkWin();
		
		if(validateMovementPlayer1()) {
			sendeDatenSpieler(1);
		}
		
		if(validateMovementPlayer2()) {
			sendeDatenSpieler(2);
		}
	}
	
	private boolean validateMovementPlayer1() {
		boolean movementDetected = false;
		
		if(player[1].getHorizontal() != movement.NO) {
			if(collided.player2LeftPlayer1Right())
			{
				//Do nothing
			}else if(player[1].getHorizontal() == movement.LEFT){
				player[1].moveLeft();
				movementDetected = true;
				//sendeDatenSpieler(1);
			}
			if(collided.player1LeftPlayer2Right())
			{
				//Do nothing
			}else if(player[1].getHorizontal() == movement.RIGHT){
				player[1].moveRight();
				movementDetected = true;
				//sendeDatenSpieler(1);
			}
		}
		
		if(player[1].getVertical() == movement.UP && player[1].getJumpHeight() <= 70) {
			if(player[1].getY() == player[2].getY()+coordinates.getPlayerSize()) {
				//Do nothing
			}else {
				player[1].moveUp();
				movementDetected = true;
				//sendeDatenSpieler(1);
				player[1].incrementJumpHeight();
				if(player[1].getJumpHeight() == 70) {
					player[1].setVertical(movement.DOWN);
				}
			}
		}else if(player[1].getVertical() == movement.DOWN && player[1].getJumpHeight() > 0) {
			if(collided.player1OnPlayer2())
			{
				//Do nothing
			}else {
				player[1].moveDown();
				movementDetected = true;
				//sendeDatenSpieler(1);
				player[1].decrementJumpHeight();
				if(player[1].getJumpHeight() == 0) {
					player[1].setVertical(movement.NO);
				}
			}
		}
		return movementDetected;
	}
	
	private boolean validateMovementPlayer2() {
		boolean movementDetected = false;
		
		if(player[2].getHorizontal() != movement.NO) {
			if(collided.player1LeftPlayer2Right())
			{
				//Do nothing
			}else if(player[2].getHorizontal() == movement.LEFT){
				player[2].moveLeft();
				movementDetected = true;
				//sendeDatenSpieler(2);
			}
			if(collided.player2LeftPlayer1Right())
			{
				//Do nothing
			}else if(player[2].getHorizontal() == movement.RIGHT){
				player[2].moveRight();
				movementDetected = true;
				//sendeDatenSpieler(2);
			}
		}
		
		if(player[2].getVertical() == movement.UP && player[2].getJumpHeight() < 71) {
			if(player[2].getY() == player[1].getY()+coordinates.getPlayerSize()) {
				//Do nothing
			}else {
				player[2].moveUp();
				movementDetected = true;
				//sendeDatenSpieler(2);
				player[2].incrementJumpHeight();
				if(player[2].getJumpHeight() == 70) {
					player[2].setVertical(movement.DOWN);
				}
			}
		}else if(player[2].getVertical() == movement.DOWN && player[2].getJumpHeight() > 0) {
			if(collided.player2OnPlayer1()) {
				//Do nothing
			}else {
				player[2].moveDown();
				movementDetected = true;
				//sendeDatenSpieler(2);
				player[2].decrementJumpHeight();
				if(player[2].getJumpHeight() == 0) {
					player[2].setVertical(movement.NO);
				}
			}
		}
		return movementDetected;
	}
	
	/*
	private void sendeDatenSpieler() {
		printWriter.println(1);
		printWriter.println(player[1].getX());
		printWriter.println(player[1].getY());
		printWriter.println(isRunning);
		printWriter.flush();
		printWriter.println(2);
		printWriter.println(player[2].getX());
		printWriter.println(player[2].getY());
		printWriter.println(isRunning);
		printWriter.flush();
	}
	*/
	
	private void sendeDatenSpieler(int buffer) {
		printWriter.println(buffer);
		printWriter.println(player[buffer].getX());
		printWriter.println(player[buffer].getY());
		printWriter.println(isRunning);
		printWriter.flush();
	}
	
	private void checkWin() {
		
		if(collided.player1OnPlayer2()) 
		{
			player[2].decrementPlayerLives();
			if(player[2].getPlayerLives() > 0) {
				playerStats[1].setMessage(playerNames[1]+" : "+player[2].getPlayerLives());
				spawnPlayersInOrigin();
			}else {
				playerStats[1].setMessage(playerNames[1]+" : "+player[2].getPlayerLives());
				endScreen(1);
			}
		}else if(collided.player2OnPlayer1()) 
		{
			player[1].decrementPlayerLives();
			if(player[1].getPlayerLives() > 0) {
				playerStats[0].setMessage(playerNames[0]+" : "+player[1].getPlayerLives());
				spawnPlayersInOrigin();
			}else {
				playerStats[0].setMessage(playerNames[0]+" : "+player[1].getPlayerLives());
				endScreen(2);
			}
		}
	}

	private void spawnPlayersInOrigin() {
		player[1].setX(coordinates.getPlayer1SpawnX());
		player[1].setY(coordinates.getPlayerBase());
		player[1].resetMovmentValues();
		player[2].setX(coordinates.getPlayer2SpawnX());
		player[2].setY(coordinates.getPlayerBase());
		player[2].resetMovmentValues();
	}

	private void endScreen(int winner) {
		
		headline = new Message(
				"Spieler "+ playerNames[winner-1] +" hat gewonnen!",
				coordinates.getScreenWidth()/3-100,
				coordinates.getScreenHeight()/5,
				80,
				Color.BLACK);
		frameWork.addMessage(headline);
		System.out.println("Test");
		
		try {
			Thread.sleep(3000);
			//TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		frameWork.removeGameObject(base);
		frameWork.removeGameObject(player[1]);
		frameWork.removeGameObject(player[2]);
		frameWork.removeTick(this);
	}
}
