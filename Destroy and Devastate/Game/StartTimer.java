package Game;

import views.GameFrameWork;
import views.Message;

public class StartTimer extends java.util.TimerTask {

	private GameFrameWork frameWork;
	private int counter = 3;
	private Message message;

	public StartTimer(GameFrameWork frameWork, Message message) {
		this.frameWork = frameWork;
		this.message = message;
		
	}

	@Override
	public void run() {
		frameWork.removeMessage(message);
		message.setMessage("Spiel startet in: " + counter);
		frameWork.addMessage(message);
		counter--;
	}
}
