package cs;

import java.util.Timer;
import java.util.TimerTask;

public class RunClient implements Runnable {

	private Client client = null;
	Game game = null;

	public RunClient(Client client, Game game) {
		this.client = client;
		this.game = game;
	}

	public void run() {
		Object o;
		client.sendObject(game);
		while (true) {
			o = client.getNextObject();
			if (o instanceof Game) {
				this.game = (Game) o;
				client.sendObject(game);
			}
		}
	}
}
