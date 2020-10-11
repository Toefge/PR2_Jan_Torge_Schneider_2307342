package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import views.GameFrameWork;
import views.IKeyboardListener;
import views.ITickableListener;
import views.Message;

public class GameLoop implements ITickableListener, IKeyboardListener {

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private int screenWidth = screenSize.width;
	private int screenHeight = screenSize.height;
	private int inputWidth = 300;
	private int inputHeight = 200;
	private int yBase = screenHeight/6*5;
	private int playerSize = 50;
	private int playerBase = yBase-playerSize;
	
	private JFrame frame;
	private JButton button;
	private JLabel label;
	private JTextField textfield;
	private PlayerData spieler1;
	private PlayerData spieler2;
	
	private Message headline;
	private Message headline2;
	private Message headline3;
	private Message spielerName1;
	private Message spielerName2;
	private Message timerMessage = new Message("Spiel startet in: ", screenWidth/3, screenHeight/4*3, 60, Color.BLACK);
	
	private GameFrameWork frameWork;
	
	private Socket socket;
	private ObjectInputStream objectInputStream;
	private ObjectOutputStream objectOutputStream;
	private boolean isRunning = true;
	
	private Base base;
	private PlayerCube player1;
	private PlayerCube player2;
	
	private boolean jump1 = false;
	private boolean jump2 = false;
	private int jumpHeight1 = 0;
	private int jumpHeight2 = 0;

	public void init() {
		try {
			
			socket = new Socket("127.0.0.1", 3445);
			
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			objectInputStream = new ObjectInputStream(socket.getInputStream());

			initInput();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initInput() {
		
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
		frame.setSize(inputWidth, inputHeight);
		frame.setLocation(screenWidth/2-inputWidth/2, screenHeight/2-inputHeight/2);
		frame.setLayout(null);
		frame.setVisible(true);
		frame.getRootPane().setDefaultButton(button);
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				spieler1 = new PlayerData(textfield.getText());
				frame.setVisible(false);
				frame.dispose();
				
				try {
					objectOutputStream.writeObject(spieler1);
					objectOutputStream.flush();
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}	
				initStartScreen();
			}
		});
	}
	
	private void initStartScreen() {
		
		frameWork = new GameFrameWork();
		frameWork.setSize(screenWidth, screenHeight);
		frameWork.setBackgroundColor(Color.LIGHT_GRAY);
		headline = new Message("Warte auf zweiten Spieler!", screenWidth/3, screenHeight/8, 40, Color.BLACK);
		headline2 = new Message("Spieler 1:", screenWidth/6, screenHeight/2-60, 40, Color.BLACK);
		spielerName1 = new Message(spieler1.getName(), screenWidth/6, screenHeight/2, 40, Color.BLACK);
		frameWork.addMessage(headline);
		frameWork.addMessage(headline2);
		headline3 = new Message("Spieler 2:", screenWidth/6*4  , screenHeight/2-60, 40, Color.BLACK);
		frameWork.addMessage(headline3);
		frameWork.addMessage(spielerName1);
		
		//Unsaubere Loesung mit diesem Thread, da dieser glaube ich nie ganz geschlossen wird.
		//Das nachfolgende Spiel findet dann in der Methode initGame() statt, die am Ende dieses Threads aufgrufen wird.
		waitForPlayer();
	}
	
	private void waitForPlayer() {
		new Thread(new Runnable() {
			private Boolean running = true;
			@Override
			public void run() {
				while (running) {
					try {

						Object os;
						os = objectInputStream.readObject();

						if (os instanceof PlayerData) {
							spieler2 = (PlayerData) os;
							spielerName2 = new Message(spieler2.getName(), screenWidth / 6 * 4, screenHeight / 2, 40,
									Color.BLACK);
							frameWork.addMessage(spielerName2);
							running = false;
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				Timer timer = new Timer();
				timer.schedule(new StartTimer(frameWork, timerMessage), 0, 1000);
				try {
					Thread.sleep(3500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				timer.cancel();
				initGame();
			}
		}).start();
	}
	
	private void initGame() {
		clearScreen();
		frameWork.setBackgroundColor(Color.LIGHT_GRAY);
				
		base = new Base(screenWidth/8, yBase, screenWidth/8*6, screenHeight, "Destroy and Devastate\\assets\\Bilder\\Base\\schwarz.png");
		player1 = new PlayerCube(screenWidth/6, playerBase, playerSize, playerSize, "Destroy and Devastate\\assets\\Bilder\\Charakter\\blau.png");
		player2 = new PlayerCube(screenWidth/6*5-playerSize, playerBase, playerSize, playerSize, "Destroy and Devastate\\assets\\Bilder\\Charakter\\gruen.jpg");
		frameWork.addGameObject(base);
		frameWork.addGameObject(player1);
		frameWork.addGameObject(player2);
		
		frameWork.addTick(this);
		frameWork.addIKeyInput(this);
		
		getInputDesAnderenSpielers();
	}
	
	private void getInputDesAnderenSpielers() {
		new Thread(new Runnable() {	
			@Override
			public void run() {
				try {
					while (isRunning) {
						Object os = objectInputStream.readObject();
						
						if (os instanceof PlayerData) {
							if(((PlayerData) os).getSpieler()==1) {
								spieler1 = (PlayerData) os;
								player1.setX(spieler1.getX());
								player1.setY(spieler1.getY());
							}else if(((PlayerData) os).getSpieler()==2) {
								spieler2 = (PlayerData) os;
								player2.setX(spieler2.getX());
								player2.setY(spieler2.getY());
							}
						}
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public int[] getKeys() {
		int [] keys = {
				KeyEvent.VK_SPACE, 
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
	public void keyDown(int key) {
		
		if(key==KeyEvent.VK_W) {
			jump1 = true;
		}
		
		if(key==KeyEvent.VK_S) {
			jump1 = false;
		}
		
		if(key==KeyEvent.VK_UP) {
			jump2 = true;
		}
		
		if(key==KeyEvent.VK_DOWN) {
			jump2 = false;
		}
		
		//Mit Enter soll alles geschlossen werden koennen. Funktioniert nicht wie geplant. Liegt aber wahrscheinlich eher an dem Reader Thread des Servers?
		if(key==KeyEvent.VK_ENTER) {
			isRunning = false;
			spieler1.setRunning(false);
			spieler2.setRunning(false);
			try {
				objectOutputStream.writeObject(spieler1);
				objectOutputStream.flush();
				objectOutputStream.writeObject(spieler2);
				objectOutputStream.flush();
				objectOutputStream.close();
				objectInputStream.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(int key) {
		
		if(key==KeyEvent.VK_A) {
			
			if(((player1.getX() == player2.getX()+playerSize) && ((player1.getY() <= player2.getY() && player2.getY() <= player1.getY()+playerSize) || ((player1.getY() <= player2.getY()+playerSize && player2.getY()+playerSize <= player1.getY()+playerSize)))) || player1.getX() == base.getX()) {
				
			}else {
				player1.moveLeft();
				sendeDatenSpieler1();
			}
		}

		if(key==KeyEvent.VK_D) {
			
			if(((player1.getX()+playerSize == player2.getX()) && ((player1.getY() <= player2.getY() && player2.getY() <= player1.getY()+playerSize) || ((player1.getY() <= player2.getY()+playerSize && player2.getY()+playerSize <= player1.getY()+playerSize)))) || player1.getX()+playerSize == base.getX()+base.getWidth()) {
				
			}else {
				player1.moveRight();
				sendeDatenSpieler1();
			}
		}
		
		if(key==KeyEvent.VK_RIGHT) {
			if(((player2.getX()+playerSize == player1.getX()) && ((player2.getY() <= player1.getY() && player1.getY() <= player2.getY()+playerSize) || ((player2.getY() <= player1.getY()+playerSize && player1.getY()+playerSize <= player2.getY()+playerSize)))) || player2.getX()+playerSize == base.getX()+base.getWidth()) {
				
			}else {
				player2.moveRight();
				sendeDatenSpieler2();
			}
		}
		
		if(key==KeyEvent.VK_LEFT) {
			if(((player2.getX() == player1.getX()+playerSize) && ((player2.getY() <= player1.getY() && player1.getY() <= player2.getY()+playerSize) || ((player2.getY() <= player1.getY()+playerSize && player1.getY()+playerSize <= player2.getY()+playerSize)))) || player2.getX() == base.getX()) {
				
			}else {
				player2.moveLeft();
				sendeDatenSpieler2();
			}
		}
	}

	private void sendeDatenSpieler2() {
		PlayerData buffer = new PlayerData("buffer");
		buffer.setX(player2.getX());
		buffer.setY(player2.getY());
		buffer.setSpieler(2);
		try {
			objectOutputStream.writeObject(buffer);
			objectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendeDatenSpieler1() {
		PlayerData buffer = new PlayerData("buffer");
		buffer.setX(player1.getX());
		buffer.setY(player1.getY());
		buffer.setSpieler(1);
		try {
			objectOutputStream.writeObject(buffer);
			objectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tick(long elapsedTime) {
		
		if(jump1 && jumpHeight1 < 71) {
			if(player1.getY() == player2.getY()+playerSize) {
				
			}else {
				player1.moveUp();
				sendeDatenSpieler1();
				jumpHeight1++;
				
				if(jumpHeight1 == 70) {
					jump1 = false;
				}
			}
		}else if(jump1 == false && jumpHeight1 > 0) {
			if((player1.getY()+playerSize == player2.getY()) && ((player1.getX() <= player2.getX() && player2.getX() <= player1.getX()+playerSize) || ((player1.getX() <= player2.getX()+playerSize && player2.getX() <= player1.getX()+playerSize)))) {
				
			}else {
				player1.moveDown();
				sendeDatenSpieler1();
				jumpHeight1--;
			}
		}
		
		
		if(jump2 && jumpHeight2 < 71) {
			if(player2.getY() == player1.getY()+playerSize) {
				
			}else {
				player2.moveUp();
				sendeDatenSpieler2();
				jumpHeight2++;
				
				if(jumpHeight2 == 70) {
					jump2 = false;
				}
			}
		}else if(jump2 == false && jumpHeight2 > 0) {
			if((player2.getY()+playerSize == player1.getY()) && ((player2.getX() <= player1.getX() && player1.getX() <= player2.getX()+playerSize) || ((player2.getX() <= player1.getX()+playerSize && player1.getX() <= player2.getX()+playerSize)))) {
				
			}else {
				player2.moveDown();
				sendeDatenSpieler2();
				jumpHeight2--;
			}
		}
		
		
		/*
		Checkt, ob ein Spieler auf der Oberseite eines anderen Landen konnte.
		Hier koennte man auch pruefen, ob ein Spieler an der Seite der Plattform runtergefallen ist. Dies ist bisher noch ausgeschaltet.
		Zudem wird nur auf der Console eine Info ausgegeben, wenn der Fall eingetroffen ist.
		Man koennte in so einem Fall eine Art Respawn verannlassen und einem Spieler ein Leben abziehen.
		 */
		if((player1.getY()+playerSize == player2.getY()) && ((player1.getX() <= player2.getX() && player2.getX() <= player1.getX()+playerSize) || ((player1.getX() <= player2.getX()+playerSize && player2.getX() <= player1.getX()+playerSize)))) {
			System.out.println("Spieler 1 hat gewonnen!");
		}
		if((player2.getY()+playerSize == player1.getY()) && ((player2.getX() <= player1.getX() && player1.getX() <= player2.getX()+playerSize) || ((player2.getX() <= player1.getX()+playerSize && player1.getX() <= player2.getX()+playerSize)))) {
			System.out.println("Spieler 2 hat gewonnen!");
		}
	}
	
	public void clearScreen() {
		frameWork.removeMessage(headline);
		frameWork.removeMessage(headline2);
		frameWork.removeMessage(headline3);
		frameWork.removeMessage(spielerName1);
		frameWork.removeMessage(spielerName2);
		frameWork.removeMessage(timerMessage);
	}
}
